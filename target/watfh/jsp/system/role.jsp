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
    <title>角色管理</title>
    <link rel="stylesheet" href="<%=basePath%>/script/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>/script/ztree/js/jquery.ztree.all.js"></script>
    
    <script type="text/javascript" src="<%=basePath%>/watfh/system/role/app.js"></script>  
  </head>
  
  <body>
   
  </body>
</html>
