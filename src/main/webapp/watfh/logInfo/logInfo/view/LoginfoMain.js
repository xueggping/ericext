/**
 * 日志查看view
 */
Ext.define('logInfo.view.LoginfoMain',{
	extend:'Ext.panel.Panel',
	alias:'widget.LoginfoMain',
	id:'loginfoMain',
	controller:'logMain',
	layout:{
		type : 'border'
	},
	border:false,
	items:[{
		xtype:'panel',
		region:'north',
		border:false,
		layout:{
			type:'column'
		},
		items:[
		{
	        xtype: 'label',
	        forId: 'startDate',
	        text: '开始时间:',
	        margin: '9 5 5 5'
    	},
    	{  
			xtype: 'textfield',
			margin: '5 5 5 5',
			labelWidth: 60,
			border:false,
			value: Ext.Date.format(new Date().addMonth( - 1), 'Y-m-d H:i:s'),
			id: 'startDate', 
			name: 'startDate',
			actionName: 'sLogTimeFind',
			actionGroup : 'logFind',
			readOnly:true,
			listeners:{  
				render:function(p){  
					p.getEl().on('click',function(){  
						WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn',maxDate:Ext.getCmp('endDate').getValue()});  
					});  
				}
			}  
		},
		{
	        xtype: 'label',
	        forId: 'endDate',
	        text: '结束时间:',
	        margin: '9 5 5 5'
    	},
		{  
			id: 'endDate',
			xtype: 'textfield',
			margin: '5 5 5 5',
			labelWidth: 60,
			border:false,
			value: Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
			readOnly:true,
			name: 'endDate',
			actionName: 'eLogTimeFind',
			actionGroup : 'logFind',
			listeners:{  
			render:function(p){  
				p.getEl().on('click',function(){  
					WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn',minDate:Ext.getCmp('startDate').getValue()});  
				});  
			}}  
		},
		{
			xtype:'textfield',
			margin:'5 5 5 5',
			actionName:'callerIP',
			actionGroup:'logFind',
			labelWidth:80,
			width:260,
			fieldLabel:'操作IP'
		},{
			xtype:'textfield',
			margin:'5 5 5 5',
			actionName:'userName',
			actionGroup:'logFind',
			labelWidth:80,
			width:260,
			fieldLabel:'操作人员'
		},{
			xtype : 'combobox',
			margin : '5 5 5 5',
			labelWidth : 80,
			width : 210,
			store : [ [ '1', '成功' ], [ '0', '异常' ] ],
			editable : false,
			actionName : 'actionFlag',
			actionGroup : 'logFind',
			fieldLabel : '是否成功'
		},{
			xtype:'button',
			margin:'5 5 5 5',
			listeners:{click:'queryWithCondition'},
			icon: getRootPath() + '/image/icon/magnifier.png',
			text:'查询'
		},{
			xtype:'button',
			margin:'5 5 5 5',
			listeners:{click:'reset'},
			icon: getRootPath() + '/image/icon/restart.png',
			text:'重置'
		}]
	},{
		xtype:'gridpanel',
		region:'center',
		border:'false',
		id:'loginfoGridpanel',
		store:{
			type : 'loginfoMain'
		},
		enableColumnHide:false,
		sortableColumns:false,
		columnLines:true,
//		selModel:{
//			selType:'checkboxmodel'
//		},
		viewConfig:{
			stripeRows:true,
			enableTextSelection:true
		},
		dockedItems:[{
			id:'loginfoGridpanelPagingtoolbar',
			xtype:'pagingtoolbar',
			displayInfo:true,
			dock:'bottom'
		}],
		columns:[{
			xtype:'gridcolumn',
			align:'center',
			dataIndex:'id',
			width:150,
			hidden:true,
			text:'ID'
		},{
			xtype: 'rownumberer',
			text: '',
			align : 'left',
			width:'2%',
			renderer:function(value,metadata,record,rowIndex){
				if(rowIndex > GLOBLE_PAGESIZE){
					rowIndex = 0;
				}
			　　　return　rowIndex + 1;
			}
		},{
			xtype:'gridcolumn',
			align:'left',
			dataIndex:'funName',
			width:'25.7%',
			text:'模块名称',
			renderer:function(val){
				if(val && val != '' && val != null){
					return '<div title="'+val+'">'+val+'</div>';
				}else{
					return val;
				}
			}
		},{
			xtype:'gridcolumn',
			align:'left',
			dataIndex:'userName',
			width:'8%',
			text:'操作人'
		},{
			xtype:'gridcolumn',
			align:'left',
			dataIndex:'callerIP',
			width:'10%',
			text:'IP'
		},{
			xtype:'gridcolumn',
			align:'center',
			dataIndex:'sTime',
			width:'12%',
			text:'访问开始时间'
		},{
			xtype:'gridcolumn',
			align:'center',
			dataIndex:'eTime',
			width:'12%',
			text:'访问结束时间'
		},{
			xtype:'gridcolumn',
			align:'left',
			dataIndex:'actionTime',
			width:'8%',
			text:'用时毫秒数'
		},{
			xtype:'gridcolumn',
			align:'center',
			dataIndex:'actionFlag',
			width:'8%',
			text:'是否异常',
			renderer:function(val){
				if(val && val == '1'){
					return '<div style="background-color: #358bc9;width: 50px;margin: auto;color: #fff;border-radius: 5px;">成功</div>';
				}else if(val && val == '0'){
					return '<div style="background-color: #cc5454;width: 50px;margin: auto;color: #fff;border-radius: 5px;">异常</div>';
				}else{
					return '';
				}
			}
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'erroInfo',
			text : '操作',
			width:'12%',
			renderer : function(record,value){
				if(record){
					return '<span style="font-family:Microsoft YaHei;color:#407434;cursor:pointer;">查看</span>';
				}else{
					return '无异常信息';
				}
			},
			listeners:{   
				 click : 'showerror',
			}   
		}]
	}]
});
