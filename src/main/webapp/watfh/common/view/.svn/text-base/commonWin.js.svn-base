Ext.define('common.view.commonWin', {
	extend : 'Ext.window.Window',
	id : 'commonWin' + module,
	alias : 'widget.commonWin'+ module,
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 210,
	width : 270,
	layout : 'column',
	items : [],
	buttons : [ {
		xtype : 'button',
		listeners: {
            click: 'addSave'
        },
		text : '保存'
	}, {
		xtype : 'button',
		text : '关闭',
		scope : this,
		handler : this.close
	} ]
});