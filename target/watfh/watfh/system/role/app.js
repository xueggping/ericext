Ext.application({
    name: 'role',
    
	appFolder: getRootPath()+'/watfh/system/role',
	
    requires: [ 'role.controller.MainController' ],

    mainView: 'role.view.RoleMain'
});
