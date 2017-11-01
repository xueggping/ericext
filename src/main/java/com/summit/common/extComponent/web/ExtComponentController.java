package com.summit.common.extComponent.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.summit.common.extComponent.bean.ExtComponent;
import com.summit.common.extComponent.service.ExtComponentInter;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.util.CommonUtil;
import com.summit.util.TreeNode;

@Controller
@RequestMapping("extComponent")
public class ExtComponentController {
	@Autowired
	private ExtComponentInter ect;
	@Autowired
	private SummitTools st;
	
	/**
	 * 查询系统菜单树
	 * @return
	 */
	@RequestMapping("getMenuTree")
	@ResponseBody
	public List<TreeNode> getMenuTree() {
		List<TreeNode> list = new ArrayList<TreeNode>();
		try {
			List<JSONObject> bean = ect.getMenuTree();
			if (st.collectionNotNull(bean)) {
				for (JSONObject infoB : bean) {
					String pid = null;
					if (infoB.get("pid")!= null) {
						pid = infoB.get("pid").toString();
					}
					list.add(new TreeNode(infoB.get("id").toString(), pid,infoB.get("name").toString()));
				}
				list = CommonUtil.creatTreeNode(list, null, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 查询组件集合
	 *@param FunctionPage：组件表对象 （页面传值/后台传参）
	 *@param 是否分页isPageing {true:分页，false：不分页}
	 */
	@RequestMapping("queryByPage")
	@ResponseBody
	public Page<ExtComponent> queryByPage(Integer start, Integer limit, ExtComponent extComponent,boolean isPageing) {
		Page<ExtComponent> page = ect.queryByPage(start,limit,extComponent,isPageing);
		return page;
	}
	
	 /**
	  * 新增
	  * @param extComponent
	  * @return
	  * @throws Exception
	  */
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(ExtComponent extComponent) throws Exception {
		Map<String, Object> list = ect.add(extComponent);
		return list;
	}
	
	/**
	 * 
	 * 根据id查询（分页）
	 * 
	 */
	@RequestMapping("queryById")
	@ResponseBody
	public  Map<String, Object> queryById(String Id) {
		List<ExtComponent> list = ect.queryById(Id);
		return st.success("", list.get(0));
	}
//	/**
//	 * 查询页面的组件集合
//	 * @param id
//	 * @param pageId
//	 * @return
//	 */
//	@RequestMapping("queryByPageId")
//	@ResponseBody
//	public Page<JSONObject> queryByPageId(int start, int limit,String pageId,boolean isPageing) {
//		Page<JSONObject>  list = ect.queryByPageId(start,limit,pageId,isPageing);
//		return list;
//	}
	
	/**
	 * 编辑保存
	 * @throws Exception 
	 */
	
	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(ExtComponent extComponent) throws Exception {
		Map<String, Object>  list = ect.edit(extComponent);
		return list;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> del(String ids) {
		Map<String, Object>  list = ect.del(ids);
		return list;
	}
	
}
