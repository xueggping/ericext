package com.summit.common.extComponent.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;
/**
 * 项目名称：watfh   
 * 类名称：ExtComponent   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-7-18 上午09:31:17    
 * @version
 */
@Component
public class ExtComponent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 134123412341L;
	
	private String id;				
	private String pageId;			
	private String xtype;			
	private String width;			
	private String height;			
	private String margin;			
	private Integer labelWidth;		
	private String actionName;		
	private Integer maxLength;		
	private String fieldLabel;		
	private Integer allowBlank;		
	private String regex;			
	private String regexText;		
	private String maxLengthText;	
	private String otherProperty;	
	private String extendComponent;	
	private Integer ordered;
	private Integer hidden;
	private String text;
	private String dataIndex;
	private String componentname;//组件对象名称
	
	public ExtComponent() {

	}
	
	public ExtComponent(String id, String pageId, String xtype,
			Integer ordered, Integer hidden, String text,String width,
			String dataIndex,String otherProperty) {
		this.id = id;
		this.pageId = pageId;
		this.xtype = xtype;
		this.ordered = ordered;
		this.hidden = hidden;
		this.text = text;
		this.width = width;
		this.dataIndex = dataIndex;
		this.otherProperty = otherProperty;
	}
	
	public ExtComponent(String id, String pageId, String xtype, 
			Integer ordered,String otherProperty,Integer hidden,String text) {
		this.id = id;
		this.pageId = pageId; 
		this.xtype = xtype;
		this.text = text;
		this.ordered = ordered;
		this.hidden = hidden;
		this.otherProperty = otherProperty;
	}
	
	public ExtComponent(String id, String pageId, String xtype, String width,
			String margin, Integer labelWidth, String actionName, Integer maxLength,
			String fieldLabel, Integer allowBlank,
			Integer ordered, Integer hidden) {
		this.id = id;
		this.pageId = pageId; 
		this.xtype = xtype;
		this.width = width;
		this.margin = margin;
		this.labelWidth = labelWidth;
		this.actionName = actionName;
		this.maxLength = maxLength;
		this.fieldLabel = fieldLabel;
		this.allowBlank = allowBlank;
		this.ordered = ordered;
		this.hidden = hidden;
	}
	
	public ExtComponent(String id, String pageId, String xtype, String width,
			String height, String margin, Integer labelWidth, String actionName,
			Integer maxLength, String fieldLabel, Integer allowBlank,
			String regex, String regexText, String maxLengthText,
			String otherProperty, String extendComponent, Integer ordered,
			Integer hidden, String text, String dataIndex, String componentname) {
		super();
		this.id = id;
		this.pageId = pageId;
		this.xtype = xtype;
		this.width = width;
		this.height = height;
		this.margin = margin;
		this.labelWidth = labelWidth;
		this.actionName = actionName;
		this.maxLength = maxLength;
		this.fieldLabel = fieldLabel;
		this.allowBlank = allowBlank;
		this.regex = regex;
		this.regexText = regexText;
		this.maxLengthText = maxLengthText;
		this.otherProperty = otherProperty;
		this.extendComponent = extendComponent;
		this.ordered = ordered;
		this.hidden = hidden;
		this.text = text;
		this.dataIndex = dataIndex;
		this.componentname = componentname;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPageId() {
		return pageId;
	}



	public void setPageId(String pageId) {
		this.pageId = pageId;
	}



	public String getXtype() {
		return xtype;
	}



	public void setXtype(String xtype) {
		this.xtype = xtype;
	}



	public String getWidth() {
		return width;
	}



	public void setWidth(String width) {
		this.width = width;
	}



	public String getHeight() {
		return height;
	}



	public void setHeight(String height) {
		this.height = height;
	}



	public String getMargin() {
		return margin;
	}



	public void setMargin(String margin) {
		this.margin = margin;
	}



	public Integer getLabelWidth() {
		return labelWidth;
	}



	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}



	public String getActionName() {
		return actionName;
	}



	public void setActionName(String actionName) {
		this.actionName = actionName;
	}



	public Integer getMaxLength() {
		return maxLength;
	}



	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}



	public String getFieldLabel() {
		return fieldLabel;
	}



	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}



	public Integer getAllowBlank() {
		return allowBlank;
	}



	public void setAllowBlank(Integer allowBlank) {
		this.allowBlank = allowBlank;
	}



	public String getRegex() {
		return regex;
	}



	public void setRegex(String regex) {
		this.regex = regex;
	}



	public String getRegexText() {
		return regexText;
	}



	public void setRegexText(String regexText) {
		this.regexText = regexText;
	}



	public String getMaxLengthText() {
		return maxLengthText;
	}



	public void setMaxLengthText(String maxLengthText) {
		this.maxLengthText = maxLengthText;
	}



	public String getOtherProperty() {
		return otherProperty;
	}



	public void setOtherProperty(String otherProperty) {
		this.otherProperty = otherProperty;
	}



	public String getExtendComponent() {
		return extendComponent;
	}



	public void setExtendComponent(String extendComponent) {
		this.extendComponent = extendComponent;
	}



	public Integer getOrdered() {
		return ordered;
	}



	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}



	public Integer getHidden() {
		return hidden;
	}



	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getDataIndex() {
		return dataIndex;
	}



	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}



	public String getComponentname() {
		return componentname;
	}



	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
