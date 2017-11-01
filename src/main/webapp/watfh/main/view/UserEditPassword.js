Ext.define('main.view.UserEditPassword', {
	extend : 'Ext.window.Window',
	alias : 'widget.UserEditPassword',
	id : 'userEditPasswordWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 210,
	width : 300,
	layout : 'column',
	title : '修改密码',
	items : [ {
		xtype : 'textfield',
		inputType : 'password',
		width : 240,
		labelWidth : 80,
		margin : '20 5 5 20',
		actionName : 'oldPassword',
		actionGroup : 'userEditPassword',
		allowBlank : false,
		maxLength : 40,
		regex : /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '旧密码'
	}, {
		xtype : 'textfield',
		inputType : 'password',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		id : 'password',
		actionName : 'password',
		actionGroup : 'userEditPassword',
		allowBlank : false,
		maxLength : 40,
		validator : function(value,field) {
			var repeatPassword = Ext.getCmp("repeatPassword");
			if(repeatPassword.getValue()!=value){
				return '两次输入的密码不一致!'
			}
			repeatPassword.setActiveError(null); 
			return true;
		},
		regex : /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '新密码'
	}, {
		xtype : 'textfield',
		inputType : 'password',
		width : 240,
		labelWidth : 80,
		margin : '5 5 5 20',
		id : 'repeatPassword',
		actionName : 'repeatPassword',
		actionGroup : 'userEditPassword',
		allowBlank : false,
		maxLength : 40,
		validator : function(value) {
			var password = Ext.getCmp("password");
			if(password.getValue()!=value){
				return '两次输入的密码不一致!'
			}
			password.setActiveError(null); 
			return true;
		},
		regex : /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '确认密码'
	} ],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners : {
				click : 'editPasswordSave'
			},
			text : '确定'
		}, {
			xtype : 'button',
			text : '关闭',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});