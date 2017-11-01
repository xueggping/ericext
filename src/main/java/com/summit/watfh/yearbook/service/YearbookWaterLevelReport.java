/**
 * 
 */
package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.tbOperate.service.TbCommonInter;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.SummitTools;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class YearbookWaterLevelReport extends YearbookAbstractService {
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private SummitTools tool;
	

	@Override
	public String getTemplate() {
		String template = "yearbook/WaterLevelReport";
		return template;
	}
	
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String stcds=null, beginYear=null,endYear=null;
		Integer bookType = yearbook.getBookType();
		switch(bookType){
			case 1:{//单站多年
				stcds = yearbook.getStcd();
				beginYear = yearbook.getStartTime();
				endYear = yearbook.getEndTime();
				break;
			}
			case 2:{//单年多站
				stcds = yearbook.getStcds();
				beginYear = yearbook.getYear();
				endYear = yearbook.getYear();
				break;
			}
		}
		String qstime = beginYear+"-01-01 00:00:00";
		String qetime = endYear+"-12-31 23:59:59";
		String qstcds = "'"+stcds.replaceAll(",", "','")+"'";
		List<Map<String,Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_DZ_C_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String,Object>> monthList = ur.jdbcTemplate.queryForList("exec UP_Get_MTZ_E_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String,Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRZ_F_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String,Object>> wfdzList = ur.jdbcTemplate.queryForList("exec UP_Get_YRZ_F_By_Stations ?,?,?",qstcds,qstime,qetime);//保证率水位
		List<JSONObject> remarkList = null;
		try {
			remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':'"+stcds+"','YR_DT':"+beginYear+",'YR_LT':"+ endYear +",'TBID_EQ':'HY_DZ_C'}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Map<String,Object>> dataList = waterLevelData(dailyList,monthList,yearList,wfdzList,remarkList,yearbook);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", dataList);
		jsonObj.put("yearbook", JSONObject.fromObject(yearbook));
		return jsonObj;
	}
	
	private List<Map<String, Object>> waterLevelData(List<Map<String, Object>> dailyList,
			List<Map<String, Object>> monthList, List<Map<String, Object>> yearList, List<Map<String, Object>> wfdzList,
			List<JSONObject> remarkList, Yearbook yearbook) {
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String,Map<String,Object>> dataMap = new HashMap<String, Map<String, Object>>();
		
		Integer type = yearbook.getBookType();
		//日水位归位
		if(tool.collectionNotNull(dailyList)){
			for(Map<String,Object> dailyMap:dailyList){
				String stcd = dailyMap.containsKey("STCD")?dailyMap.get("STCD").toString():null;
				String dt = dailyMap.containsKey("DT")?dailyMap.get("DT").toString():null;
				String avz = dailyMap.containsKey("AVZ")?String.format("%.2f",Double.parseDouble(dailyMap.get("AVZ").toString())):"&nbsp;";
				
				String year = dt.substring(0, 4);//年份
				String month = dt.substring(5,7);//月份
				String day = dt.substring(8, 10);//日期
				
				int m = Integer.parseInt(month);
				int d = Integer.parseInt(day);
				
				//单站多年
				if(type.equals(1)){
					if(dataMap.containsKey(year))
					{
						Map<String,Object> map = dataMap.get(year);
						if(map.containsKey("tableData"))
						{
							String[][] data = (String[][])map.get("tableData");
							data[d-1][m-1] = avz;
						}
						else
						{
							String[][] data = new String[36][12];
							data[d-1][m-1] = avz;
						}
					}
					else
					{
						String[][] data = new String[36][12];
						data[d-1][m-1] = avz;
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						String STNM = dailyMap.containsKey("STNM")?dailyMap.get("STNM").toString():null;
						String RVNM = dailyMap.containsKey("RVNM")?dailyMap.get("RVNM").toString():null;
						
						map.put("STCD", stcd);
						map.put("YEAR", year);
						map.put("STNM", STNM);
						map.put("RVNM", RVNM);
						map.put("tableData", data);
						
						dataMap.put(year, map);
					}
				}
				else if(type.equals(2))
				{
					//单年多站					
					if(dataMap.containsKey(stcd))
					{
						Map<String,Object> map = dataMap.get(stcd);
						if(map.containsKey("tableData"))
						{
							String[][] data = (String[][])map.get("tableData");
							data[d-1][m-1] = avz;
						}
						else
						{
							String[][] data = new String[36][12];
							data[d-1][m-1] = avz;
						}
					}
					else
					{
						String[][] data = new String[36][12];
						data[d-1][m-1] = avz;
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						String STNM = dailyMap.containsKey("STNM")?dailyMap.get("STNM").toString():null;
						String RVNM = dailyMap.containsKey("RVNM")?dailyMap.get("RVNM").toString():null;
						
						map.put("STCD", stcd);
						map.put("YEAR", year);
						map.put("STNM", STNM);
						map.put("RVNM", RVNM);
						map.put("tableData", data);
						
						dataMap.put(stcd, map);
					}
					
				}
			}
		}
		
		switch(type){
			case 1:{
				int syear = Integer.parseInt(yearbook.getStartTime());
				int eyear = Integer.parseInt(yearbook.getEndTime());
				String stcd = yearbook.getStcd();
				List<JSONObject> lt = null;
				try {
					lt = tbCommonService.queryDatasByTbName("HY_STSC_A", "{'STCD_EQ':'"+stcd+"'}");
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONObject jo = lt.get(0);
				for(int i=syear;i<=eyear;i++){
					if(!dataMap.containsKey(i+"")){
						Map map = new HashMap<String,Object>();
						map.put("STCD", stcd);
						map.put("RVNM", jo.containsKey("RVNM")?jo.getString("RVNM"):null);
						map.put("STNM", jo.containsKey("STNM")?jo.getString("STNM"):null);
						map.put("YEAR", i+"");
						String[][] tableData = new String[36][12];
						map.put("tableData", tableData);
						dataMap.put(i+"",map);
					}
					dataList.add(dataMap.get(i+""));
				}
				break;
			}
			case 2:{
				String year = yearbook.getYear();
				String[] stcdAry = yearbook.getStcds().split(",");
				for(String stcd:stcdAry){
					if(!dataMap.containsKey(stcd)){
						List<JSONObject> lt = null;
						try {
							lt = tbCommonService.queryDatasByTbName("HY_STSC_A", "{'STCD_EQ':'"+stcd+"'}");
						} catch (Exception e) {
							e.printStackTrace();
						}
						JSONObject jo = lt.get(0);
						Map map = new HashMap<String,Object>();
						map.put("STCD", stcd);
						map.put("RVNM", jo.containsKey("RVNM")?jo.getString("RVNM"):null);
						map.put("STNM", jo.containsKey("STNM")?jo.getString("STNM"):null);
						map.put("YEAR", year);
						String[][] tableData = new String[36][12];
						map.put("tableData", tableData);
						dataMap.put(stcd,map);
					}
					dataList.add(dataMap.get(stcd));
				}
			}
		}
		
		for(Entry<String, Map<String, Object>> entry:dataMap.entrySet()){
			Map<String,Object> tmap = dataMap.get(entry.getKey());
			String stcd = tmap.get("STCD").toString();
			String year = tmap.get("YEAR").toString();
			String[][] tableData = (String[][]) tmap.get("tableData");
			//月水位归位
			for(Map<String,Object> map:monthList){
				String STCD = map.containsKey("STCD")?map.get("STCD").toString():null;
				String YR = map.containsKey("YR")?map.get("YR").toString():null;
				int MTH = map.containsKey("MTH")?Integer.parseInt(map.get("MTH").toString()):null;
				
				String AVZ =     map.containsKey("AVZ")?String.format("%.2f", Double.parseDouble(map.get("AVZ").toString())):null;
				String HTZ =     map.containsKey("HTZ")?String.format("%.2f", Double.parseDouble(map.get("HTZ").toString())):null;
				String HTZDT =   map.containsKey("HTZDT")?CommonUtil.subDateStr1(map.get("HTZDT").toString()):null;
				String MNZ =     map.containsKey("MNZ")?String.format("%.2f", Double.parseDouble(map.get("MNZ").toString())):null;
				String MNZDT =   map.containsKey("MNZDT")?CommonUtil.subDateStr1(map.get("MNZDT").toString()):null;
				if(stcd.equals(STCD) && year.equals(YR)){
					tableData[31][MTH-1]=AVZ;
					tableData[32][MTH-1]=HTZ;
					tableData[33][MTH-1]=HTZDT;
					tableData[34][MTH-1]=MNZ;
					tableData[35][MTH-1]=MNZDT;
				}
			}
			
			for(Map<String,Object> map:yearList){
				String  STCD = map.containsKey("STCD")?map.get("STCD").toString():null;
				String  YR = map.containsKey("YR")?map.get("YR").toString():null;
				String  AVZ = map.containsKey("AVZ")?String.format("%.2f", Double.parseDouble(map.get("AVZ").toString())):null;
				String  HTZ = map.containsKey("HTZ")?String.format("%.2f", Double.parseDouble(map.get("HTZ").toString())):null;
				String  HTZDT = map.containsKey("HTZDT")?CommonUtil.subDatestr(map.get("HTZDT").toString()):null;
				String  MNZ = map.containsKey("MNZ")?String.format("%.2f", Double.parseDouble(map.get("MNZ").toString())):null;
				String  MNZDT = map.containsKey("MNZDT")?CommonUtil.subDatestr(map.get("MNZDT").toString()):null;
				String  S1= map.containsKey("S1")?String.format("%.2f", Double.parseDouble(map.get("S1").toString())):null;
				String  S15= map.containsKey("S15")?String.format("%.2f", Double.parseDouble(map.get("S15").toString())):null;
				String  S30= map.containsKey("S30")?String.format("%.2f", Double.parseDouble(map.get("S30").toString())):null;
				String  S90= map.containsKey("S90")?String.format("%.2f", Double.parseDouble(map.get("S90").toString())):null;
				String  S180= map.containsKey("S180")?String.format("%.2f", Double.parseDouble(map.get("S180").toString())):null;
				String  S270= map.containsKey("S270")?String.format("%.2f", Double.parseDouble(map.get("S270").toString())):null;
				String  S365= map.containsKey("S365")?String.format("%.2f", Double.parseDouble(map.get("S365").toString())):null;
				
				if(stcd.equals(STCD) && year.equals(YR)){
					tmap.put("HTZ", HTZ);
					tmap.put("HTZDT", HTZDT);
					tmap.put("MNZ", MNZ);
					tmap.put("MNZDT", MNZDT);
					tmap.put("AVZ", AVZ);
					
					tmap.put("S1", S1);
					tmap.put("S15", S15);
					tmap.put("S30", S30);
					tmap.put("S90", S90);
					tmap.put("S180", S180);
					tmap.put("S270", S270);
					tmap.put("S365", S365);
				}
			}
			
//			for(Map<String,Object> map:wfdzList){
//				String STCD  =map.containsKey("stcd")?map.get("stcd").toString():null;
//				String YR    =map.containsKey("YR")?map.get("YR").toString():null;
//				String stnm  =map.containsKey("stnm")?map.get("stnm").toString():null;
//				String RVNM  =map.containsKey("RVNM")?map.get("RVNM").toString():null;
//				String WF    =map.containsKey("WF")?map.get("WF").toString():null;
//				String RZ    =map.containsKey("RZ")?map.get("RZ").toString():null;
//				if(stcd.equals(STCD) && year.equals(YR)){
//					tmap.put("WFDZ"+WF, RZ);
//				}
//			}
			
			for(JSONObject obj:remarkList){
				String STCD = obj.containsKey("STCD")?obj.getString("STCD").toString():null;
				String YR = obj.containsKey("YR")?obj.getString("YR").toString():null;
				String nt = obj.containsKey("NT")?obj.getString("NT").toString():null;
				if(stcd.equals(STCD) && year.equals(YR)){
					tmap.put("NT", nt);
				}
			}
			
//			dataList.add(dataMap.get(entry.getKey()));
		}
		return dataList;
	}

}