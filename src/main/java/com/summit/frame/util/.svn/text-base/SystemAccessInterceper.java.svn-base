package com.summit.frame.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SystemAccessInterceper extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String p = request.getServletPath();
		//拦截器
		//放过大屏后台请求
		//放过获取版本信息请求
		if (!StringUtils.isEmpty(p) && 
				(p.equals("/daping/getRiverData.do") || p.equals("/daping/getDapingData.do") 
						|| p.equals("/daping/getVersion.do"))
			) {
			return true;
		}
		boolean flag = false;
		// 判断session里是否有已登录标志信息
		if (request.getSession().getAttribute("SESSION_VALIAD") == null){
			// 如果是ajax请求响应头会有，x-requested-with；
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")){
//				System.out.println("未登录的ajax访问");
				response.addHeader("_timeout","true");
			}else{
				// 未登录,不是ajax请求
				flag = super.preHandle(request, response, handler);
			}
		}else{
			// 已登录
			flag = super.preHandle(request, response, handler);
		}
		return flag;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
}
