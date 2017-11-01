package com.summit.system.function.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.extComponent.bean.ExtComponent;
import com.summit.common.extComponent.service.ExtComponentInter;
import com.summit.common.functionPage.bean.FunctionPage;
import com.summit.common.functionPage.service.FunctionPageImpl;
import com.summit.common.tbOperate.service.TbCommonInter;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.security.context.UserContext;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.frame.util.SysConstants;
import com.summit.frame.util.TreeNode;
import com.summit.system.function.bean.FunctionBean;
import com.summit.system.function.bean.FunctionBeanRowMapper;

@Service
@Transactional
public class FunctionService {
	@Autowired
	private UserRepository ur;
	@Autowired
	private SummitTools st;
	@Autowired
	private FunctionBeanRowMapper fbrm;
	@Autowired
	private FunctionPageImpl fp;
	@Autowired
	private ExtComponentInter extComponentImpl;
	@Autowired
	private TbCommonInter tbCommonImpl;

	public Map<String, Object> add(FunctionBean fb) throws Exception {
		String sql = "INSERT INTO [SYS_FUNCTION] ([ID], [PID], [NAME], [FDESC], [IS_ENABLED], [FURL], [IMGULR], [NOTE], [SUPER_FUN],[EXECUTESQL]) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		String id = SummitTools.getKey();
		ur.jdbcTemplate.update(sql, id, fb.getPid(), fb.getName(), fb
				.getFdesc(), fb.getIsEnabled(), fb.getFurl(), fb.getImgUlr(),
				fb.getNote(), 0,fb.getExecuteSql());
		//初始化页面组件功能
		int reslult = initFnctionPageComponent(id,fb);
		return st.success("");
	}
	public int initFnctionPageComponent(String functionId,FunctionBean fb) throws Exception{
		String url = fb.getFurl(); 
		if(url.contains("jsp/common/common.jsp")){
			//新增页面
			String add_id = SummitTools.getKey();
			FunctionPage add = new FunctionPage(add_id,functionId, fb.getName(), "300", "500", "column", null, null, "window", "add",0);
			fp.add(add);
			//查询面板
			String north_id = SummitTools.getKey();
			FunctionPage north = new FunctionPage(north_id,functionId, fb.getName(), null, null, "column", null, "north", "panel", "main",1);
			fp.add(north);
			//数据列表
			String center_id = SummitTools.getKey();
			FunctionPage center = new FunctionPage(center_id,functionId, fb.getName(), null, null, null, null, "center", "gridPanel", "main",2);
			fp.add(center);
			//图标列表
			String south_id = SummitTools.getKey();
			FunctionPage south = new FunctionPage(south_id,functionId, fb.getName(), "20%", null, "fit", null, "south", "panel", "main",3);
			fp.add(south);
			//东部布局预留
			String east_id = SummitTools.getKey();
			FunctionPage east = new FunctionPage(east_id,functionId, fb.getName(), null, "10%", "fit", "{hidden:true}", "east", "panel", "main",4);
			fp.add(east);
			//西部布局预留
			String west_id = SummitTools.getKey();
			FunctionPage west = new FunctionPage(west_id,functionId, fb.getName(), null, "10%", "fit", "{hidden:true}", "west", "panel", "main",5);
			fp.add(west);
			if( url.contains("module")){
				String moduleP = url.split("&")[0];
				String tableName = moduleP.substring(moduleP.indexOf('=') +1 ,moduleP.length());
				List<JSONObject>  popList = tbCommonImpl.queryColumnsProperties(tableName);
				if(popList != null && popList.size() >0){
					insertComp(add,popList);
					insertComp(center,popList);
				}
			}
		}
		return 0;
	}
	private void insertComp(FunctionPage fp, List<JSONObject> popList) {
		if("add".equals(fp.getContainerType())){
			int order = 0;
			for (JSONObject j : popList) {
				String id = SummitTools.getKey();
				//根据字段类型判断xtype类型
				String xtype = getXtype(j.getString("COLTYPE"));
				ExtComponent ext = new ExtComponent(
						id,
						fp.getId(), 
						xtype,"200", 
						"5 5 5 5", 
						60, 
						j.getString("COLNAME"), 
						j.getInt("COLDECIMALNUM")>0?j.getInt("COLLEN")+1:j.getInt("COLLEN"), //COLDECIMALNUM 精度，如果精度大于零。则长度为总长度的加1（小数点位），如果为零，则为本身长度
						j.containsKey("COLDESCRIPTION") && !"".equals(j.getString("COLDESCRIPTION"))?j.getString("COLDESCRIPTION"):j.getString("COLNAME"), 
						j.getBoolean("COLISNULL")?1:0, 
						order,
						0);
				extComponentImpl.add(ext);
				order++;
			}
			
			
			
		}else if("main".equals(fp.getContainerType()) && "center".equals(fp.getRegion())){
			int order = 0;
			//gridPanel中的cloumn组件
			String width = getWidth(popList.size());
			for (JSONObject j : popList) {
				String id = SummitTools.getKey();
				ExtComponent ext = new ExtComponent(
						id, 
						fp.getId(), 
						"gridcolumn", 
						order, 
						0, 
						j.containsKey("COLDESCRIPTION") && !"".equals(j.getString("COLDESCRIPTION"))?j.getString("COLDESCRIPTION"):j.getString("COLNAME"), 
						width,
						j.getString("COLNAME"),
						"{iskey:"+j.getString("COLISKEY") + "}");
				extComponentImpl.add(ext);
				order++;
			}
			//gridPanel的button组件
			initGridPanelButton(fp.getId(),order);
		}
		
	}
	private void initGridPanelButton(String fpId, int order) {
		ExtComponent add = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'add'},icon: getRootPath() + '/image/icon/add.png'}", 0, "添加");
		ExtComponent addtbs = new ExtComponent(SummitTools.getKey(), fpId, "tbseparator", order++,null, 0, null);
		ExtComponent edit = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'edit'},icon: getRootPath() + '/image/icon/edit.png'}", 0, "编辑");
		ExtComponent edittbs = new ExtComponent(SummitTools.getKey(), fpId, "tbseparator", order++,null, 0, null);
		ExtComponent del = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'del'},icon: getRootPath() + '/image/icon/delete.png'}", 0, "删除");
		ExtComponent deltbs = new ExtComponent(SummitTools.getKey(), fpId, "tbseparator", order++,null, 0, null);
		ExtComponent show = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'show'},icon: getRootPath() + '/image/icon/visitor.png'}", 0, "查看");
		ExtComponent showtbs = new ExtComponent(SummitTools.getKey(), fpId, "tbseparator", order++,null, 0, null);
		ExtComponent exportExcel = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'exportExcel'},icon:getRootPath() + '/image/icon/watf_export.png'}", 0, "导出Excel");
		ExtComponent exportExceltbs = new ExtComponent(SummitTools.getKey(), fpId, "tbseparator", order++,null, 0, null);
		ExtComponent dayin = new ExtComponent(SummitTools.getKey(), fpId, "button", order++,"{listeners: {click: 'dayin'},icon:getRootPath() + '/image/icon/copy.gif'}", 0, "打印");
		extComponentImpl.add(add);
		extComponentImpl.add(addtbs);
		extComponentImpl.add(edit);
		extComponentImpl.add(edittbs);
		extComponentImpl.add(del);
		extComponentImpl.add(deltbs);
		extComponentImpl.add(show);
		extComponentImpl.add(showtbs);
		extComponentImpl.add(exportExcel);
		extComponentImpl.add(exportExceltbs);
		extComponentImpl.add(dayin);
		
	}
	private String getXtype(String s) {
		String xtype = "textfield";
		if("int".equals(s) || "numeric".equals(s)){
			xtype = "numberfield";
		}
		return xtype;
	}
	private  static String getWidth(int size){
		float num= (float)100/size;   
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
		String s = df.format(num);//返回的是String类型 
		return s + "%";
	}
	public Map<String, Object> del(String ids) {
		ids = ids.replaceAll(",", "','");
		String sql = "SELECT * FROM SYS_FUNCTION WHERE PID IN ('" + ids + "')";
		List<FunctionBean> l = ur.queryAllCustom(sql, fbrm);
		if (st.collectionNotNull(l)) {
			return st.error("不能删除包含子节点的数据");
		}
		sql = "DELETE FROM SYS_FUNCTION WHERE ID IN ('" + ids
				+ "') AND SUPER_FUN <> 1";
		ur.jdbcTemplate.update(sql);

		sql = "DELETE FROM SYS_ROLE_FUNCTION WHERE FUNCTION_ID IN ('" + ids + "')";
		ur.jdbcTemplate.update(sql);
		
		//删除组件页面及组件
		fp.delFunctionId(ids);
		
		return st.success("");
	}

	public static void main(String[] args) {
		String url = "jsp/common/common.jsp?module=test&bbb=true";
		String url1 = "jsp/common/common.jsp?module=test";
		System.out.println("0:" + url.split("&")[0].substring(url.split("&")[0].indexOf('=') +1 ,url.split("&")[0].length()));
		System.out.println("1:" + url1.split("&")[0].substring(url1.split("&")[0].indexOf('=') +1 ,url1.split("&")[0].length()));
		
	}
	public Map<String, Object> edit(FunctionBean fb) throws Exception {
		FunctionBean f = (FunctionBean) queryById(fb.getId()).get("data");
		boolean flag = false;
		if(fb.getFurl().equals(f.getFurl())){
			flag = true;
		} else {
			String fm = f.getFurl().split("&")[0].substring(f.getFurl().split("&")[0].indexOf('=') +1 ,f.getFurl().split("&")[0].length());
			String fbm = fb.getFurl().split("&")[0].substring(fb.getFurl().split("&")[0].indexOf('=') +1 ,fb.getFurl().split("&")[0].length());
			if(fm.equals(fbm)){
				flag = true;
			}
		}
		String sql = "UPDATE SYS_FUNCTION SET NAME = ?, FDESC = ?, IS_ENABLED = ?, FURL = ?, IMGULR = ?, NOTE = ?, EXECUTESQL = ? WHERE ID = ?";
		ur.jdbcTemplate.update(sql, fb.getName(), fb.getFdesc(), fb
				.getIsEnabled(), fb.getFurl(), fb.getImgUlr(), fb.getNote(),fb.getExecuteSql(), fb
				.getId());
		//修改功能连接的时候触发是否初始化组件
		if(!flag){
			//删除组件页面及组件
			fp.delFunctionId(fb.getId());
			initFnctionPageComponent(fb.getId(),fb);
		}
		return st.success("");
	}

	private boolean isSuperUser() {
		if (st.stringEquals(SysConstants.SUPER_USERNAME, UserContext
				.getCurrentUser().getUserName())) {
			return true;
		}
		return false;
	}

	public Map<String, Object> queryById(String id) {
		String sql;
		if (isSuperUser()) {
			sql = "SELECT * FROM SYS_FUNCTION WHERE ID = ?";
		} else {
			sql = "SELECT * FROM SYS_FUNCTION WHERE ID = ? AND SUPER_FUN = 0";
		}
		List<FunctionBean> l = ur.queryAllCustom(sql, fbrm, id);
		if (st.collectionIsNull(l)) {
			return st.error("");
		}
		return st.success("", l.get(0));
	}

	public List<FunctionBean> queryAll() {
		String sql;
		if (isSuperUser()) {
			sql = "SELECT * FROM SYS_FUNCTION ORDER BY FDESC";
		} else {
			sql = "SELECT * FROM SYS_FUNCTION WHERE SUPER_FUN = 0 ORDER BY FDESC";
		}
		return ur.queryAllCustom(sql, fbrm);
	}

	public Page<JSONObject> queryByPage(int start, int limit, String pId) {
		String sql;
		if (isSuperUser()) {
			sql = "SELECT * FROM SYS_FUNCTION WHERE PID = ? ORDER BY FDESC";
		} else {
			sql = "SELECT * FROM SYS_FUNCTION WHERE PID = ? AND SUPER_FUN = 0 ORDER BY FDESC";
		}
		return ur.queryByCustomPage(sql, start, limit, pId);
	}

	public String getFunByUserName(String userName) {
		List<TreeNode<JSONObject>> tn;
		String sql;
		if (isSuperUser()) {
			sql = "SELECT * FROM SYS_FUNCTION WHERE IS_ENABLED = '1' ORDER BY FDESC";
		} else {
			sql = "SELECT DISTINCT SF.* FROM SYS_USER_ROLE SUR INNER JOIN SYS_ROLE_FUNCTION SRF ON ( SUR.ROLE_CODE = SRF.ROLE_CODE ) INNER JOIN SYS_FUNCTION SF ON (SRF.FUNCTION_ID = SF.ID) WHERE SF.IS_ENABLED = '1' AND SF.SUPER_FUN = 0 AND SUR.USERNAME = ? ORDER BY FDESC";
		}
		if (st.stringEquals(userName, SysConstants.SUPER_USERNAME)) {
			tn = st.creatTreeNode(ur.queryAllCustom(sql, fbrm), null);
		} else {
			tn = st.creatTreeNode(ur.queryAllCustom(sql, fbrm, userName), null);
		}
		return JSONArray.fromObject(tn).toString();
	}

	public Map<String, Collection<ConfigAttribute>> getResourceMap() {
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		String sql = "SELECT SRF.ROLE_CODE,SF.FURL FROM SYS_ROLE_FUNCTION SRF INNER JOIN SYS_FUNCTION SF ON (SRF.FUNCTION_ID = SF.ID)";
		List<JSONObject> l = ur.queryAllCustom(sql);
		String role, url;
		for (JSONObject o : l) {
			url = st.objJsonGetString(o, "FURL");
			if (st.stringIsNull(url)) {
				continue;
			}
			role = st.objJsonGetString(o, "ROLE_CODE");
			if (resourceMap.get(url) == null) {
				resourceMap.put(url, new ArrayList<ConfigAttribute>());
				resourceMap.get(url).add(
						new SecurityConfig(SysConstants.SUROLE_CODE));
			}
			resourceMap.get(url).add(new SecurityConfig(role));
		}
		return resourceMap;
	}

}
