Ext.define('dictionary.view.DictionaryMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.DictionaryMain',
	id : 'dictionaryMain',
	controller: 'main',
	layout : {
		type : 'border'
	},
	border : false,
	items : [ {
		xtype : 'gridpanel',
		region : 'center',
		title : '数据字典',
		store: {
	        type: 'DictionaryList'
	    },
	    id :'dictionaryGridpanel',
		enableColumnHide : false,///隐藏列
		sortableColumns : false,///隐藏排序
		columnLines : true,//列间线
		selModel: {
			selType: 'checkboxmodel'
	    },
		viewConfig : {
			stripeRows: true,//条纹行
			enableTextSelection : true//文本可选
		},
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			displayInfo : true,
			dock : 'bottom'
		}, 
		{
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				xtype : 'button',
				listeners: {
		            click: 'add'
		        },
		        icon: getRootPath() + '/image/icon/add.png',
				text : '添加'
			}, '-', {
				xtype : 'button',
				listeners: {
		            click: 'edit'
		        },
		        icon: getRootPath() + '/image/icon/edit.png',
				text : '编辑'
			}, '-', {
				xtype : 'button',
				listeners: {
		            click: 'del'
		        },
		        icon: getRootPath() + '/image/icon/delete.png',
				text : '删除'
			} ]
		} ],
		columns : [  {
			xtype: 'rownumberer',
			text: '',
			align : 'left',
			width:'3%',
			renderer:function(value,metadata,record,rowIndex){
				if(rowIndex > GLOBLE_PAGESIZE){
					rowIndex = 0;
				}
			　　　return　rowIndex + 1;
			}
		},
		{
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'code',
			width : 200,
			text : '编码'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'name',
			width : 180,
			text : '名称'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'ckey',
			width : 150,
			text : '键'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			dataIndex : 'note',
			flex : 1,
			text : '备注'
		}]
	},{
		xtype : 'panel',
		html:'<div id="dictionaryzTree" class="ztree" style="margin:0;overflow-y:auto;overflow-x:auto;height:100%;width:100%;"></div>',
		region: 'west',
		listeners: {
            afterrender : 'initzTree'
        },
        maxWidth : 250,
		width : '25%'
	}]
});
