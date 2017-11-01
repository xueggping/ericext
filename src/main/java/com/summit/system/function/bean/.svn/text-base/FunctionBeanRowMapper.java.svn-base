package com.summit.system.function.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.summit.frame.util.RowMapperUtil;

/**
 * 
 * ClassName: UserDaoRowMapper
 * 
 * @Description: TODO
 * @author 张展弋
 * @date 2016-12-28 下午05:18:27
 */
@Component
public class FunctionBeanRowMapper implements RowMapper<FunctionBean> {
	@Autowired
	RowMapperUtil rmu;

	public FunctionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new FunctionBean(rmu.resultSetGetString(rs, "ID"), rmu
				.resultSetGetString(rs, "PID"), rmu.resultSetGetString(rs,
				"NAME"), rmu.resultSetGetInt(rs, "FDESC"), rmu.resultSetGetInt(
				rs, "IS_ENABLED"), rmu.resultSetGetString(rs, "FURL"), rmu
				.resultSetGetString(rs, "IMGULR"), rmu.resultSetGetString(rs,
				"NOTE"),rmu.resultSetGetString(rs,"EXECUTESQL"));
	}
}
