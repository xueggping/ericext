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
    <title>功能页面布局组件</title><!--
     <script type="text/javascript">
        // 第一步开启mvc的动态加载，否则controller不能按需加载store和view,注意一定要写在app.js引用前面才可以
        Ext.Loader.setConfig({enabled:true});  
        Ext.Loader.setPath("importExcel","<%=basePath%>/watfh/system/importExcel");
    </script>
    -->
    <script type="text/javascript" src="<%=basePath%>/watfh/system/impExpData/app.js"></script>  
  </head>
  
  <body>
  </body>
</html>
