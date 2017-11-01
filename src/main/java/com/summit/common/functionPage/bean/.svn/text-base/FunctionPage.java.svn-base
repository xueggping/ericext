package com.summit.common.functionPage.bean;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

import com.summit.common.extComponent.bean.ExtComponent;

public class FunctionPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343412341L;
	
	
	private String id;			
	private String functionId;	
	private String title;		
	private String height;		
	private String width;
	private String layout;
	private String otherProperty;
	private String region;
	private String xtype;
	
	private String containerType;//功能面板类型
	private List<JSONObject> components;
	private Integer ordered;
	public FunctionPage() {
		// TODO Auto-generated constructor stub
	}
	
	public FunctionPage(String id, String functionId, String title,
			String height, String width,String layout, String otherProperty,
			String region,String xtype,String containerType,Integer ordered) {
		super();
		this.id = id;
		this.functionId = functionId;
		this.title = title;
		this.height = height;
		this.width = width;
		this.layout = layout;
		this.otherProperty = otherProperty;
		this.region = region;
		this.xtype = xtype;
		this.containerType = containerType;
		this.ordered = ordered;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getOtherProperty() {
		return otherProperty;
	}
	public void setOtherProperty(String otherProperty) {
		this.otherProperty = otherProperty;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	
	public List<JSONObject> getComponents() {
		return components;
	}
	
	public void setComponent(List<JSONObject> components) {
		this.components = components;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FunctionPage other = (FunctionPage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	
	public Integer getOrdered() {
		return ordered;
	}
	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}

	
}
