var startMess = true;
var endMess = true;
var activeFlag = true;
Ext.define("common.view.commonYearbook",{ 
    statics: {  
        yearbook: function(bt) {
			 var items = Ext.ComponentQuery.query('*[actionGroup='+MC.getAGP("search")+']');
	    	 var stcds = new Array();
	    	 var stcdgs = new Array();
	    	 var stime, etime;
	    	 var hasActiveError = false;
	    	 Ext.Array.each(items, function(item, index, items) {
				if (item.getValue()) {
				    if(item.timeType == 'start'){
				    	stime = item.getValue().substring(0,4)
				    }
				    if(item.timeType == 'end'){
				    	etime = item.getValue().substring(0,4)
				    }
				    if(item.isStcd){
				    	stcdAry = item.getValue().split(',');
				    	stnmAry = item.getRawValue().split(',');
				    	for(var s in stcdAry){
					    	stcds.push({"stcd":stcdAry[s],"stnm":stnmAry[s]});
					    	stcdgs.push({ boxLabel: stnmAry[s],width :100,margin : '5 10 10 0' ,name: 'stcd', inputValue: stcdAry[s] });
				    	}
				    }
				}else{
					hasActiveError = true;
				}
			 });
	    	 if(!hasActiveError){
		    	 Ext.create('Ext.window.Window',{
					id:'common_yearbook',
				    modal : true,//打开遮罩层
					constrain : true,//只能在容器内移动
					resizable : false, //不可改变窗口大小
					height : 400,
					width : 500,
					title:'打印条件',
					layout : 'border',
					items : [{
						xtype: 'tabpanel',
						id:'common_yearbook_tabpanel',
					    region:'center',
					    activeTab: 0,
					    items: [
					        {
					            title:'单站多年',
					            bookType:1,
					            layout : 'column',
					            items:[
					            	 {
										xtype: 'combobox',
										width : 240,
										labelWidth : 80,
										margin : '45 5 5 50',
										fieldLabel: '选择测站',
										actionName : 'stcd',
										actionGroup : 'yearbook_years',
										editable : false,
										valueField : 'stcd',
										displayField : 'stnm',
										value:stcds[0].stcd,
										store : {
											fields : ['stcd','stnm'],
											data : stcds
										}
									 },
					            	 {
										xtype : 'textfield',
										width : 240,
										labelWidth : 80,
										margin : '45 5 5 50',
										id:'startTime',
										actionName : 'startTime',
										actionGroup : 'yearbook_years',
										value:stime,
										fieldLabel : '起始年份',
										validator: function (val) {
										  if(activeFlag){
											var endDate = Ext.getCmp('endTime');
											if(/^\d{4}$/.test(val)){
										        var value = endDate.getValue();
										        if((value != "" && val >= value)){
										        	startMess = "起始年份不能大于终止年份";
										        }else{
										            startMess = true;
										        	if(endMess != true){
												 		endDate.validate();
										        	}
										        }
									    	}else{
									    		startMess =  '请输入正确的年份';
									    	}
											return startMess;
										 }else{
											 return true;
										 }
									   }
									},  {
										xtype : 'textfield',
										width : 240,
										labelWidth : 80,
										margin : '45 5 5 50',
										id : 'endTime',
										actionName : 'endTime',
										actionGroup : 'yearbook_years',
										value:etime,
										fieldLabel : '终止年份',
										validator: function (val) {
											if(activeFlag){
									    		var startDate = Ext.getCmp('startTime');
										    	if(/^\d{4}$/.test(val)){
											        var value = startDate.getValue();
											        if((value != "" && val <= value)){
											        	endMess = "终止年份不能小于起始年份";
											        }else{
											            endMess = true;
											        	if(startMess != true){
												        	 startDate.validate();
											        	}
											        }
										    	}else{
										    		endMess = '请输入正确的年份';
										    	}
										        return endMess;
										     }else{
										    	return true; 
										     }
										}
									}
					            ]
					        },
					        {
					            title:'多站单年',
					            bookType:2,
					            items:[
					            	{
										xtype : 'textfield',
										width : 240,
										labelWidth : 80,
										margin : '20 5 5 50',
										actionName : 'year',
										actionGroup : 'yearbook_years',
										regex:/^\d{4}$/,
										regexText :'请输入正确的年份',
										value:stime,
										fieldLabel : '年份'
									}, 
									{
								        xtype: 'checkboxgroup',
								        margin : '20 5 5 50',
								        labelWidth : 80,
								        fieldLabel: '选择测站',
								        actionName : 'stcds',
										actionGroup : 'yearbook_years',
								        columns: 3,
								        vertical: true,
								        items: stcdgs,
								    }
								]
					        }
					    ]
					},{
						xtype:'panel',
						id:'common_yearbook_format',
						region:'south',
						height:30,
						layout:'column',
						hidden:bt.bookFormat?false:true,
						items:[
							{
						        xtype: 'radiogroup',
						        id:'yearbook_radio_format',
						        vertical: true,
						        items: [
						            { width :100,margin:'0 0 0 150',boxLabel: '日月年格式', name: 'format', inputValue: 'functionName',checked: true},
						            { width :100,boxLabel: '月年格式',  name: 'format', inputValue: 'functionName1'}
						        ]
						    }
						]
					}],
					buttons : [{
						xtype : 'button',
			         	handler : function() {
						    var tab = Ext.getCmp('common_yearbook_tabpanel');
							activeFlag = tab.getActiveTab().bookType==1?true:false;
							var temp = GetValue('yearbook_years');
							if (temp == false) {
								return temp;
							}
							temp.bookType=tab.getActiveTab().bookType;
							if(tab.getActiveTab().bookType == 2){
								if(!temp.stcds.stcd){
									Ext.Msg.alert("提示信息", "请选择一个测站！");
									return false;
								}
							}
							var yearbook;
							for(var o in temp){
								if(o=='stcds'){
									if( typeof temp[o].stcd == Array){
										var sts;
										for(var s in temp[o].stcd ){
											if(sts){
											   sts += "," + temp[o].stcd[s];
											}else{
											   sts = temp[o].stcd[s];
											}
										}
									}else{
										temp[o] = temp[o].stcd;
									}
								}
								if(yearbook){
									yearbook += '&' + o + "=" + temp[o];
								}else{
									yearbook = o + "=" + temp[o];
								}
							}
							var functionName = Ext.getCmp('yearbook_radio_format').getValue().format;
			        		window.open(getRootPath() + '/yearbook/' + bt[functionName] + '.do?' + yearbook,"");
			         	},
				        icon: getRootPath() + '/image/icon/table_save.png',
						text : '确定'
					}, {
						xtype : 'button',
						text : '关闭',
						icon: getRootPath() + '/image/icon/cancel.png',
						listeners: {
				            click: 'close'
				        }
					}]
				}).show();
	    	 }else{
	    		 Ext.Msg.alert("提示信息", "请选择测站或者开始时间和结束时间！");
	    	 }
        }
    }  
});  