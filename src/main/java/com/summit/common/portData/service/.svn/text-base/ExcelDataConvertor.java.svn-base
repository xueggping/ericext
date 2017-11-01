/**
 * 
 */
package com.summit.common.portData.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.summit.common.portData.util.Column;
import com.summit.common.portData.util.Dom4jUtil;
import com.summit.common.portData.util.ExcelDataBean;
import com.summit.common.portData.util.ExcelHelperBean;
import com.summit.common.portData.util.ExcelImportBean;
import com.summit.common.portData.util.ExcelImportConfig;
import com.summit.common.portData.util.MsgBean;
import com.summit.util.CommonUtil;

/**
 * 项目名称：watf   
 * 类名称：ExcelDataConvertor   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:23:25    
 * @version
 */
@Service("excelDataConvertor")
public  class ExcelDataConvertor{
	@Autowired
	private FileService fileService;
	
	public  Logger log = LoggerFactory.getLogger(ExcelDataConvertor.class);
	
	public <T> List<T> swapBeanList(Class<T> cls,String tableName, Sheet sheet) throws Exception, InstantiationException, IllegalAccessException, IOException, DocumentException, SecurityException, IllegalArgumentException, InvocationTargetException{
		List<T> list = new ArrayList<T>();
		log.info("传入参数的表名称:"+tableName);
		//加载配置 在excelImportConfig.xml中找tableName
		ExcelImportConfig el = Dom4jUtil.getExcelImportConfig(tableName); 
		if(el==null){
			log.info("没有'"+tableName+"'的配置信息！");
			throw new Exception("没有导入的的配置信息！");
		}
		log.info("从XML中取出需要转换的表名称:"+el.getBean());
		if(checkExcelHead(sheet,el)){
			throw new Exception("表头信息不匹配，请使用导入模板导入！");
		}
		int rowSize = checkExcelDataCount(sheet,el);
		if(rowSize-el.getData().getFromLine()<= el.getData().getLimit()){
			// 数据是否为空
			if(!checkExcelDataIsNull(sheet)){
				Map<Integer, Column> mp = new HashMap<Integer, Column>();
//				int rowSize = sheet.getPhysicalNumberOfRows();
				int maxCell = 0;
				if(rowSize>1){
					//加载head
					Row row = sheet.getRow(el.getHead().getLineNo());
					maxCell = row.getPhysicalNumberOfCells();
					for (int j = 0; j < maxCell; j++) {
						Cell cell =	row.getCell(j);
						if(null!=cell)
						{
							List<Column> lc = el.getData().getColumn();
							for (Column column : lc) {
								if(column.getName().equals(cell.getStringCellValue())){
									mp.put(j,column);
								}
							}
						}
					}
				}
				log.info("XML配置中的列名:"+mp.toString());
				//加载内容
				for (int i = el.getData().getFromLine(); i < rowSize; i++) {
					JSONObject o = new JSONObject();
					Row row = sheet.getRow(i);
					ExcelDataBean excelDataBean = new ExcelDataBean();
					ExcelHelperBean excelHelperBean = new ExcelHelperBean();
					StringBuffer msg = new StringBuffer();
					StringBuffer values =  new StringBuffer();
					for (int j = 0; j < maxCell; j++) {
							if(values.length() > 0){
								values.append(" , ");
							}
							Cell cell =	row.getCell(j);
							Column column = mp.get(j);
							String property = column.getProperty();
							String value = "";
							if(property==null){
								excelHelperBean.setFit(false);
								msg.append(column.getName()+"的配置错误！\n");
							}
							//赋值
							if(cell!=null){
								if(cell.getCellType()==cell.CELL_TYPE_NUMERIC){
									Double d =  cell.getNumericCellValue();
									if(d!=null){
										value = d.toString().trim();
									}
								}else if(cell.getCellType()==cell.CELL_TYPE_STRING){
									value = cell.getStringCellValue().trim();
								}
							}
							//数据类型
							//type (1:String,2:int,3:Float,4:Double,)
							Object ov = new Object();ov=null;
							try {
								if(column.getType()==1){
									ov="";
								}
								if(column.getType() != null && column.getType()> 0 && value.length()>0){
									if(column.getType()==3 && column.getParamer1()!=null &&  column.getParamer1().length()>0 
											&& Integer.parseInt(column.getParamer1())==0
											&& value.substring(value.indexOf(".")+1, value.length()).equals(column.getParamer1())){
										column.setType(2);//如果numeric(4,0)没有小数位则为整形
									}
									if(column.getType()==3 && value.indexOf(".")<0){
										column.setType(1);//如果numeric(4,0)没有小数位则为整形
									}
									switch (column.getType()) {
									case 1:
										ov = value;
										break;
									case 2:
										ov = Math.round(Float.parseFloat(value));//四舍五入转整形
										break;
									case 3:
										if(column.getParamer1()!=null &&  column.getParamer1().length()>0 
											&& value.indexOf(".")>0
											&& Integer.parseInt(column.getParamer1())>=(value.contains(".") ? value.substring(value.indexOf(".")+1, value.length()).length():0) ){
											ov = Double.parseDouble(value);
										}else{
											excelHelperBean.setFit(false);
											msg.append(column.getName()+"列:\""+value+"\"精度与数据库中不一致！\n");
										}
										break;
									case 4:
										ov = Double.parseDouble(value);		
										break;
									default:
										ov = value;
										break;
									}
								}
							} catch (Exception e) {
								//e.printStackTrace();
								excelHelperBean.setFit(false);
								msg.append(column.getName()+"列:\""+value+"\"与数据库中类型不一致！\n");
							}
							if(ov!=null){
								value=ov.toString();
							}
							//非空验证
							if(!column.isAllowEmpty()){
								if(value==null||value.length()<1){
									log.info("验证是否为空！");
									excelHelperBean.setFit(false);
									msg.append(column.getName()+"列不能为空！\n");
								}else{
									//正则验证
									if(column.getRegex()!=null&&column.getRegex().length()>0){
										String regex =  new String(column.getRegex());
										if(!CommonUtil.regexString(regex, value)){
											excelHelperBean.setFit(false);
											msg.append(column.getName()+"列:值\""+value+"\"数据格式不对！\n");
										}
									}
								}
							}
							//长度校验
							if(column.getLength()!=null && column.getLength()>0&&value!=null){
								if(value.length() != column.getLength()&&value.length()>0){
									excelHelperBean.setFit(false);
									msg.append(column.getName()+"列:值\""+value+"\"长度必须为 "+column.getLength()+" 位！\n");
								}
							}
							//最大长度控制
							if(column.getMaxLength()!=null&&column.getMaxLength()>0&&value!=null &&value.length()>0 ){
								if(column.getParamer1()!=null && column.getParamer1().length()>0 && value.contains(".") ){//数据类型为numric时候
									if(value.substring(0,value.indexOf(".")).length() > (column.getMaxLength()-Integer.parseInt(column.getParamer1())) ){//判断整数长度 是否大于 与(最大长度-精度)
										excelHelperBean.setFit(false);
										msg.append(column.getName()+"列:值\""+value+"\"长度已经超出 "+column.getMaxLength()+" 位！\n");
									}
								}else{//字符串
									int nameLen=CommonUtil.length(value);//不能超过数据库长度
									if(nameLen>column.getMaxLength()){
										excelHelperBean.setFit(false);
										msg.append(column.getName()+"列:值\""+value+"\"长度已经超出 "+column.getMaxLength()+" 位！\n");
									}
								}
							}
							//字典转换
							if(column.getStore()!=null&&column.getStore().length()>0){
								log.info(column.getStore()+"转码---前值："+value);
								if(column.getStore().equals("sysDictionary")){//从数据字典获取值
									if(value.length()>0){
										String sdCode = "";//SysDicMap.getSdMap_name().get(column.getParams()+"."+value);
										log.info("获取数据字典:"+sdCode);
										if(sdCode==null){
											excelHelperBean.setFit(false);
											msg.append(column.getName()+"列:\""+value+"\"在数据字典中不存在！\n");
											value = "";
										}else{
											value = sdCode;
										}
									}else{
										value = column.getValue();
									}
								}
							}
							o.put(property, ov);
					}
					excelHelperBean.setMsg(msg.toString());
					excelHelperBean.setRow(i);
					T t = cls.newInstance();
					Method	fMethod = cls.getMethod("getData");
					Method method = cls.getMethod("setData", fMethod.getReturnType());
					method.invoke(t, o);
					Method	fMethod1 = cls.getMethod("getEhb");
					Method method1 = cls.getMethod("setEhb", fMethod1.getReturnType());
					method1.invoke(t, excelHelperBean);
					list.add(t);
				}
			}else{
				log.info("Excel中无数据!");
				new Exception("Excel中无数据!");
			}
		}else{
			log.info("数据超过最大条数限制!");
			throw new Exception("一次导入记录不能超过"+el.getData().getLimit()+"条！");
		}
		
		return list;
	}
	
	/**
	 * 检查头部信息是否匹配
	 * @param sheet
	 * @param el
	 * @return
	 */
	private  boolean checkExcelHead(Sheet sheet, ExcelImportConfig el) {
		Row row = sheet.getRow(el.getHead().getLineNo());
		List<Column> list = el.getData().getColumn();
		boolean flag = false;
		for (Column column : list) {
			int index = column.getOrder();
			String name = row.getCell(index).getStringCellValue();
			if(!name.equals(column.getName())){
				flag = true;
			}
		}
		return flag;
	}
	

	/** 
	* 读取excel数据,校验数据条数是否超过最大值
	* @param sheet excel中的sheet页
	* @return true 未超过限制数量  false超过限制数量
	* @author ChengHu
	**/
	protected  int checkExcelDataCount(Sheet sheet,ExcelImportConfig el) {
		int rowSize = sheet.getPhysicalNumberOfRows();//行
		
		int maxCell = 0;
		if(rowSize>el.getHead().getLineNo()){
			Row row = sheet.getRow(el.getHead().getLineNo());//sheet页的第一行
			maxCell = row.getPhysicalNumberOfCells();//sheet表头
		}
		for (int i = el.getData().getFromLine(); i < rowSize; i++) {
			Row row = sheet.getRow(i);
			int nullCount = 0;
			for (int j = 0; j < maxCell; j++) {
					Cell cell =	row.getCell(j);
					if(cell!=null){
						String value = "";
						if(cell.getCellType()==cell.CELL_TYPE_NUMERIC){
							Double d =  cell.getNumericCellValue();
							if(d!=null){
								value = d.toString().trim();
							}
						}else if(cell.getCellType()==cell.CELL_TYPE_STRING){
							value = cell.getStringCellValue().trim();
						}
						if(value.length()==0){
							nullCount++;
						}
					}else{
						nullCount++;
					}
			}
			if(nullCount>=maxCell){
				rowSize = i;
				break;
			}
		}
		log.info("数据量："+ (rowSize - el.getData().getFromLine()));
//		if(rowSize-el.getData().getFromLine()>el.getData().getLimit()){
//			return false;
//		}else{
//			return true;
//		}
		return rowSize;
	}
	
	
	/** 
	* 读取excel数据,校验数据条数是否为空
	* @param sheet excel中的sheet页
	* @return true 为空 false不为空
	* @author ChengHu
	**/
	private  boolean checkExcelDataIsNull(Sheet sheet) {
		boolean flag = false;
		if (sheet != null) {
			// 1001算表头
			if (sheet.getPhysicalNumberOfRows() <= 1) {
				flag = true;
			}
		}
		return flag;
	}
	
	public <T> ExcelImportBean<T> getBeanList(Class<T> cls ,HttpServletResponse response, HttpServletRequest request,String tableName) throws Exception{
		ExcelImportBean<T> ei = new ExcelImportBean<T>();
		//1、上传文件
		//2、读取sheet
		//3、转换Bean
		try {
			MsgBean mb = fileService.fileUpload(request, response);
			if(mb!=null&&mb.isSuccess()){
				log.info(mb.getFileName());
				List<Sheet> list = fileService.getSheet(mb.getFileName());
				if(list!=null&&list.size()>0){
					for (Sheet sheet : list) {
						List<T> l = swapBeanList(cls, tableName, sheet);
						ei.setMb(mb);
						ei.setList(l);
					}
				}else{
					new Exception("文件中没有Sheet!");
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new Exception("SecurityException");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new Exception("IllegalArgumentException");
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new Exception("InstantiationException");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("IllegalAccessException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("IOException");
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new Exception("DocumentException");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new Exception("InvocationTargetException");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ei;
	}
	
}
