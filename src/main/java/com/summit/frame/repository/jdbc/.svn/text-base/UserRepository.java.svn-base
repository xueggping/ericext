package com.summit.frame.repository.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.summit.frame.util.Page;
import com.summit.frame.util.SysConstants;
import com.summit.frame.util.UserDaoRowMapper;

/**
 * 
 * ClassName: UserRepositoryImpl
 * 
 * @Description: TODO
 * @author 张展弋
 * @date 2016-12-28 下午03:43:50
 */
public class UserRepository extends JdbcDaoSupport {
	private static final Logger log = Logger.getLogger(JdbcDaoSupport.class);
	public JdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepository(DataSource dataSource) {
		setDataSource(dataSource);
		jdbcTemplate = super.getJdbcTemplate();
	}

	@Autowired
	private UserDaoRowMapper userDaoRowMapper;

	/**
	 * String sql = "SELECT * FROM test"; List<JSONObject> l =
	 * ur.queryAllCustom(sql); String sql =
	 * "SELECT * FROM test WHERE id = ? AND name = ?"; List<JSONObject> l =
	 * ur.queryAllCustom(sql,4,5);
	 * 
	 * @Description: TODO
	 * @param @param sql
	 * @param @param args
	 * @param @return
	 * @return List<JSONObject>
	 * @throws
	 * @author 张展弋
	 * @date 2016-12-30 下午03:12:32
	 */
	public List<JSONObject> queryAllCustom(String sql, Object... args) {
		return jdbcTemplate.query(sql, args, userDaoRowMapper);
	}

	/**
	 * rowMapper参见com.summit.frame.util.UserDaoRowMapper
	 * 
	 * @Description: TODO
	 * @param @param <T>
	 * @param @param sql
	 * @param @param rowMapper
	 * @param @param args
	 * @param @return
	 * @return List<T>
	 * @throws
	 * @author 张展弋
	 * @date 2017-1-3 上午10:03:43
	 */
	public <T> List<T> queryAllCustom(String sql, RowMapper<T> rowMapper,
			Object... args)  throws DataAccessException{
		return jdbcTemplate.query(sql, args, rowMapper);
	}

	/**
	 * String sql = "SELECT * FROM test"; List<JSONObject> l =
	 * ur.queryByCustomPage(sql,1,1); String sql =
	 * "SELECT * FROM test WHERE id = ? AND name = ?"; List<JSONObject> l =
	 * ur.queryAllCustom(sql,1,1,"admin","admin");
	 * 
	 * @Description: TODO
	 * @param @param sql
	 * @param @param start
	 * @param @param pageSize
	 * @param @param args
	 * @param @return
	 * @return Object
	 * @throws
	 * @author 张展弋
	 * @date 2017-1-3 上午10:05:36
	 */
	public Page<JSONObject> queryByCustomPage(String sql, int start,
			int pageSize, Object... args) {
		return queryByCustomPage(sql, userDaoRowMapper, start, pageSize, args);
	}

	/**
	 * rowMapper参见com.summit.frame.util.UserDaoRowMapper
	 * 
	 * @Description: TODO
	 * @param @param <T>
	 * @param @param sql
	 * @param @param rowMapper
	 * @param @param start
	 * @param @param pageSize
	 * @param @param args
	 * @param @return
	 * @return List<T>
	 * @throws
	 * @author 张展弋
	 * @date 2017-1-3 上午10:14:36
	 */
	public <T> Page<T> queryByCustomPage(String sql, RowMapper<T> rowMapper,
			int start, int pageSize, Object... args) {
		// TODO Auto-generated method stub
		log.info("自定义查询开始SQL:" + sql);
		log.info("自定义查询参数start:" + start + "  pageSize:" + pageSize + "  args:"
				+ args);
		List<T> list = new ArrayList<T>();
		Page<T> p = new Page<T>(list, 0);

		if (start < 0) {
			start = 0;
		}
		if (pageSize < 1) {
			pageSize = SysConstants.PAGE_SIZE;
		}
		int page = start / pageSize + 1;
		DataSource ds = getDataSource();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection(); 
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (null != args && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					pstmt.setObject(i + 1, args[i]);
				}
			}
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			rs.last();
			int rowsCount = rs.getRow();
			if (rowsCount > 0) {
//				int pageCount = getPageCount(rowsCount, pageSize);
//				if (page > pageCount) {
//					page = pageCount;
//				}
				start = (page - 1) * pageSize + 1;
				int end = page * pageSize;
				if (end > rowsCount) {
					end = rowsCount;
				}
				try {
					rs.absolute(start);
				} catch (java.sql.SQLException e) {
				}
				for (; start <= end; start++) {
					list.add(rowMapper.mapRow(rs, start));
					rs.next();
				}
				rs.close();
				p.setTotalElements(rowsCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		log.info("自定义查询结束");
		return p;
	}
	
	
	/**
	 * 调用存储过程专用
	 * @param sql
	 * @param start
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public Page<JSONObject> queryProByCustomPage(String sql, int start,
			int pageSize, Object... args) {
		return queryProByCustomPage(sql, userDaoRowMapper, start, pageSize, args);
	}
	
	
	/**
	 * 调用存储过程专用
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param start
	 * @param pageSize
	 * @param args
	 * @return
	 */
	public <T> Page<T> queryProByCustomPage(String sql, RowMapper<T> rowMapper,
			int start, int pageSize, Object... args) {
		// TODO Auto-generated method stub
		log.info("自定义查询开始SQL:" + sql);
		log.info("自定义查询参数start:" + start + "  pageSize:" + pageSize + "  args:"
				+ args);
		List<T> list = new ArrayList<T>();
		Page<T> p = new Page<T>(list, 0);

		if (start < 0) {
			start = 0;
		}
		if (pageSize < 1) {
			pageSize = SysConstants.PAGE_SIZE;
		}
		int page = start / pageSize + 1;
		DataSource ds = getDataSource();
		Connection conn = null;
		CallableStatement csmt = null;
		try {
			conn = ds.getConnection(); 
			csmt = conn.prepareCall(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (null != args && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					csmt.setObject(i + 1, args[i]);
				}
			}
			csmt.execute();
			ResultSet rs = csmt.getResultSet();
			rs.last();
			int rowsCount = rs.getRow();
			if (rowsCount > 0) {
//				int pageCount = getPageCount(rowsCount, pageSize);
//				if (page > pageCount) {
//					page = pageCount;
//				}
				start = (page - 1) * pageSize + 1;
				int end = page * pageSize;
				if (end > rowsCount) {
					end = rowsCount;
				}
				try {
					rs.absolute(start);
				} catch (java.sql.SQLException e) {
				}
				for (; start <= end; start++) {
					list.add(rowMapper.mapRow(rs, start));
					rs.next();
				}
				rs.close();
				p.setTotalElements(rowsCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (csmt != null && !csmt.isClosed()) {
					csmt.close();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		log.info("自定义查询结束");
		return p;
	}
	
	
	
	

//	private int getPageCount(int rowsCount, int pageSize) {
//		double tmpD = (double) rowsCount / pageSize;
//		int tmpI = (int) tmpD;
//		if (tmpD > tmpI)
//			tmpI++;
//		return tmpI;
//	}
}
