Ext.define('extComponent.view.extComDetail', {
	extend : 'Ext.window.Window',
	alias : 'widget.extComDetail',
	id : 'extComDetailWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	width : 750,
	height:600,
	scrollable :'y',
	layout : 'column',
	title : '详情',
	defaultType:'textfield',
	defaults:{
		labelWidth:150,
		margin : '10 5 5 20',
		readOnly:true
	},
	items : [{
			actionName : 'xtype',
			actionGroup : 'extComDetail',
			fieldLabel : 'xtype'
		},{
			actionName : 'width',
			actionGroup : 'extComDetail',
			fieldLabel : 'width'
		},{
			actionName : 'height',
			actionGroup : 'extComDetail',
			fieldLabel : 'height'
		},{
			actionName : 'margin',
			actionGroup : 'extComDetail',
			fieldLabel : 'margin'
		},{
			actionName : 'labelWidth',
			actionGroup : 'extComDetail',
			fieldLabel : 'labelWidth'
		},{
			actionName : 'actionName',
			actionGroup : 'extComDetail',
			fieldLabel : 'actionName'
		},{
			xtype : 'numberfield',
			actionName : 'maxLength',
			actionGroup : 'extComDetail',
			fieldLabel : 'maxLength'
		},{
			actionName : 'fieldLabel',
			actionGroup : 'extComDetail',
			fieldLabel : 'fieldLabel'
		},{
			xtype: 'combobox',
			fieldLabel: 'allowBlank',
			actionName : 'allowBlank',
			actionGroup : 'extComDetail',
			editable : false,
			valueField : 'attr',
			displayField : 'name',
			store : {
				fields : ['attr','name'],
				data : [
						{'attr':'1','name':'true'},
						{'attr':'0','name':'false'},
					]
			}
		},{
			actionName : 'regex',
			actionGroup : 'extComDetail',
			fieldLabel : 'regex'
		},{
			actionName : 'regexText',
			actionGroup : 'extComDetail',
			fieldLabel : 'regexText'
		},{
			actionName : 'maxLengthText',
			actionGroup : 'extComDetail',
			fieldLabel : 'maxLengthText',
		},{
			xtype : 'numberfield',
			actionName : 'ordered',
			actionGroup : 'extComDetail',
			fieldLabel : 'ordered'
		},{
			xtype: 'combobox',
			fieldLabel: 'hidden',
			actionName : 'hidden',
			actionGroup : 'extComDetail',
			editable : false,
			valueField : 'attr',
			displayField : 'name',
			store : {
				fields : ['attr','name'],
				data : [
						{'attr':'1','name':'true'},
						{'attr':'0','name':'false'},
					]
			}
		},{
			actionName : 'text',
			actionGroup : 'extComDetail',
			fieldLabel : 'text',
		},{
			actionName : 'dataIndex',
			actionGroup : 'extComDetail',
			fieldLabel : 'dataIndex',
		},{
			xtype: 'textareafield',
			actionName : 'extendComponent',
			actionGroup : 'extComDetail',
			fieldLabel : 'extendComponent',
			labelWidth:150,
			width :'94%',
			height : 200,
		},{
			xtype: 'textareafield',
			actionName : 'otherProperty',
			actionGroup : 'extComDetail',
			fieldLabel : 'otherProperty',
			labelWidth:150,
			width :'94%',
			height : 200,
		}],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			icon: getRootPath() + '/image/icon/cancel.png',
			text : '关闭',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});