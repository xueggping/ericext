var colors = ['#2f7ed8','#0d233a','#8bbc21','#910000','#1aadce','#492970','#f28f43','#77a1e5','#c42525','#a6c96a','#4572A7','#AA4643', 
		   '#89A54E','#80699B','#3D96AE','#DB843D','#92A8CD','#A47D7C','#B5CA92','#93E7FB','red'];
 Highcharts.setOptions({
    lang: {
       　		  printChart:"打印图表",
          downloadJPEG: "下载JPEG 图片" , 
          downloadPDF: "下载PDF文档"  ,
          downloadPNG: "下载PNG 图片"  ,
          downloadSVG: "下载SVG 矢量图" , 
          exportButtonTitle: "导出图片" 
    }
});
var chartX_N=null,chartX_Y=null,chartX_R=null,chartY=null,chartT=null;
Ext.define("common.view.commonChart",{ 
	statics: { 
		setChartXY:function(o){
			if(o.chartX_N){
				chartX_N = o.dataIndex;
			}
			if(o.chartX_Y){
				chartX_Y = o.dataIndex;
			}
			if(o.chartX_R){
				chartX_R = o.dataIndex;
			}
			if(o.chartY){
				chartY = o.dataIndex;
			}
		},
		createChart:function(p){
			if(chartT){
				chartT.destroy(); //销毁chart对象
			}
			//根据选择的测站追加线数
			var series = new Array();
			for(var i=0;i<p.stcds.length;i++){
				series.push({
	        		name: p.stcds[i],
	        		stnm: p.stnms[i],
	        		data: new Array(),
	        		index:i,
            		color: colors[i%20],
		            type: p.panel.chartType || 'spline',
        		});
			}
			var width = $('#chartPanel'+ module).width(); //宽
			var height = $('#chartPanel'+ module).height(); //高
			$('#chartPanel'+ module).highcharts('StockChart', {	
		        rangeSelector: {
					enabled : false
				},
				title: {
					text:'<b>'+p.panel.chartTile+'</b>'
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
	                        return Highcharts.dateFormat(common.view.commonChart.getLabelFormat(), this.value);
	                    }
	                },
	                title: {
		                align: 'high',
		                offset: 0,
//		                text: '单位:'+'<b>'+p.panel.Xunit+'</b>',
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
		                text: '单位:'+'<b>'+p.panel.Xunit+'</b>',
		                rotation: 0,
		                y:-10,
		                x:-5
	            	},
				},
				tooltip: {  //控制point时间格式
						split: false, //控制点提示框中显示时间
						formatter: function() {
	                            var s = '<span style="color:red;">时间:'+Highcharts.dateFormat(common.view.commonChart.getLabelFormat(),this.x)+'</span></b><br/>';
	                            $.each(this.points, function(i, point) {
	                                s += '<span style="color: '+point.series.color+';">'+point.series.options.stnm+'('+point.series.name +'): '+point.y.toFixed(3)+'</span><br/>';
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
		draw:function(data){
			if((chartX_N || chartX_Y || chartX_R) && chartY){
				Ext.Array.each(data.content,function(j, index, items){
					Ext.Array.each(chartT.series,function(s, index, ss){
						if(j.STCD == s.name){
							s.addPoint([(Ext.Date.parse(common.view.commonChart.getXtime(j).time, common.view.commonChart.getXtime(j).format, false)).getTime(), j[chartY]],false);
						}
					});
				});
			}
			chartT.redraw();
		},
		getXtime:function(j){
			var cx = new Object();
			if(chartX_R){
				cx.time = j[chartX_R];
				cx.format = "Y-m-d H:i:s"
				cx.labFormat = "%Y-%m-%d";
				return cx;
			}
			if(chartX_Y){
				cx.time = j[chartX_N] + "-" + (j[chartX_Y]<10?'0'+j[chartX_Y]:j[chartX_Y]);
				cx.format = "Y-m"
				cx.labFormat = "%Y-%m-%d";
				return cx;
			}
			if(chartX_N){
				cx.time = j[chartX_N];
				cx.format = "Y"
				return cx;
			}
		},
		getLabelFormat:function(){
			if(chartX_R){
				return "%Y-%m-%d";
			}
			if(chartX_Y){
				return "%Y-%m";
			}
			if(chartX_N){
				return "%Y";
			}
		}
	
		
    }  
});  