var MainController;
//自定义扩展：将Grid表头中的全选复选框取消复选
var tbnm;
Ext.define('impexpdata.controller.MainController',{
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires : ['impexpdata.view.impExpDataMain',
	            'impexpdata.view.importView'],
	init : function() {
		MainController = this;
	},
	downLoadTemplet:function(){//下载模板
		Ext.getCmp("importField").hide();
		if(tbnm==null){
			return Ext.Msg.alert('系统提示', '请选择表名！');
		};
		window.location.href = getRootPath() + '/portDataController/downloadTemplate.do?name=' + tbnm;
	},
	exportDataBtn:function(){//导出
		if(tbnm==null){
			return Ext.Msg.alert('系统提示', '请选择表名！');
		};
		var importField =Ext.getCmp("importField");
		importField.show();
		Ext.ComponentQuery.query('fieldset [actionName=cloumn]')[0].store.proxy.extraParams = {tablenm:tbnm};
		Ext.ComponentQuery.query('fieldset [actionName=cloumn]')[0].store.load();
		Ext.ComponentQuery.query('fieldset [actionName=cloumn]')[0].store.addListener('load', function(st, rds, opts) {
			Ext.ComponentQuery.query('fieldset [actionName=cloumn]')[0].setValue(rds[0].data.ckey);
		});
//		window.location.href = getRootPath() + '/portDataController/exprotData.do?tableName=' + tbnm;
	},
	exportData:function(){//导出
		var paramsTemp = GetValue('exportData',false);
		paramsTemp.tableName=tbnm;
		var form = Ext.getCmp('exportForm').getForm();
//		window.location.href = getRootPath() + '/portDataController/exprotData.do?paramsTemp=' + Ext.JSON.encode(paramsTemp );
		if (form.isValid()) {
        	form.submit({
    		url: getRootPath() +'/portDataController/exprotData.do',
			method : 'POST',
            params : {
        		paramsTemp: Ext.JSON.encode(paramsTemp)
			},
            waitMsg: '正在导出,请稍候...',
            timeout:360000, //seconds  
            success: function(form, action) {
        		var result = action.result;
				if (result.success == true) {
					window.location.href = getRootPath() + '/portDataController/downloadZip.do?name=' + encodeURI(result.fileName)+'&path='+result.path;
				}else{
					Ext.Msg.alert('提示信息',result.msg);
				}
            },
            failure: function(form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.CLIENT_INVALID:
                        Ext.Msg.alert('导出失败', '文件路径不合法！');
                        break;
                    case Ext.form.action.Action.CONNECT_FAILURE:
                        Ext.Msg.alert('警告', '导入失败！');
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('提示', action.result.msg);
                    }
            	}
        	});
        }else{
        	return;
        }
	},
	stnmFilter: function(obj) {
		tbnm=obj.value
		var importField = Ext.getCmp("importField");
		if(importField.hidden==false){
			var cloumnCbx = Ext.ComponentQuery.query('fieldset [actionName=cloumn]')[0];//Ext.getCmp('stnmCbx');
			cloumnCbx.setValue('');
			cloumnCbx.store.proxy.extraParams = {tablenm:tbnm};
			cloumnCbx.store.load();
		}
	},
	importData:function(){//导入窗口
		Ext.getCmp("importField").hide();
		if(tbnm==null){
			return Ext.Msg.alert('系统提示', '请选择表名！');
		};
		var importView = Ext.widget("importView");
        importView.show();
	},
	importExcel:function(){//导入
		var form = Ext.getCmp('importForm');
		if (form.isValid()) {
        	form.submit({
            url: getRootPath() + '/portDataController/importData.do',
            method: 'POST',
            params : {
				tableName : tbnm
			},
            waitMsg: '正在导入,请稍候...',
            success: function(form, action) {
        		var path = action.result.path;
                var errorCount = action.result.errorCount;
                var msg = action.result.msg;
                if (errorCount > 0) {
                    Ext.Msg.confirm('系统提示', '文件导入完毕，其中' + errorCount + '条数据没有导入，请下载错误文件说明！',
                    function(btn) {
                        if (btn == 'yes') {
                            window.location.href = getRootPath() + '/portDataController/downloadMsg.do?path=' + path;
                        }
                    },
                    this);
                } else {
                    Ext.Msg.alert('提示', msg);
                }
                },
            failure: function(form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.CLIENT_INVALID:
                        Ext.Msg.alert('导入失败', '文件路径不合法！');
                        break;
                    case Ext.form.action.Action.CONNECT_FAILURE:
                        Ext.Msg.alert('警告', '导入失败！');
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('提示', action.result.msg);
                    }
            	}
        	});
        }else{
        	 Ext.Msg.alert('提示', '导入文件路径不能为空！');
        }
	}
});