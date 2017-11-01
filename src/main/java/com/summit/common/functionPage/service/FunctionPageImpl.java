package com.summit.common.functionPage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.extComponent.bean.ExtComponent;
import com.summit.common.functionPage.bean.FunctionPage;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.util.CommonUtil;

@Service
@Transactional
public class FunctionPageImpl{

	@Autowired
	private UserRepository ur;
	@Autowired
	private SummitTools st;
	
	/*@Autowired
	private FunctionPageRowMapper functionPageRowMapper;*/
	
	public Map<String, Object> add(FunctionPage bean) {
		String id = CommonUtil.getKey();//主键uuid
		if(bean.getId() != null && bean.getId().length()>0){
			id = bean.getId();
		}
		String sql = "INSERT INTO [WF_FUNCTION_PAGE] " +
				"([id], [functionId], [title], [height], [width], [layout], [otherProperty], [region], [xtype], [containerType],[ordered]) " +
				"VALUES (?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?) ";
		ur.jdbcTemplate.update(sql, id,bean.getFunctionId(),bean.getTitle(),bean.getHeight(),bean.getWidth(),bean.getLayout(),bean.getOtherProperty(),bean.getRegion(),bean.getXtype(),bean.getContainerType(),bean.getOrdered());
		return st.success("");
	}

	public Map<String, Object> del(String id) {
		id = id.replace(",", "','");
		String sql = "DELETE FROM WF_FUNCTION_PAGE WHERE id IN ('"+ id + "')";
		ur.jdbcTemplate.update(sql);
		sql = "DELETE FROM WF_COMPONENT WHERE pageId IN ('"+ id + "')";
		ur.jdbcTemplate.update(sql);
		return st.success("");
	}

	public Map<String, Object> edit(FunctionPage bean) {
		String sql = "UPDATE WF_FUNCTION_PAGE SET functionId= ?, title= ?, height= ?, width= ?, layout= ?, otherProperty= ?, region= ?, xtype= ? , containerType = ? where id= ? ";
		ur.jdbcTemplate.update(sql,bean.getFunctionId(),bean.getTitle(),bean.getHeight(),bean.getWidth(),bean.getLayout(),bean.getOtherProperty(),bean.getRegion(),bean.getXtype(),bean.getContainerType(),bean.getId());
		return st.success("");
	}

	public Page<JSONObject> queryByPage(int start, int limit,FunctionPage functionPage,Boolean isPageing) {
		StringBuilder sb = new StringBuilder("SELECT t.id, fn.NAME functionId, title, height, width, layout, (case  when otherProperty is null then '' else otherProperty end )otherProperty, region, xtype, containerType " );
		sb.append("FROM WF_FUNCTION_PAGE t ");
		sb.append("LEFT JOIN SYS_FUNCTION fn on t.functionId = fn.id where 1=1 ");
		if (st.stringNotNull(functionPage.getFunctionId())) {
			sb.append(" AND functionId = '"+functionPage.getFunctionId()+"' ");
		}
		if(isPageing==true){//true:分页，false：不分页
			return ur.queryByCustomPage(sb.toString(), start, limit);
		}else{
			List<JSONObject> l = ur.queryAllCustom(sb.toString());
			return new Page<JSONObject>(l, l.size());
		}
	}

	public Map<String, Object> queryById(String id) {
		StringBuilder sql = new StringBuilder("SELECT id, functionId, title, height, width, layout, (case  when otherProperty is null then '' else otherProperty end )otherProperty, region, xtype, containerType FROM WF_FUNCTION_PAGE WHERE id = ? " );
		List<JSONObject> l = ur.queryAllCustom(sql.toString(), id);
		if (st.collectionIsNull(l)) {
			return st.error("");
		}
		return st.success("", l.get(0));
	}

	public List<JSONObject> queryComptById(String functionid) {
		StringBuilder sql = new StringBuilder("SELECT id, functionId, title, height, width, layout, otherProperty, region, xtype, containerType FROM WF_FUNCTION_PAGE WHERE functionid = '"+functionid+"' order by ordered " );
		List<JSONObject> list = ur.queryAllCustom(sql.toString());
		List<JSONObject> resultinfo = new ArrayList<JSONObject>();
		for (JSONObject object : list) {
			StringBuilder pageIdsql = new StringBuilder("SELECT * FROM WF_COMPONENT WHERE pageId = ? order by ordered " );
			List<JSONObject> pageIdlist = ur.queryAllCustom(pageIdsql.toString(), object.get("id"));
			object.put("components", pageIdlist);
			resultinfo.add(object);
		}
		return resultinfo;
	}
	public List<JSONObject> getBusType(){
		String sql="SELECT id,pid PCODE,name FROM SYS_FUNCTION ORDER BY FDESC";
		return ur.queryAllCustom(sql);
	}

	public Map<String, Object> delFunctionId(String functionIds) {
		StringBuilder sql = new StringBuilder("SELECT id FROM WF_FUNCTION_PAGE WHERE functionid in ('"+functionIds+"')" );
		List<JSONObject> list = ur.queryAllCustom(sql.toString());
		for (int i=0; i<list.size();i++) {
			JSONObject obj = JSONObject.fromObject(list.get(i));
			StringBuilder pageIdsql = new StringBuilder("DELETE FROM WF_COMPONENT WHERE pageId = ? " );
			ur.jdbcTemplate.update(pageIdsql.toString(),obj.get("id").toString());
			if((i+1)==list.size()){
				String sqlid = "DELETE FROM WF_FUNCTION_PAGE WHERE functionId IN ('"+ functionIds + "')";
				ur.jdbcTemplate.update(sqlid);
			}
		}
		return st.success("");
	}
}
