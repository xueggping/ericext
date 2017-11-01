Ext.define('functionpage.store.WfComponentStore', {
	extend : 'Ext.data.Store',
	alias : 'store.WfComponentStore',
//	pageSize : GLOBLE_PAGESIZE,
	model : 'functionpage.model.models',
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath()+'/extComponent/queryByPage.do?isPageing=false',
		actionMethods : {
			read : 'post'
		},
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		}
	}
});