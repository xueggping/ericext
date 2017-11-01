package com.summit.common.extComponent.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.summit.common.extComponent.bean.ExtComponent;
import com.summit.frame.util.Page;

/**
 * 项目名称：watfh   
 * 类名称：ExtComponentInf   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-7-18 上午09:31:31    
 * @version
 */
@Component
public interface ExtComponentInter {
	/**
	 * 添加组件
	 * @param extComponent
	 * @return
	 */
	public Map<String, Object> add(ExtComponent extComponent);
	/**
	 * 删除组件
	 * @param id
	 * @return
	 */
	public Map<String, Object> del(String id);
	/**
	 * 修改组件
	 * @param extComponent
	 * @return
	 */
	
	public Map<String, Object> edit(ExtComponent extComponent);
	/**
	 * 分页查询
	 * @param start
	 * @param limit
	 * @param extComponent
	 * @return
	 */
	public Page<ExtComponent> queryByPage(Integer start, Integer limit, ExtComponent extComponent,boolean isPageing);
//	/**
//	 * 查询页面的组件集合
//	 * @param id
//	 * @param pageId
//	 * @return
//	 */
//	public Page<JSONObject> queryByPageId(int start, int limit,String pageId,boolean isPageing);
	/**
	 * 查询单个组件
	 * @param id
	 * @return
	 */
	public List<ExtComponent> queryById(String Id);
	/**
	 * 查询系统菜单树
	 * @return
	 */
	public List<JSONObject> getMenuTree();
}
