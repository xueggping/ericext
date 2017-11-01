package com.summit.common.extComponent.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.summit.frame.util.RowMapperUtil;
/**
 * 项目名称：watfh   
 * 类名称：ExtComponentRowMapper   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-7-18 上午09:53:24    
 * @version
 */
@Component
public class ExtComponentRowMapper implements RowMapper<ExtComponent>{

	@Autowired
	RowMapperUtil rmu;
	public ExtComponent mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new ExtComponent(
				rmu.resultSetGetString(rs, "id"), 
				rmu.resultSetGetString(rs, "pageId"),
				rmu.resultSetGetString(rs, "xtype"), 
				rmu.resultSetGetString(rs, "width"), 
				rmu.resultSetGetString(rs, "height"), 
				rmu.resultSetGetString(rs, "margin"), 
				rmu.resultSetGetInt(rs, "labelWidth"), 
				rmu.resultSetGetString(rs, "actionName"), 
				rmu.resultSetGetInt(rs, "maxLength"), 
				rmu.resultSetGetString(rs, "fieldLabel"), 
				rmu.resultSetGetInt(rs, "allowBlank"), 
				rmu.resultSetGetString(rs, "regex"),
				rmu.resultSetGetString(rs, "regexText"), 
				rmu.resultSetGetString(rs, "maxLengthText"), 
				rmu.resultSetGetString(rs, "otherProperty"), 
				rmu.resultSetGetString(rs, "extendComponent"), 
				rmu.resultSetGetInt(rs, "ordered"),
				rmu.resultSetGetInt(rs, "hidden"),
				rmu.resultSetGetString(rs, "text"),
				rmu.resultSetGetString(rs, "dataIndex"),
				rmu.resultSetGetString(rs, "componentname")
				);
	}
	
}
