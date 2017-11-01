package com.summit.common.portData.util;

/**
 * 项目名称：watf   
 * 类名称：ExcelHelperBean   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:41    
 * @version
 */
public class ExcelHelperBean {

	private String msg;//消息
	private boolean fit = true;//是否通过文件检查
	private boolean success = true;//是否通过数据验证(录入验证)
	private int row;//文件触发行数
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean isFit() {
		return fit;
	}
	public void setFit(boolean fit) {
		this.fit = fit;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
}
