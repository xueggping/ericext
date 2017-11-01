var MainController;
var dictionaryzTree = null;
var checkNode = {
	code : "root",
	name : "数据字典"
};
Ext.define('dictionary.controller.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'dictionary.view.DictionaryMain',
			'dictionary.view.DictionaryAdd', 'dictionary.view.DictionaryEdit',

			'dictionary.store.DictionaryList' ],
	init : function() {
		MainController = this;
	},
	add : function() {
		var win = Ext.widget('DictionaryAdd');
		win.show();
		SetValue('dictionaryAdd', {
			pcode : checkNode.code,
			pname : checkNode.name
		});
	},
	edit : function() {
		var selData = GetGridPanelSelectValue('dictionaryGridpanel',
				[ 'code' ], 1, 1);
		if (selData == false) {
			return;
		}
		var myMask = new Ext.LoadMask(Ext.getCmp('dictionaryMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/dictionary/queryByCode.do',
			method : 'POST',
			params : {
				code : selData.code
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					var win = Ext.widget('DictionaryEdit');
					win.show();
					SetValue('dictionaryEdit', result.data);
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
		var selData = GetGridPanelSelectValue('dictionaryGridpanel', [ 'code' ],
				1);
		if (selData == false) {
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这 ' + selData.allDatalength + ' 条信息？', function(btn) {
					if (btn == "yes") {
						var myMask = new Ext.LoadMask(Ext.getCmp('dictionaryMain'), {
							msg : "请求进行中,请等待..."
						});
						myMask.show();
						Ext.Ajax.request( {
							url : getRootPath() + '/dictionary/del.do',
							method : 'POST',
							params : {
								codes : selData.code
							},
							success : function(response, opts) {
								myMask.hide();
								var result = Ext.JSON
										.decode(response.responseText);
								if (result.success == true) {
									Ext.Msg.alert('提示信息', "删除成功" + result.msg
											+ "！");
									Ext.getCmp('dictionaryGridpanel').store.load();
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
		var win = Ext.getCmp('dictionaryAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'dictionaryAdd',
				getRootPath() + '/dictionary/add.do', function(response, opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					Ext.getCmp('dictionaryGridpanel').store.load();
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
		var win = Ext.getCmp('dictionaryEditWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'dictionaryEdit',
				getRootPath() + '/dictionary/edit.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
					Ext.getCmp('dictionaryGridpanel').store.load();
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
		var myMask = new Ext.LoadMask(Ext.getCmp('dictionaryMain'), {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : getRootPath() + '/dictionary/queryTree.do',
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
					rootPId : null
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					checkNode = treeNode;
					Ext.getCmp('dictionaryGridpanel').setTitle(checkNode.name);
					Search( {
						storeParentId : 'dictionaryGridpanel',
						type : 'form',
						extraParams : {
							pId : treeNode.id
						}
					});
				},
				beforeDblClick : function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("dictionaryzTree");
					if (treeNode.isParent) {
						zTree.expandNode(treeNode);
					}
					return false;
				}
			}
		};
		dictionaryzTree = $.fn.zTree.init($("#dictionaryzTree"), zTreeSetting,
				treeNode);
	}
});