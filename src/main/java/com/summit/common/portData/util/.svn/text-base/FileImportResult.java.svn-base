package com.summit.common.portData.util;

import java.io.File;



/**
 * 项目名称：watf   
 * 类名称：FileImportResult   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:55:19    
 * @version
 */
public class FileImportResult {
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	// 错误类型,0表示除数据错误之外的所有错误包括表头信息与下载模板信息不匹配，文件格式不正确，数据条数超过最大限制等 ,1表示有错误数据,如果是0则不进行数据导入处理，2表示数据导入成功无任何错误信息
	private int errorType;
	private int successCount;
	private int errorCount;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private File errorFile;
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public File getErrorFile() {
		return errorFile;
	}
	public void setErrorFile(File errorFile) {
		this.errorFile = errorFile;
	}
	
	
}
