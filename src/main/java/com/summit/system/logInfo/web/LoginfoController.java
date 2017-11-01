package com.summit.system.logInfo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.summit.frame.util.Page;
import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;
import com.summit.system.logInfo.bean.SysLog;
import com.summit.system.logInfo.service.LoginfoService;

import net.sf.json.JSONObject;

/**
 * 日志查看 
 * @author hanlei
 * 2017年3月6日  下午4:47:54
 */
@Controller
@RequestMapping("loginfo")
public class LoginfoController {
	@Autowired
	private LoginfoService service;
	@Autowired
	ILogUtil logUtil;
	@RequestMapping("delLogs")
	@ResponseBody
	public Map<String,Object> delLogs(String ids,HttpServletRequest request)
	{
		Map<String, Object> res = new HashMap<String, Object>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "删除日志");
			res = service.delLogs(ids);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	@RequestMapping("querySyslogByPage")
	@ResponseBody
	public Page<JSONObject> queryLogsByPage(int start,int limit,SysLog bean,
			HttpServletRequest request)
	{
		Page<JSONObject> res = new Page<JSONObject>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "日志分页查询");
			res = service.queryLogsByPage(start, limit, bean);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	
	
	@RequestMapping("queryAllSyslog")
	@ResponseBody
	public List<JSONObject> queryAllSyslog(
			HttpServletRequest request){
		return service.queryAllSyslog();
	}

}
