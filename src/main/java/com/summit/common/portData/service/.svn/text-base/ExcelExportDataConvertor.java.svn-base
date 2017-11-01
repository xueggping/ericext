package com.summit.common.portData.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;

import com.summit.common.portData.util.Column;
import com.summit.common.portData.util.Dom4jUtil;
import com.summit.common.portData.util.ExcelImportConfig;
import com.summit.common.portData.util.FileManager;
import com.summit.common.portData.util.MsgBean;

/**       
 * 项目名称：watf   
 * 类名称：ExcelExportDataConvertor   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-5-18 下午04:04:29    
 * @version    
 */
public abstract class ExcelExportDataConvertor {
	/**
	 * 根据配置文件，excel模板文件，数据模型BeanList生成导出的Excel文件
	 * @param cls
	 * @param listT
	 * @param response
	 * @param request
	 * @return
	 * @throws RmsException
	 */
	public  <T> Boolean export(String tableName,List<T> listT, HttpServletResponse response, HttpServletRequest request) throws Exception{
		List<File> files = new ArrayList<File>();
		int account = listT.size();
		try {
			//读取excel模板文件
			MsgBean mb = FileService.fileExport(null,request, response);
			File sourceFile = new File(mb.getPath() + mb.getFileName());
			//导出的文件个数
			int batchNo;
			//定义导出的最大条数
			Locale locale = Locale.getDefault();   
		    ResourceBundle localResource = ResourceBundle.getBundle("fileManager", locale);//加载fileManager.properties
		    int maxRecord = Integer.parseInt(localResource.getString("maxRecord"));
			if (account > maxRecord) {
				batchNo = account / (maxRecord + 1);
				for (int i = 0; i < batchNo + 1; i++) {
					if (account - i * maxRecord < maxRecord) {
						List<T> beanList = new ArrayList<T>();
						for(int j = i * maxRecord;j <account;j++){
							beanList.add(listT.get(j));
						}
						String exportName = mb.getFileName();
						exportName = exportName.split("\\.")[0] + "_part" + (i + 1) + "." + exportName.split("\\.")[1];
						this.exportData(files,tableName,beanList,response,exportName,sourceFile);
					} else {
						List<T> beanList = new ArrayList<T>();
						for(int j = i * maxRecord;j < (i + 1) * maxRecord;j++){
							beanList.add(listT.get(j));
						}
						String exportName = mb.getFileName();
						exportName = exportName.split("\\.")[0] + "_part" + (i + 1) + "." + exportName.split("\\.")[1];
						this.exportData(files,tableName,beanList,response,exportName,sourceFile);
					}
				}
			} else {
				List<T> beanList = new ArrayList<T>();
				for(int j = 0;j < account;j++){
					beanList.add(listT.get(j));
				}
				String exportName = mb.getFileName();
				this.exportData(files,tableName,beanList,response,exportName,sourceFile);
			}
			if(files.size()>1){//多个文件需要放到压缩包中下载
				Zipdowload.downLoadFiles(files, request, response);
			}else if(files.size()==1){
				//导出Excel文件
				this.outPutFile(files.get(0),response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public  <T> void exportData(List<File> files,String tableName, List<T> listT,HttpServletResponse response,String exportFile,File sourceFile) throws Exception{
		//创建导出excle文件
		File targetFile = this.buildtargetFile(exportFile);
		//复制excel文件到指定位置
		this.copyFile(sourceFile, targetFile);
		//读取配置文件信息
		ExcelImportConfig el = Dom4jUtil.getExcelImportConfig(tableName); 
		//根据List和配置给excel导出文件中写值 并返回file
		File file = this.swapBeanList(el,listT,targetFile);
		files.add(file);
		//导出Excel文件
//		this.outPutFile(file,response);
	}
	/**
	 * 生成导出文件
	 * @param  cls
	 * @param  el
	 * @param  ListT
	 * @param  file
	 * @return File
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws DocumentException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public  <T> File swapBeanList(ExcelImportConfig el,List<T> ListT, File file) throws Exception, InstantiationException, IllegalAccessException, IOException, DocumentException, SecurityException, IllegalArgumentException, InvocationTargetException{
		//转换数据为Excel显示的数据
		List<T> ListTvo = ListT;//this.convertor();
		if(el==null){
			throw new Exception("没有导入的的配置信息！");
		}
		XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(file));//后缀为xlsx的读法
		XSSFSheet sheet = workBook.getSheetAt(0);//目前只处理第一个sheet页
		// 数据是否为空
		Map<Integer, Column> mp = new HashMap<Integer, Column>();
		int rowSize = sheet.getPhysicalNumberOfRows();
		int maxCell = 0;
		if(rowSize>1){
			//加载head 保存对应匹配的Cls应该给Excel列设置的属性
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
		//清除模板的原有数据内容
		for (int i = el.getData().getFromLine(); i < rowSize; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < maxCell; j++) {
				Cell cell =	row.getCell(j);
				if(cell != null){
					cell.setCellValue("");
				}
			}
		}
		//加载内容
		for (int i = el.getData().getFromLine(); i < ListTvo.size()+el.getData().getFromLine(); i++) {
			Row row = sheet.createRow(i);
//			Row row = sheet.getRow(i);
			JSONObject t = null;
			if(ListTvo.size() >= i-1){
				t = (JSONObject) ListTvo.get(i-el.getData().getFromLine());
//				Class<? extends Object> temp = t.getClass();
				for (int j = 0; j < maxCell; j++) {
				   Cell cell =	row.createCell(j);//row.getCell(j);
				   CellStyle style = workBook.createCellStyle();  //给导出的行添加 单元格的边框样式
				   style.setBorderBottom(CellStyle.BORDER_THIN);  
				   style.setBorderLeft(CellStyle.BORDER_THIN);  
				   style.setBorderRight(CellStyle.BORDER_THIN);  
				   style.setBorderTop(CellStyle.BORDER_THIN);  
				   cell.setCellStyle(style);  
					//赋值
					Column column = mp.get(j);
					if(column != null){
						String property = column.getProperty();
//						property = property.substring(0, 1).toUpperCase() + property.substring(1); 
						Object value;
//						Method fMethod;
						try {
//							fMethod = (Method) t.get(property);//temp.getMethod("get"+property);
							value = t.get(property);//fMethod.invoke(t);
							if(column.getType() != null && column.getType()> 0 && value!=null){
								cell.setCellValue(value.toString());
							}else{
								cell.setCellValue("");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		//构建文件
		FileOutputStream fOut = new FileOutputStream(file);
		workBook.write(fOut);
		fOut.flush();
		fOut.close();
		return file;
	}
	/**
	 * 导出文件
	 * @param file
	 * @param response
	 */
	public  void outPutFile(File file,HttpServletResponse response){
		//获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载  
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
		response.setContentType("application/octet-stream; charset=utf-8");  
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
        
        OutputStream out;  
        try {  
        	String fileName = new String(file.getName().getBytes("GBK"),"ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
            FileInputStream inputStream = new FileInputStream(file);  
  
            //3.通过response获取ServletOutputStream对象(out)  
            out = response.getOutputStream();  
  
            out.write(FileUtils.readFileToByteArray(file));  
            inputStream.close();  
            out.close();  
            out.flush();  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		/*OutputStream os = null;  
	    try {  
	    	os = response.getOutputStream();
//	    	response.reset();  
	        String fileName = new String(file.getName().getBytes("GBK"),"ISO-8859-1");
	        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
	        response.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(FileUtils.readFileToByteArray(file));  
	        os.flush();  
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {  
	        if (os != null) {  
	            try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }  
	    } */
	}
	/**
	 * 复制文件
	 * @param  sourceFile
	 * @param  targetFile
	 * @throws IOException
	 */
	public  void copyFile(File sourceFile, File targetFile) throws IOException {
	    BufferedInputStream inBuff = null;
	    BufferedOutputStream outBuff = null;
	    try {
	        // 新建文件输入流并对它进行缓冲
	        inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
	        // 新建文件输出流并对它进行缓冲
	        outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
	        // 缓冲数组
	        byte[] b = new byte[1024 * 5];
	        int len;
	        while ((len = inBuff.read(b)) != -1) {
	            outBuff.write(b, 0, len);
	        }
	        // 刷新此缓冲的输出流
	        outBuff.flush();
	    } finally {
	        // 关闭流
	        if (inBuff != null)
	            inBuff.close();
	        if (outBuff != null)
	            outBuff.close();
	    }
	 }
	/**
	 * 构建新文件
	 * @param  fileName
	 * @return File
	 * @throws RmsException
	 */
	 public  File buildtargetFile(String fileName) throws Exception {
		String path = FileManager.getExportPath();//#导出文件路径
		File pathFile = new File(path);
		File targetFile = new File(path + fileName);
		if(!pathFile.exists()){
			pathFile.mkdirs();
		}
		if(targetFile.exists()){
			targetFile.delete();
		}
		if(!targetFile.exists()){
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("IO异常");
			}
		}
		return targetFile;
	 }
	 public abstract <T> List<T> convertor(List<T> listT);
}
