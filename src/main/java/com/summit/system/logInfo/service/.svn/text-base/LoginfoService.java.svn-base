package com.summit.system.logInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;
import com.summit.frame.util.SummitTools;
import com.summit.system.logInfo.bean.SysLog;

import net.sf.json.JSONObject;

/**
 * 日志查看 业务类
 * @author hanlei
 * 2017年3月6日  下午3:45:09
 */
@Service
@Transactional
public class LoginfoService {
	@Autowired
	private UserRepository re;
	@Autowired
	private SummitTools st;
	/**
	 * 删除日志
	 * @param:@param ids
	 * @param:@return 
	 * @return Map<String,Object> 
	 * @author hanlei
	 * @date 2017年3月6日 下午3:55:40
	 */
	public Map<String, Object> delLogs(String ids)
	{
		if(st.stringNotNull(ids)){
			ids = ids.replaceAll(",", "','");
		}
		String sql = "delete from wf_sys_log where id in ('"+ids+"')";
		System.out.println(sql);
		re.jdbcTemplate.update(sql);
		return st.success("");
	}
	/**
	 * 分页查询日志数据
	 * @param:@param start
	 * @param:@param limit
	 * @param:@param bean
	 * @param:@return 
	 * @return Page<JSONObject> 
	 * @author hanlei
	 * @date 2017年3月6日 下午3:57:53
	 */
	public Page<JSONObject> queryLogsByPage(int start,int limit,SysLog bean)
	{
		String sql = "select id,funName,callerIP,erroInfo,eTime,actionFlag,"
				+ "sTime,userName,actionTime from wf_sys_log where 1=1 ";
		if(st.stringNotNull(bean.getFunName())){
			sql += " and funName like '%"+bean.getFunName()+"%'";
		}
		if(st.stringNotNull(bean.getCallerIP())){
			sql += " and callerIP like '%"+bean.getCallerIP()+"%'";
		}
		//操作人
		if(st.stringNotNull(bean.getUserName())){
			sql += " and userName like '%"+bean.getUserName()+"%'";
		}
		if(st.stringNotNull(bean.getActionFlag())){
			sql += " and actionFlag = '"+bean.getActionFlag()+"'";
		}
		if(st.stringNotNull(bean.getsLogTimeFind())){
			sql += " and sTime >='"+bean.getsLogTimeFind()+"'";
		}
		if(st.stringNotNull(bean.geteLogTimeFind())){
			sql += " and eTime <='"+bean.geteLogTimeFind()+"'";
		}
		sql += " order by stime desc";
		return re.queryByCustomPage(sql, start, limit);
	}

	public List<JSONObject> queryAllSyslog(){
		String sql = "select * from wf_sys_log where 1=1 order by sTime desc ";
		return re.queryAllCustom(sql);
	}
}
