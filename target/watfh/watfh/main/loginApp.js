$(document).keydown(function(event){
	if(event.keyCode==13){
		MainController.login();
	}
});
Ext.application( {
 	mainView: 'login.view.AutoScrollPanel'
});
var MainController;
Ext.define('login.controller.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	init : function() {
		MainController = this;
//		Ext.Ajax.request( {
//			url : getRootPath() + '/daping/getVersion.do',
//			success : function(response, opts) {
//				var result = Ext.JSON.decode(response.responseText);
//				$('#version').html(result);
//			},
//			failure : function(response, opts) {
//			}
//		});
	},
	login : function(){
		var userName = $('#userName').val();
		var userPwd = $('#userPwd').val();
		Ext.Ajax.request( {
			url : getRootPath() + '/j_spring_security_check',
			method : 'POST',
			params : {
				j_username : userName,
				j_password : userPwd
			},
			success : function(response, opts) {
				window.location.href=getRootPath()+'/jsp/main.jsp';
			},
			failure : function(response, opts) {
				if(response.status == 0){
					window.location.href=getRootPath()+'/jsp/main.jsp';
				}
				Ext.toast( {
					html : "请登录！",
					autoCloseDelay : 1000,
					align : 'br'
				});
			}
		});
	}
});
Ext.define('login.view.AutoScrollPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.autoScrollPanel',
    layout: 'fit',
    items: {
        xtype: 'container',
        layout: 'fit',
    	autoScroll : true,
        items:[{
    		xtype: 'loginMain'
    	}]
    }
});
Ext.define('login.view.LoginMain', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.loginMain',
	controller : 'main',
	layout : 'vbox',
	border : false,
	minWidth : 928,
	minHeight : 472,
	listeners: {
            afterrender : function (){
				if(!Ext.isEmpty(errorMsg)){
					Ext.toast( {
						html : errorMsg,
						autoCloseDelay : 1000,
						align : 'br'
					});
					errorMsg = "";
				}
            }
    },
	items : [ {
		xtype : 'panel',
		width : '100%',
		layout : 'hbox',
		border : false,
		flex : 3,
		minHeight:472,
		items : [ {
			xtype : 'container',
			height : '100%',
			flex : 1
		}, {
			xtype : 'container',
			height : '100%',
			layout : 'vbox',
			width : 928,
			items : [ {
					xtype : 'container',
					width : '100%',
					flex : 1
				}, {
					xtype : 'panel',
					width : '100%',
					id : 'heheda',
					layout : 'vbox',
					border : false,
					height : 472,
					bodyStyle : 'background-image:url('+getRootPath()+'/image/system/longinPage/bg.png); background-size:928px 472px;',
					items : [ {
						xtype : 'container',
						width : '100%',
						flex : 1
					}, {
						xtype : 'container',
						layout : 'column',
						width : '100%',
						items : [ {
							xtype : 'container',
							width : 165,
							margin : '0 0 0 250',
							html : '<table border="0"><tr><th><img style="margin:0 0 0 0;" src="'+ getRootPath()+'/image/system/longinPage/yonghuming.png" border="0"  height=30 width=30/></th><th><input id="userName" value="admin" type="text" style="width:120px; margin:8 0 0 0; border:0px; font-size:14pt; "></th></tr></table>'
						},{
							xtype : 'container',
							width : 165,
							inputType : 'password',
							margin : '0 0 0 50',
							html : '<table border="0"><tr><th><img style="margin:0 0 0 0;" src="'+ getRootPath()+'/image/system/longinPage/mima.png" border="0"  height=30 width=30/></th><th><input id="userPwd" value="admin" type="password" style="width:120px; margin:8 0 0 0; border:0px; font-size:14pt; "></th></tr></table>'
						},{
							xtype : 'button',
							width : 80,
							height : 30,
							margin : '10 0 0 30',
							listeners: {
					            click: 'login'
					        },
							text : '登&nbsp&nbsp&nbsp录'
						},{
							xtype : 'panel',
							width : 165,
							border:false,
							height:1,
							bodyStyle : 'background:#157FCB;',
							margin : '0 0 50 250'
						},{
							xtype : 'panel',
							width : 165,
							border:false,
							height:1,
							bodyStyle : 'background:#157FCB;',
							margin : '0 0 0 50',
						},{
							xtype : 'panel',
							width : 165,
							border:false,
							height:20,
							html:'<div style="color:#157FCB;" id="version"></div>',
							margin : '30 0 0 -30',
						}]
					}]
				}
			 ]
		}, {
			xtype : 'container',
			height : '100%',
			flex : 1
		} ]
	}, {
		xtype : 'panel',
		width : '100%',
		layout : 'hbox',
		border : false,
		bodyStyle : 'background:#569DE6;',
		flex : 2,
		items : [ {
			xtype : 'container',
			height : '100%',
			flex : 1
		}, {
			xtype : 'container',
			height : '100%',
			layout : 'vbox',
			width : 605,
			items : [ {
					xtype : 'container',
					width : '100%',
					flex : 1
				}, {
					xtype : 'container',
					width : '100%',
					height : 248,
					html : '<img src="'+ getRootPath()+'/image/system/longinPage/bg2.png" border="0"  height=248 width=605/>'
				}
			 ]
		}, {
			xtype : 'container',
			height : '100%',
			flex : 1
		} ]
	} ]
});
