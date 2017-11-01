Ext.define('user.store.UserGrantRole',{
	extend : 'Ext.data.Store',
	alias : 'store.UserGrantRole',
	fields : [ 'CODE','NAME' ],
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/role/queryAll.do',
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