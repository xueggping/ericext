package com.summit.common.charts.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.summit.common.extComponent.bean.ExtComponent;
import com.summit.frame.util.Page;

/**   
*    
* 项目名称：watfh   
* 类名称：ChartstInter   
* 类描述  公共图表生成
* 创建人：hhc   
* 创建时间：2017-8-2 下午05:35:03   
* @version    
*    
*/
@Component
public interface ChartstInter {
	
public Map<String,String> getQueryCloumn(String tableName);
public List<JSONObject> getStationTree(String tableName);
}
