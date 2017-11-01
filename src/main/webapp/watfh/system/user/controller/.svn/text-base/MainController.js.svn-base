var MainController;
Ext.define('user.controller.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'user.view.UserMain', 'user.view.UserAdd',
			'user.view.UserEdit', 'user.view.UserGrantRole',

			'Ext.ux.form.ItemSelector',

			'user.store.UserMain', 'user.store.UserGrantRole' ],
	init : function() {
		MainController = this;
	},
	find : function() {
		Search( {
			actionGroup : 'userFind',
			storeParentId : 'userGridPanel',
			type : 'form'
		});
	},
	reset : function() {
		Reset('userFind');
	},
	add : function() {
		var win = Ext.widget('UserAdd');
		win.show();
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('userGridPanel', [ 'USERNAME',
				'STATE' ], 1, 1);
		if (selData == false) {
			return;
		}
		if (selData.STATE == 0) {
			Ext.Msg.alert('提示信息', "已删除数据不可编辑！");
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('userMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/user/queryByUserName.do',
			method : 'POST',
			params : {
				userName : selData.USERNAME
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('UserEdit');
					win.show();
					SetValue('userEdit', result.data);
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
		var selData = GetGridPanelSelectValue('userGridPanel', [ 'USERNAME' ],
				1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('userMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/user/del.do',
							method : 'POST',
							params : {
								userNames : selData.USERNAME
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON
										.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功" + result.msg
											+ "！");
									MainController.find();
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
	resetPassword : function() {
		var selData = GetGridPanelSelectValue('userGridPanel', [ 'USERNAME',
				'STATE' ], 1, 1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要将 ' + selData.USERNAME + ' 的密码重置为888888吗？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('userMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/user/resetPassword.do',
							method : 'POST',
							params : {
								userName : selData.USERNAME
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON
										.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert("提示信息", "重置成功！");
									MainController.find();
								} else {
									Ext.Msg.alert('提示信息', "重置失败" + result.msg
											+ "！");
								}
							},
							failure : function(response, opts) {
								myMask.hide();
								Ext.Msg.alert('提示信息', "重置失败！");
							}
						});
					}
				});
	},
	grantRole : function() {
		var selData = GetGridPanelSelectValue('userGridPanel', [ 'NAME','USERNAME',
				'STATE' ], 1, 1);
		if (selData == false) {
			return;
		}
		if (selData.STATE == 0) {
			Ext.Msg.alert('提示信息', "已删除数据不可分配角色！");
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('userMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/user/queryRoleByUserName.do',
			method : 'POST',
			params : {
				userName : selData.USERNAME
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('UserGrantRole');
					win.show();
					var data = {};
					data.name = selData.NAME;
					data.userName = selData.USERNAME;
					data.role = result.data;
					SetValue('userGrantRole', data);
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
	addSave : function() {
		var win = Ext.getCmp('userAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'userAdd', getRootPath() + '/user/add.do',
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
		var win = Ext.getCmp('userEditWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'userEdit',
				getRootPath() + '/user/edit.do', function(response, opts) {
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
	userGrantRoleSave : function() {
		var win = Ext.getCmp('userGrantRoleWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'userGrantRole', getRootPath() + '/user/grantRole.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
					myMask.hide();
					var result = Ext.JSON.decode(response.responseText);
					if (result.success == true) {
						win.close();
						Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
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
	}
});
