var logMainController;
Ext.define('logInfo.controller.LoginfoMainController',{
	extend:'Ext.app.ViewController',
	alias:'controller.logMain',
	requires:['logInfo.view.LoginfoMain','logInfo.view.LogError',
	          'logInfo.store.LoginfoMain'],
	init : function(){
		logMainController = this;
	},
	queryWithCondition : function(){
		Search({
			actionGroup:'logFind',
			storeParentId:'loginfoGridpanelPagingtoolbar',
			type:'form'
		});
	},
	showerror : function(){ 
		setTimeout(function(){
			var dataGridSer = Ext.getCmp('loginfoGridpanel');
			var last = dataGridSer.getSelectionModel().lastSelected;
			if(last){
				var errorinfo = last.data.erroInfo;
				var flag = last.data.actionFlag;
				if(flag == '1'){
//					var msg = '<div style="width: 150px;margin: auto;color: #11EE3D;border-radius: 5px;">无异常信息</div>';
//					Ext.Msg.alert('提示信息',msg);
//					return;
				}else{
					logMainController.openerrorwin(errorinfo);
				}
			}else{
				Ext.Msg.alert('提示信息','请重新选择');
			}
		},1)
	},
	openerrorwin : function(errorinfo){
		var win=Ext.widget('LogError');
		win.show();
		Ext.getCmp('errorinfo').setValue(errorinfo);
	},
	reset : function(){
		Reset('logFind');
		Ext.getCmp("startDate").setValue(Ext.Date.format(new Date().addMonth( - 1), 'Y-m-d H:i:s'));
		Ext.getCmp("endDate").setValue(Ext.Date.format(new Date(), 'Y-m-d H:i:s'));
	},
	delLogs : function(){
		var selData = GetGridPanelSelectValue('loginfoGridpanel',['id'],1);
		if(selData == false){
			return;
		}
		Ext.MessageBox.confirm('温馨提示',
				'确定要删除这'+selData.allDatalength+'条数据?',
				function(btn){
			        if(btn=="yes"){
			        	var delMask = new Ext.LoadMask(Ext.getCmp('loginfoMain'),{
			        		msg:'请求进行中,请等待...'
			        	});
			        	delMask.show();
			        	Ext.Ajax.request({
			        		url:getRootPath()+'/loginfo/delLogs.do',
			        		method:'post',
			        		params:{ids:selData.id},
			        		success:function(response,opts){
			        			delMask.hide();
			        			var result = Ext.JSON.decode(response.responseText);
			        			if(result.success == true){
			        				Ext.Msg.alert('提示信息','删除成功'+result.msg+'!');
			        				logMainController.queryWithCondition();
			        			}else{
			        				Ext.Msg.alert('提示信息','删除失败！')
			        			}
			        		},
			        		failure:function(response,opts){
			        			delMask.hide();
			        			Ext.Msg.alert('提示信息','删除失败！');
			        		}
			        	});
			        }
		        });
	}
	

});
