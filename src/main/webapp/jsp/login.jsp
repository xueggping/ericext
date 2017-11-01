<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="Base.jsp" flush="true" />
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户登录</title>
		<script language="JavaScript" type="text/JavaScript">
			if (top != self) {
				top.document.location = window.location;
			}
		</script>
		<script language="JavaScript" type="text/JavaScript">
			var errorMsg = "";
			if(GetQueryString("error")=="true"){
				window.onload = function() {
				<%
					Exception e = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
					if(e instanceof org.springframework.security.authentication.BadCredentialsException){
				%>
					errorMsg = '\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF,\u8BF7\u91CD\u65B0\u8F93\u5165\uFF01';
				<%	
					}else if(e instanceof org.springframework.security.authentication.DisabledException){
				%>
					errorMsg = '\u6B64\u7528\u6237\u5DF2\u88AB\u7981\u7528,\u8BF7\u4E0E\u7CFB\u7EDF\u7BA1\u7406\u5458\u8054\u7CFB\uFF01';
				<%
					}
				%>
					}
			}
		</script>
		<script type="text/javascript" src="<%=basePath%>/watfh/main/loginApp.js"></script>
	</head>
	<body>
	</body>
</html>
