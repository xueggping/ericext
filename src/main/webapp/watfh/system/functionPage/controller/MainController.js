var MainController;
//自定义扩展：将Grid表头中的全选复选框取消复选
Ext.define('functionpage.controller.MainController',{
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : ['functionpage.view.FunctionPageMain',
	            'functionpage.view.FunctionPageAdd',
	            'functionpage.store.FunctionPageMain',
	            'functionpage.view.WfComponent',
	            'functionpage.store.WfComponentStore',
	            'Ext.ux.ZTreeComboBox',
	            'functionpage.model.models'],
	init : function() {
		MainController = this;
	},
	find : function() {
		Search( {
			actionGroup : 'funcpageFind',
			storeParentId : 'thresholdGridPanel',
			type : 'form'
		});
	},
	reset : function() {
		Reset('funcpageFind');
	},
	add : function() {
		var win = Ext.widget('FunctionPageAdd');//alias
		win.show();
	},
	addSave : function() {
		var win = Ext.getCmp('funcPageAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var type='add';
		var msg='新增';
		if(win.down('hiddenfield[actionName=id]').value.length>0 ){
			type='edit';
			msg='修改';
		}
		var status = Submit('form', 'thresholdAdd', getRootPath() + '/funcpage/'+type+'.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
					myMask.hide();
					var result = Ext.JSON.decode(response.responseText);
					if (result.success == true) {
						win.close();
						Ext.Msg.alert('提示信息', msg+"成功" + result.msg + "！");
						MainController.find();
					} else {
						Ext.Msg.alert('提示信息', msg+"失败" + result.msg + "！");
					}
				}, function(response, opts) {
					//ajax发送失败，关闭遮罩层
					myMask.hide();
					Ext.Msg.alert('提示信息', msg+"失败!");
				});
		//数据验证没有通过，在这里关闭遮罩层
		if (status == false) {
			myMask.hide();
		}
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('thresholdGridPanel', ['id'], 1, 1);
		if (selData == false) {
			return;
		}
//		Ext.ComponentQuery.query('panel [actionName=stcd]')[0].readOnly = true;
		var myMask = new Ext.LoadMask(Ext.getCmp('functionPageMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request({
		    url : getRootPath() + '/funcpage/queryById.do',
			method : 'POST',
			params : {
			    id : selData.id
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('FunctionPageAdd');
					win.setTitle("修改");
					SetValue('thresholdAdd', result.data);
					var str = Ext.JSON.encode(result.data.otherProperty).replace(new RegExp("\"","gm"),""); 
					win.down('textareafield[actionName=otherProperty]').setValue(str); 
					win.show();
				} else {
					Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试" + result.msg + "！");
				}
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
	
	},
	del : function() {
		var selData = GetGridPanelSelectValue('thresholdGridPanel', ['id'], 1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('functionPageMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/funcpage/del.do',
							method : 'POST',
							params : {
								id : selData.id
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功" + result.msg+ "！");
									MainController.find();
								} else {
									Ext.Msg.alert('提示信息', "删除失败" + result.msg+ "！");
								}
							},
							failure : function(response, opts) {
								myMask.hide();
								Ext.Msg.alert('提示信息', "删除失败！");
							}
						});
					}
				});
	},
	wfComponent: function(grid, rowIndex, colIndex) {
		grid.getSelectionModel().deselectAll()//清空所有选中行
		grid.getSelectionModel().select(colIndex,true);
		var rec = grid.getStore().getAt(colIndex);
		var view = Ext.widget('WfComponent');
		var regtion='';
		if(rec.data.region!=undefined){
			regtion='_'+rec.data.region;
		}
        view.setTitle('<span style="color:red;"><B>组件位置:' + rec.data.containerType+'_'+rec.data.xtype+regtion+'</B></span>'+'的信息');
        view.show();
        MainController.findZ();
	},
	findZ  : function(){
		var selData = GetGridPanelSelectValue('thresholdGridPanel', ['id'], 1);
		 var new_params = {
 	            'PageId': selData.id
     	 };
    	 var waterdataGrid = Ext.getCmp('componentGrid');
    	 var waterStore=waterdataGrid.store;
    	 waterStore.proxy.extraParams = new_params;
    	 waterStore.load();
	},
	saveZ : function(editor,e) {
		var win = Ext.getCmp('wfComponentWindow');
		 var msg='修改', func='edit';
		 if(e.record.data.componentname == null){
			 msg='新增';
			 func='add';
			 e.record.data.id = null;
		 }
		 var myMask = new Ext.LoadMask(win, {
				msg : "请求进行中,请等待..."
			});
		 myMask.show();
		 Ext.Ajax.request({   
			url : getRootPath() + '/extComponent/'+func+'.do',  
			method:'POST',
			params:e.record.data,
			success:function(response, opts){
				//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					Ext.Msg.alert('提示信息', msg+"成功" + result.msg + "！");
					MainController.findZ();
				} else {
					Ext.Msg.alert('提示信息', msg+"失败" + result.msg + "！");
				}
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', msg+"失败！");
			}
		}); 
	},
	cancelEdit: function() {//添加一行数据时，如果没有点击update而是点击cancel，则此行数据不会加入到store中
		var graid = Ext.getCmp('componentGrid');
     	var me = graid.plugins[0];
//     	if (me.editing) {
//         	me.getEditor().cancelEdit();
         	var record=me.context.record;//包含属性如下：grid,record,field,value,row,column,rowIdx,colIdx,view,store
         	var id=record.data.ptno;
         	if(id=='' || id==undefined || id==null){//id是由后台自动生成的，如果id不存在，则说明是通过页面的添加按钮直接添加的，还没有存入到后台数据库中，可以直接在页面删除
         		var grid=me.context.grid;
         		var items=grid.getSelectionModel().getSelection();
	             	Ext.each(items,function(item){
	             		grid.store.remove(item);
             	})
         	}
//     	}
     },
 	addZ: function(editor,e) {
    	 var selData = GetGridPanelSelectValue('thresholdGridPanel', ['id'], 1);
    	 var graid = Ext.getCmp('componentGrid');
    	 var store=graid.store;
    	 var r = Ext.create('functionpage.model.models', {
    		 'pageId': selData.id
    	 });
    	 store.insert(0, r);
    	 graid.plugins[0].startEdit(0, 0);//rowEditing.startEdit(0, 0);
 	},
	delZ : function() {
		var selDataDel = GetGridPanelSelectValue('componentGrid', ['id'], 1);
		if (selDataDel == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selDataDel.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('wfComponentWindow'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/extComponent/del.do',
							method : 'POST',
							params : {
								ids : selDataDel.id
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功" + result.msg+ "！");
									MainController.findZ();
								} else {
									Ext.Msg.alert('提示信息', "删除失败" + result.msg+ "！");
								}
							},
							failure : function(response, opts) {
								myMask.hide();
								Ext.Msg.alert('提示信息', "删除失败！");
							}
						});
					}
				});
	},
	exportData:function(){
		window.location.href = getRootPath() + '/portDataController/exprotData.do?tableName=WF_FUNCTION_PAGE';
	}
});