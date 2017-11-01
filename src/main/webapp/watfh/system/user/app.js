Ext.application({
    name: 'user',
    
	appFolder: getRootPath()+'/watfh/system/user',
	
    requires: [ 'user.controller.MainController' ],

    mainView: 'user.view.UserMain'
});
