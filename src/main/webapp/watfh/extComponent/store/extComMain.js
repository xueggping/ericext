Ext.define('extComponent.store.extComMain', {
	extend : 'Ext.data.Store',
	alias : 'store.extComMain',
	pageSize : 20,
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath()+'/extComponent/queryByPage.do?isPageing=true',
		actionMethods : 'post',
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		},
		extraParams : {
			state : 1
		}
	//附加参数一般放查询条件用
	},
	autoLoad : true
});