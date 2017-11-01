package com.summit.loadOnStartUp;


import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.summit.system.dictionary.service.DictionaryService;

public class ContextLoaderListenerOverWrite extends ContextLoaderListener {
	
	/**
	 * @description 重写ContextLoaderListener的contextInitialized方法
	 */
	public void contextInitialized(ServletContextEvent event) {
		try {
			super.contextInitialized(event);
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			//初始化数据字典
			((DictionaryService)ctx.getBean("dictionaryService")).initSysDic();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
