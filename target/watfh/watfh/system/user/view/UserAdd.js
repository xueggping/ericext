Ext.define('user.view.UserAdd', {
	extend : 'Ext.window.Window',
	alias : 'widget.UserAdd',
	id : 'userAddWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 380,
	width : 300,
	layout : 'column',
	title : '添加用户',
	items : [ {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '20 5 5 20',
		actionName : 'userName',
		actionGroup : 'userAdd',
		allowBlank: false,
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '登录名'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'userAdd',
		allowBlank: false,
		maxLength: 40,
		fieldLabel : '姓名'
	}, {
		xtype : 'textfield',
		inputType : 'password',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'password',
		actionGroup : 'userAdd',
		allowBlank: false,
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '密码'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'phoneNumber',
		actionGroup : 'userAdd',
		maxLength: 40,
		fieldLabel : '联系电话'
	}, {
		xtype : 'textfield',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		actionName : 'email',
		actionGroup : 'userAdd',
		vtype:'email', 
		maxLength: 40,
		fieldLabel : '邮箱'
	}, {
		xtype : 'textareafield',
		width : 240,
		labelWidth : 80,
		height : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'userAdd',
		maxLength: 80,
		fieldLabel : '备注'
	}, {
		xtype : 'radiogroup',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		fieldLabel : '当前状态',
		actionName : 'isEnabled',
		actionGroup : 'userAdd',
		allowBlank: false,
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