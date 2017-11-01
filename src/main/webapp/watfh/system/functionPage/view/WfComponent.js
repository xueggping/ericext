Ext.define('functionpage.view.WfComponent',{
	extend : 'Ext.window.Window',
	alias : 'widget.WfComponent',
	id : 'wfComponentWindow',
	modal : true,//打开遮罩层
	constrain : true,//只能在容器内移动
	resizable : false, //不可改变窗口大小
	width : 1080,
	height:400,
	scrollable :'y',
	title : '',
	layout:'fit',
	items : [{
		xtype : 'gridpanel',
		border : false,
		plugins: [{
	        ptype: 'rowediting',
	        clicksToEdit: 2,////双击进行修改  1-单击   2-双击    0-可取消双击/单击事件
	        saveBtnText: '保存',
	        cancelBtnText: '取消',
	        autoCancel: false, 
            listeners:{
				edit:'saveZ',
				cancelEdit : 'cancelEdit'
			}
		}],
		region : 'center',
		id : 'componentGrid',
		store : {
			type : 'WfComponentStore'
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
		    xtype : 'toolbar',
			dock : 'top',
			items : [{
				xtype : 'button',
				listeners : {
					click : 'addZ'
				},
				icon: getRootPath() + '/image/icon/add.png',
				text : '新增'
			}, {
				 xtype: 'tbseparator'
			}, {
				xtype : 'button',
				listeners : {
					click : 'delZ'
				},
				icon: getRootPath() + '/image/icon/delete.png',
				text : '删除'
			}]//新增，编辑，查看，删除
		}],//dockedItems end
		defaultType:'textfield',
		columns : [{
			xtype : 'gridcolumn',
			hidden : true,
			dataIndex : 'id',
		},{
			xtype : 'gridcolumn',
			align : 'center',
			width : '15%',
			dataIndex : 'componentname',
			text : '组件位置',
			renderer : function(value,metaData,record,colIndex,store,view) {
				if(Ext.isEmpty(value) || value == "null" || value == null ||  value == ""){
				}else{
					metaData.tdAttr = 'data-qtip="<font class=data-qtip>' + value + '</font>"'; 
				}
				return value;
			}
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'xtype',
			text : 'xtype',
			maxLength: 100,
			editor: true
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'width',
			text : 'width',
			editable : false,// 必须默认为true，否则 isCellEditable 没法用
			editor: {
				regex: /^\d+(\.\d+)*[%]?$/,
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'height',
			text : 'height',
			editable : false,// 必须默认为true，否则 isCellEditable 没法用
			editor: {
				regex: /^\d+(\.\d+)*[%]?$/,
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'margin',
			text : 'margin',// 必须默认为true，否则 isCellEditable 没法用
			editor: {
				maxLength: 20
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'labelWidth',
			text : 'labelWidth',// 必须默认为true，否则 isCellEditable 没法用
			editor: {
				maxLength: 20
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'actionName',
			text : 'actionName',
			editor: {
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'maxLength',
			text : 'maxLength',
			editor: {
				maxValue : 9999999999,
				minValue : 0
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'fieldLabel',
			text : 'fieldLabel',
			editor: {
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'allowBlank',
			text : 'allowBlank',
			editor: {
				xtype: 'combobox',
				valueField : 'attr',
				displayField : 'name',
				store : {
					fields : ['attr','name'],
					data : [
							{'attr':'1','name':'true'},
							{'attr':'0','name':'false'},
						]
				}
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'regex',
			text : 'regex',
			editor: {
				maxLength: 500
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'regexText',
			text : 'regexText',
			editor: {
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'maxLengthText',
			text : 'maxLengthText',
			width :'10%',
			editor: {
				maxLength: 100
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'otherProperty',
			text : 'otherProperty',
			width :'10%',
			editor: true
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'extendComponent',
			text : 'extendComponent',
			width :'11%',
			editor: true
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'ordered',
			text : 'ordered',
			editor: {
				maxValue : 9999999999,
				minValue : 0,
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'hidden',
			text : 'hidden',
			editor: {
				xtype: 'combobox',
				valueField : 'attr',
				displayField : 'name',
				store : {
					fields : ['attr','name'],
					data : [
							{'attr':'1','name':'true'},
							{'attr':'0','name':'false'},
						]
				}
	        }
		},{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'text',
			text : 'text',
			editor: {
				maxLength: 100,
	        }
		},
		{
			xtype : 'gridcolumn',
			align : 'center',
			dataIndex : 'dataIndex',
			text : 'dataIndex',
			editor: {
				maxLength: 100,
	        }
		}
		/*{ 
			xtype : 'gridcolumn',
			dataIndex : 'stcd',
			text : '测站编码',
			hidden:true
		},{ 
			xtype : 'gridcolumn',
			align : 'center',
			width : 145,
			dataIndex : 'mstm',
			text : '施测时间',
			editable : false,// 必须默认为true，否则 isCellEditable 没法用
			editor: {
				id:'tfId',
	            xtype: 'datetimefield',
	            format: 'Y-m-d H:i:s',
	            allowBlank: false
	        },
	        renderer : function(value,metaData,record,colIndex,store,view){//v是单元格的值，r是record对象，i是行索引 
	            if(record.data.ptno!=null){//如果你的什么什么条件
	            	alert(record.data.ptno!=null)
	            	Ext.getCmp('tfId').disable();//让你逻辑的某个组件禁用，这里的xxx就是editor:这里的id，如：tfId 
//	              Ext.getCmp('tfId').enable();//让你逻辑的某个组件启用    
	        	}else{
	        		Ext.getCmp('tfId').enable();//让你逻辑的某个组件启用    
	        	}
	        }
		},
		{ 
			xtype : 'gridcolumn',
			align : 'right',
			dataIndex : 'ptno',
			text : '点序号',
		},
		{ 
			xtype : 'gridcolumn',
			align : 'right',
			dataIndex : 'rz',
			text : '库水位(m)',
			editor: {
	            xtype: 'numberfield',
	            maxValue: 9999.999,
	            allowBlank: false
	        }
		},{ 
			xtype : 'gridcolumn',
			align : 'right',
			dataIndex : 'w',
			width:'20%',
			text : '蓄水量(m³/s)',
			editor: {
	            xtype: 'numberfield',
	            maxValue: 9999.999,
	            allowBlank: false
	        }
		},{ 
			xtype : 'gridcolumn',
			align : 'right',
			dataIndex : 'wsfa',
			flex : 1,
			text : '水面面积(km²)',
			editor: {
				xtype: 'numberfield',
	            maxValue: 9999999
	        }
		}*/]
	}],//items end
	initComponent : function(){
//	    this.buttons = [{
//			xtype : 'button',
//			text : '关闭',
//			scope : this,
//			icon: getRootPath() + '/image/icon/cancel.png',
//			handler : this.close
//		} ];
		this.callParent(arguments);
	}
});