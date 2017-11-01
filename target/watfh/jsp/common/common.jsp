<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../Base.jsp" flush="true"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>common</title>
    <link rel="stylesheet" href="<%=basePath%>/script/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=basePath%>/script/ztree/js/jquery.ztree.all.js"></script>
    <script type="text/javascript" src="<%=basePath%>/script/comboBoxTree.js"></script>
    <script type="text/javascript" src="<%=basePath%>/script/highstock.js"></script>
    <script type="text/javascript" src="<%=basePath%>/script/exporting.js"></script>
    <script type="text/javascript" src="<%=basePath%>/watfh/common/app.js"></script>  
    <script type="text/javascript">
    	var module = GetQueryString('module');
    	var functionId = GetQueryString('functionId');
    	var executeSql = GetQueryString('executeSql');
    </script>
  </head>
  
  <body>
   
  </body>
</html>
