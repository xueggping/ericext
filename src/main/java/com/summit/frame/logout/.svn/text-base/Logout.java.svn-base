package com.summit.frame.logout;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;

/**
 * 
 * 
 * 退出系统日志记录
 * 在前台直接调用，退出逻辑有spring security框架完成，本方法只为记录日志。
 * @author whh
 *
 */
@Controller
@RequestMapping(value = "logout", method = RequestMethod.GET)
public class Logout {
	@Autowired
	ILogUtil logUtil;
	@RequestMapping("logout")
	@ResponseBody
	public void logout(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LogBean logBean = logUtil.insertLog(request,"1", "退出系统！");
		logUtil.updateLog(logBean, "1");
	}
	
	

}
