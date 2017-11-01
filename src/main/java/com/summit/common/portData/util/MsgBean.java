package com.summit.common.portData.util;

/**
 * 项目名称：watf   
 * 类名称：MsgBean   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:55    
 * @version
 */
public class MsgBean {

	private boolean success;
	private String msg;
	private Integer errorCount;
	private Integer successCount;
	private String path;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	
}
