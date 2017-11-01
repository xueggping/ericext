Ext.define('zhdzcx.store.rainfallStore', {
	extend : 'Ext.data.Store',
	alias : 'store.rainfallStore',
	pageSize : GLOBLE_PAGESIZE,
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/zhdzcxController/getData.do',
		actionMethods : {
			read : 'post'
		},
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		},
		extraParams : {
			type:1
		}
	}
});
