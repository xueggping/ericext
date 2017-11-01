var MainController;
var chartT,chartX,chartY;
Ext.define('zhdzcx.controller.zhdzcxController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 
			'zhdzcx.view.zhdzcxRainfallCompare',
			'zhdzcx.view.zhdzcxRiverfallCompare',
			'zhdzcx.view.zhdzcxFlowCompare',
			'zhdzcx.view.zhdzcxZflCompare',
			'zhdzcx.view.zhdzcxTempCompare',
			'zhdzcx.view.zhdzcxHslCompare',
			'zhdzcx.view.zhdzcxSslCompare',
			'zhdzcx.view.zhdzcxMain',
			'zhdzcx.view.zhdzcxTimeWin',
			'zhdzcx.store.rainfallStore',
			'Ext.ux.ZTreeComboBox'
		],
	init : function() {
		MainController = this;
	},
	addTime:function(bt){
		var win = Ext.widget('zhdzcxTimeWin');
		win.showAt(700,320);
	},
	addTimeSave:function(bt){
		var start = Ext.getCmp('startDate')
		var end = Ext.getCmp('endDate')
		var tv = Ext.getCmp('times');
		if(!start.validate() || !end.validate()){
			Ext.Msg.alert("提示信息", '对照时间不正确！');
		}else{
			if(tv.text == ''){
				tv.setText(start.getValue() + ' - ' + end.getValue()); 
			}else{
				tv.setText(tv.text + "," + start.getValue() + ' - ' + end.getValue())
			}
			if(tv.hidden){
				tv.show();
			}
			bt.ownerCt.ownerCt.close();
		}
	},
	find:function(bt){
		var tab = Ext.getCmp('tab');
		var type = tab.getActiveTab().type;
		var grid = tab.getActiveTab().items.items[0];
		var store = grid.getStore();
		if(Ext.isEmpty(Ext.getCmp('stcd').getValue()) || Ext.isEmpty(Ext.getCmp('times').text)){
			Ext.Msg.alert('提示信息', "测站和对照时间段不能为空！");
		}
		store.proxy.extraParams = {
			type : type,
			stcd : Ext.getCmp('stcd').getValue(),
			times : Ext.getCmp('times').text
		};
		store.reload({scope: this,
		    callback: function(records, operation, success) {
		    	 //加载图表
				 MainController.createChart(tab.getActiveTab());
				 var times = Ext.getCmp('times').text.split(',');
				 for(var k = 0;k < times.length; k++){
					 var PT = new Object();
					 PT.type = type;
					 PT.stcd = Ext.getCmp('stcd').getValue();
					 PT.times = times[k];
					 var totalCount = MainController.getDatacount(PT);
			         var pageCount = Math.ceil(totalCount / 10000)
					 for(var i = 1;i <= pageCount;i++){
					 	 PT.start =((i - 1) * 10000) + 1;
			             PT.limit = 10000;
				    	 Ext.Ajax.request( {
							url : store.getProxy().url,
							method : 'POST',
							params : PT,
							success : function(response, opts) {
								var result = Ext.JSON.decode(response.responseText);
								//开始绘图
								MainController.drawChart(tab.getActiveTab(),{tp:PT.times,data:result});
							},
							failure : function(response, opts) {
								Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
							}
						});
					  }
				 }
				
		    }
		});
		
	},
	tabchange:function( tabPanel, newCard, oldCard, eOpts ){
		MainController.find();
	},
	createChart:function(p){
		if(chartT){
			chartT.destroy(); //销毁chart对象
		}
		//根据选择的测站追加线数
		var series = new Array();
		var times = Ext.getCmp('times').text.split(",");
		for(var i=0;i<times.length;i++){
			series.push({
        		name: times[i],
        		data: new Array(),
        		index:i,
	            type: p.chartType || 'spline',
    		});
		}
		var width = $('#chartPanel'+ p.type).width(); //宽
		var height = $('#chartPanel'+ p.type).height(); //高
		$('#chartPanel'+ p.type).highcharts('StockChart', {	
	        rangeSelector: {
				enabled : false
			},
			title: {
				text:'<b>'+p.title+'</b>'
			},
			credits : {
				enabled : false
			},
			chart : {
				width : width,
				height : height
			},
			xAxis: {
				gridLineColor: '#197F07',//横向网格线颜色
				gridLineDashStyle: 'solid',//横向网格线样式
				gridLineWidth: 1,//横向网格线宽度
            	crosshair : true,
		        minorGridLineWidth: 0,
		        minorTickInterval: 'auto',
		        minorTickLength: 4,//轴线刻度长度
		        minorTickWidth:1,
		        lineWidth:3,
                labels: {//控制X轴时间格式
                    formatter: function() {
                        return Highcharts.dateFormat("%Y-%m-%d", this.value);
                    }
                },
                title: {
	                align: 'high',
	                offset: 0,
	                rotation: 0,
	                y:15,
	                x:5
            	},
            },
			yAxis: {
            	gridLineColor: '#197F07',//横向网格线颜色
				gridLineDashStyle: 'solid',//横向网格线样式
				gridLineWidth: 1,//横向网格线宽度
				lineWidth : 3,
				crosshair : true,
            	opposite:false,//数据点十字准星线 只用给Y轴加
            	alternateGridColor:'#FDFFD5',//间隔网格背景
				plotLines: [{
					value: 0,
					width: 1, //x轴线宽度
					color: 'silver'
				}],
				minorGridLineColor: '#F0F0F0',
		        minorGridLineWidth: 5, //Y轴轴线间隔
		        sminorTickLength: 4,//轴线刻度长度
		        minorTickInterval: 'auto',
				title: {
	                align: 'high',
	                offset: 0,
	                text: '单位:'+'<b>'+p.Xunit+'</b>',
	                rotation: 0,
	                y:-10,
	                x:-5
            	},
			},
			tooltip: {  //控制point时间格式
					split: false, //控制点提示框中显示时间
					formatter: function() {
                            var s = '';
                            $.each(this.points, function(i, point) {
                            	s+='<span style="color: '+point.series.color+';">时间:'+Highcharts.dateFormat("%Y-%m-%d",point.x)+'</span></b><br/>';
                                s += '<span style="color: '+point.series.color+';">时间段('+point.series.name +'): '+point.y.toFixed(3)+'</span><br/>';
                            });
                            return s;
                        }, 
			},
			navigator: { //拖动框时间格式化
			    xAxis: {
			        labels: {
			            format: '{value:%Y-%m-%d %H:%M:%S}'  
			        }
			    }
			},
			series:series
		},function(c){
			chartT = c;
		});
	},
	drawChart:function(p,d){
		if(p.chartX && p.chartY){
			Ext.Array.each(chartT.series,function(s, index, ss){
				if(d.tp == s.name){
					Ext.Array.each(d.data.content,function(j, index, items){
						s.addPoint([(Ext.Date.parse(j[p.chartX], "Y-m-d H:i:s", false)).getTime(), j[p.chartY]],false);
					});
				}
			});
		}
		chartT.redraw();
	},
	getDatacount:function(tp){
		var count;
		Ext.Ajax.request( {
			url : getRootPath() + '/zhdzcxController/getDataCount.do',
			method : 'POST',
			async :false,
			params :tp,
			success : function(response, opts) {
				count =  response.responseText;
			},
			failure : function(response, opts) {
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
		return Number(count);
	},
	reset:function(bt){
		var tv = Ext.getCmp('times');
		var stcd = Ext.getCmp('stcd');
		tv.setText('');
		tv.hide();
		stcd.setValue('');
	},
	close:function(bt, e, eOpts){
		bt.ownerCt.ownerCt.close();
	}
}
);