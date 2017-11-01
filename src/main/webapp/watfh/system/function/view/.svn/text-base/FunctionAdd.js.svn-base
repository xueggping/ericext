Ext.define('function.view.FunctionAdd', {
	extend : 'Ext.window.Window',
	alias : 'widget.FunctionAdd',
	id : 'functionAddWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 390,
	width : 680,
	layout : 'column',
	title : '添加功能',
	items : [ {
		xtype : 'displayfield',
		width : 600,
		labelWidth : 90,
		margin : '10 5 5 20',
		actionName : 'pname',
		actionGroup : 'functionAdd',
		fieldLabel : '上级功能模块'
	}, {
		xtype : 'displayfield',
		hidden : true,
		actionName : 'pid',
		actionGroup : 'functionAdd'
	}, {
		xtype : 'textfield',
		width : 200,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'name',
		actionGroup : 'functionAdd',
		allowBlank: false,
		maxLength: 40,
		fieldLabel : '功能名称'
	}, {
		xtype : 'numberfield',
		width : 200,
		labelWidth : 60,
		margin : '5 5 5 10',
		actionName : 'fdesc',
		actionGroup : 'functionAdd',
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
		actionGroup : 'functionAdd',
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
		actionGroup : 'functionAdd',
		maxLength: 250,
		fieldLabel : '链接地址'
	}, {
		xtype : 'textfield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'imgUlr',
		actionGroup : 'functionAdd',
		maxLength: 250,
		fieldLabel : '图片地址'
	},{
		xtype : 'textareafield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'executeSql',
		actionGroup : 'functionAdd',
		maxLength: 250,
		fieldLabel : '执行sql'
	},{
		xtype : 'textareafield',
		width : 625,
		labelWidth : 60,
		margin : '5 5 5 20',
		actionName : 'note',
		actionGroup : 'functionAdd',
		maxLength: 250,
		fieldLabel : '功能描述'
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
			icon: getRootPath() + '/image/icon/cancel.png',
			text : '关闭',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});