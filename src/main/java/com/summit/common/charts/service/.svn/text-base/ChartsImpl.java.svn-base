package com.summit.common.charts.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.portData.util.FileManager;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.SummitTools;

@Service
@Transactional
public class ChartsImpl implements ChartstInter{

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SummitTools st;
	
	private static String cotenxtPath = FileManager.class.getResource("/").getPath().replace("%20", " ");
	
	public Map<String,String> getQueryCloumn(String tableName) {
		Map<String,String> map = new HashMap<String,String>();
		String path = createRealPath("chartConfig.xml");
		File file = new File(path);
		
    	SAXReader saxReader = new SAXReader();
    	Document doc = null;
		try {
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	 Element root = doc.getRootElement(); 
    	 Iterator<?> table=root.elementIterator("table");
    	 while(table.hasNext()){
    		 Element element=(Element)table.next();
    		 String tbnm = element.elementTextTrim("tableName");
    		 if(tbnm.equals(tableName)){
    			 Element columns = element.element("columns");
        		 Iterator<?> iterator=columns.elementIterator("column");
        		 while(iterator.hasNext()){
                     Element el=(Element)iterator.next();
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("x")){
                    	 map.put("x",el.getText());
                     } 
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("y")){
                    	 map.put("y",el.getText());
                     }
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("dwx")){
                    	 map.put("dwx",el.getText());
                     }
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("dwy")){
                    	 map.put("dwy",el.getText());
                     }
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("title")){
                    	 map.put("title",el.getText());
                     }
                     if(el.attribute("name")!=null&&el.attributeValue("name").equals("time")){
                    	 map.put("time",el.getText());
                     }
        		 }
    		 }
    	 }
    	 return map;
	}
	
	public List<JSONObject> getStationTree(String tableName){
		List<JSONObject> l = new ArrayList<JSONObject>();
		JSONObject root = new JSONObject();
		root.put("id", "1012");
		root.put("name", "乌鲁木齐");
		root.put("PCODE", "root");
		l.add(root);
		String sql = " select stcd as id,stnm as name,'1012' PCODE from HY_STSC_A";
		l.addAll(ur.queryAllCustom(sql));
		return l;
	}
	
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
}
