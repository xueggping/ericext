Ext.application({
    name: 'functionpage',
	appFolder: getRootPath()+'/watfh/system/functionPage',
    requires: [ 'functionpage.controller.MainController'],
    mainView: 'functionpage.view.FunctionPageMain',
//    models:['models']
});
