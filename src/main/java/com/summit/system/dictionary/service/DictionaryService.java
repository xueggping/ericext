package com.summit.system.dictionary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.frame.ehcache.DictionaryCacheImpl;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.frame.util.SysConstants;
import com.summit.system.dictionary.bean.DictionaryBean;
import com.summit.system.dictionary.bean.DictionaryBeanRowMapper;

/**
 * 项目名称：watf   
 * 类名称：DictionaryService   
 * update :2017-2-22 下午08:02:41 
 * @version
 */

@Service
@Transactional
public class DictionaryService {
	@Autowired
	private UserRepository ur;
	@Autowired
	private SummitTools st;
	@Autowired
	private DictionaryBeanRowMapper dbrm;
	@Autowired
	private DictionaryCacheImpl dictionaryCacheImpl;
	
	private static Logger logger = LoggerFactory.getLogger(DictionaryService.class);

	public Map<String, Object> add(DictionaryBean db) {
		String sql = "SELECT * FROM SYS_DICTIONARY WHERE CODE = ?";
		List<JSONObject> l = ur.queryAllCustom(sql, db.getCode());
		if (st.collectionNotNull(l)) {
			return st.error("编码" + db.getCode() + "已存在！");
		}
		sql = "INSERT INTO [SYS_DICTIONARY] ([CODE], [PCODE], [NAME], [CKEY], [NOTE]) VALUES (?, ?, ?, ?, ?)";
		ur.jdbcTemplate.update(sql, db.getCode(), db.getPcode(), db.getName(),
				db.getCkey(), db.getNote());
//		SysDicMap.add(db);
		//新增字典对象加入缓存
		dictionaryCacheImpl.addDic(db);
		return st.success("");
	}

	public Map<String, Object> del(String codes) {
		String codeArr[] = codes.split(",");
		codes = codes.replaceAll(",", "','");
		String sql = "SELECT * FROM SYS_DICTIONARY WHERE PCODE IN ('" + codes
				+ "')";
		List<DictionaryBean> l = ur.queryAllCustom(sql, dbrm);
		if (st.collectionNotNull(l)) {
			return st.error("不能删除包含子节点的数据");
		}
		sql = "DELETE FROM SYS_DICTIONARY WHERE CODE IN ('" + codes + "')";
		ur.jdbcTemplate.update(sql);
		for (String code : codeArr) {
//			SysDicMap.reomve(code);
			dictionaryCacheImpl.delDic(code);
		}
		return st.success("");
	}

	public Map<String, Object> edit(DictionaryBean db) {
		String sql = "UPDATE SYS_DICTIONARY SET NAME = ?, CKEY = ?, NOTE =? WHERE CODE = ?";
		ur.jdbcTemplate.update(sql, db.getName(), db.getCkey(), db.getNote(),
				db.getCode());
//		SysDicMap.update(db);
		dictionaryCacheImpl.editDic(db);
		return st.success("");
	}

	public Map<String, Object> queryByCode(String code) {
//		DictionaryBean db = SysDicMap.getByCode(code);
		DictionaryBean db=  dictionaryCacheImpl.queryByCode(code);
		if (db == null) {
			return st.error("");
		}
		return st.success("", db);
	}

	public List<DictionaryBean> queryAll() {
		return ur.queryAllCustom("SELECT * FROM SYS_DICTIONARY", dbrm);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryTree() {
		return st.success("",  dictionaryCacheImpl.getAll());
	}

	public Page<DictionaryBean> queryByPage(int start, int limit, String pId) {
//		List<DictionaryBean> list = SysDicMap.getChildList(pId);
		List<DictionaryBean> list = dictionaryCacheImpl.findChildList(pId);
		
		List<DictionaryBean> l = new ArrayList<DictionaryBean>();
		for (int i = start, j = 0; i < list.size(); i++, j++) {
			if (j >= limit) {
				break;
			}
			l.add(list.get(i));
		}
		return new Page<DictionaryBean>(l, list.size());
	}
	
	public List<DictionaryBean> queryByPid(String pId){
		return dictionaryCacheImpl.findChildList(pId);
	}
	
	/**
	 * 初始化字典缓存加载
	 */
	public void initSysDic(){
		List<DictionaryBean> all = queryAll();
		for (DictionaryBean dictionaryBean : all) {
			dictionaryCacheImpl.setCacheElement(SysConstants.DICTIONARY, dictionaryBean.getCode(), dictionaryBean);
		}
		dictionaryCacheImpl.setCacheElement(SysConstants.DICTIONARY, "dictionaryAll", all);
		logger.info("Dictionary Initialized...");
	}
	/**
	 * 根据测站编码查询 测站基本信息
	 * @param s
	 * @param len
	 * @return
	 */
	public JSONObject getStsc(String stcd) {
		String sql = "select * from HY_STSC_A where stcd = " + stcd;
		List<JSONObject> s = ur.queryAllCustom(sql);
		if(s!= null && s.size() > 0){
			return s.get(0);
		}
		return null;
	}
	
	
	
	
}
