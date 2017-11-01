Ext.define('role.view.RoleAuthorization', {
	extend : 'Ext.window.Window',
	alias : 'widget.RoleAuthorization',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : '100%',
	width : 680,
	layout : 'column',
	title : '分配角色',
	id : 'roleAuthorizationWindow',
	html:'<div id="authorizationFunzTree" class="ztree" style="margin:0;overflow-y:auto;overflow-x:auto;height:100%;width:100%;"></div>',
	listeners: {
        afterrender : 'initAauthorizationFunzTree'
    },
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners: {
	            click: 'roleAuthorizationSave'
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