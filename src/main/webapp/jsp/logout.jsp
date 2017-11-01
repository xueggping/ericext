<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@ page
	import="org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices"%>

<%
	if (request.getSession(false) != null) {
		session.invalidate();
	}
	Cookie terminate = new Cookie(
	TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY,null);
	String contextPath = request.getContextPath();
	terminate.setPath(contextPath != null && contextPath.length() > 0 ? contextPath	: "/");
	terminate.setMaxAge(0);
	response.addCookie(terminate);
	
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>
<html>
<head>
<script type="text/javascript">
	window.location.href = "<%=basePath%>/jsp/login.jsp";
</script>
</head>
</html>
