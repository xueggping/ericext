/**
 * 
 */
package com.summit.common.tbOperate.service;

import java.util.List;

import com.summit.frame.util.Page;

import net.sf.json.JSONObject;

/**
 * 表操作公共接口
 * 
 * @author Zhang.Zhao
 *
 */
public interface TbCommonInter {
	/**
	 * 根据表名查询数据库表字段及属性
	 * @param tableName 表名
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> queryColumnsProperties(String tableName) throws Exception;
	/**
	 * 根据表名查询主键
	 * @param tableName 表名
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> queryKeys(String tableName) throws Exception;
	/**
	 * 添加表数据
	 * @param tableName 表名
	 * @param paramer 待保存数据（jsonobject字符串{}）
	 * @return
	 * @throws Exception
	 */
	public int addTableData(String tableName,String paramer) throws Exception;
	/**
	 * 修改表数据
	 * @param tableName 表名
	 * @param paramer 待修改数据（必须带有主键，json字符串{}）
	 * @return
	 * @throws Exception
	 */
	public int editTableData(String tableName,String paramer) throws Exception;
	/**
	 * 删除表数据(根据删除条件项)
	 * @param tableName 表名
	 * @param paramer 删除条件项（json字符串{}）
	 * @return
	 * @throws Exception 
	 */
	public int deleteTableData(String tableName,String paramer) throws Exception;
	/**
	 * 根据表名查询数据集合
	 * @param tbaleName 表名
	 * @param paramer 页面传参
	 * @return List<Object>
	 * @throws Exception 
	 */
	public List<JSONObject> queryDatasByTbName(String tableName,String paramer) throws Exception;
	
	public Page<JSONObject> queryPageDatasByTbName(String tableName, String paramer, int start, int limit) throws Exception;
	
	/**
	 * 根据sql语句查询数据集合
	 * @param sql sql语句 
	 * @param paramer 页面传参
	 * @return List<Object>
	 * @throws Exception 
	 */
	public List<JSONObject> queryDatasBySQL(String sql,String paramer) throws Exception;
	
	public Page<JSONObject> queryPageDatasBySQL(String sql, String paramer, int start, int limit);
	
	public List<JSONObject> queryPicDatasBySQL(String sql, String paramer ,String tableName) throws Exception;
	/**
	 * 根据表名和查询条件查询（不分页）
	 * @param tableName 表名
	 * @param stcds 测站编码串（以，分割）
	 * @param columns 查询字段（以，分割）
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param dateColumn 按时间查询字段
	 * @param start 
	 * @param limit
	 * @return
	 */
	public List<JSONObject> queryPicData(String tableName, String paramer, String columns) throws IllegalArgumentException;
}