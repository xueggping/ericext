var getRootPath = function() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}
/**
 * 获取url中的参数
 * @param {Object} name
 * @return {TypeName} 
 */
var GetQueryString = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null && StrExistent(r[2])) {
		return r[2].split(";")[0];
	}
	return null;
}
/**
 * 判断字符串是否存在
 * @param {Object} s
 * @return {TypeName} 
 */
var StrExistent=function(str){
	if (typeof(str) == "string"&& $.trim(str)!= "") {
　　		return true
　　}
	return false;
}
/**
 * 克隆对象
 * @param {Object} myObj
 * @return {TypeName} 
 */
var cloneObject = function(myObj) {
	if (typeof (myObj) != 'object') {
		return myObj;
	}
	if (myObj == null) {
		return myObj;
	}
	var myNewObj;
	if (myObj instanceof Array) {
		myNewObj = [];
		for ( var i = 0; i < myObj.length; i++) {
			myNewObj.push(cloneObject(myObj[i]));
		}
	} else {
		myNewObj = {};
		for ( var i in myObj) {
			myNewObj[i] = cloneObject(myObj[i]);
		}
	}
	return myNewObj;
}
/**
 * 将第一个数组的元素追加到第二个数组中
 * @param {Object} arr1
 * @param {Object} arr2
 */
var appendArray = function(arr1, arr2) {
	if (arr1 instanceof Array && arr2 instanceof Array) {
		for ( var i in arr2) {
			arr1.push(arr2[i]);
		}
	}
	return arr1;
}
/**
 * 去尾
 * @param {Object} number 数字
 * @param {Object} num 精确的小数位数
 * @return {TypeName} 
 */
var NumberFloor = function(number, num) {
	return Math.floor(number * Math.pow(10, num)) / Math.pow(10, num);
}
/**
 * 产生随机数
 * @param {Object} Min
 * @param {Object} Max
 * @return {TypeName} 
 */
var GetRandomNum = function(Min, Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return (Min + Math.round(Rand * Range));
}
/**
 * 数据提交方法
 * @param {} type(必须)        提交类型 (json,form)
 * @param {} actionGroup(必须) 所要提交的数据字段标识
 * @param {} url(必须)		 提交的处理链接url
 * @param {} success(response, opts)	 数据提交成功后的处理函数(尽量此处调用其它方法完成后续操作)
 * @param {} failure(response, opts)	 数据提交失败后的处理函数(尽量此处调用其它方法完成后续操作)
 * @return {TypeName} 数据验证未通过返回false，成功验证数据并通过ajax将数据发送至后台（不能保证ajax发送成功）返回true
 * Submit('form', 'UP_Str', getRootPath() + '/qyzcssz/upReRange.do',
 function(response, opts) {
 var result = Ext.JSON.decode(response.responseText);
 if (result.success == true) {
 Ext.Msg.alert('提示信息', "更改成功!"+result.msg);
 } else {
 Ext.Msg.alert('提示信息', "更改失败!"+result.msg);
 }
 }, function(response, opts) {
 Ext.Msg.alert('提示信息', "更改失败!");
 });
 * 
 * 
 * var win = Ext.getCmp('userAddWindow');
		var myMask = new Ext.LoadMask(win, {
			msg : "请求进行中,请等待..."
		});
		myMask.show();
		var status = Submit('form', 'userAdd', getRootPath() + '/user/add.do',
				function(response, opts) {
					//ajax发送成功，关闭遮罩层
					myMask.hide();
					var result = Ext.JSON.decode(response.responseText);
					if (result.success == true) {
						win.close();
						Ext.Msg.alert('提示信息', "保存成功" + result.msg + "！");
						MainController.find();
					} else {
						Ext.Msg.alert('提示信息', "保存失败" + result.msg + "！");
					}
				}, function(response, opts) {
					//ajax发送失败，关闭遮罩层
					myMask.hide();
					Ext.Msg.alert('提示信息', "保存失败!");
				});
		//数据验证没有通过，在这里关闭遮罩层
		if (status == false) {
			myMask.hide();
		}
 */
Submit = function(type, actionGroup, url, success, failure) {
	var paramsTemp = GetValue(actionGroup);
	if (paramsTemp == false) {
		return false;
	}
	return _Submit(paramsTemp, type, url, success, failure)
};
_Submit = function(paramsTemp, type, url, success, failure) {
	switch (type) {
	case "JSON":
	case "json":
		params = {
			dataJson : Ext.JSON.encode(paramsTemp)
		}
		break;
	default:
		params = paramsTemp;
		break;
	}
	Ext.Ajax.request( {
		url : url,
		method : 'POST',
		params : params,
		success : function(response, opts) {
			if (success) {
				success(response, opts);
			}
		},
		failure : function(response, opts) {
			if (failure) {
				failure(response, opts);
			}
		}
	});
	return true;
};
/**
 * 数据提交方法
 * @param {} type(默认form)		提交类型 (json,form)
 * @param {} actionGroup(必须)	所要提交的数据字段标识
 * @param {} url(必须)			提交的处理链接url
 * @param {} extraParams 附加参数
 * @param {} success(response, opts)	 数据提交成功后的处理函数(尽量此处调用其它方法完成后续操作)
 * @param {} failure(response, opts)	 数据提交失败后的处理函数(尽量此处调用其它方法完成后续操作)
 * 
 * 
 * FlexibleSubmit({
 actionGroup : 'UP_Str',
 url : getRootPath() + '/qyzcssz/upReRange.do',
 extraParams : {stcds : '1,2,3'},
 success : function(response, opts) {
 var result = Ext.JSON.decode(response.responseText);
 if (result.success == true) {
 Ext.Msg.alert('提示信息', "更改成功!"+result.msg);
 } else {
 Ext.Msg.alert('提示信息', "更改失败!"+result.msg);
 }
 }, 
 failure : function(response, opts) {
 Ext.Msg.alert('提示信息', "更改失败!");
 }
 });
 */
FlexibleSubmit = function(param) {
	var paramsTemp = GetValue(param.actionGroup);
	if (paramsTemp == false) {
		return false;
	}
	for ( var s in param.extraParams) {
		paramsTemp[s] = param.extraParams[s];
	}
	return _Submit(paramsTemp, param.type, param.url, param.success, param.failure)
}

/**
 * 
 * 日期格式化函数
 * @param {Object} fmt
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
Date.prototype.Format = function(fmt){
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  

/**
 * 时间字符串格式化成Date对象
 * @param {Object} str
 * @return {TypeName} 
 */
convertStringToDate = function(str){
	// 转换日期格式
	str = str.replace(/-/g, '/'); // "2010/08/01";
	// 创建日期对象
	return  new Date(str);
}


/**
 * 给控件设置值{actionName : value}格式
 * @param {Object} actionGroup(必须)
 * @param {Object} data(必须)数据 格式:{actionName:value,actionName:value,actionName:{continueSet : true}}
 当value为{continueSet : true}表示赋值时跳过此字段常见于，页面需要重新赋值，但是有些控件不需要重新赋值。
 * @param {Object} useDef是否使用默认值，默认为false，
 当为true时如果data中对应控件值为null或不存在则不执行setValue操作(即不赋值，使用页面默认值)
 常见用于:页面创建好了，需要赋值，但是有些页面控件有默认值，这些有默认值的控件无需赋值。
 * 
 */
SetValue = function(actionGroup, data, useDef) {
	Ext
			.each(
					Ext.ComponentQuery
							.query('*[actionGroup=' + actionGroup + ']'),
					function(item) {
						if (useDef == true
								&& (typeof (data[item.actionName]) == "undefined" || data[item.actionName] == null)) {
							return;
						}
						if ((typeof (data[item.actionName]) != "undefined" && data[item.actionName] != null)
								&& data[item.actionName].continueSet == true) {
							return;
						}
						switch (item.xtype) {
						case "radiogroup":
							var value = new Object();
							value[item.actionName] = data[item.actionName];
							item.setValue(value);
							break;
					    case "datetimefield":
					    	if(data[item.actionName] != null){
						    	item.setValue( convertStringToDate(data[item.actionName]).Format("yyyy-MM-dd hh:mm:ss"));
					    	}
					    	break;
						default:
							item.setValue(data[item.actionName]);
						}
					});
}
/**
 * 获取指定控件组的值，返回{actionName : value}格式，如果输入窗口校验失败，返回false
 * @param {Object} actionGroup(必须)
 * @param [boolean] flag默认为true，当为false时表示空间值如果为""则跳过不获取
 * @return {TypeName} 
 */
GetValue = function(actionGroup, flag) {
	var b = true;
	if (flag == false) {
		b = false;
	}
	var paramsTemp = {};
	var hasActiveError = false;
	var items = Ext.ComponentQuery.query('*[actionGroup=' + actionGroup + ']');
	for ( var i in items) {
		var item = items[i];
		item.validate();
		if (item.hasActiveError()) {
			hasActiveError = true;
		}
		var value = "";
		switch (item.xtype) {
		case "radiogroup":
			// value = item.getValue()[item.actionName];
			value = item.getValue();
			// value = encodeURI(value);
			break;
		case "datetimefield":
		case "timefield":
		case "datefield":
			value = item.getRawValue();
			break;
		default:
			value = item.getValue();
		}
		if (!b && (value === '' || value === null)) {
			continue;
		}
		//2017-10-23修改 因为json数据转换时null值报错 by:hhc
		//value = value==''?null:value;
		value = value==''?'':value;
		if( typeof value == 'string'){
			paramsTemp[item.actionName] = Ext.String.trim(value);
		}else{
			paramsTemp[item.actionName] = value;
		}
	}
	if (hasActiveError) {
		return false;
	}
	return paramsTemp;
};
EasySearch = function(actionGroup, storeParentId) {
	Search( {
		actionGroup : actionGroup,
		storeParentId : storeParentId
	});
};
/**
 * public 查询操作方法
 * @param {} actionGroup 需要查询条件集合主要用于获取页面参数
 * @param {} storeParentId(必须)	 当前列表分页工具条pagingtoolbar的ID，如当前列表无分页，则传入当前列表gridpanel的ID
 * @param {} type	提交方式，为了迎合后台框架默认为json方式提交，当传入form或FORM时为表单方式提交,方便定制其它功能。
 * @param {} extraParams 附加参数，该参数将同搜素框内数据一并传往后台,该参数常见于于左边一棵树，右边一张表，树表联动，且表上有搜索框，
 * 							此时可以讲树的id（父ID）在执行搜索时作为附加参数同搜素参数一块传到后台
 * @param {} nullValue默认为false，当为false时表示搜索框值如果为""或null则跳过不获取（不往后台发送）注意：方法GetValue中的nullValue参数默认为false
 * 
 * 页面查询控件输入框需添加以下两个属性
 * actionName : 'adnm',_前为查询条件类型，后为对应数据库字段映射bean中的字段（不能重复），也可根据实际情况自定义
 * actionGroup:'Q_XzlxrView',为组名，表示哪些控件是一组（同组的相同）
 */
Search = function(param) {
	var nullValue = false;
	if (param.nullValue == true) {
		nullValue = true;
	}
	var paramsTemp;
	if(param.actionGroup){
		paramsTemp = GetValue(param.actionGroup, nullValue);
	}else{
		paramsTemp = {};
	}
	if (paramsTemp == false) {
		return;
	}
	for ( var s in param.extraParams) {
		paramsTemp[s] = param.extraParams[s];
	}
	var storeParent = Ext.getCmp(param.storeParentId);
	switch (param.type) {
	case "form":
	case "FORM":
		storeParent.store.proxy.extraParams = paramsTemp;
		//		Ext.apply(storeParent.store.proxy.extraParams, paramsTemp);
		break;
	default:
		storeParent.store.proxy.extraParams = {
			paramJson : Ext.JSON.encode(paramsTemp)
		};
		//		Ext.apply(storeParent.store.proxy.extraParams, {paramJson:Ext.JSON.encode(paramsTemp)});
	}
	if (storeParent.xtype == 'pagingtoolbar') {
		storeParent.moveFirst();
	} else {
		storeParent.store.load();
	}
};
/**
 * 重置组件组的值
 * @param {} actionGroup(必须)
 */
Reset = function(actionGroup,data) {
	SetValue(actionGroup, data||new Object());
};
/**
 * 获取gridPanel中选中行指定列的数据集合
 * @param {Object} gridPanelId (必须)
 * @param {Object} dataIndexs (必须)数组需要提取的列的数据格式['dataIndex','dataIndex']
 * @param {Object} dataMinCount 选择行的行数限制最小值，一般为1或者0
 * @param {Object} dataMaxCount 选择行的行数限制最大值，没有表示无限制，一般编辑删除等操作，此处值为1
 * @return {TypeName} 
 * 
 * 
 * var selectValue = GetGridPanelSelectValue('ylzqyzGridPanel',['STCD']);
 if(selectValue == false){
 return;
 }
 var stcds = selectValue.STCD
 * selectValue.allDatalength选择数据的总条数
 */
GetGridPanelSelectValue = function(gridPanelId, dataIndexs, dataMinCount,
		dataMaxCount) {
	if (dataMinCount < 1) {
		return false;
	}
	var dataGrid = Ext.getCmp(gridPanelId);
	if (dataGrid.getSelectionModel().hasSelection()) {
		var a = dataGrid.getSelectionModel().getSelection();
		if (dataMinCount == dataMaxCount && dataMinCount != a.length) {
			Ext.Msg.alert("提示信息", "请选择" + dataMinCount + "条数据进行操作！");
			return false;
		}
		if (a.length < dataMinCount) {
			Ext.Msg.alert("提示信息", "请至少选择" + dataMinCount + "条数据进行操作！");
			return false;
		}
		if (a.length > dataMaxCount) {
			Ext.Msg.alert("提示信息", "请至多选择" + dataMaxCount + "条数据进行操作！");
			return false;
		}
		var paramsTemp = {};
		paramsTemp.allDatalength=a.length;
		for ( var v in dataIndexs) {
			var jsonStr = "";
			for ( var i = 0; i < a.length; i++) {
				jsonStr += a[i].get(dataIndexs[v]);
				if (i < a.length - 1) {
					jsonStr += ",";
				}
			}
			paramsTemp[dataIndexs[v]] = jsonStr;
		}
		return paramsTemp;
	} else {
		if (dataMinCount > 0) {
			Ext.Msg.alert("提示信息", "请选择数据进行操作！");
			return false;
		}
		return {};
	}
};
/**
 * 获取store中的所有raw数据（如果被修改则为修改前数据）
 * @param {Object} id
 * @return {TypeName} 
 */
GetGridPanelRaw = function(id) {
	var items = Ext.getCmp(id).getStore().data.items;
	var raw = [];
	for ( var j in items) {
		raw.push(items[j].raw);
	}
	return raw;
};
/**
 * 获取store中所有data数据（如果被修改则为修改后数据）
 * @param {Object} id
 * @return {TypeName} 
 */
GetGridPanelData = function(id) {
	var items = Ext.getCmp(id).getStore().data.items;
	var data = [];
	for ( var j in items) {
		data.push(items[j].data);
	}
	return data;
};
/**
 * 从数组中移除对象
 * @param {Object} arr
 * @param {Object} val
 * 例如：
 * var somearray = ["mon", "tue", "wed", "thur"]
 * removeArrayValue(somearray, "tue");
 */
removeArrayValue = function (arr, val) {
  for(var i=0; i<arr.length; i++) {
    if(arr[i] == val) {
      arr.splice(i, 1);
      break;
    }
  }
};

/**
 * ext grid导出excel方法
 * @param {Object} grid grid表格对象
 * @param {Object} config 导出excel的配置项
 * config 可配置项有：{
   	type: 'excel',
	tableConfig:{defaultColumnWidth: 148,defaultRowHeight: 22.75},
	title: 'excel标题',
	fileName: 'export.xml',
    defaultStyle: {
        alignment: {
            Vertical: 'Top'
        },
        font: {
            FontName: 'Calibri',
            Family: 'Swiss',
            Size: 11,
            Color: '#000000'
        }
    },
    titleStyle: {
        name: 'Title',
        alignment: {
            Horizontal: 'Center',
            Vertical: 'Center'
        },
        font: {
            FontName: 'Cambria',
            Family: 'Swiss',
            Size: 18,
            Color: '#1F497D'
        }
    },
    groupHeaderStyle: {
        name: 'Group Header',
        borders: [{
            Position: 'Bottom',
            LineStyle: 'Continuous',
            Weight: 1,
            Color: '#4F81BD'
        }]
    },
    groupFooterStyle: {
        name: 'Total Footer',
        borders: [{
            Position: 'Top',
            LineStyle: 'Continuous',
            Weight: 1,
            Color: '#4F81BD'
        }]
    },
    tableHeaderStyle: {
        name: 'Heading 1',
        alignment: {
            Horizontal: 'Center',
            Vertical: 'Center'
        },
        borders: [{
            Position: 'Bottom',
            LineStyle: 'Continuous',
            Weight: 1,
            Color: '#4F81BD'
        }],
        font: {
            FontName: 'Calibri',
            Family: 'Swiss',
            Size: 11,
            Color: '#1F497D'
        }
    },
    exportAll:true,
	totalData:result,
	url : getRootPath()+'/loginfo/queryall.do',
	params:{start:0,limit:10000}
   }
 */
exportExcel = function (grid,config){
	if(config.exportAll && config.url){
		 var myMask = new Ext.LoadMask(grid.ownerCt, {
			msg : "请求进行中,请等待..."
		 });
		 myMask.show();
		 Ext.Ajax.request( {
			url : config.url,
			method : 'POST',
			params : config.params,
			success : function(response, opts) {
			 	 myMask.hide();
				 var result = Ext.JSON.decode(response.responseText);
				 if(result instanceof Array){
					 config.totalData = result;
				 }else{
					 config.totalData = result.content;
				 }
				 grid.saveDocumentAs(config);
			},
			failure : function(response, opts) {
				myMask.hide();
				Ext.Msg.alert('提示信息','导出失败！');
			}
		});
	}else{
		grid.saveDocumentAs(config);
	}
};

function getMaxDay(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}

//月   new Date().addMonth( - 1),
Date.prototype.addMonth = Date.prototype.addMonth ||
function(Month) {
	this.setMonth(this.getMonth() + Month);
	return this;
};
//日 new Date().addDate( - 1),
Date.prototype.addDate= Date.prototype.addDate ||
function(Date) {
	this.setDate(this.getDate() + Date);
	return this;
};
Date.prototype.addHour = Date.prototype.addHour ||
function(Hour) {
	this.setHours(this.getHours() + Hour);
	return this;
};

/**
 * 根据字段数值获取对应名称
 * @param  {[type]} value 字段数值
 * @param  {[type]} type  1:库/河水特征码,2:测流方法,3:测积方法,4:测速方法,5:统计变量类别,6:起测岸别
 * @return {[type]}       
 */
function getNmById(value,type) {
	switch (type) {
		case 1:
			return value==1 ? '水位(m)' : value==2 ? '断流' : value==3 ? '流向不定' : value==4 ? '逆流' : value==5 ? '起涨' : value==6 ? '洪峰' : value=='P' ? '水电厂发电流量' : value;
		case 2:
			return value==1 ? '水位流量关系曲线' : value==2 ? '浮标及溶液测流法' : value==3 ? '流速仪及量水建筑物' : value==4 ? '估算法' : value==5 ? 'ADCP' : value==6 ? '电功率反推法' : value=='9' ? '其它方法' : value;
		case 3:
			return value==1 ? '水位面积关系曲线' : value==2 ? '测深杆或测深锤、铅鱼' : value==3 ? '回声测深仪' : value==5 ? 'ADCP' : value==9 ? '其它方法' : value;
		case 4:
			return value==1 ? '流速仪' : value==2 ? '浮标法' : value==3 ? '声学法' : value==5 ? 'ADCP' : value==9 ? '其它方法' : value;
		case 5:
			return value=='Z' ? '水位(m)' : value=='Q' ? '流量(m³/s)' : value=='W1' ? '最大1d洪量(10^6m³)' : value=='W3' ? '最大3d洪量(10^6m³)' : value=='W5' ? '最大5d洪量(10^6m³)' : value=='W7' ? '最大7d洪量(10^6m³)' : value=='W10' ? '最大10d洪量(10^6m³)' : value=='W15' ? '最大15d洪量(10^6m³)' : value=='W30' ? '最大30d洪量(10^6m³)' : value=='W60' ? '最大60d洪量(10^6m³)' : value;
		case 6:
			return value=='L' ? '左岸' : value=='R' ? '右岸' : value;
	}
};

function getTbName(tbName){
	var nm = '';
	switch (tbName) {
		case 'adinfob':
			nm = 'Ad_Info_B';//行政区划表
			break;
		case 'stbprp':
			nm = 'ST_STBPRP_B';//测站信息表
			break;
		case 'instcd':
			nm = 'ST_INSTCD_B';//单位名称编码表
			break;
		case 'soilch':
			nm = 'ST_SOILCH_B';//土壤墒情特征值表
			break;
		case 'wasrl':
			nm = 'ST_WASRL_B';//堰闸站关系表
			break;
		case 'rvsect':
			nm = 'ST_RVSECT_B';//大断面测验成果表
			break;
		case 'zvarl':
			nm = 'ST_ZVARL_B';//库(湖)容曲线表
			break;
		case 'rsvrstrl':
			nm = 'ST_RSVRSTRL_B';//库(湖)站关系表
			break;
		case 'rsvrfsr':
			nm = 'ST_RSVRFSR_B';//库(湖)站汛限水位表
			break;
		case 'rsvrfcch':
			nm = 'ST_RSVRFCCH_B';//库(湖)站防洪指标表
			break;
		case 'zqrl':
			nm = 'ST_ZQRL_B';//水位流量关系曲线表
			break;
		case 'rsinfob':
			nm = 'RS_Info_B';//水库基本情况表
			break;
		case 'rvinfob':
			nm = 'RV_Info_B';//河流基本情况表
			break;
		case 'rvfcch':
			nm = 'ST_RVFCCH_B';//河道站防洪指标表
			break;
		case 'fsdr':
			nm = 'ST_FSDR_B';//洪水传播时间表
			break;
		case 'frapar':
			nm = 'ST_FRAPAR_B';//洪水频率分析参数表
			break;
		case 'ffrar':
			nm = 'ST_FFRAR_B';//洪水频率分析成果表
			break;
		case 'stsmtask':
			nm = 'ST_STSMTASK_B';//测站报送任务表
			break;
	}
	return nm;
};
function jMap(){  
    //私有变量  
    var arr = {};  
    //增加  
    this.put = function(key,value){  
        arr[key] = value;  
    }  
    //查询  
    this.get = function(key){  
        if(arr[key]){  
            return arr[key]  
        }else{  
            return null;  
        }  
    }  
    //删除  
    this.remove = function(key){  
        //delete 是javascript中关键字 作用是删除类中的一些属性  
        delete arr[key]  
    }  
    //遍历  
    this.eachMap = function(fn){  
        for(var key in arr){  
            fn(key,arr[key])  
        }  
    }  
} ; 
