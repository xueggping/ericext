Ext.define('dictionary.store.DictionaryList', {
	extend : 'Ext.data.Store',
	alias : 'store.DictionaryList',
	pageSize : GLOBLE_PAGESIZE,
	fields : [ 'code', 'name', 'ckey', 'note' ],
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/dictionary/queryByPage.do',
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