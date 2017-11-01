Ext.define('dictionary.view.DictionaryEdit', {
	extend : 'Ext.window.Window',
	alias : 'widget.DictionaryEdit',
	id : 'dictionaryEditWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 272,
	width : 270,
	layout : 'column',
	title : '编辑数据字典',
	items : [ {
		xtype : 'displayfield',
		width : 210,
		labelWidth : 40,
		margin : '20 5 5 20',
		actionName : 'code',
		actionGroup : 'dictionaryEdit',
		fieldLabel : '编码'
	}, {
		xtype : 'textfield',
		width : 210,
		labelWidth : 40,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'dictionaryEdit',
		allowBlank: false,
		maxLength: 20,
		fieldLabel : '名称'
	}, {
		xtype : 'textfield',
		width : 210,
		labelWidth : 40,
		margin : '5 5 5 20',
		actionName : 'ckey',
		actionGroup : 'dictionaryEdit',
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '键'
	}, {
		xtype : 'textareafield',
		width : 210,
		labelWidth : 40,
		height : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'dictionaryEdit',
		maxLength: 100,
		fieldLabel : '备注'
	}],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners: {
	            click: 'editSave'
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