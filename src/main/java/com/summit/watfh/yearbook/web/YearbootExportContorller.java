package com.summit.watfh.yearbook.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.summit.common.portData.util.FileManager;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;


@Controller
@RequestMapping("yearBookExport")
public class YearbootExportContorller {
	private static Configuration cfg;  
    
    private static final String TEMPLATEFILENAME = FileManager.getRealProperty("yearbookTemplate");//"src/main/resources/templates/ftl";  
      
    private static Template dateTmp;  
    
    static{  
        try{  
            //初始化参数  
            cfg = new Configuration(Configuration.VERSION_2_3_23);  
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATEFILENAME));  
            cfg.setDefaultEncoding("UTF-8");  
            cfg.setTemplateUpdateDelayMilliseconds(0);  
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
    }  
      
    @RequestMapping("export") 
    @ResponseBody
    public void test_exportExcel(Map<String,Object> root,String template,String saveFileName,int fileType) throws Exception{ 
    	 String exportPath = FileManager.getProperty("yearbookExport");
         FileOutputStream fos= new FileOutputStream(exportPath + saveFileName + (fileType==1?".xls":".doc"));
         OutputStreamWriter osw =new OutputStreamWriter(fos, "UTF-8");
         BufferedWriter bw =new BufferedWriter(osw, 1024);
//         List<Employee>  empList = new ArrayList<Employee>();  
//         empList.add(new Employee("张三", 20, "女"));  
//         empList.add(new Employee("张三", 20, "女"));  
//         empList.add(new Employee("张三", 20, "女"));  
//         root.put("empList", empList); 
         try {
        	dateTmp = cfg.getTemplate(template + ".ftl","utf-8");  
        	dateTmp.process(root, bw); 
        	bw.flush(); 
		 } catch (Exception e) {
			e.printStackTrace();
		 }finally{  
			bw.close();  
         }
          
    }  
    
   class Employee {
    	private String name;
    	private Integer age;
    	private String sex;
    	public String getName() {
    		return name;
    	}
    	public void setName(String name) {
    		this.name = name;
    	}
    	public Integer getAge() {
    		return age;
    	}
    	public void setAge(Integer age) {
    		this.age = age;
    	}
    	public String getSex() {
    		return sex;
    	}
    	public void setSex(String sex) {
    		this.sex = sex;
    	}
    	public Employee(String name, Integer age, String sex) {
    		super();
    		this.name = name;
    		this.age = age;
    		this.sex = sex;
    	}
    	
    	
    }
    
}
