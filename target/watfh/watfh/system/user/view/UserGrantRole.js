Ext.define('user.view.UserGrantRole', {
	extend : 'Ext.window.Window',
	alias : 'widget.UserGrantRole',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 340,
	width : 435,
	layout : 'column',
	title : '分配角色',
	id : 'userGrantRoleWindow',
	items : [  {
		xtype : 'displayfield',
		hidden : true,
		actionName : 'userName',
		actionGroup : 'userGrantRole'
	},{
		xtype : 'displayfield',
		width : 240,
		labelWidth : 40,
		margin : '10 5 0 20',
		actionName : 'name',
		actionGroup : 'userGrantRole',
		fieldLabel : '姓名'
	}, {
		xtype : 'itemselector',
		width : 380,
		labelWidth : 40,
		height : 200,
		margin : '5 0 5 20',
		actionName : 'role',
		actionGroup : 'userGrantRole',
		fieldLabel : '角色',
		store: {
	        type: 'UserGrantRole'
	    },
		displayField : 'NAME',
		valueField : 'CODE',
		fromTitle : '可选',
		toTitle : '已选',
		buttons : [ 'add', 'remove' ],
		buttonsText : {
			add : "添加选择项",
			remove : "删除选择项"
		}
	}],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners: {
	            click: 'userGrantRoleSave'
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