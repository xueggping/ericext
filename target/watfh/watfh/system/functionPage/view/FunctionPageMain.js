Ext.define('functionpage.view.FunctionPageMain',{
	extend : 'Ext.panel.Panel',//1
	alias : 'widget.FunctionPageMain',//2
	id : 'functionPageMain',//3
	controller : 'main',//4
	layout : {
		type : 'border'
	},//5
	border : false,//6
	items : [{
	    xtype : 'panel',
		region : 'north',
		border : false,
		layout : {
			type : 'column'
		},
		items : [{
            xtype: 'treecombox',
            margin: '5,5,5,5',
			labelAlign:'right',
            labelWidth : 60,
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
				     url:getRootPath() + '/funcpage/getFunction.do',
			    },
				autoLoad : true
		    }),
            displayField: 'text',
            valueField: 'id',
            actionName : 'functionId',
			actionGroup : 'funcpageFind',
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
			xtype : 'button',
			margin : '5 5 5 5',
			listeners : {
				click : 'find'
			},
			icon: getRootPath() + '/image/icon/magnifier.png',
			text : '查询'
		}, {
			xtype : 'button',
			margin : '5 5 5 5',
			listeners : {
				click : 'reset'
			},
			icon: getRootPath() + '/image/icon/restart.png', 
			text : '重置'
		} ]
	},{

        xtype: 'gridpanel',
        region:'center',
        border : false,
		id : 'thresholdGridPanel',
		store : {
			type : 'FunctionPageMain'
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
		},
		dockedItems : [{
			xtype : 'pagingtoolbar',
			displayInfo : true,
			dock : 'bottom',
			id:'pageitem'
		},{
		    xtype : 'toolbar',
			dock : 'top',
			items : [{
				xtype : 'button',
				listeners : {
					click : 'add'
				},
				icon: getRootPath() + '/image/icon/add.png',
				hidden:false,
				text : '新增'
			}, {
				 xtype: 'tbseparator',
				 hidden:false
			},{
				xtype : 'button',
				listeners : {
					click : 'edit'
				},
				icon: getRootPath() + '/image/icon/edit.png',
				text : '编辑'
			},'-', 
			{
				xtype : 'button',
				listeners : {
					click : 'del'
				},
				icon: getRootPath() + '/image/icon/delete.png',
				hidden:false,
				text : '删除'
			},'-', 
			{
				xtype : 'button',
				 listeners: {
		            click: 'exportData'
		        },
    			icon:getRootPath() + '/image/icon/watf_export.png',
				text : '导出Excel'
			}]//新增，编辑，查看，删除
		}],//dockedItems end
		columns : [{
			xtype: 'rownumberer',
			text: '',
			align : 'left',
			width:'2.5%',
			renderer:function(value,metadata,record,rowIndex){
				if(rowIndex > GLOBLE_PAGESIZE){
					rowIndex = 0;
				}
			　　　return　rowIndex + 1;
			}
		},{
			xtype: 'gridcolumn',
			hidden:true,
			dataIndex: 'id',
			text: '主键ID'
		},{
			xtype: 'gridcolumn',
			align: 'left',
			width:'15%',
			dataIndex: 'functionId',
			text: '功能',
			style : {
				"text-align" : "center"
			},
			renderer : function(value,metaData,record,colIndex,store,view) {
				if(Ext.isEmpty(value) || value == "null" || value == null ||  value == ""){
				}else{
					metaData.tdAttr = 'data-qtip="<font class=data-qtip>' + value + '</font>"'; 
				}
				return value;
			}
		},{
			xtype : 'gridcolumn',
			align : 'left',
			width:'10%',
			dataIndex : 'title',
			text : '标题',
			style : {
				"text-align" : "center"
			},
			renderer : function(value,metaData,record,colIndex,store,view) {
				if(Ext.isEmpty(value) || value == "null" || value == null ||  value == ""){
				}else{
					metaData.tdAttr = 'data-qtip="<font class=data-qtip>' + value + '</font>"'; 
				}
				return value;
			}
		},{
            xtype: 'gridcolumn',
            align: 'center',
            width:'6%',
            dataIndex: 'height',
            text: '高度',
			style : {
				"text-align" : "center"
			}
        },
        {
            xtype: 'gridcolumn',
            align: 'left',
            dataIndex: 'width',
            width:'6%',
            text: '宽度',
			style : {
				"text-align" : "center"
			}
        },{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'layout',
			text : '布局',
			style : {
				"text-align" : "center"
			}
		},
		{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'region',
			text : '区域',
			style : {
				"text-align" : "center"
			}
		},
		{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'xtype',
			text : '组件类型',
			style : {
				"text-align" : "center"
			}
		},
		{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'containerType',
			text : '面板类型',
			style : {
				"text-align" : "center"
			}
		},
		{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'otherProperty',
			flex:1,
			text : '其它属性',
			style : {
				"text-align" : "center"
			},
			renderer: function(value,metaData,record,colIndex,store,view) {////鼠标放到上面时提示该单元格信息
				var str = Ext.JSON.encode(value).replace(new RegExp("\"","gm"),""); 
				if(Ext.isEmpty(value) || value == "null" || value == null ||  value == ""){
				}else{
					metaData.tdAttr = 'data-qtip="<font class=data-qtip>' + str + '</font>"'; 
				}  
				return str; 
			}
		},
		{
			header: '组件',
			xtype: 'actioncolumn',
			align: 'center',
			width:'5%',
			icon: getRootPath() + '/image/icon/edit.png',
			tooltip: "编辑组件",
//			action: 'wfComponent'
			listeners : {
				click : 'wfComponent'
			}
			/*handler: function (grid, rowIndex, colIndex, node, e, record, rowEl) {  
				grid.getSelectionModel().deselectAll()//清空所有选中行
				MainController.wfComponent(grid, rowIndex);
		    }*/ 
		}]
	}]
});