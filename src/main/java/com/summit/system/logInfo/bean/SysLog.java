package com.summit.system.logInfo.bean;

import java.io.Serializable;
/**
 * 系统日志[WF_SYS_LOG]
 * @author hanlei
 * 2017年3月6日  下午3:04:40
 */
import java.util.Date;
public class SysLog implements Serializable {
	public SysLog(String id,String userName, String callerIP, String funName, Date sTime, Date eTime, String erroInfo,
			int actionTime, String actionFlag) {
		this.id = id;
		this.userName = userName;
		this.callerIP = callerIP;
		this.funName = funName;
		this.sTime = sTime;
		this.eTime = eTime;
		this.erroInfo = erroInfo;
		this.actionTime = actionTime;
		this.actionFlag = actionFlag;
	}
	public SysLog() {
		super();
	}
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String callerIP;
	private String funName;
	private Date sTime;//访问开始时间
	private Date eTime;
	private String erroInfo;
	private int actionTime;
	private String actionFlag;//调用是否成功标志  1：成功  0：失败
	private String sLogTimeFind,eLogTimeFind;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCallerIP() {
		return callerIP;
	}
	public void setCallerIP(String callerIP) {
		this.callerIP = callerIP;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public Date getsTime() {
		return sTime;
	}
	public void setsTime(Date sTime) {
		this.sTime = sTime;
	}
	public Date geteTime() {
		return eTime;
	}
	public void seteTime(Date eTime) {
		this.eTime = eTime;
	}
	public String getErroInfo() {
		return erroInfo;
	}
	public void setErroInfo(String erroInfo) {
		this.erroInfo = erroInfo;
	}
	public int getActionTime() {
		return actionTime;
	}
	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public String getsLogTimeFind() {
		return sLogTimeFind;
	}
	public void setsLogTimeFind(String sLogTimeFind) {
		this.sLogTimeFind = sLogTimeFind;
	}
	public String geteLogTimeFind() {
		return eLogTimeFind;
	}
	public void seteLogTimeFind(String eLogTimeFind) {
		this.eLogTimeFind = eLogTimeFind;
	}

}
