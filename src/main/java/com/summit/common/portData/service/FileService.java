package com.summit.common.portData.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.summit.common.portData.bean.ExprotData;
import com.summit.common.portData.util.Dom4jUtil;
import com.summit.common.portData.util.ExcelHelperBean;
import com.summit.common.portData.util.ExcelImportConfig;
import com.summit.common.portData.util.FileManager;
import com.summit.common.portData.util.MsgBean;
import com.summit.util.CommonUtil;

/**
 * 项目名称：watf   
 * 类名称：FileService   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:11    
 * @version
 */

@Service("fileService")
public class FileService {
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	public  List<Sheet> getSheet(String fileName) throws Exception{
		List<Sheet> list = new ArrayList<Sheet>();
		String remotePath = FileManager.getUploadPath();
		File uploaderFile = new File(remotePath + fileName);
		
		if(!uploaderFile.exists()){
			throw new Exception(fileName+"文件没有找到！");
		}
//		String fileSavePath = uploaderFile.getPath().toString();
//		//文件保存路径
//		File file = new File(fileSavePath);
//		log.info(fileSavePath);
		//文件后缀校验
		if(excelFileSuffixCheck(uploaderFile)){
			Workbook workbook = null;
			try {
				String[] tempArr = uploaderFile.getName().split("\\.");
				if(tempArr[tempArr.length-1].equalsIgnoreCase("xls")){
					workbook = new HSSFWorkbook(new FileInputStream(uploaderFile));
				}else if(tempArr[tempArr.length-1].equalsIgnoreCase("xlsx")){
					workbook = new XSSFWorkbook(new FileInputStream(uploaderFile));
				}
				// 目前只处理第一个sheet页
				Sheet sheet = workbook.getSheetAt(0);
				
				list.add(sheet);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			new Exception("文件格式不正确,导入只支持xls、xlsx后缀的文件!");
		}
		
		return list;
	}
		
	/** 
    * excel文件后缀格式校验
    * @param excelFile excel文件对象
    * @return true 符合规定文件格式  false不符合规定格式
    * @author ChengHu
    **/
	public  boolean excelFileSuffixCheck(File excelFile) {
		boolean flag = false;
		String[] tempArr = excelFile.getName().split("\\.");
		if(tempArr[tempArr.length-1].equalsIgnoreCase("xls")){
			flag = true;
		}else if(tempArr[tempArr.length-1].equalsIgnoreCase("xlsx")){
			flag = true;
		}
		return flag;
	}
	
	//上传文件
	public  MsgBean fileUpload(HttpServletRequest request,HttpServletResponse response){
		String fileName = CommonUtil.getKey();//需要生成文件名称前缀 随机生成的文件名
		MsgBean mb = new MsgBean();
	 	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        /**页面控件的文件流**/  
	 	try {   
			MultipartFile multipartFile = multipartRequest.getFile("filePath");
			if(!multipartFile.isEmpty()){
				/**获取文件的后缀**/  
				String suffix = multipartFile.getOriginalFilename().substring (multipartFile.getOriginalFilename().lastIndexOf(".")+1);  
				/**使用UUID生成文件名称**/  
				fileName +="." + suffix; 
				/**拼成完整的文件保存路径加文件**/   
				String path = FileManager.getUploadPath();// 取得上传文件以后的存储路径  
				File uploaderFile = new File(path + fileName);
				multipartFile.transferTo(uploaderFile);  
			}
	 	}catch (Exception e){
	 		e.printStackTrace();
	 		mb.setSuccess(false);
	 		return mb;
	 	}
		/*String fileName = CommonUtil.getKey();//需要生成文件名称前缀 随机生成的文件名
		MsgBean mb = new MsgBean();
		// 构造一个文件上传处理对象
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload1 = new ServletFileUpload(factory);
		upload1.setHeaderEncoding("UTF-8");
		Iterator items;
		try {
			// 解析表单中提交的所有文件内容
			items = upload1.parseRequest(request).iterator();
			while (items.hasNext()) {
				FileItem item = (FileItem) items.next();
				if (!item.isFormField()) {
					// 取出上传文件的文件名称
					String name = item.getName();
					System.out.println(name);
					// 上传文件以后的存储路径
					String suffix="";
					suffix = name.substring(name.lastIndexOf('.') + 1, name.length());
					fileName +="." + suffix;
					// 上传文件
					if (fileName.length() > 0) {
						// tomct路径存储
						String path = FileManager.getUploadPath();// 取得上传文件以后的存储路径
//						String RealPath = request.getSession().getServletContext().getRealPath("/");// 获取web项目的路径
						File uploaderFile = new File(path + fileName);
						System.out.println("路径："+path+""+fileName);
						if(!uploaderFile.exists()){
							uploaderFile.createNewFile();
						}
						String fileSavePath = uploaderFile.getPath().toString();
						item.write(uploaderFile);
						//文件保存路径
						File file = new File(fileSavePath);
						//写入文件
						item.write(file);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mb.setSuccess(false);
			return mb;
		}*/
//		System.out.println("return:---"+fileName);
		mb.setFileName(fileName);
		mb.setSuccess(true);
		mb.setMsg("上传成功!");
		return mb;
	}
	public static MsgBean fileExport(ExprotData paramsTemp, HttpServletRequest request,HttpServletResponse response){
		MsgBean mb = new MsgBean();
		Locale locale = Locale.getDefault();   
	    ResourceBundle localResource = ResourceBundle.getBundle("fileManager", locale);//加载fileManager.properties
	    String name;
		try {
			if(paramsTemp==null){
				name = new String(request.getParameter("tableName").getBytes("ISO-8859-1"),"UTF-8");//Ad_Info_B
			}else{
				name = new String(paramsTemp.getTableName().getBytes("ISO-8859-1"),"UTF-8");//Ad_Info_B
			}
			
			 //从属性文件中获取的名称
		    String downloadName = localResource.getString(name);//行政区划.xlxs
		    String url = localResource.getString("template");//模板下载的路径
//			String name=path.substring(path.indexOf("\\")+1,path.length());
//			//从属性文件中获取的名称
//			String downloadName = localResource.getString(name);   
//			path = path.substring(0,path.indexOf("\\")+1);// + downloadName;
			if(null!=url&&!"".equals(url)){
				String webPath = FileManager.class.getResource("/").toURI().getPath();//request.getSession().getServletContext().getRealPath("");
				url=webPath+"\\"+url;
				mb.setFileName(downloadName);
				mb.setPath(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mb;
	}
	//写入导入信息
	public  File writeImportMsg(List<ExcelHelperBean> list,MsgBean mb,String tableName) throws Exception{

		String path = FileManager.getUploadPath();
		File uploaderFile = new File(path + mb.getFileName());
		
		if(!uploaderFile.exists()){
			throw new Exception(mb.getFileName()+"文件没有找到！");
		}
		String fileSavePath = uploaderFile.getPath().toString();
		//文件保存路径
		File file = new File(fileSavePath);
		//文件后缀校验
		if(excelFileSuffixCheck(file)){
			Workbook workbook = null;
			try {
				
				FileOutputStream fos = null;
				String[] tempArr = file.getName().split("\\.");
				if(tempArr[tempArr.length-1].equalsIgnoreCase("xls")){
					workbook = new HSSFWorkbook(new FileInputStream(file));
				}else if(tempArr[tempArr.length-1].equalsIgnoreCase("xlsx")){
					workbook = new XSSFWorkbook(new FileInputStream(file));
				}
				// 目前只处理第一个sheet页
				Sheet sheet = workbook.getSheetAt(0);
				
				//加载配置
				ExcelImportConfig el = Dom4jUtil.getExcelImportConfig(tableName); 
				if(el==null){
					return null;
				}
				int successC = 0;
				int msgC = 0;
				int rowSize = sheet.getPhysicalNumberOfRows();
				if(rowSize>1){//创建错误输出列
					//加载head
					Row row = sheet.getRow(el.getHead().getLineNo());
					int maxCell = row.getPhysicalNumberOfCells();
					successC = maxCell;
					msgC = maxCell+1;
					Cell successCell = row.createCell(successC);
					successCell.setCellValue("是否成功");
					Cell msgCell = row.createCell(msgC);
					msgCell.setCellValue("错误描述");
				}
				for (ExcelHelperBean eb : list) {//给指定列写入数据
					Row row = sheet.getRow(eb.getRow());
					if(row==null){ break;}
					Cell successCell = row.createCell(successC);
					log.info("是否成功:"+eb.isSuccess());
					successCell.setCellValue(eb.isSuccess()==true?"是":"否");
					Cell msgCell = row.createCell(msgC);
					msgCell.setCellValue(eb.getMsg());
				}
				fos = new FileOutputStream(file);
				workbook.write(fos);//输出
				fos.flush();
				fos.close(); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}else{
			new Exception("文件格式不正确,导入只支持xls、xlsx后缀的文件!");
		}
	
		return file;
	}
	
}
