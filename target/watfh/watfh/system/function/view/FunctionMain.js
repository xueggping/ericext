Ext.define('function.view.FunctionMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.FunctionMain',
	id : 'functionMain',
	controller: 'main',
	layout : {
		type : 'border'
	},
	border : false,
	items : [ {
		xtype : 'gridpanel',
		region : 'center',
		title : '功能树',
		store: {
	        type: 'FunctionList'
	    },
	    id : 'functionGridpanel',
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
		columns : [ {
			xtype: 'rownumberer',
			text: '',
			align : 'left',
			width:'2%',
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
			width : 200,
			dataIndex : 'NAME',
			text : '功能名称'
		}, {
			xtype : 'gridcolumn',
			align : 'center',
			width : 80,
			dataIndex : 'FDESC',
			text : '功能排序'
		}, {
			xtype : 'gridcolumn',
			align : 'center',
			width : 80,
			dataIndex : 'IS_ENABLED',
			renderer : function(val, record) {
				if (val == "1") {
					return "启用"
				}
				if (val == "0") {
					return "禁用"
				}
				return "状态异常";
			},
			text : '是否启用'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			flex : 1,
			dataIndex : 'FURL',
			text : '链接地址'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			flex : 1,
			dataIndex : 'IMGULR',
			text : '图片地址'
		}, {
			xtype : 'gridcolumn',
			align : 'left',
			flex : 1,
			dataIndex : 'NOTE',
			text : '功能描述'
		}]
	},{
		xtype : 'panel',
		html:'<div id="functionzTree" class="ztree" style="margin:0;overflow-y:auto;overflow-x:auto;height:100%;width:100%;"></div>',
		region: 'west',
		listeners: {
            afterrender : 'initzTree'
        },
        maxWidth : 250,
		width : '25%'
	}]
});
