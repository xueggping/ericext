package com.summit.common.functionPage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.summit.common.functionPage.bean.FunctionPage;
import com.summit.common.functionPage.service.FunctionPageImpl;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.frame.util.SysConstants;
import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;
import com.summit.system.function.bean.FunctionBean;
import com.summit.system.function.service.FunctionService;
import com.summit.util.CommonUtil;
import com.summit.util.TreeNode;

/**
 * 
 * ClassName: DemoController
 * 
 * @Description: 功能页面布局组件配置
 * @author wh
 * @date 2016-12-28 上午10:30:47
 */
@Controller
@RequestMapping("funcpage")
public class FunctionPageController {

	@Autowired
	private FunctionPageImpl function;
	@Autowired
	SummitTools st;
	@Autowired
	private FunctionService fs;
	/*@Autowired
	private SummitTools st;*/
	@Autowired
	ILogUtil logUtil;
	
	/**
	 * 查询布局组件集合
	 *@param FunctionPage：组件表对象 （页面传值/后台传参）
	 *@param 是否分页isPageing {true:分页，false：不分页}
	 */
	@RequestMapping("queryByPage")
	@ResponseBody
	public Page<JSONObject> queryByPage(Integer start, Integer limit,
			FunctionPage functionPage,Boolean isPageing, HttpServletRequest request) {
		Page<JSONObject> res = new Page<JSONObject>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", " 查询布局组件集合");
			start = (start == null) ? 1 : start;
			limit = (limit == null) ? SysConstants.PAGE_SIZE : limit;
			res = function.queryByPage(start, limit, functionPage,isPageing);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/**
	 * @Description 根据ID查找布局组件
	 * @param 组件表对象ID 主键id uuid
	 * @return 组件表对象 FunctionPage
	 * 
	 */
	@RequestMapping("queryById")
	@ResponseBody
	public Map<String, Object> queryById(String id) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LogBean logBean = logUtil.insertLog(request,"1", " 根据ID查找布局组件");
		Map<String, Object>  list = null;
		try {
			list = function.queryById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return list;
	}
	/**
	 * 获取页面布局
	 * @Description 通过功能模块的ID来获取该模块的布局及所有的组件对象
	 * @param 功能模块的主键ID
	 * @return 
	 * 
	 */
	@RequestMapping("queryComptById")
	@ResponseBody
	public List<JSONObject> queryComptById(String functionid) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		LogBean logBean = logUtil.insertLog(request,"1", "获取页面布局");
		List<JSONObject>  list = null;
		try {
			list = function.queryComptById(functionid);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return list;
	}
	/**
	 * 新增布局组件
	 * @param 参数：组件表对象 FunctionPage
	 * @return 返回值:Int 是否插入成功
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(FunctionPage functionPage, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "新增布局组件");
			res = function.add(functionPage);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/**
	 * 修改布局组件
	 * @param 参数：组件表对象 FunctionPage
	 * @return 返回值:Int 是否插入成功
	 */
	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(FunctionPage functionPage, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "修改布局组件");
			res = function.edit(functionPage);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/**
	 * 删除布局组件
	 * @Description 删除页面组件（实现批量删除）
	 * @param 组件表对象ID
	 * @return 返回值:Int 是否成功
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> del(String id, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", " 删除布局组件");
			res = function.del(id);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/**
	 * 删除功能组件下的所有信息
	 * @Description 
	 * @param functionId
	 * @return 返回值:Int 是否成功
	 */
	@RequestMapping("delFunctionId")
	@ResponseBody
	public Map<String, Object> delFunctionId(String functionId, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "删除功能组件");
			res = function.delFunctionId(functionId);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	
	/**
	 * 功能下拉树
	 */
	@RequestMapping("getFunction")
	@ResponseBody
	public List<TreeNode> getAdcdList() {
		List<TreeNode> list = new ArrayList<TreeNode>();
		try {
			List<FunctionBean>  bean = fs.queryAll();
			if (st.collectionNotNull(bean)) {
				for (FunctionBean infoB : bean) {
					String pid = null;
					if (infoB.getPid() != null) {
						pid = infoB.getPid();
					}
					list.add(new TreeNode(infoB.getId(), pid,infoB.getName()));
				}
				list = CommonUtil.creatTreeNode(list, null, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
