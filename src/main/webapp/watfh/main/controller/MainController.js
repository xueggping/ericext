var menuButs = null;
var westMenuId = 'westMenuId';
var mainButPanelId = "mainButPanelId"
var tabpanelId = 'tabpanelId';
var menuButsWidth = 100;
var MainController;
Ext.define('main.controller.MainController',{
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : [ 'main.view.Main',
			'main.view.UserEditPassword', 'Ext.ux.IFrame',
			'Ext.ux.TabCloseMenu' ],
	init : function() {
		MainController = this;
	},
	logout : function() {
		//记录退出日志
		Ext.Ajax.request( {
			url : getRootPath() + '/logout/logout.do',
			method : 'GET',
			success : function(response, opts) {
				window.location.href=getRootPath()+'/jsp/logout.jsp';
			},
			failure : function(response, opts) {
			}
		});
	},
	editPassword : function() {
		var win = Ext.widget('UserEditPassword');
		win.show();
	},
	editPasswordSave : function() {
		var win = Ext.getCmp('userEditPasswordWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'userEditPassword', getRootPath() + '/user/editPassword.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
					myMask.hide();
					var result = Ext.JSON.decode(response.responseText);
					if (result.success == true) {
						win.close();
						Ext.Msg.alert('提示信息', "修改成功" + result.msg + "！");
						MainController.find();
					} else {
						Ext.Msg.alert('提示信息', "修改失败" + result.msg + "！");
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
	initMain : function() {
		fun = Ext.JSON.decode(fun);
		if (fun.length > 0) {
			menuButs = MainController.initData(fun[0]).children;
			var westMenu = Ext.getCmp(westMenuId);
//			var mainButPanel = Ext.getCmp(mainButPanelId);
//			mainButPanel.setWidth(menuButs.length
//					* menuButsWidth);
			for ( var i = 0; i < menuButs.length; i++) {
//				mainButPanel.add(MainController
//						.getMenuButItem(menuButs[i]));
				westMenu.add(MainController
						.getWestMenuItem(menuButs[i]));
			}
			if (menuButs.length > 0) {
				setTimeout(
						'mbtMenuButOnclick("' + menuButs[0].id + '")',
						50);
			}
		}
	},
	initData : function(data) {
		data.url = data.data.furl
		if (data.data.imgUlr) {
			data.iconTemp = data.data.imgUlr.split("-")[0]
			data.icon = getRootPath() + data.iconTemp;
		}
		data.checked = null;
		for ( var i in data.children) {
			data.children[i] = MainController
					.initData(data.children[i])
		}
		return data;
	},
	getWestMenuItem : function(menuBut) {
		var store = Ext.create('Ext.data.TreeStore', {
			root : {
				expanded : true,
				children : menuBut.children
			}
		});
		var titleHtml = '<span style="font-family:\'宋体\';font-size:12px;font-style:normal;text-decoration:none;">' + menuBut.text + '</span>';
		if (typeof (menuBut.iconTemp) == "string"
				&& menuBut.iconTemp.length > 0) {
			titleHtml = '<table border=0><tr><td><img src="'
					+ menuBut.icon
					+ '" width="16" height="16"  style="vertical-align:bottom"/></td><td width=4></td><td width=100>'
					+ titleHtml + '</td></tr></table>';
		}
		return Ext.create('Ext.tree.Panel', {
			title : titleHtml,
			id : westMenuId + menuBut.id,
			menuBut : menuBut,
			rootVisible : false,
			store : store,
			viewConfig : {},
			listeners : {
				itemclick : function(tree, record, item, index,
						e, eOpts) {
					MainController.mtOnclick(record.raw);
				},
				beforeexpand : function(p, animate, eOpts) {
					if (p.menuBut.id == checkId) {
						return true;
					} else {
						mbtMenuButOnclick(p.menuBut.id);
						return false;
					}
				}
			}
		});
	},
	mtOnclick : function(tree) {
		if(tree.url == false){
			return;
		}
		
		if (tree.leaf == false) {
			return;
		}
		var id = tabpanelId + tree.id;
		var tabpanel = Ext.getCmp(tabpanelId);

		var have = false;
		tabpanel.items.each(function(item) {
			if (item.id == id) {
				tabpanel.setActiveTab(item);
				have = true;
				return;
			}
			//            if(item.closable){
				//                tabpanel.remove(item);
				//            }
			});
		if (have) {
			return;
		}
		var tabpanelItem = MainController.getTabpanelItem(tree, id, true);
		tabpanel.add(tabpanelItem);
		tabpanel.setActiveTab(tabpanelItem);
	},
	getTabpanelItem : function(tree, id, closable) {
		var executeSql = tree.data.executeSql;
		var functionId = tree.id;
		var height = '100%';
		if(tree.url.indexOf("?")>0){
			height = tree.url.indexOf("scroll")>0?1200:height;
			tree.url = tree.url + "&functionId=" + functionId + "&executeSql="+executeSql;
		}else{
			tree.url = tree.url + "?functionId=" + functionId + "&executeSql="+executeSql;
		}
		return Ext.create('Ext.panel.Panel', {
			title : tree.text,
			id : id,
			closable : closable,
			width : '100%',
			scrollable : true,
			height : height,
			items : {
				xtype : 'uxiframe',
				border : 0,
				scrollable : true,
				src : getRootPath() + "/" + tree.url,
				width : '100%',
				height : height
			}
		});
	},
	getMenuButItem : function(menuBut) {
		menuBut.bUrl = getRootPath()
				+ menuBut.data.imgUlr.split("-")[1];
		menuBut.bUrld = getRootPath()
				+ menuBut.data.imgUlr.split("-")[2];
		return Ext.create('Ext.Container',{
			border : false,
			width : 80,
			height : 80,
			margin : '10 5 10 5',
			html : '<img id="'
					+ menuBut.id
					+ '" title="'
					+ menuBut.text
					+ '" onclick="mbtMenuButOnclick(\''
					+ menuBut.id
					+ '\');" onmousemove="mbtMouseover(\''
					+ menuBut.id
					+ '\',\''
					+ menuBut.bUrld
					+ '\');" onmouseout="mbtMouseout(\''
					+ menuBut.id
					+ '\',\''
					+ menuBut.bUrl
					+ '\');" src="'
					+ menuBut.bUrl
					+ '" border="0"  height=70 style="cursor:pointer" />'
		});
	}
});
var checkId = null;
mbtMenuButOnclick = function(id) {
	checkId = id;
	Ext.getCmp(westMenuId + id).expand();
//	for ( var i in menuButs) {
//		if (menuButs[i].id == checkId) {
//			document.getElementById(menuButs[i].id).src = menuButs[i].bUrld;
//		} else {
//			document.getElementById(menuButs[i].id).src = menuButs[i].bUrl;
//		}
//	}
};
mbtMouseout = function(id, p) {
	if (id == checkId) {
		return;
	}
	document.getElementById(id).src = p;
};
mbtMouseover = function(id,p){
	document.getElementById(id).src = p;
}