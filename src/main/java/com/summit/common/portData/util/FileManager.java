/**
 * 
 */
package com.summit.common.portData.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import jcifs.smb.SmbFile;

/**
 * 项目名称：watf   
 * 类名称：FileManager   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:46:46    
 * @version
 */
public class FileManager {
	private static String fileManager = "/fileManager.properties";
	
	private static String cotenxtPath = FileManager.class.getResource("/").getPath().replace("%20", " ");
	private static String createRealPath(String path){
		if(isWindows()){
			return cotenxtPath.substring(1) + path;
		}
		return cotenxtPath + path;
	}
	
	private static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		  //windows
		     return (os.indexOf( "win" ) >= 0);

	}

	public static String getProperty(String key){
		Properties prop = new Properties();
		InputStream in = FileManager.class.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty(key);
			return path;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRealProperty(String key){
		Properties prop = new Properties();
		InputStream in = FileManager.class.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty(key);
			return createRealPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getAddressTemplate() {
		 

		Properties prop = new Properties();
		InputStream in = FileManager.class.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty("template");
			return createRealPath(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//应该改成带参数的
	public static String getUploadPath(){
		Properties prop = new Properties();
		InputStream in = FileManager.class.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty("upload");
			return createRealPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getRemoteUploadPath(){
		Properties prop = new Properties();
		InputStream in = FileManager.class.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty("remoteUpload");
			SmbFile file = new SmbFile(path);
			if(!file.exists()){
				file.mkdirs();
			}
			return path;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getExportPath(){
		Properties prop = new Properties();
		InputStream in = FileManager.class
				.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty("export");
			return createRealPath(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getExcelImportConfig(){
		Properties prop = new Properties();
		InputStream in = FileManager.class
				.getResourceAsStream(fileManager);
		try {
			prop.load(in);
			String path = prop.getProperty("excelImportConfig");
			return createRealPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
