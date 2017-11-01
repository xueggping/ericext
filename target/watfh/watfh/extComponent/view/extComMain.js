Ext.define('extComponent.view.extComMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.extComMain',
	id : 'extComMain',
	controller : 'main',
	layout : {
		type : 'border'
	},
	border : false,

	items : [ 
	{
		xtype : 'panel',
		region : 'north',
		border : false,
		layout : {
			type : 'column'
		},
		items : [{
        xtype: 'treecombox',
		margin : '5 5 5 10',
		labelWidth: 60,
        width: 300,
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
//		allowBlank: false,
		actionGroup : 'extComponentFind',
		fieldLabel : '组件列表',
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
			xtype : 'button',
			icon: getRootPath() + '/image/icon/magnifier.png',
			margin : '5 5 5 5',
			listeners : {
				click : 'find'
			},
			text : '查询'
		}, {
			xtype : 'button',
			icon: getRootPath() + '/image/icon/restart.png',
			margin : '5 5 5 5',
			listeners : {
				click : 'reset'
			},
			text : '重置'
		} ]
	}, {
		xtype : 'gridpanel',
//		forceFit : true,//自动填充
		border : false,
		region : 'center',
		id : 'extComponentGridPanel',
		store : {
			type : 'extComMain'
		},
		enableColumnHide : false,///隐藏列
		sortableColumns : false,///隐藏排序
		columnLines : true,//列间线
		selModel : {
			selType : 'checkboxmodel'
		},
		viewConfig : {
			stripeRows : true,//条纹行
			enableTextSelection : true
		//文本可选
		},
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			displayInfo : true,
			dock : 'bottom',
			id:'pageitem'
		}, {
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'button',
				icon: getRootPath() + '/image/icon/add.png',
				listeners : {
					click : 'add'
				},
				text : '添加'
			}, '-', {
				xtype : 'button',
				icon: getRootPath() + '/image/icon/edit.png',
				listeners : {
					click : 'edit'
				},
				text : '编辑'
			}, '-', {
				xtype : 'button',
				icon: getRootPath() + '/image/icon/delete.png',
				listeners : {
					click : 'del'
				},
				text : '删除'
			}, '-', {
				xtype : 'button',
				icon: getRootPath() + '/image/icon/visitor.png',
				listeners : {
					click : 'detail'
				},
				text : '查看'
			}]
		} ],
		columns : [{
			xtype : 'gridcolumn',
			hidden : true,
			dataIndex : 'id',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			width : '13%',
			dataIndex : 'componentname',
			text : '组件位置',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'xtype',
			text : 'xtype'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'width',
			text : 'width'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'height',
			text : 'height'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'margin',
			text : 'margin'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'labelWidth',
			text : 'labelWidth'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'actionName',
			text : 'actionName'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'maxLength',
			text : 'maxLength',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'fieldLabel',
			text : 'fieldLabel'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'allowBlank',
			text : 'allowBlank',
			renderer : function(value,record){
				if(value=='0'){
					return 'false'
				}else if(value=='1'){
					return 'true';
				}else{
					return '';
				}
			}
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'regex',
			text : 'regex'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'regexText',
			text : 'regexText'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'maxLengthText',
			text : 'maxLengthText',
			width :'10%',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'otherProperty',
			text : 'otherProperty',
			width :'10%',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'extendComponent',
			text : 'extendComponent',
			width :'11%',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'ordered',
			text : 'ordered'
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'hidden',
			text : 'hidden',
			renderer : function(value,record){
				if(value=='0'){
					return 'false'
				}else if(value=='1'){
					return 'true';
				}else{
					return '';
				}
			}
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'text',
			text : 'text'
		},
		{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'dataIndex',
			text : 'dataIndex'
		}]
	} ]
});
