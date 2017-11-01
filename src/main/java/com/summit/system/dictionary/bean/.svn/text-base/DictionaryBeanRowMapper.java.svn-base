package com.summit.system.dictionary.bean;

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
public class DictionaryBeanRowMapper implements RowMapper<DictionaryBean> {
	@Autowired
	RowMapperUtil rmu;

	public DictionaryBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DictionaryBean(rmu.resultSetGetString(rs, "CODE"),
				rmu.resultSetGetString(rs, "PCODE"), rmu.resultSetGetString(rs, "NAME"),
				rmu.resultSetGetString(rs, "CKEY"), rmu.resultSetGetString(rs, "NOTE"));
	}
}
