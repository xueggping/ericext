/**
 * 
 */
package com.summit.common.tbOperate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.charts.service.ChartstInter;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class TbCommonImpl implements TbCommonInter{
	@Autowired
	private UserRepository repository;
	@Autowired
	private ChartstInter chartstinter;

	public List<JSONObject> queryColumnsProperties(String tableName) throws Exception {
		List<JSONObject> list=null;
		if(!StringUtils.isNotBlank(tableName)){
			throw new Exception("获取表字段属性：表名tableName为空");
		}
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("select c.name as COLNAME,t.name as COLTYPE, \n");
		buf.append("convert(bit,c.IsNullable)  as COLISNULL, \n");
		buf.append("convert(bit,case when exists(select 1 from sysobjects where xtype='PK' and parent_obj=c.id and name in ( \n");
		buf.append("select name from sysindexes where indid in( \n");
		buf.append(" select indid from sysindexkeys where id = c.id and colid=c.colid))) then 1 else 0 end) as COLISKEY, \n");
		buf.append("convert(bit,COLUMNPROPERTY(c.id,c.name,'IsIdentity')) as COLINCREASE, \n");
		buf.append("c.Length as COLSIZE, \n");
		buf.append("COLUMNPROPERTY(c.id,c.name,'PRECISION') as COLLEN, \n");
		buf.append("isnull(COLUMNPROPERTY(c.id,c.name,'Scale'),0) as COLDECIMALNUM, \n");
		buf.append("ISNULL(CM.text,'') as COLDEFAULT,isnull(ETP.value,'') AS COLDESCRIPTION \n");
		buf.append("from syscolumns c \n");
		buf.append("inner join systypes t on c.xusertype = t.xusertype \n");
		buf.append("left join sys.extended_properties ETP on ETP.major_id = c.id and ETP.minor_id = c.colid and ETP.name='MS_Description' \n"); 
		buf.append("left join syscomments CM on c.cdefault=CM.id where 1=1 \n");
		buf.append("and c.id = object_id(?)");
			
		list = repository.queryAllCustom(buf.toString(), tableName);
		
		return list;
	}

	public List<JSONObject> queryKeys(String tableName) throws Exception {
		List<JSONObject> list=null;
		if(!StringUtils.isNotBlank(tableName)){
			throw new Exception("获取表主键：表名tableName为空");
		}
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("SELECT a.name COLNAME FROM   syscolumns a inner  join sysobjects d on a.id=d.id \n"); 
		buf.append("where  d.name=? \n");
		buf.append("and exists(SELECT 1 FROM sysobjects where xtype='PK' and  parent_obj=a.id \n"); 
		buf.append("and name in (SELECT name  FROM sysindexes   WHERE indid in(SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid )))");
		
		list = repository.queryAllCustom(buf.toString(), tableName);
		
		return list;
	}

	public int addTableData(String tableName, String paramer) throws Exception {
		List<JSONObject> list;
		
		if(!StringUtils.isNotBlank(tableName))
		{
			throw new Exception("新增表数据接口：表名tableName为空");
		}
		
		if(!StringUtils.isNotBlank(paramer))
		{
			throw new Exception("新增表数据接口：参数paramer为空");
		}
			
		StringBuffer  buf = new StringBuffer();
		StringBuffer valueBuf = new StringBuffer();
		
		list = queryColumnsProperties(tableName);
		JSONObject json = JSONObject.fromObject(paramer);
			
		if(list!=null && list.size()!=0){
			buf.append("insert into ").append(tableName).append("(");
			valueBuf.append(" values(");
			int j=0;
			for(int i=0;i<list.size();i++)
			{
				JSONObject obj = list.get(i);
				String colName = obj.getString("COLNAME");
				String colType = obj.getString("COLTYPE");
				if(json.containsKey(colName))
				{
					String colValue = json.getString(colName);
					if(j!=0)
					{
						buf.append(","+colName);
						valueBuf.append(",'").append(colValue).append("'");
					}
					else
					{
						buf.append(colName);
						valueBuf.append("'").append(colValue).append("'");
					}
					j++;
				}
			}
			valueBuf.append(")");
			buf.append(")").append(valueBuf.toString());
			if(j!=0)
			{
				int k = repository.jdbcTemplate.update(buf.toString());
				return k;
			}
			else
			{
				throw new Exception("新增表数据接口:数据参数与表名不匹配，无法新增表数据");
			}
		}
		else
		{
			throw new Exception("新增表数据接口:表名不存在");
		}
	}

	public int editTableData(String tableName, String paramer) throws Exception {
		if(!StringUtils.isNotBlank(tableName))
		{
			throw new Exception("修改表数据：表名为空");
		}
		if(!StringUtils.isNotBlank(paramer))
		{
			throw new Exception("新增表数据接口：参数paramer为空");
		}
		
		List<JSONObject> keyList = queryKeys(tableName);
		List<JSONObject> columnList = queryColumnsProperties(tableName);
		JSONObject parJson = JSONObject.fromObject(paramer);
		StringBuffer buf = new StringBuffer();
		buf.append("update ").append(tableName).append(" set ");

		int j=0;
		for(JSONObject obj:columnList)
		{
			String colName = obj.getString("COLNAME");
			if(parJson.containsKey(colName))
			{
				Object colObj = parJson.get(colName);
				if(!(colObj instanceof JSONNull))//处理值为NULL的情况
				{
					String colValue = parJson.getString(colName);
					if(j!=0)
					{
						buf.append(","+colName+"='"+colValue+"'");
					}
					else
					{
						buf.append(colName+"='"+colValue+"'");
					}
				}
				else
				{
					if(j!=0)
					{
						buf.append(","+colName+"=null");
					}
					else
					{
						buf.append(colName+"=null");
					}
				}
				
				j++;
			}
		}
		buf.append(" where 1=1 ");
		for(int k=0;k<keyList.size();k++)
		{
			JSONObject obj=keyList.get(k);
			String colName = obj.getString("COLNAME");
			if(parJson.containsKey(colName)){
				String colValue = parJson.getString(colName);
				buf.append(" and ").append(colName).append("='").append(colValue).append("'");
			}
		}
		
		if(j!=0)
		{
			return repository.jdbcTemplate.update(buf.toString());
		}
		return 0;
	}

	public int deleteTableData(String tableName, String paramer) throws Exception {
		StringBuffer buf = new StringBuffer();
			List<JSONObject> proList = queryColumnsProperties(tableName);
			buf.append("delete from ").append(tableName).append(" where 1=1 ");
			if(StringUtils.isNotBlank(paramer))
			{
				int j=0;
				JSONObject parJson = JSONObject.fromObject(paramer);
				for(JSONObject obj:proList)
				{
					String colName = obj.getString("COLNAME");
					if(parJson.containsKey(colName))
					{
						Object colObj = parJson.get(colName);
						if(!(colObj instanceof JSONNull))
						{
							String colValue = parJson.getString(colName);
							colValue = colValue.replace(",","','");
							buf.append(" and ").append(colName).append(" in ('").append(colValue).append("')");
						}
						else
						{
							buf.append(" and ").append(colName).append(" is null ");
						}
						j++;
					}
				}
				if(j!=0)
				{
					return repository.jdbcTemplate.update(buf.toString());
				}
				else
				{
					throw new Exception("删除表数据：删除条件不正确");
				}
			}
		return 0;
	}
	
	public Page<JSONObject> queryPageDatasByTbName(String tableName, String paramer, int start,int limit) throws IllegalArgumentException {
		if(!StringUtils.isNotBlank(tableName))
		{
			throw new IllegalArgumentException("查询数据集合：表名tableName不能为空");
		}
		
//			String sql = appendSql(tableName,paramer);
		StringBuffer buf = new StringBuffer();
		buf.append("select * from ").append(tableName).append(" where 1=1 ");
		if(StringUtils.isNotBlank(paramer))
		{
			buf.append(appendSQL(tableName,paramer));
		}
		Page<JSONObject> page = repository.queryByCustomPage(buf.toString(), start, limit);
		return page;
		
	}

	public List<JSONObject> queryDatasByTbName(String tableName, String paramer) throws IllegalArgumentException {
		if(!StringUtils.isNotBlank(tableName))
		{
			throw new IllegalArgumentException("查询数据集合：表名tableName不能为空");
		}
		StringBuffer buf = new StringBuffer();
		buf.append("select * from ").append(tableName).append(" where 1=1 ");
		if(StringUtils.isNotBlank(paramer))
		{
			buf.append(appendSQL(tableName,paramer));
		}
		List<JSONObject> list = repository.queryAllCustom(buf.toString());
		return list;
	}
	
	public List<JSONObject> queryDatasBySQL(String sql, String paramer) throws IllegalArgumentException {
		int count = StringUtils.countMatches(sql, "?");
		if(count > 0){
			if(StringUtils.isNotBlank(paramer))
			{
				JSONObject parJson = JSONObject.fromObject(paramer);
				if(parJson.keySet().size() == count){
					return repository.queryAllCustom(sql, appendArgs(paramer));
				}
			}
		}else{
			StringBuffer buf = new StringBuffer();
			if(StringUtils.isNotBlank(paramer))
			{
				buf.append(appendSql(paramer));
			}
			return repository.queryAllCustom(buf.toString());
		}
		return new ArrayList<JSONObject>();
	}

	public List<JSONObject> queryPicDatasBySQL(String sql, String paramer, String tableName) throws IllegalArgumentException {
		int count = StringUtils.countMatches(sql, "?");
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		JSONObject jsonobj = new JSONObject();
		if(count > 0){
			if(StringUtils.isNotBlank(paramer))
			{
				JSONObject parJson = JSONObject.fromObject(paramer);
				if(parJson.keySet().size() == count){
					jsonList = repository.queryAllCustom(sql, appendArgs(paramer));
				}
			}
		}else{
			StringBuffer buf = new StringBuffer();
			if(StringUtils.isNotBlank(paramer))
			{
				buf.append(appendSql(paramer));
			}
			jsonList = repository.queryAllCustom(buf.toString());
		}
		Map<String,String> queryColumn = chartstinter.getQueryCloumn(tableName);
		String x = queryColumn.get("x");
		if(StringUtils.isNotBlank(x)){
			x = x.substring(0,x.indexOf(" "));
		}
		String y = queryColumn.get("y");
		if(StringUtils.isNotBlank(y)){
			y = y.substring(0,y.indexOf(" "));
		}
		if(jsonList.size()>0){
			ArrayList<JSONObject> rdata = new ArrayList<JSONObject>();
			for (JSONObject jsonObject : jsonList) {
				Map<String,String> map = new HashMap<String,String>();
				if(jsonObject.get(x)!=null){
					map.put("x", jsonObject.get(x).toString());
				}
				if(jsonObject.get(y)!=null){
					map.put("y", jsonObject.get(y).toString());
				}
				JSONObject jsonMap = JSONObject.fromObject(map);
				rdata.add(jsonMap);
			}
			return rdata;
		}else{
			return new ArrayList<JSONObject>();
		}
	}

	
	public Page<JSONObject> queryPageDatasBySQL(String sql, String paramer, int start, int limit) {
		int count = StringUtils.countMatches(sql, "?");
		if(count > 0){
			if(StringUtils.isNotBlank(paramer))
			{
				JSONObject parJson = JSONObject.fromObject(paramer);
				if(parJson.keySet().size() == count){
					return repository.queryByCustomPage(sql, start, limit,appendArgs(paramer));
				}
			}
		}else{
			StringBuffer buf = new StringBuffer();
			if(StringUtils.isNotBlank(paramer))
			{
				buf.append(appendSql(paramer));
			}
			return repository.queryByCustomPage(buf.toString(),start, limit);
		}
		return new Page<JSONObject>(new ArrayList<JSONObject>(), 0);
	}

	
	public List<JSONObject> queryPicData(String tableName, String paramer, String columns) throws IllegalArgumentException{
		if(!StringUtils.isNotBlank(tableName))
		{
			throw new IllegalArgumentException("表名为空");
		}
		
		if(!StringUtils.isNotBlank(columns))
		{
			throw new IllegalArgumentException("查询字段为空");
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append("select top 10000 ").append(columns).append(" from ").append(tableName).append(" where 1=1 ");
		if(StringUtils.isNotBlank(paramer))
		{
			buf.append(appendSQL(tableName,paramer));
		}
		
		List<JSONObject> list = repository.queryAllCustom(buf.toString());
		return list;
	}
	
	private String appendSql(String paramer){
		StringBuffer buf = new StringBuffer();
		JSONObject parJSON = JSONObject.fromObject(paramer);
		if(parJSON != null)
		{
			Iterator it = parJSON.keys();
			while(it.hasNext())
			{
				String key = it.next().toString();
				String value = parJSON.getString(key);
				String realkey = key.substring(0,key.lastIndexOf("_"));
				String op = key.substring(key.lastIndexOf("_")+1);
				if("EQ".equals(op)){
					Object colObj = parJSON.get(key);
					if(!(colObj instanceof JSONNull))
					{
						buf.append(" and ").append(realkey).append("=").append("'").append(value).append("'");
					}
					else
					{
						buf.append(" and ").append(realkey).append(" is null ");
					}
				}else if("LIKE".equals(op)){
					buf.append(" and ").append(realkey).append(" like ").append("'%").append(value).append("%'");
				}else if("IN".equals(op)){
					value = value.replaceAll(",", "','");
					buf.append(" and ").append(realkey).append(" in ('").append(value).append("')");
				}else if("DT".equals(op)){
					buf.append(" and ").append(realkey).append(">=").append("'").append(value).append("'");
				}else if("LT".equals(op)){
					buf.append(" and ").append(realkey).append("<=").append("'").append(value).append("'");
				}
			}
		}
		return buf.toString();
	}
	
	private Object[] appendArgs(String paramer) {
		JSONObject parJson = JSONObject.fromObject(paramer);
		Iterator it = parJson.keys();
		Object[] o = new Object[parJson.keySet().size()];
		int i = 0;
		while(it.hasNext())
		{
			String key = it.next().toString();
			if(key.toLowerCase().contains("stcd")){
				o[i++] =  "'" + parJson.getString(key).replace(",", "','") + "'";
			}else{
				o[i++] =  parJson.getString(key);
			}
		}
		return o;
	}
	
	private String appendSQL(String tableName,String paramerJson){
		StringBuffer buf = new StringBuffer();
		JSONObject parJSON = JSONObject.fromObject(paramerJson);
		try {
			List<JSONObject> proList = queryColumnsProperties(tableName);
			if(parJSON != null)
			{
				Iterator it = parJSON.keys();
				while(it.hasNext())
				{
					String key = it.next().toString();
					String value = parJSON.getString(key);
					String realkey = key.substring(0,key.lastIndexOf("_"));
					String op = key.substring(key.lastIndexOf("_")+1);
					boolean realkeyType = getKeyType(proList,realkey);
					if(realkeyType){
						if("EQ".equals(op)){
							Object colObj = parJSON.get(key);
							if(!(colObj instanceof JSONNull))
							{
								buf.append(" and ").append(realkey).append("=").append(value);
							}
							else
							{
								buf.append(" and ").append(realkey).append(" is null ");
							}
						}else if("LIKE".equals(op)){
							buf.append(" and ").append(realkey).append(" like ").append("'%").append(value).append("%'");
						}else if("IN".equals(op)){
							buf.append(" and ").append(realkey).append(" in (").append(value).append(")");
						}else if("DT".equals(op)){
							buf.append(" and ").append(realkey).append(">=").append(value);
						}else if("LT".equals(op)){
							buf.append(" and ").append(realkey).append("<=").append(value);
						}
					}else{
						if("EQ".equals(op)){
							Object colObj = parJSON.get(key);
							if(!(colObj instanceof JSONNull))
							{
								buf.append(" and ").append(realkey).append("=").append("'").append(value).append("'");
							}
							else
							{
								buf.append(" and ").append(realkey).append(" is null ");
							}
						}else if("LIKE".equals(op)){
							buf.append(" and ").append(realkey).append(" like ").append("'%").append(value).append("%'");
						}else if("IN".equals(op)){
							value = value.replaceAll(",", "','");
							buf.append(" and ").append(realkey).append(" in ('").append(value).append("')");
						}else if("DT".equals(op)){
							buf.append(" and ").append(realkey).append(">=").append("'").append(value).append("'");
						}else if("LT".equals(op)){
							buf.append(" and ").append(realkey).append("<=").append("'").append(value).append("'");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	/**
	 * 判断字段的类型是否为int或者numeric
	 * @param proList
	 * @param realkey
	 * @return boolean （true：表示为int或者numeric,false:表示其他类型）
	 */
	private boolean getKeyType(List<JSONObject> proList,String realkey) {
		boolean flag = false;
		for (JSONObject j : proList) {
			if(realkey.equals(j.getString("COLNAME")) && ("int".equals(j.getString("COLTYPE")) || "numeric".equals(j.getString("COLTYPE")))){
				flag = true;
				break;
			};
		}
		return flag;
	}
	
	public static void main(String[] args) {
		String x = "exec UP_Get_DCWEAP_D_By_Stations";
		int count = StringUtils.countMatches(x, "?");
		System.out.println(count);
	}
}