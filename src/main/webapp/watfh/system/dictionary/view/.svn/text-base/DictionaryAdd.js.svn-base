Ext.define('dictionary.view.DictionaryAdd', {
	extend : 'Ext.window.Window',
	alias : 'widget.DictionaryAdd',
	id : 'dictionaryAddWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 322,
	width : 270,
	layout : 'column',
	title : '添加数据字典',
	items : [ {
		xtype : 'displayfield',
		width : 210,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'pcode',
		actionGroup : 'dictionaryAdd',
		fieldLabel : '上级编码'
	}, {
		xtype : 'displayfield',
		width : 210,
		labelWidth : 60,
		margin : '0 5 5 20',
		actionName : 'pname',
		actionGroup : 'dictionaryAdd',
		fieldLabel : '上级名称'
	}, {
		xtype : 'textfield',
		width : 210,
		labelWidth : 60,
		margin : '0 5 5 20',
		actionName : 'code',
		actionGroup : 'dictionaryAdd',
		allowBlank: false,
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '编码'
	}, {
		xtype : 'textfield',
		width : 210,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'dictionaryAdd',
		maxLength: 20,
		fieldLabel : '名称'
	}, {
		xtype : 'textfield',
		width : 210,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'ckey',
		actionGroup : 'dictionaryAdd',
		maxLength: 40,
		regex: /^\w+$/,
		regexText : '只能输入数字、字母和下划线!',
		fieldLabel : '键'
	}, {
		xtype : 'textareafield',
		width : 210,
		labelWidth : 60,
		height : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'dictionaryAdd',
		maxLength: 100,
		fieldLabel : '备注'
	}],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			listeners: {
	            click: 'addSave'
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