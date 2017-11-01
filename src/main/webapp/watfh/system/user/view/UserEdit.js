Ext.define('user.view.UserEdit', {
	extend : 'Ext.window.Window',
	alias : 'widget.UserEdit',
	id : 'userEditWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 380,
	width : 300,
	layout : 'column',
	title : '编辑用户',
	items : [ {
		xtype : 'displayfield',
		width : 240,
		labelWidth : 80,
		margin : '20 5 5 20',
		actionName : 'userName',
		actionGroup : 'userEdit',
		fieldLabel : '登陆名'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'userEdit',
		allowBlank: false,
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '姓名'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'phoneNumber',
		actionGroup : 'userEdit',
		fieldLabel : '联系电话'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'email',
		actionGroup : 'userEdit',
		vtype:'email', 
		fieldLabel : '邮箱'
	}, {
		xtype : 'textareafield',
		width : 240,
		labelWidth : 80,
		height : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'userEdit',
		maxLength: 80,
		fieldLabel : '备注'
	}, {
		xtype : 'radiogroup',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		fieldLabel : '当前状态',
		actionName : 'isEnabled',
		actionGroup : 'userEdit',
		items : [ {
			xtype : 'radiofield',
			checked : true,
			id : 'male',
			name : 'isEnabled',
			inputValue : 1,
			boxLabel : '启用'
		}, {
			xtype : 'radiofield',
			id : 'feMale',
			name : 'isEnabled',
			inputValue : 0,
			boxLabel : '禁用'
		} ]
	} ],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners : {
				click : 'editSave'
			},
			icon: getRootPath() + '/image/icon/table_save.png',
			text : '保存'
		}, {
			xtype : 'button',
			text : '关闭',
			icon: getRootPath() + '/image/icon/cancel.png',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});