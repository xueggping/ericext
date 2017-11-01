Ext.define('functionpage.view.FunctionPageAdd',{
	extend : 'Ext.window.Window',
	alias : 'widget.FunctionPageAdd',
	id : 'funcPageAddWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	width : 550,
	scrollable :'y',
	layout : 'column',
	title : '添加',
	items : [{  
        xtype:'hiddenfield',  
        actionName : 'id',
		actionGroup : 'thresholdAdd'
    },{
        xtype: 'treecombox',
//		labelAlign:'right',
		margin : '5 5 5 10',
		labelWidth: 60,
        width: '55%',
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
			     url:getRootPath() + '/funcpage/getFunction.do',
		    },
			autoLoad : true
	    }),
        displayField: 'text',
        valueField: 'id',
        actionName : 'functionId',
		allowBlank: false,
		actionGroup : 'thresholdAdd',
		fieldLabel : '功能',
		listeners:{
	        'select': function(node,selModel,record){
		    	var rows = selModel.childNodes;
		    	if(rows.length>0){
		    		this.setValue(null);
		    		this.setRawValue('');
		    	}
		    }
	   	}
	
	}, {
		xtype : 'textfield',
		margin : '5 5 5 10',
		labelWidth: 30,
		actionName : 'title',
		actionGroup : 'thresholdAdd',
		fieldLabel : '标题'
	}, {
		xtype : 'textfield',
        minValue: 0,
        margin : '5 5 5 10',
		labelWidth: 60,
        width: '55%',
		actionName : 'height',
		actionGroup : 'thresholdAdd',
		fieldLabel : '高度',
		regex: /^\d+(\.\d+)*[%]?$/
	}, {
		xtype : 'textfield',
        minValue: 0,
		margin : '5 5 5 10',
		labelWidth: 30,
		actionName : 'width',
		actionGroup : 'thresholdAdd',
		fieldLabel : '宽度',
		regex: /^\d+(\.\d+)*[%]?$/
	}, {
		xtype : 'textfield',
		margin : '5 5 5 10',
		labelWidth: 60,
		 width: '55%',
		actionName : 'xtype',
		allowBlank: false,
		actionGroup : 'thresholdAdd',
		fieldLabel : '组件类型'//window或panel
	}, {
		xtype: 'combobox',
		margin : '5 5 5 10',
		labelWidth: 30,
		fieldLabel : '布局',
		actionName : 'layout',
		actionGroup : 'thresholdAdd',
		editable : false,
		displayField: 'name',
	    valueField: 'ckey',
	    displayField: 'name',
		valueField: 'ckey',
		store : Ext.create('Ext.data.Store', {  
			fields : [ 'code', 'pcode', 'name', 'ckey'],
			proxy : {
				type : 'ajax',// 异步获取数据
				url : getRootPath() + '/dictionary/queryByPid.do',
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'content',
					totalProperty : 'totalElements'
				}
				,
				extraParams : {//附加参数一般放查询条件用
					pId : 'layout'
				}
			},
		    autoLoad : true
	    })
	}, {
		xtype : 'textfield',
		margin : '5 5 5 10',
		labelWidth: 60,
		 width: '55%',
		 allowBlank: false,
		actionName : 'containerType',
		actionGroup : 'thresholdAdd',
		fieldLabel : '面板类型'//window或panel
	}, {
		xtype: 'combobox',
		margin : '5 5 5 10',
		labelWidth: 30,
		fieldLabel : '区域',
		actionName : 'region',
		actionGroup : 'thresholdAdd',
		editable : false,
		displayField: 'name',
	    valueField: 'ckey',
	    displayField: 'name',
		valueField: 'ckey',
		store : Ext.create('Ext.data.Store', {  
			fields : [ 'code', 'pcode', 'name', 'ckey'],
			proxy : {
				type : 'ajax',// 异步获取数据
				url : getRootPath() + '/dictionary/queryByPid.do',
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'content',
					totalProperty : 'totalElements'
				}
				,
				extraParams : {//附加参数一般放查询条件用
					pId : 'region'
				}
			},
		    autoLoad : true
	    })
	
	}, {
		xtype : 'textareafield',
		width:'97%',
		margin:'5 5 5 5',
		labelWidth: 60,
		actionName : 'otherProperty',
		actionGroup : 'thresholdAdd',
//		maxLength: 100,
		fieldLabel : '其它属性',
		listeners : {  
	        render : function(field) {  
	            Ext.QuickTips.init();  
	            Ext.QuickTips.register({  
	                target : field.el,  
	                text : '值为json格式'  
	            })  
	        }  
	    }  
	}],//items end
	initComponent : function(){
	    this.buttons = [{
			xtype : 'button',
			listeners : {
				click : 'addSave'
			},
			icon: getRootPath() + '/image/icon/table_save.png',
			text : '保存'
		}, {
			xtype : 'button',
			text : '关闭',
			scope : this,
			icon: getRootPath() + '/image/icon/cancel.png',
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});