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
* 类名称：RealSandInRateReport   
* 类描述：实测悬移质输沙率成果年报
* 创建人：hhc  
* 创建时间：2017-8-17 下午04:57:09   
*    
*/
@Service
@Transactional
public class RealSandInRateReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SummitTools st;
	
	@Override
	public JSONObject getData(Yearbook yearbook){ 
		String stcds ="";
		if(st.stringNotNull(yearbook.getStcds())){
			stcds = "'" + yearbook.getStcds().replace(",", "','") + "'";
		}
		String sql = "exec UP_Get_OBQS_G_By_Stations ?,?,?";
		if(yearbook.getBookType() == null){
			try {
				throw new Exception("必须选择打印条件");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> x = new ArrayList<Map<String, Object>>();
		List<List<Map<String, Object>>> dataList = new ArrayList<List<Map<String, Object>>>();
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
			x = ur.jdbcTemplate.queryForList(sql, stcds, yearbook.getStartTime(), yearbook.getEndTime());
			for(int i=0;i<years.length;i++){
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				List <String> mm = new ArrayList<String>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String dt = m.containsKey("MSSBGTM")?m.get("MSSBGTM")!=null?m.get("MSSBGTM").toString():"" : "";
					String year = dt.substring(0, 4);//年份
					if(year.equals(years[i])){
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						String QSOBNO = m.containsKey("QSOBNO")?m.get("QSOBNO")!=null?m.get("QSOBNO").toString():"" : ""; //输沙率
						String QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?m.get("QOBNO").toString():"" : "";//流量
						String day = subDateforDay(dt); //日
						String month = subDateforMonth(dt); //月
						String stime = subDateforTime(dt); //起
						String  enddt = m.containsKey("MSSEDTM")?m.get("MSSEDTM")!=null?m.get("MSSEDTM").toString():"" : ""; 
						String etime = subDateforTime(enddt);//止
						Double QQ = m.containsKey("Q")?Float.parseFloat(m.get("Q").toString())!=0.0?Float.parseFloat(m.get("Q").toString()):0.0 : 0.0; //流量m³/s
						String Q = String .format("%.1f",QQ);
						Double SSQ = m.containsKey("SSQS")?Float.parseFloat(m.get("SSQS").toString())!=0.0?Float.parseFloat(m.get("SSQS").toString()):0.0 : 0.0;//断面输沙率
						String SSQS = String .format("%.3f",SSQ);
						String XSAVCS = m.containsKey("XSAVCS")?m.get("XSAVCS")!=null?m.get("XSAVCS").toString():"" : ""; //断面平均
						String IXCS = m.containsKey("IXCS")?m.get("IXCS")!=null?m.get("IXCS").toString():"" : ""; //单样
						String SMMT = m.containsKey("SMMT")?m.get("SMMT")!=null?m.get("SMMT").toString():"" : ""; //断面平均含沙量
						String IXCSOM = m.containsKey("IXCSOM")?m.get("IXCSOM")!=null?m.get("IXCSOM").toString():"" : ""; //单样含沙量
						map.put("RVNM", RVNM); 
						map.put("STNM", STNM);
						map.put("QSOBNO", QSOBNO);
						map.put("QOBNO", QOBNO);
						map.put("YEAR", year);
						map.put("day", day);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("stime", stime);
						map.put("etime", etime);
						map.put("Q", Q);
						map.put("SSQS", SSQS);
						map.put("XSAVCS", XSAVCS);
						map.put("IXCS", IXCS);
						map.put("SMMT", SMMT);
						map.put("IXCSOM", IXCSOM);
						yeardata.add(map);
					}
				}
				if(!yeardata.isEmpty()){
					dataList.add(yeardata);
				}
			}
			break;
		case 2://多站单年
			String[] stcdarr = yearbook.getStcds().split(",");
			//组织数据
			x = ur.jdbcTemplate.queryForList(sql, stcds, yearbook.getStartTime(), yearbook.getEndTime());
			for(int i=0;i<stcdarr.length;i++){
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				List <String> mm = new ArrayList<String>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String sd = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";
					if(sd.equals(stcdarr[i])){
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						String QSOBNO = m.containsKey("QSOBNO")?m.get("QSOBNO")!=null?m.get("QSOBNO").toString():"" : ""; //输沙率
						String QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?m.get("QOBNO").toString():"" : "";//流量
						String dt = m.containsKey("MSSBGTM")?m.get("MSSBGTM")!=null?m.get("MSSBGTM").toString():"" : "";//时间
						String year = dt.substring(0, 4);//年份
						String day = subDateforDay(dt); //日
						String month = subDateforMonth(dt); //月
						String stime = subDateforTime(dt); //起
						String  enddt = m.containsKey("MSSEDTM")?m.get("MSSEDTM")!=null?m.get("MSSEDTM").toString():"" : ""; 
						String etime = subDateforTime(enddt);//止
						String Q = m.containsKey("Q")?m.get("Q")!=null?m.get("Q").toString():"" : ""; //流量m³/s
						Q = String .format("%.1f",Float.parseFloat(Q));
//						if(st.stringNotNull(XSAVV)){
//							XSAVV = XSAVV.substring(0,XSAVV.indexOf(".")+3);
//						}
						String SSQS = m.containsKey("SSQS")?m.get("SSQS")!=null?m.get("SSQS").toString():"" : ""; //断面输沙率
						String XSAVCS = m.containsKey("XSAVCS")?m.get("XSAVCS")!=null?m.get("XSAVCS").toString():"" : ""; //断面平均
						String IXCS = m.containsKey("IXCS")?m.get("IXCS")!=null?m.get("IXCS").toString():"" : ""; //单样
						String SMMT = m.containsKey("SMMT")?m.get("SMMT")!=null?m.get("SMMT").toString():"" : ""; //断面平均含沙量
						String IXCSOM = m.containsKey("IXCSOM")?m.get("IXCSOM")!=null?m.get("IXCSOM").toString():"" : ""; //单样含沙量
						map.put("RVNM", RVNM); 
						map.put("STNM", STNM);
						map.put("QSOBNO", QSOBNO);
						map.put("QOBNO", QOBNO);
						map.put("YEAR", year);
						map.put("day", day);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("stime", stime);
						map.put("etime", etime);
						map.put("Q", Q);
						map.put("SSQS", SSQS);
						map.put("XSAVCS", XSAVCS);
						map.put("IXCS", IXCS);
						map.put("SMMT", SMMT);
						map.put("IXCSOM", IXCSOM);
						yeardata.add(map);
					}
				}
				if(!yeardata.isEmpty()){
					dataList.add(yeardata);
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
	private static String subDateforTime(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int hour = Integer.parseInt(dateStr.substring(11,13));
			String min =  dateStr.substring(14,16);
			return hour+":"+min;      
		}
		return "";
	}
	
	@Override
	public String getTemplate() {
		return "yearbook/RealSandInRateReport";
	}

}
