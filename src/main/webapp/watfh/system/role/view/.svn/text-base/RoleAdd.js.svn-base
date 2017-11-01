Ext.define('role.view.RoleAdd', {
	extend : 'Ext.window.Window',
	alias : 'widget.RoleAdd',
	id : 'roleAddWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 205,
	width : 300,
	layout : 'column',
	title : '添加角色',
	items : [ {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '15 5 5 20',
		actionName : 'name',
		actionGroup : 'roleAdd',
		maxLength : 40,
		allowBlank : false,
		fieldLabel : '角色名称'
	}, {
		xtype : 'textareafield',
		width : 240,
		labelWidth : 80,
		height : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'roleAdd',
		maxLength : 250,
		fieldLabel : '角色描述'
	} ],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners : {
				click : 'addSave'
			},
			icon: getRootPath() + '/image/icon/table_save.png',
			text : '保存'
		}, {
			xtype : 'button',
			icon: getRootPath() + '/image/icon/cancel.png',
			text : '关闭',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});