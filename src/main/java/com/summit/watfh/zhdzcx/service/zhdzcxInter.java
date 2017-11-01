package com.summit.watfh.zhdzcx.service;


import org.springframework.stereotype.Component;

@Component
public interface zhdzcxInter {
	public String getSql(Integer type,String stcd,String times);
}
