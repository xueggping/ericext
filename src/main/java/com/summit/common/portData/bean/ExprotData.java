package com.summit.common.portData.bean;

import java.io.Serializable;

public class ExprotData implements Serializable{
	private static final long serialVersionUID = 1343412341L;
	private String tableName;
	private String cloumn;			
	private String adesc;	
	private String startDate;		
	private String endDate;		
	private String lines;
	private String otherProperty;
	public String getCloumn() {
		return cloumn;
	}
	public void setCloumn(String cloumn) {
		this.cloumn = cloumn;
	}
	public String getAdesc() {
		return adesc;
	}
	public void setAdesc(String adesc) {
		this.adesc = adesc;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public String getOtherProperty() {
		return otherProperty;
	}
	public void setOtherProperty(String otherProperty) {
		this.otherProperty = otherProperty;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	
	
	

	
}