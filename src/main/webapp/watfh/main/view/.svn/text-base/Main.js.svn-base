Ext.define('main.view.Main', {
	extend : 'Ext.panel.Panel',
	id : 'mainView',
	alias : 'widget.Main',
	controller: 'main',
	layout : 'border',
	border : false,
	listeners: {
        afterrender : 'initMain'
   },
	items : [ {
		xtype : 'tabpanel',
		region: 'center',
		plugins : [{
			ptype: 'tabclosemenu',
			closeTabText : '关闭当前页',
			closeOthersTabsText : '关闭其它页',
			closeAllTabsText : '关闭所有页'
		}],
		id : tabpanelId,
		items:[
        	{
        		xtype: 'panel',
        		title: '逐日降水量 >> 日降水量',
//        		HTML : '<IFRAME SRC="'+GETROOTPATH()+'/JSP/SYSTEM/LOGINFO.JSP" FRAMEBORDER="0" WIDTH="100%" SCROLLING="AUTO" HEIGHT="100%"></IFRAME>'
        		html : '<iframe src="'+getRootPath()+'/jsp/common/common.jsp?module=V_HY_DP_C&scroll=true&executeSql=true&functionId=294b79b2eeb04f2184f50fd878a6e828" frameBorder="0" width="100%" scrolling="auto" height="100%"></iframe>'
        	}
        ]
	},{
		xtype : 'panel',
		region: 'west',
		title:'功能菜单',
		layout: 'accordion',
		id: westMenuId,
		collapseMode: 'header', // border专用，如果可以隐藏，隐藏后样子'mini'：没有。‘header’：留个头
		hideCollapseTool: false,//title上隐藏展开折叠按钮。
		collapseDirection: 'left', //border专用，如果可以隐藏，隐藏时按钮方向，感觉没啥用
		collapsed: false, //如果可以隐藏，是否默认隐藏
		animCollapse: true,//如果可以隐藏，是否有隐藏动画
		collapsible: true,//如果可以隐藏，是否允许完全隐藏
		split: true,//是否可以隐藏默认false
		width : 210
	},{
		xtype : 'panel',
		region: 'north',
		border : false,
        hideCollapseTool: true,//title上隐藏展开折叠按钮。
		split: true,//是否可以隐藏默认false
		collapseDirection: 'top', //border专用，如果可以隐藏，隐藏时按钮方向，感觉没啥用
		collapsed: false, //如果可以隐藏，是否默认隐藏
		animCollapse: true,//如果可以隐藏，是否有隐藏动画
		collapsible: false,//如果可以隐藏，是否允许完全隐藏
		collapseMode: 'mini', // border专用，如果可以隐藏，隐藏后样子'mini'：没有。‘header’：留个头
		height : 78,
//		bodyStyle : 'background-image:url('+getRootPath()+'/image/system/mainPage/bg.jpg); background-size:100% 90px;',
		layout : 'border',
//		bbar : ['->',{
//				xtype : 'button',
//				listeners : {
//					click : 'editPassword'
//				},
//				text : '修改密码'
//			},{
//				xtype : 'button',
//				listeners : {
//					click : 'logout'
//				},
//				text : '退出登录'
//			}],
		items :[{
			xtype : 'container',
			region: 'west',
			border : false,
//			width: 620,
			width : 570,
			html : '<div style="background-repeat:no-repeat;background-image:url('+getRootPath()+'/image/system/mainPage/sunshine.png); background-size:auto 100px;"><img src="'+getRootPath()+'/image/system/mainPage/title.png" border="0"  height=67 style="margin:6.5;cursor:pointer" /><div/>'
		},
		{
			xtype : 'container',
			region: 'center',
//			html : '<div style="height:100%; width:100%; background-image:url('+getRootPath()+'/image/system/mainPage/sunshine.png); background-size:auto 100px;"><div/>',
			border : false
		},
		{
			xtype : 'container',
			region: 'east',
//			id : mainButPanelId,
			width : 260,
			layout : 'column',
			border : false,
			html:'<div style="height:100%; width:100%;margin-top:20px;">' +
				'<div align="right" style="height:30px;"><span class="welcome-label-text" style="margin-right:10px;">欢迎您: ' + userName + '<span></div>'+
				'<div style="height:33px; width:253px;background:#6FACEA;filter:Alpha(Opacity=80);background-image:url('+getRootPath()+'/image/system/menu/bg.png);">' +
					'<div onclick="MainController.editPassword()" align="center" style="float:left;width:100px;cursor:pointer;margin-left:25px;margin-top:5px;">' +
						'<div style="float:left;margin-right:5px"><img src="'+getRootPath()+'/image/system/menu/editPass.png" /></div>' +
						'<div style="float:left;"><span class="welcome-label-text">修改密码</span></div>' +
					'</div>' +
					'<div onclick="MainController.logout()" align="center" style="float:right;width:100px;cursor:pointer;margin-left:25px;margin-top:5px;">' +
						'<div style="float:left;margin-right:5px"><img src="'+getRootPath()+'/image/system/menu/exit.png" /></div>' +
						'<div style="float:left;"><span class="welcome-label-text">退出系统</span></div>' +
					'</div>' +
				'</div>'+
			'</div>'
		}
		]
	}]
});
