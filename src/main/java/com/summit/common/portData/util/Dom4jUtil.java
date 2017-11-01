package com.summit.common.portData.util;

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map; 
import org.dom4j.Document; 
import org.dom4j.Element; 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

/**
 * 项目名称：watf   
 * 类名称：Dom4jUtil   
 * 类描述：   
 * 创建人：xuegp  
 * 创建时间：2017-4-21 上午11:47:34    
 * @version
 */
public class Dom4jUtil {
	
    public static Map<String, ExcelImportConfig>  getExcelImportConfigMap(Document doc){ 
    	Map<String, ExcelImportConfig> map = new HashMap<String, ExcelImportConfig>();
        if(doc == null)  return null; 
        Element root = doc.getRootElement(); 
        Iterator<?> convertor=root.elementIterator("convertor");
        while(convertor.hasNext()){
        	ExcelImportConfig excelImportConfig = new ExcelImportConfig();
            Element element=(Element)convertor.next();
            String bean = element.elementTextTrim("bean");
            Element head = element.element("head");
            excelImportConfig.setBean(bean);
            excelImportConfig.getHead().setLineNo(Integer.parseInt(head.attribute("lineNo").getValue()));
            Element data = element.element("data");
            if(data.attribute("fromLine")!=null){
            	excelImportConfig.getData().setFromLine(Integer.parseInt(data.attributeValue("fromLine")));
            }
            if(data.attribute("limit")!=null){
            	excelImportConfig.getData().setLimit(Integer.parseInt(data.attributeValue("limit")));
            }
            Iterator<?> iterator=data.elementIterator("column");
            List<Column> listColumn = new ArrayList<Column>();
            while(iterator.hasNext()){
                Element el=(Element)iterator.next();
                Column c = new Column();
                if(el.attribute("name")!=null)
                	c.setName(el.attributeValue("name"));
                if(el.attribute("order")!=null)
                	c.setOrder(Integer.parseInt(el.attributeValue("order")));
                if(el.attribute("property")!=null)
                	c.setProperty(el.attributeValue("property"));
                if(el.attribute("allowEmpty")!=null)
                	c.setAllowEmpty(new Boolean(el.attributeValue("allowEmpty")));
                if(el.attribute("type")!=null)
                	c.setType(Integer.parseInt(el.attributeValue("type")));
                if(el.attribute("maxLength")!=null)
                	c.setMaxLength(Integer.parseInt(el.attributeValue("maxLength")));
                if(el.attribute("length")!=null)
                	c.setLength(Integer.parseInt(el.attributeValue("length")));
                if(el.attribute("store")!=null)
                	c.setStore(el.attributeValue("store"));
                if(el.attribute("params")!=null)
                	c.setParams(el.attributeValue("params"));
                if(el.attribute("value")!=null)
                	c.setValue(el.attributeValue("value"));
                if(el.attribute("regex")!=null)
                	c.setRegex(el.attributeValue("regex"));
                if(el.attribute("paramer")!=null)
                	c.setParamer(el.attributeValue("paramer"));
                if(el.attribute("paramer1")!=null)
                	c.setParamer1(el.attributeValue("paramer1"));
                if(el.attribute("paramer2")!=null)
                	c.setParamer2(el.attributeValue("paramer2"));
                if(el.attribute("paramer3")!=null)
                	c.setParamer3(el.attributeValue("paramer3"));
                if(el.attribute("paramer4")!=null)
                	c.setParamer4(el.attributeValue("paramer4"));
                if(el.attribute("paramer5")!=null)
                	c.setParamer5(el.attributeValue("paramer5"));
                if(el.attribute("paramer6")!=null)
                	c.setParamer6(el.attributeValue("paramer6"));
                if(el.attribute("paramer7")!=null)
                	c.setParamer7(el.attributeValue("paramer7"));
                if(el.attribute("paramer8")!=null)
                	c.setParamer8(el.attributeValue("paramer8"));
                if(el.attribute("paramer9")!=null)
                	c.setParamer9(el.attributeValue("paramer9"));
                listColumn.add(c);
                
            }
            excelImportConfig.getData().setColumn(listColumn);
            map.put(bean, excelImportConfig);
        }
        return map; 
    } 
     
     public static void main(String[] args) throws IOException,DocumentException {
    
	    FileInputStream fis = new FileInputStream(FileManager.getExcelImportConfig());
	    byte[] b = new byte[fis.available()];
	    fis.read(b);
	    String str = new String(b);
	    
	    Document doc = DocumentHelper.parseText(str);
	    
	    System.out.println(doc.asXML());
	    long beginTime = System.currentTimeMillis();
	    
	    Map<String, ExcelImportConfig> map = Dom4jUtil.getExcelImportConfigMap(doc);
	    ExcelImportConfig e =map.get("com.summit.rms.deviceManager.domain.RmsOpticalDeviceBean");
	    System.out.println(e.getData().getColumn().get(0).getName());
	    System.out.println("Use time:"+(System.currentTimeMillis()-beginTime));
   }
     public static ExcelImportConfig getExcelImportConfig(String key) throws IOException,DocumentException {
    	String path = FileManager.getExcelImportConfig();//excelImportConfig.xml
    	File file = new File(path);
    	SAXReader saxReader = new SAXReader();
    	Document document = saxReader.read(file);
 	    Map<String, ExcelImportConfig> map = Dom4jUtil.getExcelImportConfigMap(document);
 	    ExcelImportConfig e =map.get(key);
 	    
 	    return e;
     }
} 
