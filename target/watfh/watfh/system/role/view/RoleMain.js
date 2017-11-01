Ext.define('role.view.RoleMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.RoleMain',
	id : 'roleMain',
	controller: 'main',
	layout : {
		type : 'border'
	},
	border : false,
		
	items : [ {
		xtype : 'panel',
		region : 'north',
		border : false,
		layout : {
			type : 'column'
		},
		items : [ 
		{
			xtype : 'textfield',
			margin : '5 5 5 5',
			labelWidth : 60,
			width : 210,
			actionName : 'name',
			actionGroup : 'roleFind',
			fieldLabel : '角色名称'
		}, {
			xtype : 'button',
			margin : '5 5 5 5',
			text : '查询',
			icon: getRootPath() + '/image/icon/magnifier.png',
			listeners : {
				click : 'find'
			}
		}, {
			xtype : 'button',
			margin : '5 5 5 5',
			listeners : {
				click : 'reset'
			},
			icon: getRootPath() + '/image/icon/restart.png',
			text : '重置'
		} ]
	}, {
		xtype : 'gridpanel',
		border : false,
		region : 'center',
		store: {
	        type: 'RoleMain'
	    },
	    id : 'roleGridPanel',
		enableColumnHide : false,///隐藏列
		sortableColumns : false,///隐藏排序
		columnLines : true,//列间线
		selModel: {
			selType: 'checkboxmodel'
	    },
		viewConfig : {
			stripeRows: true,//条纹行
			enableTextSelection : true//文本可选
		},
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store: {
		        type: 'RoleMain'
		    },
			displayInfo : true,
			dock : 'bottom'
		}, 
		{
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'button',
				listeners: {
		            click: 'add'
		        },
		        icon: getRootPath() + '/image/icon/add.png',
				text : '添加'
			}, '-', {
				xtype : 'button',
				listeners: {
		            click: 'edit'
		        },
		        icon: getRootPath() + '/image/icon/edit.png',
				text : '编辑'
			}, '-', {
				xtype : 'button',
				listeners: {
		            click: 'del'
		        },
		        icon: getRootPath() + '/image/icon/delete.png',
				text : '删除'
			}, '-', {
				xtype : 'button',
				listeners: {
		            click: 'authorization'
		        },
		        icon: getRootPath() + '/image/icon/cog_edit.png',
				text : '授权'
			} ]
		} ],				
		columns : [  {
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
			xtype : 'gridcolumn',
			hidden : true,
			width : 80,
			dataIndex : 'CODE',
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'NAME',
			width : 200,
			text : '角色名称'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'NOTE',
			flex : 1,
			text : '角色描述'
		} ]
	} ]
});
