Ext.define('user.view.UserMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.UserMain',
	id : 'userMain',
	controller : 'main',
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
		items : [ {
			xtype : 'textfield',
			margin : '5 5 5 5',
			labelWidth : 60,
			width : 210,
			actionName : 'userName',
			actionGroup : 'userFind',
			fieldLabel : '登陆名'
		}, {
			xtype : 'textfield',
			margin : '5 5 5 5',
			labelWidth : 60,
			width : 210,
			actionName : 'name',
			actionGroup : 'userFind',
			fieldLabel : '姓名'
		}, {
			xtype : 'combobox',
			margin : '5 5 5 5',
			labelWidth : 60,
			width : 210,
			store : [ [ '1', '启用' ], [ '0', '禁用' ], [ '', '所有' ] ],
			editable : false,
			actionName : 'isEnabled',
			actionGroup : 'userFind',
			fieldLabel : '当前状态'
		}, {
			xtype : 'combobox',
			margin : '5 5 5 5',
			labelWidth : 60,
			width : 210,
			store : [ [ '1', '正常' ], [ '0', '已删除' ], [ '', '所有' ] ],
			value : 1,
			editable : false,
			actionName : 'state',
			actionGroup : 'userFind',
			fieldLabel : '删除状态'
		}, {
			xtype : 'button',
			margin : '5 5 5 5',
			listeners : {
				click : 'find'
			},
			icon: getRootPath() + '/image/icon/magnifier.png',
			text : '查询'
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
		id : 'userGridPanel',
		store : {
			type : 'UserMain'
		},
		enableColumnHide : false,///隐藏列
		sortableColumns : false,///隐藏排序
		columnLines : true,//列间线
		selModel : {
			selType : 'checkboxmodel'
		},
		viewConfig : {
			stripeRows : true,//条纹行
			enableTextSelection : true
		//文本可选
		},
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			displayInfo : true,
			dock : 'bottom'
		}, {
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'button',
				listeners : {
					click : 'add'
				},
				icon: getRootPath() + '/image/icon/add.png',
				text : '添加'
			}, '-', {
				xtype : 'button',
				listeners : {
					click : 'edit'
				},
				icon: getRootPath() + '/image/icon/edit.png',
				text : '编辑'
			}, '-', {
				xtype : 'button',
				listeners : {
					click : 'del'
				},
				icon: getRootPath() + '/image/icon/delete.png',
				text : '删除'
			}, '-', {
				xtype : 'button',
				listeners : {
					click : 'resetPassword'
				},
				icon: getRootPath() + '/image/icon/action_refresh_blue.gif',
				text : '重置密码'
			}, '-', {
				xtype : 'button',
				listeners : {
					click : 'grantRole'
				},
				icon: getRootPath() + '/image/icon/cog_edit.png',
				text : '分配角色'
			} ]
		} ],
		columns : [ {
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
			align : 'left',
			dataIndex : 'USERNAME',
			width : 150,
			text : '登陆名'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'NAME',
			width : 150,
			text : '姓名'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'PHONE_NUMBER',
			width : 150,
			text : '联系电话'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'EMAIL',
			width : 150,
			text : '邮箱'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'NOTE',
			flex : 1,
			text : '备注'
		}, {
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'IS_ENABLED',
			width : 80,
			renderer : function(val, record) {
				if (val == "1") {
					return "启用"
				}
				if (val == "0") {
					return "禁用"
				}
				return "状态异常";
			},
			text : '当前状态'
		}, {
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'LAST_UPDATE_TIME',
			width : 150,
			text : '最后更新时间'
		}, {
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'STATE',
			width : 80,
			renderer : function(val, record) {
				if (val == "1") {
					return "正常"
				}
				if (val == "0") {
					return "已删除"
				}
				return "状态异常";
			},
			text : '删除状态'
		} ]
	} ]
});
