package com.summit.frame.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;

/**
 * 
 * ClassName: CommonController
 * 
 * @Description: TODO
 * @author 张展弋
 * @date 2016-12-28 上午10:30:11
 */
@Controller
@RequestMapping(value = "common")
public class CommonController {
	@Autowired
	ILogUtil logUtil;

	@RequestMapping("index")
	public ModelAndView region(HttpServletRequest request) {
		LogBean logBean = new LogBean();
		ModelAndView mav = new ModelAndView();
		try {
			logBean = logUtil.insertLog(request,"1", "CommonController模块index.do");
			mav = new ModelAndView("main");
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return mav;
	}

	@RequestMapping("redirectTo")
	public ModelAndView redirectTo(String pageName,HttpServletRequest request) {
		LogBean logBean = new LogBean();
		ModelAndView mav = new ModelAndView();
		try {
			logBean = logUtil.insertLog(request,"1", "CommonController模块redirectTo.do");
			mav = new ModelAndView(pageName);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return mav;
	}

	@RequestMapping("redirectTo/{pageName}")
	public ModelAndView redirectToURL(@PathVariable("pageName") String pageName,
			HttpServletRequest request) {
		LogBean logBean = new LogBean();
		ModelAndView mav = new ModelAndView();
		try {
			logBean = logUtil.insertLog(request,"1", "redirectTo[{pageName}].do");
			mav = new ModelAndView(pageName);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return mav;
	}

}
