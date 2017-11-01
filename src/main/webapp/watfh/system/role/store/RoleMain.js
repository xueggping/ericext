Ext.define('role.store.RoleMain', {
	extend : 'Ext.data.Store',
	alias : 'store.RoleMain',
	fields : [ 'CODE', 'NAME', 'NOTE' ],
	proxy : {
		type : 'ajax',// 异步获取数据
		url : getRootPath() + '/role/queryByPage.do',
		actionMethods : {
			read:'post'
		},
		reader : {
			type : 'json',
			root : 'content',
			totalProperty : 'totalElements'
		},
		extraParams : {}
	//附加参数一般放查询条件用
	},
	autoLoad : true
});