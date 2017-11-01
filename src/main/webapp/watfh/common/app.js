Ext.application({
    name: 'common',
    
	appFolder: getRootPath()+'/watfh/common',
	
    requires: [ 'common.controller.MainController' ],

    mainView: 'common.view.commonMain'
});
