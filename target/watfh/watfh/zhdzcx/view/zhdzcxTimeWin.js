var startMess = true;
var endMess = true;
Ext.define('zhdzcx.view.zhdzcxTimeWin', {
	extend : 'Ext.window.Window',
	id : 'zhdzcxTimeWin',
	alias : 'widget.zhdzcxTimeWin',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 190,
	width : 270,
	title:'添加对照时间',
	layout : 'column',
	items : [
		{
	        xtype: 'textfield',
	        id:'startDate',
	        name: 'startDate',
	        fieldLabel: '开始时间',
	        width:240,
	        labelWidth:60,
	        margin:'20 5 10 5',
	        allowBlank: false,
	        listeners:{
		        render:function(p){
					p.getEl().on('click',function(){  
						WdatePicker({dateFmt:p.format||'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'}); 
					});
		        }
	        },
	        validator: function (val) {
				var endDate = Ext.getCmp('endDate');
		        var value = endDate.getValue();
		        if((value != "" && val >= value)){
		        	startMess = "起始年份不能大于终止年份";
		        }else{
		            startMess = true;
		        	if(endMess != true){
				 		endDate.validate();
		        	}
		        }
				return startMess;
		   }
	    }, {
	        xtype: 'textfield',
	        id:'endDate',
	        name: 'endDate',
	        fieldLabel: '结束时间',
	        margin:'10 5 20 5',
	        width:240,
	        labelWidth:60,
	        allowBlank: false,
	        listeners:{
		        render:function(p){
					p.getEl().on('click',function(){  
						WdatePicker({dateFmt:p.format||'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'}); 
					});
		        }
	        },
	        validator: function (val) {
	    		var startDate = Ext.getCmp('startDate');
		        var value = startDate.getValue();
		        if((value != "" && val <= value)){
		        	endMess = "终止年份不能小于起始年份";
		        }else{
		            endMess = true;
		        	if(startMess != true){
			        	 startDate.validate();
		        	}
		        }
	       		 return endMess;
	     	}
	    }
	],
	buttons : [ {
		xtype : 'button',
		listeners: {
            click: 'addTimeSave'
        },
		text : '确认'
	}, {
		xtype : 'button',
		text : '关闭',
		listeners: {
            click: 'close'
        },
	} ]
});
