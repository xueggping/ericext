package com.summit.frame.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.summit.frame.security.context.UserContext;
import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;
import com.summit.system.function.service.FunctionService;

public class CustomAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {

	Logger logger = LoggerFactory
			.getLogger(CustomAuthenticationSuccessHandler.class);
	@Autowired
	FunctionService fs;
	@Autowired
	ILogUtil logUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		logger.debug("Loading menuItem ....");
		HttpSession session = request.getSession();

		String sysFunctions = getUserFunction();
		session.setAttribute("userName", UserContext.getUsername());
		session.setAttribute("fun", sysFunctions);
		session.setAttribute("SESSION_VALIAD", true);
		LogBean logBean = logUtil.insertLog(request,"1", "用户登录");
		super.onAuthenticationSuccess(request, response, authentication);
		logUtil.updateLog(logBean, "1");
	}

	public String getUserFunction() {
		logger.debug("user name:" + UserContext.getUsername());
		return fs.getFunByUserName(UserContext.getUsername());
	}

}
