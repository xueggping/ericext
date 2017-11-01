package com.summit.watfh.yearbook.service;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.summit.watfh.yearbook.bean.Yearbook;

@Component
public interface YearbookAbstractServiceInter {
	public  ModelAndView getPage(Yearbook yearbook,String yearbookStr);
	abstract  JSONObject getData(Yearbook yearbook);
	abstract  String getTemplate();
}
