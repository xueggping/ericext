Ext.define('logInfo.view.LogError', {
	extend : 'Ext.window.Window',
	alias : 'widget.LogError',
	id : 'LogErrorWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 285,
	width : 555,
	layout : 'column',
	title : '错误信息',
	items : [ {
		xtype : 'textarea',
		width : 505,
		height : 170,
		labelWidth : 60,
		margin : '15 5 5 20',
		readOnly:true,
		id : 'errorinfo',
	}],
	initComponent : function() {
		this.buttons = [{
			xtype : 'button',
			text : '关闭',
			scope : this,
			handler : this.close,
			icon: getRootPath() + '/image/icon/cancel.png'
		} ];
		this.callParent(arguments);
	}
});