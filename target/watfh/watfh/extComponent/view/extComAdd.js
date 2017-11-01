Ext.define('extComponent.view.extComAdd', {
	extend : 'Ext.window.Window',
	id : 'extComAddWindow',
	alias : 'widget.extComAdd',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	height : 600,
	width : 750,
	scrollable :'y',
	layout : 'column',
	title : '添加',
	defaultType:'textfield',
	defaults:{
		labelWidth:150,
		margin : '10 5 5 20',
	},
	items : [{
	        xtype: 'treecombox',
	        width: 450,
	        maxPickerHeight: 450,
	        editable:false,
	        store: Ext.create('Ext.data.TreeStore', {  
		        fields: ['id', 'text'],
		        root: {
		            text: '功能树', 
		            id: 0, 
		            expanded: true 
		        },
		        proxy: {
				     type: 'ajax',
				      url:getRootPath() + '/extComponent/getMenuTree.do',
			    },
				autoLoad : true
		    }),
	        displayField: 'text',
	        valueField: 'id',
	        actionName : 'pageId',
			allowBlank: false,
			actionGroup : 'extComAdd',
			fieldLabel : '组件位置',
			listeners:{
		        'select': function(node,selModel,record){
			    	var rows = selModel.childNodes;
			    	if(rows.length>0){
			    		this.setValue(null);
			    		this.setRawValue('');
			    	}
			    }
		   	}
		
		},{
			actionName : 'xtype',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'xtype'
		},{
			actionName : 'width',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'width'
		},{
			actionName : 'height',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'height'
		},{
			actionName : 'margin',
			actionGroup : 'extComAdd',
			maxLength: 20,
			fieldLabel : 'margin'
		},{
			actionName : 'labelWidth',
			actionGroup : 'extComAdd',
			maxLength: 20,
			fieldLabel : 'labelWidth'
		},{
			actionName : 'actionName',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'actionName'
		},{
			xtype : 'numberfield',
			actionName : 'maxLength',
			actionGroup : 'extComAdd',
			maxValue : 9999999999,
			minValue : 0,
			fieldLabel : 'maxLength'
		},{
			actionName : 'fieldLabel',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'fieldLabel'
		},{
			xtype: 'combobox',
			fieldLabel: 'allowBlank',
			actionName : 'allowBlank',
			actionGroup : 'extComAdd',
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
			actionGroup : 'extComAdd',
			maxLength: 500,
			fieldLabel : 'regex'
		},{
			actionName : 'regexText',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'regexText'
		},{
			actionName : 'maxLengthText',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'maxLengthText',
		},{
			xtype : 'numberfield',
			maxValue : 9999999999,
			minValue : 0,
			actionName : 'ordered',
			actionGroup : 'extComAdd',
			fieldLabel : 'ordered'
		},{
			xtype: 'combobox',
			fieldLabel: 'hidden',
			actionName : 'hidden',
			actionGroup : 'extComAdd',
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
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'text',
		},{
			actionName : 'dataIndex',
			actionGroup : 'extComAdd',
			maxLength: 100,
			fieldLabel : 'dataIndex',
		},{
			xtype: 'textareafield',
			actionName : 'extendComponent',
			actionGroup : 'extComAdd',
			fieldLabel : 'extendComponent',
			labelWidth:150,
			width :'94%',
			height : 200,
		},{
			xtype: 'textareafield',
			actionName : 'otherProperty',
			actionGroup : 'extComAdd',
			fieldLabel : 'otherProperty',
			labelWidth:150,
			width :'94%',
			height : 200,
		}],
	initComponent : function() {
		this.buttons = [ {
			xtype : 'button',
			icon: getRootPath() + '/image/icon/table_save.png',
			listeners : {
				click : 'addSave'
			},
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