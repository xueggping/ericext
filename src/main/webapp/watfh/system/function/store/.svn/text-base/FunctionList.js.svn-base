Ext.define('function.store.FunctionList', {
	extend : 'Ext.data.Store',
	alias : 'store.FunctionList',
	pageSize : GLOBLE_PAGESIZE,
	fields : [ 'NAME', 'FDESC', 'IS_ENABLED', 'FURL', 'IMGULR', 'NOTE' ],
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/function/queryByPage.do',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		},
		extraParams : {
			pId : checkNode.code
		}
	},
	autoLoad : true
});