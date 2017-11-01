Ext.define('functionpage.store.FunctionPageMain', {
	extend : 'Ext.data.Store',
	alias : 'store.FunctionPageMain',
	pageSize : GLOBLE_PAGESIZE,
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/funcpage/queryByPage.do?isPageing=true',
		actionMethods : {
			read : 'post'
		},
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		},
		extraParams : {}//true:分页，false：不分页
	},
	autoLoad : true
});