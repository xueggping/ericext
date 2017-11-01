package com.summit.system.logInfo.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.summit.frame.util.RowMapperUtil;
/**
 * 系统日志表封装类
 * @author hanlei
 * 2017年3月6日  下午3:28:48
 */
@Component
public class SysLogRowMapper implements RowMapper<SysLog> 
{
	@Autowired
	private RowMapperUtil rmu;

	public SysLog mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		return new SysLog(rmu.resultSetGetString(rs, "id"),
				rmu.resultSetGetString(rs, "userName"), 
				rmu.resultSetGetString(rs, "callerIP"),
				rmu.resultSetGetString(rs, "funName"),
				rmu.resultSetGetTimestamp(rs, "sTime"),
				rmu.resultSetGetTimestamp(rs, "eTime"),
				rmu.resultSetGetString(rs, "erroInfo"),
				rmu.resultSetGetInt(rs, "actionTime"),
				rmu.resultSetGetString(rs, "actionFlag"));
	}

}
