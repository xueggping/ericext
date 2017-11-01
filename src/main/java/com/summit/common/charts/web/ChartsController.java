package com.summit.common.charts.web;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.summit.common.charts.service.ChartstInter;
import com.summit.common.tbOperate.service.TbCommonInter;
import com.summit.frame.util.SummitTools;

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
@Controller
@RequestMapping("charts")
public class ChartsController {
	@Autowired
	private ChartstInter ct;
	@Autowired
	private SummitTools st;
	@Autowired
	private TbCommonInter tci;
	
	/**
	 * 获取图表查询条件
	 * param tableName 
	 */
	@RequestMapping("getQueryCloumn")
	@ResponseBody
	public Map<String,String> getQueryCloumn(String tableName){
		return  ct.getQueryCloumn(tableName);
	}
	
	/**
	 * 获取测站树
	 * param tableName 
	 */
	@RequestMapping("getStationTree")
	@ResponseBody
	public List<JSONObject> getStationTree(String tableName){
		return  ct.getStationTree(tableName);
		
	}
}
