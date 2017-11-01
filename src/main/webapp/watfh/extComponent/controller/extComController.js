var MainController;
Ext.define('extComponent.controller.extComController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'extComponent.view.extComMain', 'extComponent.view.extComAdd',
			'extComponent.view.extComEdit','extComponent.view.extComDetail',

			'Ext.ux.form.ItemSelector',

			'extComponent.store.extComMain'],
	init : function() {
		MainController = this;
	},
	find : function() {
		Search( {
			actionGroup : 'extComponentFind',
			storeParentId : 'extComponentGridPanel',
			type : 'form'
		});
	},
	reset : function() {
		Reset('extComponentFind');
	},
	add : function() {
		var win = Ext.widget('extComAdd');
		win.show();
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('extComponentGridPanel', [ 'id'], 1, 1);
		if (selData == false) {
			return;
		}
		if (selData.STATE == 0) {
			Ext.Msg.alert('提示信息', "已删除数据不可编辑！");
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('extComMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/extComponent/queryById.do',
			method : 'POST',
			params : {
				Id : selData.id
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('extComEdit');
					win.show();
					SetValue('extComEdit', result.data);
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
	detail : function() {
		var selData = GetGridPanelSelectValue('extComponentGridPanel', [ 'id'], 1, 1);
		if (selData == false) {
			return;
		}
		if (selData.STATE == 0) {
			Ext.Msg.alert('提示信息', "已删除数据不可编辑！");
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('extComMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/extComponent/queryById.do',
			method : 'POST',
			params : {
				Id : selData.id
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('extComDetail');
					win.show();
					SetValue('extComDetail', result.data);
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
		var selData = GetGridPanelSelectValue('extComponentGridPanel', [ 'id' ],
				1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('extComMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/extComponent/del.do',
							method : 'POST',
							params : {
								ids : selData.id
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

	addSave : function() {
		var win = Ext.getCmp('extComAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'extComAdd', getRootPath() + '/extComponent/add.do',
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
		var win = Ext.getCmp('extComEditWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'extComEdit',
				getRootPath() + '/extComponent/edit.do', function(response, opts) {
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
	}
});
