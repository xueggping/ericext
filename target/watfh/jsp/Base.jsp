<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 引入jQuery -->
	<script type="text/javascript" src="<%=basePath%>/script/jquery-3.1.1.min.js"></script>  
	<script type="text/javascript" src="<%=basePath%>/script/tools.js"></script>  
	<!-- 引入ExtJs样式  -->  
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/script/ext/classic/MyResources4/resources/preview-theme-neptune-84d2588f-c4d8-42c2-ad75-bcff1a19dc0b.css">  
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/script/ext/ux/classic/resources/ux-all.css" type="text/css">
    <!-- 引入ExtJs核心Js -->  
    <script type="text/javascript" src="<%=basePath%>/script/ext/ext-all.js"></script>  
    <script type="text/javascript" src="<%=basePath%>/script/ext/classic/locale/locale-zh_CN.js"></script>   
     <script type="text/javascript">
		// 第一步开启mvc的动态加载，否则controller不能按需加载store和view,注意一定要写在app.js引用前面才可以
		Ext.Loader.setConfig({enabled:true});  
		Ext.QuickTips.init();//Initializes the global QuickTips instance and prepare any quick tips.
		Ext.Loader.setPath("Ext.ux", "<%=basePath%>/script/ext/ux/classic/src");
		Ext.apply(Ext.form.field.VTypes, {
			// 调用名
			fdate:function(value,field){
				// 获取 id
				var start = field.startField;
				var end = field.endField;
				// 获取 组件
				startf = Ext.getCmp(start);
				endf = Ext.getCmp(end);
				// 设置
				if(startf){
					if(startf.getValue()>=value){
						return false;
					}else{
						startf.focus();
						return true;
					}
				}
				if(endf){
					if(value>=endf.getValue()){
						return false;
					}else{
						endf.focus();
						return true;
					}
				}
			},
			fdateText:"开始时间不能大于结束时间"
		});
		var GLOBLE_PAGESIZE = 20;
	</script>
   
  </head>
  
  <body>
   
  </body>
</html>
