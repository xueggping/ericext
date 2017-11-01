package com.summit.frame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import net.sf.json.JSONObject;
import net.sourceforge.jtds.jdbc.ClobImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.summit.frame.util.SummitTools.DateTimeType;

/**
 * 
 * ClassName: UserDaoRowMapper
 * 
 * @Description: TODO
 * @author 张展弋
 * @date 2016-12-28 下午05:18:27
 */
@Component
public class UserDaoRowMapper implements RowMapper<JSONObject> {
	@Autowired
	SummitTools st;

	public JSONObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsd = rs.getMetaData();
		JSONObject o = new JSONObject();
		String columnName;
		for (int i = 0; i < rsd.getColumnCount(); i++) {
			columnName = rsd.getColumnName(i + 1);
			Object oo = rs.getObject(columnName);
			if (oo instanceof ClobImpl) {
				ClobImpl c = (ClobImpl) oo;
				Reader reader = c.getCharacterStream();
				BufferedReader r = new BufferedReader(reader);
				StringBuilder b = new StringBuilder();
				String line;
				try {
					while ((line = r.readLine()) != null) {
						b.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				o.put(columnName, b.toString());
			} else if (oo instanceof Date) {
				o.put(columnName, st.DTFormat(DateTimeType.dateTime,
						((Date) oo).getTime()));
			} else if (oo instanceof Timestamp) {
				o.put(columnName, st.DTFormat(DateTimeType.dateTime,
						((Timestamp) oo).getTime()));
			} else {
				o.put(columnName, oo);
			}
		}
		return o;
	}
}
