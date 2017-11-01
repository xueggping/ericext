Ext.define('logInfo.store.LoginfoMain',{
	extend:'Ext.data.Store',
	alias:'store.loginfoMain',
	pageSize:GLOBLE_PAGESIZE,
	fields:['id','funName','callerIP','erroInfo','sTime','eTime','userName','actionTime','actionFlag'],
	proxy:{
		type:'ajax',
		url:getRootPath()+'/loginfo/querySyslogByPage.do',
		actionMethods:'post',
		reader:{
			type:'json',
			root:'content',
			totalProperty:'totalElements'
		}
	},
	autoLoad:true
});