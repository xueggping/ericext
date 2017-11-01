package com.summit.watfh.yearbook.service;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import com.summit.watfh.yearbook.bean.Yearbook;

@Component
public abstract  class YearbookAbstractService implements YearbookAbstractServiceInter{
	
	public  ModelAndView getPage(Yearbook yearbook,String yearbookStr) {
		if(yearbookStr != null && yearbookStr.length()>0){
			yearbook = (Yearbook) JSONObject.toBean(JSONObject.fromObject(yearbookStr), Yearbook.class);
		}
		ModelAndView mv = new ModelAndView(this.getTemplate());  
        mv.addObject("data", this.getData(yearbook));  
        return mv; 
	}
	
	public abstract  JSONObject getData(Yearbook yearbook);
	
	public abstract  String getTemplate();
	
}
