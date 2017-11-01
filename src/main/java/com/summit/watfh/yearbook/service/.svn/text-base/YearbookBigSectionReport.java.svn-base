package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.SummitTools;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;


/**   
*    
* 项目名称：watfh   
* 类名称：YearbookBigSectionReport   
* 类描述：实测大断面成果年鉴
* 创建人：hhc  
* 创建时间：2017-8-17 下午02:00:09   
*    
*/
@Service
@Transactional
public class YearbookBigSectionReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SummitTools st;
	
	@Override
	public JSONObject getData(Yearbook yearbook){ 
		String stcd = "'"+yearbook.getStcd()+"'";
		String stcds ="";
		if(st.stringNotNull(yearbook.getStcds())){
			stcds = "'" + yearbook.getStcds().replace(",", "','") + "'";
		}
		String osmy_sql = "exec UP_Get_XSMSRS_G_By_Stations ?,?,?";
		String msoy_sql = "exec UP_Get_XSMSRS_G_By_Stations_Year ?,?";
		if(yearbook.getBookType() == null){
			try {
				throw new Exception("必须选择打印条件");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> x = new ArrayList<Map<String, Object>>();
		List<List<List<Map<String, Object>>>> dataList = new ArrayList<List<List<Map<String, Object>>>>();
		JSONObject json =new JSONObject();
		switch (yearbook.getBookType()) {
		case 1://单站多年
			//组织数据
			String endtm = yearbook.getEndTime().substring(0,4);
			String strtm = yearbook.getStartTime().substring(0,4);
			int difference = Integer.parseInt(endtm)-Integer.parseInt(strtm);
			String[] years = new String[difference+1];
			years[0] = strtm;
			for(int i=1;i<=difference;i++){
				years[i] = String.valueOf(Integer.parseInt(years[i-1])+1);
			}                   
			x = ur.jdbcTemplate.queryForList(osmy_sql, stcd, yearbook.getStartTime(), yearbook.getEndTime());
			for(int i=0;i<years.length;i++){  //根据查询条件对各年数据分组
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String dt = m.containsKey("OBDT")?m.get("OBDT")!=null?m.get("OBDT").toString():"" : "";
					String year="";
					if(st.stringNotNull(dt)){
						year = dt.substring(0, 4);//年份
					}
					if(year.equals(years[i])){
						String VTNO = m.containsKey("VTNO")?m.get("VTNO")!=null?m.get("VTNO").toString():"" : "";//垂线号
						String DI = m.containsKey("DI")?m.get("DI")!=null?m.get("DI").toString():"" : "";//起点距
						if(st.stringNotNull(DI)){
							DI = DI.substring(0,DI.indexOf(".")+2);
						}
						String RVBDEL = m.containsKey("RVBDEL")?m.get("RVBDEL")!=null?m.get("RVBDEL").toString():"" : "";//河底高程
						if(st.stringNotNull(RVBDEL)){
							RVBDEL = RVBDEL.substring(0,RVBDEL.indexOf(".")+3);
						}
						String XSNMLC = m.containsKey("XSNMLC")?m.get("XSNMLC")!=null?m.get("XSNMLC").toString():"" : "";//断面名称及位置 
						String OBDRZ = m.containsKey("OBDRZ")?m.get("OBDRZ")!=null?m.get("OBDRZ").toString():"" : "";//测时水位
						if(st.stringNotNull(OBDRZ)){
							OBDRZ = OBDRZ.substring(0,OBDRZ.indexOf(".")+3);
						}
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						map.put("STNM", STNM);
						map.put("RVNM", RVNM);
						map.put("VTNO", VTNO);
						map.put("DI", DI);
						map.put("RVBDEL", RVBDEL);
						map.put("OBDT", dt);
						map.put("XSNMLC", XSNMLC);
						map.put("OBDRZ", OBDRZ);
						map.put("YEAR", year);
						yeardata.add(map);
					}
				}
				List<String> period = new ArrayList<String>();
				//获取该站该年数据的时间段
				for (Map<String, Object> map : yeardata) {
					if(!period.contains(subDate(map.get("OBDT").toString()))){
						period.add(subDate(map.get("OBDT").toString()));
					};
				}
				
				//把该站该年的数据按时段分组
				List<List<Map<String, Object>>> newyeardata = new ArrayList<List<Map<String, Object>>>();
				for (String list : period) {
					List<Map<String, Object>> grouplist = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> yd : yeardata) {
						if(list.equals(subDate(yd.get("OBDT").toString()))){
							grouplist.add(yd);
						}
					}
					newyeardata.add(grouplist);
				}
				
				List resultdata = new ArrayList();
				List datalist=null;
				//计算每时段数据的总数，把每段数据分成5组
				for (List<Map<String, Object>> list : newyeardata) {
					List<Map<String, Object>> col1 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col2 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col3 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col4 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col5 = new ArrayList<Map<String, Object>>();
					int size = list.size();   //计算每组分时段数据每列应该分多少行数据
					int rowsNum = 1;
					while(true){
						if(5*rowsNum>=size){
							break;
						}
						rowsNum++;
					}
					int countNum = 1;
					for (Map<String, Object> map : list) {
						if(countNum<=rowsNum){
							map.put("change", true);
							map.put("DATE",subDate(map.get("OBDT").toString()));
							col1.add(map);
						}
						if(countNum>rowsNum&&countNum<=2*rowsNum){
							col2.add(map);
						}
						if(countNum>2*rowsNum&&countNum<=3*rowsNum){
							col3.add(map);
						}
						if(countNum>3*rowsNum&&countNum<=4*rowsNum){
							col4.add(map);
						}
						if(countNum>4*rowsNum&&countNum<=5*rowsNum){
							col5.add(map);
						}
						countNum++;
					}
					datalist = new ArrayList();
					datalist.add(col1);
					datalist.add(col2);
					datalist.add(col3);
					datalist.add(col4);
					datalist.add(col5);
					resultdata.add(datalist);
				}
				
				if(!resultdata.isEmpty()){
					dataList.add(resultdata);
				}
			}
			break;
		case 2://多站单年
			String[] stcdarr = yearbook.getStcds().split(",");              
 			x = ur.jdbcTemplate.queryForList(msoy_sql, stcds, yearbook.getYear());
			for(int i=0;i<stcdarr.length;i++){  //根据查询条件对各年数据分组
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String sd = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";
					if(sd.equals(stcdarr[i])){
						String VTNO = m.containsKey("VTNO")?m.get("VTNO")!=null?m.get("VTNO").toString():"" : "";//垂线号
						String DI = m.containsKey("DI")?m.get("DI")!=null?m.get("DI").toString():"" : "";//起点距
						if(st.stringNotNull(DI)){
							DI = DI.substring(0,DI.indexOf(".")+2);
						}
						String RVBDEL = m.containsKey("RVBDEL")?m.get("RVBDEL")!=null?m.get("RVBDEL").toString():"" : "";//河底高程
						if(st.stringNotNull(RVBDEL)){
							RVBDEL = RVBDEL.substring(0,RVBDEL.indexOf(".")+3);
						}
						String XSNMLC = m.containsKey("XSNMLC")?m.get("XSNMLC")!=null?m.get("XSNMLC").toString():"" : "";//断面名称及位置 
						String OBDRZ = m.containsKey("OBDRZ")?m.get("OBDRZ")!=null?m.get("OBDRZ").toString():"" : "";//测时水位
						if(st.stringNotNull(OBDRZ)){
							OBDRZ = OBDRZ.substring(0,OBDRZ.indexOf(".")+3);
						}
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						String dt = m.containsKey("OBDT")?m.get("OBDT")!=null?m.get("OBDT").toString():"" : "";
						String year = dt.substring(0, 4);//年份
						map.put("STNM", STNM);
						map.put("RVNM", RVNM);
						map.put("VTNO", VTNO);
						map.put("DI", DI);
						map.put("RVBDEL", RVBDEL);
						map.put("OBDT", dt);
						map.put("XSNMLC", XSNMLC);
						map.put("OBDRZ", OBDRZ);
						map.put("YEAR", year);
						yeardata.add(map);
					}
				}
				List<String> period = new ArrayList<String>();
				//获取该站该年数据的时间段
				for (Map<String, Object> map : yeardata) {
					if(!period.contains(subDate(map.get("OBDT").toString()))){
						period.add(subDate(map.get("OBDT").toString()));
					};
				}
				
				//把该站该年的数据按时段分组
				List<List<Map<String, Object>>> newyeardata = new ArrayList<List<Map<String, Object>>>();
				for (String list : period) {
					List<Map<String, Object>> grouplist = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> yd : yeardata) {
						if(list.equals(subDate(yd.get("OBDT").toString()))){
							grouplist.add(yd);
						}
					}
					newyeardata.add(grouplist);
				}
				
				List resultdata = new ArrayList();
				List datalist=null;
				//计算每时段数据的总数，把每段数据分成5组
				for (List<Map<String, Object>> list : newyeardata) {
					List<Map<String, Object>> col1 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col2 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col3 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col4 = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> col5 = new ArrayList<Map<String, Object>>();
					int size = list.size();   //计算每组分时段数据每列应该分多少行数据
					int rowsNum = 1;
					while(true){
						if(5*rowsNum>=size){
							break;
						}
						rowsNum++;
					}
					int countNum = 1;
					for (Map<String, Object> map : list) {
						if(countNum<=rowsNum){
							map.put("change", true);
							map.put("DATE",subDate(map.get("OBDT").toString()));
							col1.add(map);
						}
						if(countNum>rowsNum&&countNum<=2*rowsNum){
							col2.add(map);
						}
						if(countNum>2*rowsNum&&countNum<=3*rowsNum){
							col3.add(map);
						}
						if(countNum>3*rowsNum&&countNum<=4*rowsNum){
							col4.add(map);
						}
						if(countNum>4*rowsNum&&countNum<=5*rowsNum){
							col5.add(map);
						}
						countNum++;
					}
					datalist = new ArrayList();
					datalist.add(col1);
					datalist.add(col2);
					datalist.add(col3);
					datalist.add(col4);
					datalist.add(col5);
					resultdata.add(datalist);
				}
				
				if(!resultdata.isEmpty()){
					dataList.add(resultdata);
				}
			}
			break;
		default:
			break;
		}
		json.put("data", dataList);
		json.put("yearbook",JSONObject.fromObject(yearbook));
        return json;
	}
	
	@SuppressWarnings("unused")
	private static String subDateforDay(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d2 = Integer.parseInt(dateStr.substring(8, 10));
			return d2+"";
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	private static String subDateforMonth(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d2 = Integer.parseInt(dateStr.substring(5, 7));
			return d2+"";
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	private static String subDate(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			String year = dateStr.substring(0,4);
			String month = dateStr.substring(5,7);
			String day =  dateStr.substring(8,10);
			return year+"年"+month+"月"+day+"日";      
		}
		return "";
	}
	
	@Override
	public String getTemplate() {
		return "yearbook/BigSectionReport";
	}

}
