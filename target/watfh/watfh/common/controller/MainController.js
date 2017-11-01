var MC;
var FG;
Ext.define('common.controller.MainController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.main',
	requires: [
		'common.view.commonMain',
		'common.view.commonChart',
		'common.view.commonYearbook',
		'Ext.ux.ZTreeComboBox',
		'Ext.ux.grid.DPrinter',
		'Ext.ux.grid.plugin.Exporter'
    ],
    init: function() {
        MC = this;
        MC.initMainView();
    },
    containChart:false,
    /**
     * 初始化north部分查询条件的panel
     * @param {Object} o
     * @return {TypeName} 
     */
    initNorthPanel:function(o){
    	if(o.components.length == 0){
    		return;
    	}
    	Ext.Array.each(o.components,function(compnent,index,itmes){
    		//处理扩展组件
    		o.components[index] = MC.extendsCmp(compnent,"search");
    		//处理其他属性
    		Ext.apply(compnent,MC.decodeOthProperty(compnent.otherProperty));
    		//处理公共转换
    		MC.transferTF(compnent);
    		//添加actionGroup属性
    		MC.setAGP(compnent,"search");
    		//初始化开始时间和结束时间的值（默认值为当前时间为结束时间，开始时间当前时间减三年）//后期可调整
    		MC.initSearchValue(compnent);
    	});
    	//添加查询按钮
    	var find = {
			xtype : 'button',
			margin : '5 5 5 5',
			text : '查询',
			icon: getRootPath() + '/image/icon/magnifier.png',
			listeners : {
				click : 'find'
			}
		};
    	//添加重置按钮
    	var reset =  {
			xtype : 'button',
			margin : '5 5 5 5',
			listeners : {
				click : 'reset'
			},
			icon: getRootPath() + '/image/icon/restart.png',
			text : '重置'
		}; 
		o.components.push(find);
		o.components.push(reset);
    	return new Ext.panel.Panel(Ext.applyIf({
    		id:'northPanel' + module,
			region : 'north',
			border : false,
			chartTile:o.title,
			height : o.height || '10%',
			layout : o.layout || 'column',
			items : o.components
		},MC.decodeOthProperty(o.otherProperty)));
    },
    /**
     * 初始化south部分图标的panel部分
     * @param {Object} o
     * @return {TypeName} 
     */
    initSouthPanel:function(o){
    	return new Ext.panel.Panel(Ext.applyIf({
    		id:'southPanel' + module,
			region : 'south',
			border : false,
			height:o.height || '30%',
			layout : o.layout ||'fit',
//			listeners:{
//				afterrender:MC.chart
//			},
			html:'<div id="chartPanel'+module+'" style="width:100%;height:100%;" />'
		},MC.decodeOthProperty(o.otherProperty)));
    },
    /**
     * 初始化center部分的panel部分
     * @param {Object} o
     * @return {TypeName} 
     */
    initCenterPanel:function(o){
    	var buttons = new Array();
    	MC.cloumns = new Array();
    	Ext.Array.each(o.components,function(compnent,index,itmes){
    		//处理扩展组件
    		o.components[index] = MC.extendsCmp(compnent);
    		//处理其他属性
    		Ext.applyIf(compnent,MC.decodeOthProperty(compnent.otherProperty));
    		//处理公共转换
    		MC.transferTF(compnent);
    		//判断组件类型决定给bar还是列表
    		if(compnent.xtype == 'button' || compnent.xtype == 'tbseparator'){
    			buttons.push(compnent);
    		}else if(compnent.xtype == 'gridcolumn'){
    			MC.cloumns.push(compnent);
    		}
    		//为制作图标设计x,y轴的属性
    		common.view.commonChart.setChartXY(compnent);
    	});
    	return new Ext.grid.Panel(Ext.applyIf({
    		id:'centerGridPanel' + module,
			border : false,
			region : 'center',
			enableColumnHide : false,///隐藏列
			sortableColumns : false,///隐藏排序
			columnLines : true,//列间线
			printTitle : o.title,
			plugins: [{
               ptype: 'gridexporter'    //grid导出插件
            }],
			selModel: {
				selType: 'checkboxmodel'
		    },
			viewConfig : {
				stripeRows: true,//条纹行
				enableTextSelection : true//文本可选
			},
			dockedItems : [ {
				xtype : 'pagingtoolbar',
				displayInfo : true,
				dock : 'bottom'
			}, 
			{
				xtype : 'toolbar',
				dock : 'top',
				items : buttons
			} ],
			columns : MC.cloumns
    	},MC.decodeOthProperty(o.otherProperty)));
    },
     initEastPanel:function(o){
    	Ext.Array.each(o.components,function(compnent,index,itmes){
    		//处理扩展组件
    		o.components[index] = MC.extendsCmp(compnent);
    		//处理其他属性
    		Ext.apply(compnent,MC.decodeOthProperty(compnent.otherProperty));
    		//处理公共组件转换
    		MC.transferTF(compnent);
    	});
    	return new Ext.panel.Panel(Ext.applyIf({
    		id:'eastPanel' + module,
			region : 'east',
			border : false,
			width : o.width || '10%',
			layout : o.layout || 'column',
			items : o.components
		},MC.decodeOthProperty(o.otherProperty)));
     },
     initWestPanel:function(o){
    	 Ext.Array.each(o.components,function(compnent,index,itmes){
    		//处理扩展组件
    		o.components[index] = MC.extendsCmp(compnent);
    		//处理其他属性
    		Ext.apply(compnent,MC.decodeOthProperty(compnent.otherProperty));
    		//处理公共组件转换
    		MC.transferTF(compnent);
    	});
    	return new Ext.panel.Panel(Ext.applyIf({
    		id:'westPanel' + module,
			region : 'west',
			border : false,
			width : o.width || '10%',
			layout : o.layout || 'column',
			items : o.components
		},MC.decodeOthProperty(o.otherProperty)));
     },
    /**
     * 初始化展示页面的方法
     */
    initMainView:function(){
    	var cm = Ext.getCmp('commonMain' + module);
    	var myMask = new Ext.LoadMask(cm, {msg : "请求进行中,请等待..."});
		myMask.show();
    	//获取该页面的所有组件
    	Ext.Ajax.request( {
			url : getRootPath() + '/funcpage/queryComptById.do',
			method : 'POST',
			params : {
				functionid : functionId
			},
			success : function(response, opts) {
				myMask.hide();
				FG = Ext.JSON.decode(response.responseText);
				Ext.Array.each(FG,function(o,index,itmes){
					if(o.containerType == 'main' && o.region == "north"){//初始化north查询条件部分
				    	cm.add(MC.initNorthPanel(o));
					}else if(o.containerType == 'main' && o.region == "center"){//初始化center数据列表部分
				    	cm.add(MC.initCenterPanel(o));
					}else if(o.containerType == 'main' && o.region == "south"){//初始化south图标部分
				    	cm.add(MC.initSouthPanel(o));
				    	MC.containChart = true;
					}else if(o.containerType == 'main' && o.region == "west"){//初始化wets部分
						cm.add(MC.initWestPanel(o));
					}else if(o.containerType == 'main' && o.region == "east"){//初始化east部分
						cm.add(MC.initEastPanel(o));
					}
				});
			    var store = Ext.create('Ext.data.Store', {
					pageSize:25,
					proxy:{
						type:'ajax',
						url:MC.getUrl(true),
						actionMethods:'post',
						reader:{
							type:'json',
							root:'content',
							totalProperty:'totalElements'
						},
						extraParams : MC.getParams(GetValue(MC.getAGP("search"),false)||{})
					},
					autoLoad:true
				 });
		       var grid = Ext.getCmp('centerGridPanel' + module);
		       grid.reconfigure(store);
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
    },
    /**
     * 查询
     * @param {Object} bt
     * @param {Object} e
     * @param {Object} eOpts
     * @return {TypeName} 
     */
    find:function(bt, e, eOpts){
    	var paramsTemp = GetValue(MC.getAGP("search"),false);
		if (paramsTemp == false) {
			return false;
		}
		var g = Ext.getCmp('centerGridPanel' + module);
		if(executeSql=='false'){
			g.store.proxy.extraParams = {
				tableName : module,
				parameterJSON:Ext.JSON.encode(paramsTemp)
			};
		}else if(executeSql=='true'){
			g.store.proxy.extraParams = {
				functionId : functionId,
				parameterJSON:Ext.JSON.encode(paramsTemp)
			};
		}
		g.store.load({
		    scope: this,
		    callback: function(records, operation, success) {
		    	if(MC.containChart){
			    	MC.chart(bt);
		    	}
		    }
		});
    },
    /**
     * 重置
     * @param {Object} bt
     * @param {Object} e
     * @param {Object} eOpts
     */
    reset:function(bt, e, eOpts){
    	var data = new Object();
    	var items = Ext.ComponentQuery.query('*[actionGroup='+MC.getAGP("search")+']');
    	Ext.Array.each(items, function(item, index, items) {
		    if(item.timeType == 'start'){
	    		//设置当前时间向前减5年
	    		var stm = new Date();
	    		stm.setFullYear(parseInt(stm.getFullYear()-3));
				stm = stm.Format(item.format||"yyyy-MM-dd hh:mm:ss");
	    		data[item.actionName] = stm;
		    }
		    if(item.timeType == 'end'){
	    		//设置为当前时间
	    		var etm = new Date();
				etm = etm.Format(item.format||"yyyy-MM-dd hh:mm:ss");
	    		data[item.actionName] = etm;
		    }
		});
    	Reset(MC.getAGP("search"),data);
    },
    /**
     * gridpanel导出excel
     * @param {Object} bt
     * @param {Object} e
     * @param {Object} eOpts
     */
    exportExcel:function(bt, e, eOpts){
    	 var grid = bt.ownerCt.ownerCt;
    	 var paramsTemp = GetValue(MC.getAGP("search"),false);
		 if (paramsTemp == false) {
			 return false;
		 }
		 Ext.Ajax.request( {
			url : MC.getUrl(),
			method : 'POST',
			params : MC.getParams(paramsTemp),
			success : function(response, opts) {
				var result = Ext.JSON.decode(response.responseText);
				if(result.length > 10000){
					Ext.Msg.alert('提示信息', "导出excel数据量太大，请过滤数据！");
					return;
				};
				grid.saveDocumentAs({
		           type: 'excel',
		           exportAll:true,
		           url:true,
		           totalData:result,
		           title: bt.excelTile || 'excel',
//		           fileName: bt.fileName || 'excel.xls'
		           fileName: bt.fileName || 'excel.xml'
		        }); 
			},
			failure : function(response, opts) {
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		 });
    },
    /**
     * 公共打印方法
     * @param {Object} bt
     * @param {Object} e
     * @param {Object} eOpts
     */
    dayin:function(bt, e, eOpts){
    	 var grid = bt.ownerCt.ownerCt;
    	 var paramsTemp = GetValue(MC.getAGP("search"),false);
		 if (paramsTemp == false) {
			 return false;
		 }
		 var cmps = MC.getMComp(MC.getAGP("search"),'timeType');
		 Ext.Array.each(cmps, function(item, index, cmps) {
			 if(item.timeType == 'start'){
				 bt.searchTime = item.getValue();
			 }
			 if(item.timeType == 'end'){
				bt.searchTime = bt.searchTime +' - '+ item.getValue();
			 }
		 });
		 Ext.Ajax.request( {
			url : MC.getUrl(),
			method : 'POST',
			params : MC.getParams(paramsTemp),
			success : function(response, opts) {
				var result = Ext.JSON.decode(response.responseText);
				bt.printConfig = {};
				bt.printConfig.data = result;
				bt.printConfig.Title = grid.printTitle;
	            Ext.ux.grid.DPrinter.print(grid,bt); 
			},
			failure : function(response, opts) {
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
		 
    },
    yearbook:function(bt, e, eOpts){
    	var temp = GetValue(MC.getAGP("search"));
		if (temp == false) {
			return temp;
		}
    	common.view.commonYearbook.yearbook(bt);
    },
    /**
     * 自动动态生成hightchart图标方法
     * @param {Object} panel.
     * @param {Object} eOpts
     */
    chart:function(bt){
    	 var panel = bt.ownerCt;
    	 //3确定查询方式url和分页参数
    	 var paramsTemp = GetValue(MC.getAGP("search"),false);
		 if (paramsTemp == false) {
			 return false;
		 }
		 var items = Ext.ComponentQuery.query('*[actionGroup='+MC.getAGP("search")+']');
		 var stcdAry,stnmAry;
		 //设置线条数组
		 Ext.Array.each(items, function(param, index, items) {
		  	if(param.isStcd){
				 stcdAry = param.value.split(',');
				 stnmAry = param.rawValue.split(',');
		  	}
		 });
		 if(!stcdAry){
			 return false;
		 }
		 //创建图表对象
		 common.view.commonChart.createChart({stcds:stcdAry,stnms:stnmAry,panel:panel});
		 var PT = MC.getParams(paramsTemp);
		 var g = Ext.getCmp('centerGridPanel' + module);
		 var store = g.store;
             totalCount = store.getTotalCount();
         var pageCount = Math.ceil(totalCount / 10000)
		 for(var i = 1;i <= pageCount;i++){
		 	 PT.start =((i - 1) * 10000) + 1;
             PT.limit = 10000;
	    	 Ext.Ajax.request( {
				url : MC.getUrl(true),
				method : 'POST',
				params : PT,
				success : function(response, opts) {
					var result = Ext.JSON.decode(response.responseText);
					//开始绘图
					common.view.commonChart.draw(result);
				},
				failure : function(response, opts) {
					Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
				}
			});
		 }
    },
    initSearchValue:function(item){
	    if(item.timeType == 'start'){
	    	if(!item.value){
	    		//设置当前时间向前减5年
	    		var stm = new Date();
	    		stm.setFullYear(parseInt(stm.getFullYear()-3));
				stm = stm.Format(item.format||"yyyy-MM-dd hh:mm:ss");
	    		item.value = stm;
	    	}
	    }
	    if(item.timeType == 'end'){
	    	if(!item.value){
	    		//设置为当前时间
	    		var etm = new Date();
				etm = etm.Format(item.format||"yyyy-MM-dd hh:mm:ss");
	    		item.value = etm ;
	    		
	    	}
	    }
    },
    /**
     * 新增数据的公共方法
     */
	add : function(bt, e, eOpts) {
    	MC.showWin("add",'addSave');
	},
	/**
	 * 修改数据公共方法
	 */
	edit : function(bt, e, eOpts) {
		var myMask = new Ext.LoadMask(bt.ownerCt.ownerCt.ownerCt, {
			msg : "请求进行中,请等待..."
		});
		var dataGrid = bt.ownerCt.ownerCt;
		if (dataGrid.getSelectionModel().hasSelection()) {
			var a = dataGrid.getSelectionModel().getSelection();
			if (a.length != 1) {
				Ext.Msg.alert("提示信息", "请选择1条数据进行操作！");
				return false;
			}
			var paramsTemp = {};
			for(var c in MC.cloumns){
				if(MC.cloumns[c].iskey){
					paramsTemp[MC.cloumns[c].dataIndex + '_EQ'] = a[0].data[MC.cloumns[c].dataIndex];
				}
			}
			myMask.show();
			Ext.Ajax.request( {
				url : getRootPath() + '/tbOperate/queryDataByTbName.do',
				method : 'POST',
				params : {
					tableName : module,
					parameterJSON:Ext.JSON.encode(paramsTemp)
				},
				success : function(response, opts) {
					myMask.hide();
					var result = Ext.JSON.decode(response.responseText);
					if (result.length == 1) {
						var agp = "edit";
						if(bt.agp){
							agp = bt.agp;
						}	
						MC.showWin(agp,'editSave');
						SetValue(MC.getAGP(agp), result[0]);
					} else {
						Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试" + result.msg + "！");
					}
				},
				failure : function(response, opts) {
					myMask.hide();
					Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
				}
			});
		}else{
			Ext.Msg.alert("提示信息", "请选择1条数据进行操作！");
		}
		
	},
	/**
	 * 删除数据公共方法
	 */
	del : function(bt, e, eOpts) {
		var dataGrid = bt.ownerCt.ownerCt;
		if (dataGrid.getSelectionModel().hasSelection()) {
			var a = dataGrid.getSelectionModel().getSelection();
//			if (a.length != 1) {
//				Ext.Msg.alert("提示信息", "请选择1条数据进行操作！");
//				return false;
//			}
			var paramsTemp = {};
			var keyColumn = '';
			for(var c in MC.cloumns){
				if(MC.cloumns[c].iskey){
					keyColumn = MC.cloumns[c].dataIndex;
				}
			}
			paramsTemp[keyColumn] ='';
			for(var i =1;i<=a.length;i++){
				if(i==a.length){
					paramsTemp[keyColumn] += a[i-1].data[keyColumn];
				}else{
					paramsTemp[keyColumn] += a[i-1].data[keyColumn]+',';
				}
			}
			
			Ext.MessageBox.confirm('温馨提示','确定要删除这 ' + a.length + ' 条信息？', function(btn) {
				if (btn == "yes") {
					var myMask = new Ext.LoadMask(bt.ownerCt.ownerCt.ownerCt, {
						msg : "请求进行中,请等待..."
					});
					myMask.show();
					Ext.Ajax.request( {
						url : getRootPath() + '/tbOperate/delData.do',
						method : 'POST',
						params : {
							tableName : module,
							parameterJSON:Ext.JSON.encode(paramsTemp)
						},
						success : function(response, opts) {
							myMask.hide();
							var result = Ext.JSON.decode(response.responseText);
							if (result.success == true) {
								Ext.Msg.alert('提示信息', "删除成功!");
								MC.refresh();
							} else {
								Ext.Msg.alert('提示信息', "删除失败!");
							}
						},
						failure : function(response, opts) {
							myMask.hide();
							Ext.Msg.alert('提示信息', "删除失败！");
						}
					});
				}
			});
		}else{
			Ext.Msg.alert("提示信息", "请选择1条数据进行操作！");
		}
	},
	show:function(bt, e, eOpts){
		bt.agp = "show";
		MC.edit(bt, e, eOpts);
	},
	
	showWin:function(agp,control){
		Ext.Array.each(FG,function(o,index,itmes){
    		if(o.containerType == 'add'){
    			Ext.Array.each(o.components,function(compnent,index,itmes){
    				//处理扩展组件
    				o.components[index] = MC.extendsCmp(compnent,agp);
    				//处理其他属性
		    		Ext.applyIf(compnent,MC.decodeOthProperty(compnent.otherProperty));
		    		//处理公共转换
		    		MC.transferTF(compnent);
		    		//添加actionGroup属性
		    		MC.setAGP(compnent,agp);
		    		//为查看页面添加readOnly属性
		    		compnent.readOnly = agp=="show"?true:false;
		    		//删除无用干扰属性
		    		delete compnent.pageId;
		    		delete compnent.id;
		    		//首先在date类型的文本组建中添加属性date：true
		    		//判断如果control是editSave:循环MC.columns找到属性为iskey并且date为true.那么把该组件的listeners属性删除
		    		if(agp == 'edit'){
		    			for(var c in MC.cloumns){
			    			if(compnent.readOnly){
			    				continue;
			    			}
							if(MC.cloumns[c].iskey && MC.cloumns[c].dataIndex == compnent.actionName){
								compnent.readOnly = true;
								if(compnent.date){
									delete compnent.listeners
								}
							}
						}
		    		}
		    		if(agp == "show"){
		    			for(var c in MC.cloumns){
			    			if(MC.cloumns[c].iskey && MC.cloumns[c].dataIndex == compnent.actionName && compnent.date){
								delete compnent.listeners
			    			}
		    			}
		    		}
		    		
		    	});
    			var flag = agp=="show"?true:false;
    			//处理窗口的其他属性
    			Ext.applyIf(o,MC.decodeOthProperty(o.otherProperty));
    			Ext.create('Ext.window.Window',Ext.apply({
    				id:'commonWin'+ agp + module,
				    modal : true,//打开遮罩层
					constrain : true,//只能在容器内移动
					resizable : false, //不可改变窗口大小
					height : 400,
					width : 500,
					layout : 'column',
					items : o.components,
					buttons : [{
						xtype : 'button',
						listeners: {
				            click: control
				        },
				        hidden:flag,
				        icon: getRootPath() + '/image/icon/table_save.png',
						text : '保存'
					}, {
						xtype : 'button',
						text : '关闭',
						icon: getRootPath() + '/image/icon/cancel.png',
						listeners: {
				            click: 'close'
				        }
					}]
				},o)).show();
    		}
		});
	},
	
	addSave : function(bt, e, eOpts) {
		MC.save(bt,MC.getAGP("add"),getRootPath() + '/tbOperate/addData.do');
	},
	editSave : function(bt, e, eOpts) {
		MC.save(bt,MC.getAGP("edit"),getRootPath() + '/tbOperate/editData.do');
	},
	/**
	 * 保存数据的公共方法
	 * @param {Object} bt
	 * @param {Object} handelType
	 * @param {Object} url
	 * @return {TypeName} 
	 */
	save:function(bt,handelType,url){
		var paramsTemp = GetValue(handelType);
		if (paramsTemp == false) {
			return false;
		}
		var win = bt.ownerCt.ownerCt;
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		Ext.Ajax.request( {
			url : url ,
			method : 'POST',
			params : {
				tableName:module,
				parameterJSON:Ext.JSON.encode(paramsTemp)
			},
			success : function(response, opts) {
				myMask.hide();
				var result = Ext.JSON.decode(response.responseText);
				if (result.success == true) {
					win.close();
					Ext.Msg.alert('提示信息', "保存成功！");
					MC.refresh();
				} else {
					Ext.Msg.alert('提示信息', "保存失败！");
				}
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息', "保存失败!");
			}
		});
	},
	/**
	 * 关闭窗口的功能方法
	 * @param {Object} bt
	 * @param {Object} e
	 * @param {Object} eOpts
	 */
	close:function(bt, e, eOpts){
		bt.ownerCt.ownerCt.close();
	},
	/**
	 * 生成日期组件
	 * @param {Object} p
	 */
	renderDate:function(p){ 
		p.getEl().on('click',function(){  
			WdatePicker({dateFmt:p.format||'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'}); 
		}); 
	},
	 /**
     * 处理扩展组件的公共方法
     * @param {Object} cmp
     * @return {TypeName} 
     */
    extendsCmp:function(cmp,groupType){
    	var compnent = cmp;
    	if(typeof cmp.extendComponent == 'string'){
    		compnent = cmp.extendComponent == ""?cmp:Ext.JSON.decode(cmp.extendComponent);
    	}else if(typeof cmp.extendComponent == 'object'){
    		compnent = cmp.extendComponent;
    	}
    	if(groupType){
	    	MC.setAGP(compnent,groupType);
    	}
    	return compnent;
    },
    /**
     * 把组件的其他属性追加给该组件
     * @param {Object} property
     * @return {TypeName} 
     */
    decodeOthProperty:function(property){
    	if(typeof property == 'string'){
    		return Ext.JSON.decode(property || '{}');
    	}else if(typeof property == 'object'){
    		return property;
    	}else{
    		return {};
    	}
    },
    /**
     * 组件的公共转换方法
     * @param {Object} compnent
     */
    transferTF:function(compnent){
    	if(compnent.hidden == 0){
			compnent.hidden = false;
		}else if(compnent.hidden == 1){
			compnent.hidden = true;
		}
		if(compnent.allowBlank == 0){
			compnent.allowBlank = false;
		}else if(compnent.allowBlank == 1){
			compnent.allowBlank = true;
		}
		if(compnent.width && (typeof compnent.width == 'string') && compnent.width.indexOf("%") < 0 ){
			compnent.width = Number(compnent.width);
		}
    },
    /**
     * 给组件添加actionGroup属性作为检索的依据
     * @param {Object} o
     * @param {Object} groupType 表示字段（ "add",新增标识"edit",编辑标识 "search",查询标识 "show" 查看标识 ）该类型可扩充
     */
    setAGP:function(o,groupType){
    	o.actionGroup = "common_" + module + "_" + groupType;
    },
    /**
     * 获取actionGroup属性
     * @param {Object} groupType
     * @return {TypeName} 
     */
    getAGP:function(groupType){
    	return "common_" + module + "_" + groupType;
    },
    /**
     * 后期扩展使用给默认的主键赋值
     * @param {Object} o
     */
    setPKValue:function(o){
    	if(o.pk && o.autopk){
    		//自动生成Id值
    	}
    },
    refresh:function(){
		var grid = Ext.getCmp('centerGridPanel' + module);
			grid.store.load();
	},
	/**
	 * 根据特定属性获取组件
	 * @param {Object} actionGroup
	 * @param {Object} property
	 * @return {TypeName} 
	 */
	getMComp:function(actionGroup,property){
		var compArr = new Array();
		var items = Ext.ComponentQuery.query('*[actionGroup='+actionGroup+']');
		Ext.Array.each(items, function(item, index, items) {
			if(item[property]){
				compArr.push(item);
			}
		});
		return compArr;
	},
	/**
	 * 公共打印，导出excel，制作图标使用，功能模块主页面查询store使用
	 * 获取查询请求的url
	 * @param {Object} paging
	 */
	getUrl:function(paging){
		var url = getRootPath() + '/tbOperate/queryDataByTbName.do';
		if(paging){
			if(executeSql=='true'){
				url = getRootPath()+'/tbOperate/queryPageDataBySQL.do';
			}else{
				url = getRootPath()+'/tbOperate/queryPageDataByTbName.do';
			}
		}else{
			if(executeSql=='true'){
				url = getRootPath() + '/tbOperate/queryDataBySQL.do';
			}
		}
		return url;
	},
	/**
	 * 公共打印，导出excel，制作图标使用，功能模块主页面查询store使用
	 * 获取查询请求的参数
	 * @param {Object} paramsTemp
	 * @return {TypeName} 
	 */
	getParams:function(paramsTemp){
		var params = {
				tableName : module,
				parameterJSON:Ext.JSON.encode(paramsTemp)
			};
		if(executeSql=='true'){
			 params = {
					functionId : functionId,
					parameterJSON:Ext.JSON.encode(paramsTemp)
				 };
		}
		return params;
	}
});
