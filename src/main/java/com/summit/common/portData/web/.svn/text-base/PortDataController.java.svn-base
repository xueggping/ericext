package com.summit.common.portData.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.summit.common.portData.bean.ExprotData;
import com.summit.common.portData.service.ExportData;
import com.summit.common.portData.service.PortDataService;
import com.summit.common.portData.service.Zipdowload;
import com.summit.common.portData.util.FileImportResult;
import com.summit.common.portData.util.FileManager;
import com.summit.common.portData.util.MsgBean;
import com.summit.frame.util.Page;
import com.summit.frame.util.LogUtil.bean.LogBean;
import com.summit.frame.util.LogUtil.service.ILogUtil;


/**       
 * 项目名称：watf   
 * 类名称：FileController   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 下午02:44:57    
 * @version    
 */
@Controller
@RequestMapping("portDataController")
public class PortDataController {

	@Autowired
	private PortDataService portDataService;
	@Autowired
	ILogUtil logUtil;

	/*****
	 * @Description 设备模板下载
	 * 
	 * ***/
	@RequestMapping("downloadTemplate")
	public void downloadTemplate(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Locale locale = Locale.getDefault();   
		ResourceBundle localResource = ResourceBundle.getBundle("fileManager", locale);   
		String name = new String(req.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		String webPath = FileManager.class.getResource("/").getPath();//req.getSession().getS
		String downloadName=localResource.getString(name);   
		String url = localResource.getString("template") + downloadName;
		url=webPath+"\\"+url;
		if(null!=url&&!"".equals(url)){
			OutputStream os = null;  
			try {  
				os = res.getOutputStream();
				res.reset();  
				String fileName = new String(downloadName.getBytes("GBK"),"ISO-8859-1");
				res.setHeader("Content-Disposition", "attachment; filename=" + fileName);  
				res.setContentType("application/octet-stream; charset=utf-8");  
				os.write(FileUtils.readFileToByteArray(new File(url)));  
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
			} 
		}
	}
	/*****
	 * @Description 导出
	 * @param tableName 导出的表名, startTime 开始时间， endTime 结束时间
	 * @return MsgBean 
	 * 
	 * ***/
	@RequestMapping(value = "exprotData2")
	@ResponseBody
	public MsgBean exprotData2(String paramsTemp,HttpServletResponse response, HttpServletRequest request){
		MsgBean mb = new MsgBean();
		JSONObject jsonObj=JSONObject.fromObject(paramsTemp);  
		ExprotData bean = (ExprotData) JSONObject.toBean(jsonObj, ExprotData.class);
		try {
			portDataService.exportData2(bean,response,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mb;
	}
	/*****
	 * @Description 导出
	 * @param tableName 导出的表名, startTime 开始时间， endTime 结束时间
	 * @return MsgBean 
	 * 
	 * ***/
	@RequestMapping(value = "exprotData")
	@ResponseBody
	public MsgBean exprotData(String paramsTemp,HttpServletResponse response, HttpServletRequest request){
		MsgBean mb = new MsgBean();
		JSONObject jsonObj=JSONObject.fromObject(paramsTemp);  
		ExprotData bean = (ExprotData) JSONObject.toBean(jsonObj, ExprotData.class);
		try {
			mb =portDataService.exportData(bean,request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mb;
	}
	@RequestMapping(value = "downloadZip")
	@ResponseBody
	public void downloadZip(HttpServletResponse response, HttpServletRequest request){
		try {
			String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			String path =new String(request.getParameter("path").getBytes("iso-8859-1"),"UTF-8");
			File file = new File(path+name);
			Zipdowload.downloadZip(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*****
	 * @Description 导入
	 * @param tableName 导入的表名
	 * @return MsgBean 
	 * 
	 * ***/
	@RequestMapping("importData")
	@ResponseBody
	public MsgBean importData(HttpServletResponse response, HttpServletRequest request,String tableName){
		MsgBean mb = new MsgBean();
		try {
			FileImportResult  retx = portDataService.importData(request,response,tableName);
			int errorCount = retx.getErrorCount();
			if(errorCount > 0){
				mb.setSuccess(true);
				mb.setErrorCount(errorCount);
				mb.setMsg(retx.getMsg());
				mb.setPath(retx.getErrorFile().getName());
			}else{
				mb.setSuccess(true);
				mb.setErrorCount(errorCount);
				mb.setSuccessCount(retx.getSuccessCount());
				mb.setMsg(retx.getMsg());
			}
		} catch (Exception e) {
			mb.setSuccess(false);
			mb.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return mb;
	}
	/*****
	 * @Description 查询数据库中的表名
	 * @return List<JSONObject> ckey name 
	 * 
	 * ***/
	@RequestMapping("getTableNm")
	@ResponseBody
	public Page<JSONObject> getTableNm(String pId,Integer start, Integer limit, HttpServletRequest request) {
		Page<JSONObject> res = new Page<JSONObject>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "查询数据库中的表名");
			res = portDataService.getTableNm(pId,start,limit);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/*****
	 * @Description 字段名
	 * @param tablenm 表名
	 * @return List<JSONObject> ckey name 
	 * ***/
	@RequestMapping("getCloumn")
	@ResponseBody
	public List<JSONObject> getCloumn(String tablenm, HttpServletRequest request) {
		List<JSONObject> res = new ArrayList<JSONObject>();
		LogBean logBean = new LogBean();
		try {
			logBean = logUtil.insertLog(request,"1", "查询字段名");
			res = portDataService.getCloumn(tablenm);
		} catch (Exception e) {
			e.printStackTrace();
			logBean.setActionFlag("0");
			logBean.setErroInfo(e.toString());
		}
		logUtil.updateLog(logBean,"1");
		return res;
	}
	/*****
	 * @Description 下载导入异常的文件
	 * 
	 * ***/
	@RequestMapping(value="downloadMsg.do")
	@ResponseBody
	public void downloadMsg(HttpServletResponse res, HttpServletRequest request){
		String path = request.getParameter("path");
		String remotePath = FileManager.getRemoteUploadPath() + path;
		SmbFileInputStream in = null;  
	    OutputStream os = null;  
        try {  
            SmbFile file = new SmbFile(remotePath);       
            if(!file.exists()) {  
                System.out.println("下载的文件不存在！");  
                return;  
            }     
            res.reset(); 
            String[] tempArr = path.split("\\.");
			if(tempArr[tempArr.length-1].equalsIgnoreCase("xls")){
				res.setHeader("Content-Disposition", "attachment; filename=File.xls"); 
			}else if(tempArr[tempArr.length-1].equalsIgnoreCase("xlsx")){
				res.setHeader("Content-Disposition", "attachment; filename=File.xlsx"); 
			}
			res.setContentType("application/octet-stream; charset=utf-8");  
            in = new SmbFileInputStream(file);   
            DataInputStream dis=new DataInputStream(in);   
            os = res.getOutputStream();  
            byte[] buf=new byte[1024];  
            int left=(int)file.length();     
            int read=0;     
            while(left>0) {  
                read=dis.read(buf);     
                left-=read;     
                os.write(buf,0,read);     
            }     
            if (true) {  
                return;  
            }    
        } catch (Exception e) {  
                e.printStackTrace();  
        } finally {
        	try {
				if (in != null) {  
					in.close();    
				}  
				if (os != null) {  
					os.close();    
				}   
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
	}

}
