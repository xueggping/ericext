package com.summit.watfh.zhdzcx.web;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;
import com.summit.watfh.zhdzcx.service.zhdzcxInter;

@Controller
@RequestMapping("zhdzcxController")
public class zhdzcxController {
	@Autowired
	private zhdzcxInter zi;
	@Autowired
	private UserRepository repository;
	
	@ResponseBody
	@RequestMapping("getData")
	public Page<JSONObject> getData(Integer type,String stcd,String times,Integer start,Integer limit) {
		if(StringUtils.isEmpty(stcd) || StringUtils.isEmpty(times)){
			return new Page<JSONObject>(new ArrayList<JSONObject>(), 0);
		}
		
		return repository.queryByCustomPage(zi.getSql(type,stcd,times), start, limit);
	}
	@ResponseBody
	@RequestMapping("getDataCount")
	public int getDataCount(Integer type,String stcd,String times){
		if(StringUtils.isEmpty(stcd) || StringUtils.isEmpty(times)){
			return 0;
		}
		List<JSONObject> l = repository.queryAllCustom(zi.getSql(type,stcd,times));
		if(l.size() > 0){
			return l.size();
		}else{
			return 0;
		}
	}
	
}
