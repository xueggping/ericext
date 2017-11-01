Ext.define('zhdzcx.view.zhdzcxMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.zhdzcxMain',
	id : 'zhdzcxMain',
	controller : 'main',
	layout : {
		type : 'border'
	},
	border : false,

	items : [ 
	{
		xtype : 'panel',
		region : 'north',
		border : false,
		layout : {
			type : 'column'
		},
		items : [
		{            
			xtype: 'ztreecombox',            
			margin: '5,5,5,5',            
			labelWidth : 60,            
			width: 250,            
			isStcd:true,	
			id:'stcd',
			actionName : 'STCD',	
			actionGroup:'search',
			ztreeConfig:{	        	
				url:getRootPath() + '/charts/getStationTree.do',	        	
				root:{		        	
					idKey: "id",					
					pIdKey: "PCODE",        			
					rootPId:'root'	        	
				},	        	
				expandAll:true,	        	
				chkboxType: { "Y": "", "N": "" }        	
			},            
			fieldLabel: '选择测站'        
		},{
	        xtype: 'label',
	        id: 'times',
	        actionName:'times',
	        actionGroup:'search',
	        hidden:true,
	        text: '',
	        style: {
				fontSize:'16px',
				color:'red'	
	        },
	        margin: '10 5 5 5'
		},{
			xtype : 'button',
			icon: getRootPath() + '/image/icon/add.png',
			margin : '5 5 5 5',
			listeners : {
				click : 'addTime'
			},
			text : '对照时间段'
		},
		{
			xtype : 'button',
			icon: getRootPath() + '/image/icon/magnifier.png',
			margin : '5 5 5 5',
			listeners : {
				click : 'find'
			},
			text : '查询'
		}, {
			xtype : 'button',
			icon: getRootPath() + '/image/icon/restart.png',
			margin : '5 5 5 5',
			listeners : {
				click : 'reset'
			},
			text : '重置'
		} ]
	},{
		xtype: 'tabpanel',
		region : 'center',
		id:'tab',
		border : false,
		items:[
			{ xtype:'zhdzcxRainfallCompare'},
	        { xtype:'zhdzcxRiverfallCompare'},
	        { xtype:'zhdzcxFlowCompare'},
	        { xtype:'zhdzcxZflCompare'},
	        { xtype:'zhdzcxTempCompare'},
	        { xtype:'zhdzcxHslCompare'},
	        { xtype:'zhdzcxSslCompare'}
		],
		listeners:{
			tabchange:'tabchange'
		}
	}]
});
