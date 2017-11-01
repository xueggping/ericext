package com.summit.common.portData.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summit.common.portData.bean.ExprotData;
import com.summit.common.portData.util.Column;
import com.summit.common.portData.util.Dom4jUtil;
import com.summit.common.portData.util.ExcelDataBean;
import com.summit.common.portData.util.ExcelHelperBean;
import com.summit.common.portData.util.ExcelImportBean;
import com.summit.common.portData.util.ExcelImportConfig;
import com.summit.common.portData.util.FileImportResult;
import com.summit.common.portData.util.FileManager;
import com.summit.common.portData.util.MsgBean;
import com.summit.common.portData.util.SmbUtil;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.Page;
import com.summit.system.function.service.FunctionService;

/**
 * 项目名称：watf   
 * 类名称：FileService   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:11    
 * @version
 */

@Service("portDataService")
public class PortDataService extends ExcelExportDataConvertor{
	@Autowired
	private ExcelDataConvertor excelDataConvertor;
	@Autowired
	private UserRepository ur;
	private static final Class<ExcelDataBean> entityClz = ExcelDataBean.class;
	@Autowired
	private FileService fileService;
	
	public void exportData2(ExprotData paramsTemp,HttpServletResponse response, HttpServletRequest request) throws Exception{
		StringBuffer sql=new StringBuffer();
		if(paramsTemp.getAdesc()!=null && paramsTemp.getAdesc().length()>0){
			sql.append("select * from ( ");
			sql.append("select ROW_NUMBER() over(order by case when "+paramsTemp.getCloumn()+" is null then 1 else 0 end asc,"+paramsTemp.getCloumn()+" "+paramsTemp.getAdesc()+" ) as rownumber,* ");
			sql.append("from " + paramsTemp.getTableName() + " ");
			sql.append(")t where 1=1 ");
		}else{
			sql.append("select * from " + paramsTemp.getTableName() + " where 1=1 ");
		}
		if(paramsTemp.getLines()!=null && paramsTemp.getLines().length()>0){
			sql.append(" and rownumber <="+paramsTemp.getLines()+" ");
		}
		if(paramsTemp.getStartDate()!=null && paramsTemp.getStartDate().length()>0){
			sql.append(" and "+paramsTemp.getCloumn()+" > '" + paramsTemp.getStartDate() + "'");
		}
		if(paramsTemp.getEndDate()!=null && paramsTemp.getEndDate().length()>0){
			sql.append(" and "+paramsTemp.getCloumn()+" <= '" + paramsTemp.getEndDate() + "'");
		}
		List<JSONObject> listT = ur.queryAllCustom(sql.toString());
		
		//读取配置文件信息
		ExcelImportConfig el = Dom4jUtil.getExcelImportConfig(paramsTemp.getTableName()); 
		export(paramsTemp.getTableName(),listT, response, request);
	}
	public MsgBean exportData(ExprotData paramsTemp, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//加载内容
		StringBuffer sql=new StringBuffer();
		StringBuffer count=new StringBuffer();
		count.append("select count(0)count from "+paramsTemp.getTableName()+" where 1=1 ");
		if(paramsTemp.getAdesc()!=null && paramsTemp.getAdesc().length()>0){
			sql.append("select ROW_NUMBER() over(order by case when "+paramsTemp.getCloumn()+" is null then 1 else 0 end asc,"+paramsTemp.getCloumn()+" "+paramsTemp.getAdesc()+" ) as rownumber,* ");
			sql.append("from " + paramsTemp.getTableName() + " where 1=1 ");
		}else{
			sql.append("select * from " + paramsTemp.getTableName() + " where 1=1 ");
		}
		StringBuffer tjsql=new StringBuffer();
		if(paramsTemp.getStartDate()!=null && paramsTemp.getStartDate().length()>0){
			tjsql.append(" and "+paramsTemp.getCloumn()+" > '" + paramsTemp.getStartDate() + "'");
		}
		if(paramsTemp.getEndDate()!=null && paramsTemp.getEndDate().length()>0){
			tjsql.append(" and "+paramsTemp.getCloumn()+" <= '" + paramsTemp.getEndDate() + "'");
		}
		String linessql="";
		if(paramsTemp.getLines()!=null && paramsTemp.getLines().length()>0){
			count.delete(0, count.length());
			count.append("select  count(0)count from ( ");
			count.append("select ROW_NUMBER() over(order by case when "+paramsTemp.getCloumn()+" is null then 1 else 0 end asc,"+paramsTemp.getCloumn()+" "+paramsTemp.getAdesc()+" ) as rownumber ");
			count.append("from " + paramsTemp.getTableName() + " where 1=1 ");
			count.append(tjsql);
			count.append(")t where 1=1 ");
			linessql=" and rownumber <="+paramsTemp.getLines()+" ";
		}else{
			count.append(tjsql);
		}
		//读取配置文件信息
		ExcelImportConfig el = Dom4jUtil.getExcelImportConfig(paramsTemp.getTableName()); 
		//读取excel模板文件
		MsgBean mb = FileService.fileExport(paramsTemp,request, response);
		File file = new File(mb.getPath() + mb.getFileName());
		XSSFWorkbook S = new XSSFWorkbook(new FileInputStream(file));//后缀为xlsx的读法
		XSSFSheet sheetold = S.getSheetAt(0);//目前只处理第一个sheet页
		
		// 数据是否为空
		Map<Integer, Column> mp = new HashMap<Integer, Column>();
		int rowSize = sheetold.getPhysicalNumberOfRows();
		if(rowSize>1){
			//加载head 保存对应匹配的Cls应该给Excel列设置的属性
			Row row = sheetold.getRow(el.getHead().getLineNo());
			List<Column> list = el.getData().getColumn();
			int i=0;
			for (Column column : list) {
				Cell cell =	row.getCell(i);
				if(column.getName().equals(cell.getStringCellValue())){
					mp.put(i,column);
				}else{
					mb.setSuccess(false);
					mb.setMsg("模板与配置文件不匹配，模板！");
					return mb;
				}
				i++;
			}
		}
		//数据
		List<File> files = new ArrayList<File>();
		//定义导出的最大条数
		Locale locale = Locale.getDefault();
		ResourceBundle localResource = ResourceBundle.getBundle("fileManager", locale);//加载fileManager.properties
		int maxRec = Integer.parseInt(localResource.getString("maxRecord"));
		List<JSONObject> listcount = ur.queryAllCustom(count.toString()+linessql);
		int account = Integer.parseInt(listcount.get(0).get("count").toString());
		if(account>maxRec){
			mb.setSuccess(false);
			mb.setMsg("超过最大导出条数不能导出  请重新选择导出条件！");
			return mb;
		}
		//读取excel模板文件
	    String name = new String(paramsTemp.getTableName().getBytes("ISO-8859-1"),"UTF-8");
	    //从属性文件中获取的名称
	    String extName = localResource.getString(name); 
		int maxRecord = 1000;//1048575;
		int batchNo;//导出的文件个数
		StringBuffer sqlend =new StringBuffer();
		try {
			if (account > maxRecord) {
				batchNo = (account%maxRecord==0)?account / maxRecord:(account / maxRecord+1);
				for (int i = 1; i <= batchNo; i++) {
					int start = (i - 1) * maxRecord + 1; 
					int end = i * maxRecord;
					sqlend.delete(0, sqlend.length());
					sqlend.append("select * from ( "+sql.append(tjsql).toString()+")t where 1=1 ");
					sqlend.append(" and rownumber >="+start);
					sqlend.append(" and rownumber <= "+end);
					List<JSONObject> listT = ur.queryAllCustom(sqlend.toString()+linessql);
					String exportName = extName.split("\\.")[0] + "_part" + (i) + "." + extName.split("\\.")[1];
					fun(files,listT,el,exportName,mp,sheetold);
				}
			} else {
				sqlend.append("select * from ( "+sql.append(tjsql).toString()+")t where 1=1 ");
				List<JSONObject> listT = ur.queryAllCustom(sqlend.toString()+linessql);
				fun(files,listT,el,extName,mp,sheetold);
			}
			String filename="";
			if(files.size()>1){//多个文件需要放到压缩包中下载
				filename = Zipdowload.downLoadFiles(files);
			}else if(files.size()==1){
				filename = files.get(0).getName();
				//导出Excel文件
//				this.outPutFile(files.get(0),response);
			}
			mb.setSuccess(true);
			mb.setMsg("导出成功！");
			mb.setPath(FileManager.getExportPath());
			mb.setFileName(filename);
		} catch (Exception e) {
			e.printStackTrace();
			mb.setSuccess(false);
			mb.setMsg("导出失败！");
		}
		return mb;
	}
	private void fun(List<File> files ,List<JSONObject> listT, ExcelImportConfig el,String exportName, Map<Integer, Column> mp, XSSFSheet sheetold) {
		try {
		  //创建导出excle文件
			File file = this.buildtargetFile(exportName);
			OutputStream out = new FileOutputStream(file);
			ExportData exportData = new ExportData();
			exportData.createSheet(el,sheetold);//创建表头
			exportData.PoiWriteExcel_To2007(listT, out,el,mp);//放值
			files.add(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  FileImportResult importData(HttpServletRequest request,HttpServletResponse response,String tableName) throws Exception{
		FileImportResult rt = new FileImportResult();
		ExcelImportBean<ExcelDataBean> ei = excelDataConvertor.getBeanList(entityClz,response, request, tableName);
		boolean flag = false;
		List<ExcelHelperBean> lehbBeans  = new ArrayList<ExcelHelperBean>();
		List<JSONObject> data = new ArrayList<JSONObject>();
		int errorCount = 0;//失败行数
		int successCount = 0;//成功行数
		for (ExcelDataBean EDB : ei.getList()) {
			if(!EDB.getEhb().isFit()){
				errorCount++;
				lehbBeans.add(EDB.getEhb());
				flag = true;
			}else{
				successCount++;
				data.add(EDB.getData());
			}
		}
		if(flag){
			File file = fileService.writeImportMsg(lehbBeans, ei.getMb(), tableName);
			SmbUtil.smbPut(FileManager.getRemoteUploadPath(), FileManager.getUploadPath()+file.getName());
			rt.setErrorFile(file);
			rt.setErrorCount(errorCount);
			rt.setSuccessCount(successCount);
			rt.setMsg("导入失败！");
		}else{
			//判断是否存在数据，如果存在则更新，如果不存在则插入
			String[] s = getExcuteSqls(data,tableName);
			if(s.length > 0){
				int[] count = ur.jdbcTemplate.batchUpdate(s);
				for (int i = 0; i < count.length; i++) {
					if(count[i] == 1){
						successCount++;
					}else{
						errorCount++;
					}
				}
				rt.setErrorCount(errorCount);
				rt.setSuccessCount(successCount);
				rt.setMsg("导入成功!");
			}
		}
		return rt;
	}
	private String[] getExcuteSqls(List<JSONObject> data,String tableName) throws IOException, DocumentException {
		String[] strings = new String[data.size()];
		List<JSONObject> oldList = ur.queryAllCustom("select * from " + tableName + " where 1=1");
		List<String> keysList = getTableClunmId(tableName);
		
		for (int i = 0; i < data.size(); i++) {
			JSONObject jt = data.get(i);
			boolean flag = false;  
			for (JSONObject jo : oldList) {
				int flagCount = keysList.size();
				for (String key : keysList) {
					if(jo.getString(key).equals(jt.getString(key))){
						flagCount--;
					}
				}
				//如果flagCount大于0,则说明需要新增，如果等于0 则需要更新
				if(flagCount == 0){
					flag = true;
					break;
				}
			}
			//是否更新数据，true 为更新数据，false 为新增数据。
			//组织sql语句
			if(flag){
				String updateValues = getupDateValues(jt,keysList);
				String keyValues = getkeyValues(jt,keysList);
				strings[i] = "update " + tableName + " set  " + updateValues + " where 1=1 " + keyValues;
			}else{
				String insertColumn = getInsertColumn(jt);
				String insertValues = getInsertValues(jt);
				strings[i] = "insert into " + tableName + "(" + insertColumn + ") values (" + insertValues + ")" ;
			}
		}
		return strings;
	}

	private String getInsertValues(JSONObject jt) {
		StringBuffer values = new StringBuffer();
		Iterator<?> keys = jt.keys();
		while(keys.hasNext()){  
			String key = keys.next().toString();
			Object value = jt.get(key);
			if(values.length() > 0){
				values.append(",");
			}
			if(value instanceof String){
				values.append("'");
				values.append(value);
				values.append("'");
			}else if(value instanceof Integer){
				values.append(value);
			}else if(value instanceof Double){
				values.append(value);
			}else if(value instanceof Float){
				values.append(value);
			}else{
				values.append("null");
			}
		}
		return values.toString();
	}


	private String getInsertColumn(JSONObject jt) {
		StringBuffer columns = new StringBuffer();
		Iterator<?> keys = jt.keys();
		while(keys.hasNext()){  
			String key = keys.next().toString();
			if(columns.length() > 0){
				columns.append(",");
			}
			columns.append(key);
		}
		return columns.toString();
	}


	private String getkeyValues(JSONObject jt, List<String> keysList) {
		StringBuffer keyValues = new StringBuffer();
		Iterator<?> keys = jt.keys();
		while(keys.hasNext()){ 
			String key = keys.next().toString();
			Object value = jt.get(key);
			boolean flag = false;
			for (String s : keysList) {
				if(key.equals(s)){
					flag = true;
				}
			}
			if(flag){
				if(value instanceof String){
					keyValues.append("and " + key + "='" + value + "'");
				}else {
					keyValues.append("and " + key + "=" + value);
				}
			}
		}
		return keyValues.toString();
	}


	private String getupDateValues(JSONObject jt, List<String> keysList) {
		StringBuffer dateValues = new StringBuffer();
		Iterator<?> keys = jt.keys();
		while(keys.hasNext()){ 
			String key = keys.next().toString();
			Object value = jt.get(key);
			boolean flag = false;
			for (String s : keysList) {
				if(key.equals(s)){
					flag = true;
				}
			}
			if(!flag){
				if(dateValues.length() > 0){
					dateValues.append(", ");
				}
				if(value instanceof String){
					dateValues.append(key + "=");
					dateValues.append("'");
					dateValues.append(value);
					dateValues.append("'");
				}else if(value instanceof Integer){
					dateValues.append(key + "=" + value);
				}else if(value instanceof Double){
					dateValues.append(key + "=" + value);
				}else if(value instanceof Float){
					dateValues.append(key + "=" + value);
				}else if(value.toString().equals("{}")){
					dateValues.append(key + "=" + null);
				}else{
					dateValues.append(key + "=" + value);
				}
			}
		}
		return dateValues.toString();
	}
	
	/**
	 * 查找表的主键
	 * @param tableName
	 * @return
	 */
	private List<String> getTableClunmId(String tableName){
		 List<String>  list = new ArrayList<String>();
		String sqlString = "select c.name "
			+"	from sysindexes i "
			+"	join sysindexkeys k on i.id = k.id and i.indid = k.indid "
			+"	join sysobjects o on i.id = o.id "
			+"	join syscolumns c on i.id=c.id and k.colid = c.colid "
			+"	where o.xtype = 'U' " 
			+"	and exists(select 1 from sysobjects where xtype = 'PK' and name = i.name) " 
			+"	and o.name = '" + tableName +"'";
		List<JSONObject> maps = ur.queryAllCustom(sqlString);
		for (JSONObject j : maps) {
			list.add(j.getString("name"));
		}
		return list;
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> List<T> convertor(List<T> listT) {
		List<T> listTvo = new ArrayList<T>();
		for (@SuppressWarnings("unused") T t : listT) {
			//实现具体的转换扩展
		}
		return listTvo;
	}
	
	public Page<JSONObject> getTableNm(String pId,int start, int limit) {
		StringBuilder sql = new StringBuilder("select ENAME ckey, CNAME+'('+ENAME+')' name from S_TABLES " );
		try {
			if(pId!=null && pId.length()>0){
				pId = new String(pId.getBytes("iso-8859-1"),"UTF-8");
				sql.append("where CNAME+'('+ENAME+')' like '%"+pId+"%'");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ur.queryByCustomPage(sql.toString(), start, limit);
	}

	public List<JSONObject> getCloumn(String tablenm) {
		StringBuilder sb = new StringBuilder("select a.name ckey,a.name,b.name type from syscolumns a LEFT JOIN systypes b ");
		sb.append("on a.xusertype=b.xusertype where id=object_id('"+tablenm+"') ");
		return ur.queryAllCustom(sb.toString());
	}

}
