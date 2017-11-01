Ext.define('user.store.UserMain', {
	extend : 'Ext.data.Store',
	alias : 'store.UserMain',
	pageSize : GLOBLE_PAGESIZE,
	fields : [ 'USERNAME', 'NAME', 'PHONE_NUMBER', 'EMAIL', 'NOTE',
			"IS_ENABLED", 'LAST_UPDATE_TIME' ],
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/user/queryByPage.do',
		actionMethods : {
			read:'post'
		},
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