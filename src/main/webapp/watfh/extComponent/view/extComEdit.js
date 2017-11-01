Ext.define('extComponent.view.extComEdit', {
	extend : 'Ext.window.Window',
	alias : 'widget.extComEdit',
	id : 'extComEditWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	width : 750,
	height : 600,
	scrollable :'y',
	layout : 'column',
	title : '编辑',
	defaultType:'textfield',
	defaults:{
		labelWidth:150,
		margin : '10 5 5 20',
	},
	items : [{
			actionName : 'id',
			actionGroup : 'extComEdit',
			hidden : true
		},{
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
			actionGroup : 'extComEdit',
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
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'xtype'
		},{
			actionName : 'width',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'width'
		},{
			actionName : 'height',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'height'
		},{
			actionName : 'margin',
			actionGroup : 'extComEdit',
			maxLength: 20,
			fieldLabel : 'margin'
		},{
			actionName : 'labelWidth',
			actionGroup : 'extComEdit',
			maxLength: 20,
			fieldLabel : 'labelWidth'
		},{
			actionName : 'actionName',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'actionName'
		},{
			xtype : 'numberfield',
			actionName : 'maxLength',
			actionGroup : 'extComEdit',
			maxValue : 9999999999,
			minValue : 0,
			fieldLabel : 'maxLength'
		},{
			actionName : 'fieldLabel',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'fieldLabel'
		},{
			xtype: 'combobox',
			fieldLabel: 'allowBlank',
			actionName : 'allowBlank',
			actionGroup : 'extComEdit',
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
			actionGroup : 'extComEdit',
			maxLength: 500,
			fieldLabel : 'regex'
		},{
			actionName : 'regexText',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'regexText'
		},{
			actionName : 'maxLengthText',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'maxLengthText',
		},{
			xtype : 'numberfield',
			maxValue : 9999999999,
			minValue : 0,
			actionName : 'ordered',
			actionGroup : 'extComEdit',
			fieldLabel : 'ordered'
		},{
			xtype: 'combobox',
			fieldLabel: 'hidden',
			actionName : 'hidden',
			actionGroup : 'extComEdit',
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
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'text',
		},{
			actionName : 'dataIndex',
			actionGroup : 'extComEdit',
			maxLength: 100,
			fieldLabel : 'dataIndex',
		},{
			xtype: 'textareafield',
			actionName : 'extendComponent',
			actionGroup : 'extComEdit',
			fieldLabel : 'extendComponent',
			labelWidth:150,
			width :'94%',
			height : 200,
		},{
			xtype: 'textareafield',
			actionName : 'otherProperty',
			actionGroup : 'extComEdit',
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
				click : 'editSave'
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