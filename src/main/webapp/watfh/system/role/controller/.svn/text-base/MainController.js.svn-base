var MainController;
var authorizationFunzTree = null;
Ext.define('role.controller.MainController',{
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'role.view.RoleMain', 'role.view.RoleAdd',
			'role.view.RoleEdit', 'role.view.RoleAuthorization',

			'role.store.RoleMain' ],
	init : function() {
		MainController = this;
	},

	find : function() {
		Search( {
			actionGroup : 'roleFind',
			storeParentId : 'roleGridPanel',
			type : 'form'
		});
	},
	reset : function() {
		Reset('roleFind');
	},

	add : function() {
		var win = Ext.widget('RoleAdd');
		win.show();
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('roleGridPanel',
				[ 'CODE' ], 1, 1);
		if (selData == false) {
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('roleMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/role/queryByCode.do',
			method : 'POST',
			params : {
				code : selData.CODE
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('RoleEdit');
					win.show();
					SetValue('roleEdit', result.data);
				} else {
					Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试" + result.msg
							+ "！");
				}
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
	},
	del : function() {
		var selData = GetGridPanelSelectValue('roleGridPanel',
				[ 'CODE' ], 1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(
						btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext
								.getCmp('roleMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/role/del.do',
							method : 'POST',
							params : {
								codes : selData.CODE
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON
										.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功"
											+ result.msg + "！");
									MainController.find();
								} else {
									Ext.Msg.alert('提示信息', "删除失败"
											+ result.msg + "！");
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
	authorization : function() {
		var selData = GetGridPanelSelectValue('roleGridPanel', [ 'CODE' ], 1, 1);
		if (selData == false) {
			return;
		}
		var win = Ext.widget('RoleAuthorization');
		win.roleCode = selData.CODE;
		win.show();
		
	},
	addSave : function() {
		var win = Ext.getCmp('roleAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'roleAdd',
				getRootPath() + '/role/add.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					MainController.find();
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
		var win = Ext.getCmp('roleEditWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'roleEdit',
				getRootPath() + '/role/edit.do', function(response,
						opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					MainController.find();
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
	roleAuthorizationSave : function() {
		var checkedNodes = authorizationFunzTree.getCheckedNodes(true);
		var funIds ="";
		for(var i in checkedNodes){
			funIds += checkedNodes[i].id+","
		}
		var win = Ext.getCmp('roleAuthorizationWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/role/roleAuthorization.do',
			method : 'POST',
			params : {
				roleCode : win.roleCode,
				funIds : funIds
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert("提示信息", "保存成功！");
				} else {
					Ext.Msg.alert('提示信息', "保存失败" + result.msg + "！");
				}
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', "保存失败！");
			}
		});
	},
	initAauthorizationFunzTree : function(win) {
		var myMask = new Ext.LoadMask(Ext.getCmp('roleMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
//			url : getRootPath() + '/function/queryTree.do',
			url : getRootPath() + '/role/getRoleFunInfo.do',
			method : 'POST',
			params : {
				roleCode : win.roleCode
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					MainController._initAauthorizationFunzTree(result.data);
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
	_initAauthorizationFunzTree : function(data) {
		var zTreeSetting = {
			data : {
				keep : {
					parent : true
				}
			},
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
				enable : true
			},
			callback : {
				beforeDblClick : function(treeId, treeNode) {
					var zTree = $.fn.zTree
							.getZTreeObj("authorizationFunzTree");
					if (treeNode.isParent) {
						zTree.expandNode(treeNode);
					}
					return false;
				}
			}
		};
		authorizationFunzTree = $.fn.zTree.init(
				$("#authorizationFunzTree"), zTreeSetting, data.treeNode);
		for(var i in data.funId){
			authorizationFunzTree.checkNode(authorizationFunzTree.getNodeByParam("id", data.funId[i], null), true, false);
		}
	}
});
