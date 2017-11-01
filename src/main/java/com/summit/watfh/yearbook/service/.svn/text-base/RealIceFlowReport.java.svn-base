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
* 类描述：实测冰流量成果
* 创建人：hhc  
* 创建时间：2017-8-18 下午02:51:09   
*    
*/
@Service
@Transactional
public class RealIceFlowReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SummitTools st;
	
	@Override
	public JSONObject getData(Yearbook yearbook){ 
		String stcds ="";
		if(st.stringNotNull(yearbook.getStcds())){
			stcds = "'" + yearbook.getStcd().replace(",", "','") + "'";
		}
		String sql = "exec UP_Get_OBIQ_G_By_Stations ?,?,?";
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
					String dt = m.containsKey("MSQBGTM")?m.get("MSQBGTM")!=null?m.get("MSQBGTM").toString():"" : "";
					String year = dt.substring(0, 4);//年份
					if(year.equals(years[i])){
						String STCD = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";//站码
						String QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?m.get("QOBNO").toString():"" : "";//施测号数
						String day = subDateforDay(dt); //日
						String month = subDateforMonth(dt); //月
						String stime = subDateforTime(dt); //起
						String  enddt = m.containsKey("MSQEDTM")?m.get("MSQEDTM")!=null?m.get("MSQEDTM").toString():"" : ""; 
						String etime = subDateforTime(enddt);//止
						String XSLC = m.containsKey("XSLC")?m.get("XSLC")!=null?m.get("XSLC").toString():"" : "";//断面位置
						String MSQMT = m.containsKey("MSQMT")?m.get("MSQMT")!=null?m.get("MSQMT").toString():"" : "";//测验方法
						String Z = m.containsKey("Z")?m.get("Z")!=null?m.get("Z").toString():"" : "";//水位
						String IQ = m.containsKey("IQ")?m.get("IQ")!=null?m.get("IQ").toString():"" : "";//冰流量
						String XSAVICCM = m.containsKey("XSAVICCM")?m.get("XSAVICCM")!=null?m.get("XSAVICCM").toString():"" : "";//疏密度
						String XSAVICTK = m.containsKey("XSAVICTK")?m.get("XSAVICTK")!=null?m.get("XSAVICTK").toString():"" : "";//冰厚或冰花厚
						String XSAVICV = m.containsKey("XSAVICV")?m.get("XSAVICV")!=null?m.get("XSAVICV").toString():"" : "";//冰速
						if(st.stringNotNull(XSAVICV)){
							XSAVICV = XSAVICV.substring(0,XSAVICV.indexOf(".")+3);
						}
						String OPWTWD = m.containsKey("OPWTWD")?m.get("OPWTWD")!=null?m.get("OPWTWD").toString():"" : "";//敞露水面宽
						String XSAVFSDS = m.containsKey("XSAVFSDS")?m.get("XSAVFSDS")!=null?m.get("XSAVFSDS").toString():"" : "";//冰花密度
						String FSK = m.containsKey("FSK")?m.get("FSK")!=null?m.get("FSK").toString():"" : "";//冰花折算系数
						map.put("STCD", STCD);
						map.put("QOBNO", QOBNO);
						map.put("YEAR", year);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("DAY", day);
						map.put("STIME", stime);
						map.put("ETIME", etime);
						map.put("XSLC", XSLC);
						map.put("MSQMT", MSQMT);
						map.put("Z", Z);
						map.put("IQ", IQ);
						map.put("XSAVICCM", XSAVICCM);
						map.put("XSAVICTK", XSAVICTK);
						map.put("XSAVICV", XSAVICV);
						map.put("OPWTWD", OPWTWD);
						map.put("XSAVFSDS", XSAVFSDS);
						map.put("FSK", FSK);
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
					if(sd.equals(stcdarr[i])){String STCD = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";//站码
						String QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?m.get("QOBNO").toString():"" : "";//施测号数
						String dt = m.containsKey("MSQBGTM")?m.get("MSQBGTM")!=null?m.get("MSQBGTM").toString():"" : "";
						String day = subDateforDay(dt); //日
						String month = subDateforMonth(dt); //月
						String year = dt.substring(0, 4);//年份
						String stime = subDateforTime(dt); //起
						String  enddt = m.containsKey("MSQEDTM")?m.get("MSQEDTM")!=null?m.get("MSQEDTM").toString():"" : ""; 
						String etime = subDateforTime(enddt);//止
						String XSLC = m.containsKey("XSLC")?m.get("XSLC")!=null?m.get("XSLC").toString():"" : "";//断面位置
						String MSQMT = m.containsKey("MSQMT")?m.get("MSQMT")!=null?m.get("MSQMT").toString():"" : "";//测验方法
						String Z = m.containsKey("Z")?m.get("Z")!=null?m.get("Z").toString():"" : "";//水位
						String IQ = m.containsKey("IQ")?m.get("IQ")!=null?m.get("IQ").toString():"" : "";//冰流量
						String XSAVICCM = m.containsKey("XSAVICCM")?m.get("XSAVICCM")!=null?m.get("XSAVICCM").toString():"" : "";//疏密度
						String XSAVICTK = m.containsKey("XSAVICTK")?m.get("XSAVICTK")!=null?m.get("XSAVICTK").toString():"" : "";//冰厚或冰花厚
						String XSAVICV = m.containsKey("XSAVICV")?m.get("XSAVICV")!=null?m.get("XSAVICV").toString():"" : "";//冰速
						if(st.stringNotNull(XSAVICV)){
							XSAVICV = XSAVICV.substring(0,XSAVICV.indexOf(".")+3);
						}
						String OPWTWD = m.containsKey("OPWTWD")?m.get("OPWTWD")!=null?m.get("OPWTWD").toString():"" : "";//敞露水面宽
						String XSAVFSDS = m.containsKey("XSAVFSDS")?m.get("XSAVFSDS")!=null?m.get("XSAVFSDS").toString():"" : "";//冰花密度
						String FSK = m.containsKey("FSK")?m.get("FSK")!=null?m.get("FSK").toString():"" : "";//冰花折算系数
						map.put("STCD", STCD);
						map.put("QOBNO", QOBNO);
						map.put("YEAR", year);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("DAY", day);
						map.put("STIME", stime);
						map.put("ETIME", etime);
						map.put("XSLC", XSLC);
						map.put("MSQMT", MSQMT);
						map.put("Z", Z);
						map.put("IQ", IQ);
						map.put("XSAVICCM", XSAVICCM);
						map.put("XSAVICTK", XSAVICTK);
						map.put("XSAVICV", XSAVICV);
						map.put("OPWTWD", OPWTWD);
						map.put("XSAVFSDS", XSAVFSDS);
						map.put("FSK", FSK);
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
		return "yearbook/RealIceFlowReport";
	}

}
