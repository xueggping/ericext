Ext.define('function.view.FunctionEdit', {
	extend : 'Ext.window.Window',
	alias : 'widget.FunctionEdit',
	id : 'functionEditWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 390,
	width : 680,
	layout : 'column',
	title : '添加功能',
	items : [ {
		xtype : 'displayfield',
		hidden : true,
		actionName : 'id',
		actionGroup : 'functionEdit'
	}, {
		xtype : 'textfield',
		width : 200,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'functionEdit',
		allowBlank: false,
		maxLength: 40,
		fieldLabel : '功能名称'
	}, {
		xtype : 'numberfield',
		width : 200,
		labelWidth : 60,
		margin : '5 5 5 10',
		actionName : 'fdesc',
		actionGroup : 'functionEdit',
		maxValue : 1000,
		minValue : 0,
		decimalPrecision : 0,//小数位数，默认0
		fieldLabel : '功能排序'
	}, {
		xtype : 'radiogroup',
		width : 200,
		labelWidth : 60,
		margin : '5 5 5 10',
		fieldLabel : '是否启用',
		actionName : 'isEnabled',
		actionGroup : 'functionEdit',
		items: [{
            xtype: 'radiofield',
            checked: true,
    		name:'isEnabled',
            inputValue:1,
            boxLabel: '启用'
        },
        {
            xtype: 'radiofield',
    		name:'isEnabled',
            inputValue:0,
            boxLabel: '禁用'
        }]
	}, {
		xtype : 'textfield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'furl',
		actionGroup : 'functionEdit',
		maxLength: 250,
		fieldLabel : '链接地址'
	}, {
		xtype : 'textfield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'imgUlr',
		actionGroup : 'functionEdit',
		maxLength: 250,
		fieldLabel : '图片地址'
	},,{
		xtype : 'textareafield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'executeSql',
		actionGroup : 'functionEdit',
		maxLength: 250,
		fieldLabel : '执行sql'
	},{
		xtype : 'textareafield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'functionEdit',
		maxLength: 250,
		fieldLabel : '功能描述'
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
			icon: getRootPath() + '/image/icon/cancel.png',
			text : '关闭',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});