package com.summit.common.portData.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：watf   
 * 类名称：ExcelImportConfig   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:22:16    
 * @version
 */
public class ExcelImportConfig {
	private String bean;
	private Head head = new Head();
	private Data data = new Data();
	
	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Head{
		
		private Integer lineNo = -1;
		
		private List<Column> column = new ArrayList<Column>();
		
		public List<Column> getColumn() {
			return column;
		}
		
		public void setColumn(List<Column> column) {
			this.column = column;
		}
		
		public Integer getLineNo() {
			return lineNo;
		}

		public void setLineNo(Integer lineNo) {
			this.lineNo = lineNo;
		}
	}
	
	public class Data{
		private Integer fromLine;
		private Integer rowNum;
		private Integer limit;
		private List<Column> column = new ArrayList<Column>();
		
		public Integer getFromLine() {
			return fromLine;
		}
		public void setFromLine(Integer fromLine) {
			this.fromLine = fromLine;
		}
		public Integer getLimit() {
			return limit;
		}
		public void setLimit(Integer limit) {
			this.limit = limit;
		}
		public List<Column> getColumn() {
			return column;
		}
		public void setColumn(List<Column> column) {
			this.column = column;
		}
		public Integer getRowNum() {
			return rowNum;
		}
		public void setRowNum(Integer rowNum) {
			this.rowNum = rowNum;
		}	
		
	}
	

}

