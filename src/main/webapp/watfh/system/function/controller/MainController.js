var MainController;
var functionzTree = null;
var checkNode = {
	id : "root",
	name : "功能树"
};
Ext.define('function.controller.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'function.view.FunctionMain', 'function.view.FunctionAdd',
			'function.view.FunctionEdit',

			'function.store.FunctionList' ],
	init : function() {
		MainController = this;
	},
	add : function() {
		var win = Ext.widget('FunctionAdd');
		win.show();
		SetValue('functionAdd', {
			pid : checkNode.id,
			pname : checkNode.name
		});
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('functionGridpanel', [ 'ID' ], 1,
				1);
		if (selData == false) {
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('functionMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/function/queryById.do',
			method : 'POST',
			params : {
				id : selData.ID
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('FunctionEdit');
					win.show();
					SetValue('functionEdit', result.data);
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
		var selData = GetGridPanelSelectValue('functionGridpanel', [ 'ID' ],
				1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('functionMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/function/del.do',
							method : 'POST',
							params : {
								ids : selData.ID
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON
										.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功" + result.msg
											+ "！");
									Ext.getCmp('functionGridpanel').store.load();
									MainController.initzTree();
								} else {
									Ext.Msg.alert('提示信息', "删除失败" + result.msg
											+ "！");
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
	addSave : function() {
		var win = Ext.getCmp('functionAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'functionAdd',
				getRootPath() + '/function/add.do', function(response, opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					Ext.getCmp('functionGridpanel').store.load();
					MainController.initzTree();
				} else {
					Ext.Msg.alert('提示信息', "保存失败" + result.msg + "！");
				}
			}, function(response, opts) {
				//ajax发送失败，关闭遮罩层
				myMask.hide();
				Ext.Msg.alert('提示信息', "保存失败!");
			});
		//数据验证没有通过，在这里关闭遮罩层
		if (status == false) {
			myMask.hide();
		}
	},
	editSave : function() {
		var win = Ext.getCmp('functionEditWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'functionEdit',
				getRootPath() + '/function/edit.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					Ext.getCmp('functionGridpanel').store.load();
					MainController.initzTree();
				} else {
					Ext.Msg.alert('提示信息', "保存失败" + result.msg + "！");
				}
			}, function(response, opts) {
				//ajax发送失败，关闭遮罩层
				myMask.hide();
				Ext.Msg.alert('提示信息', "保存失败!");
			});
		//数据验证没有通过，在这里关闭遮罩层
		if (status == false) {
			myMask.hide();
		}
	},
	initzTree : function() {
		var myMask = new Ext.LoadMask(Ext.getCmp('functionMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/function/queryTree.do',
			method : 'POST',
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					MainController._initzTree(result.data);
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
	_initzTree : function(treeNode) {
		var zTreeSetting = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : true
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "nodeId",
					pIdKey : "nodePid",
					rootPId : null
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					checkNode = treeNode;
					Ext.getCmp('functionGridpanel').setTitle(checkNode.name);
					Search( {
						storeParentId : 'functionGridpanel',
						type : 'form',
						extraParams : {
							pId : treeNode.nodeId
						}
					});
				},
				beforeDblClick : function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("functionzTree");
					if (treeNode.isParent) {
						zTree.expandNode(treeNode);
					}
					return false;
				}
			}
		};
		functionzTree = $.fn.zTree.init($("#functionzTree"), zTreeSetting,
				treeNode);
	}
});