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
public class YearbookVaporizeReport extends YearbookAbstractService {
	
	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private SummitTools tool;

	@Override
	public String getTemplate() {
		String template = "yearbook/VaporizeReport";
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
		List<Map<String,Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_DWE_C_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String,Object>> monthList = ur.jdbcTemplate.queryForList("exec UP_Get_MTWE_E_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String,Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRWE_F_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<JSONObject> remarkList = null;
		try {
			remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"','TBID_EQ':'HY_YRWE_C'}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Map<String,Object>> data = vaporizeReportData(dailyList,monthList,yearList,remarkList,yearbook);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", data);
		jsonObj.put("yearbook", JSONObject.fromObject(yearbook));
		return jsonObj;
	}

	private List<Map<String, Object>> vaporizeReportData(List<Map<String, Object>> dailyList,
			List<Map<String, Object>> monthList, List<Map<String, Object>> yearList, List<JSONObject> remarkList,Yearbook yearbook) {
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String,Map<String,Object>> dataMap = new HashMap<String,Map<String,Object>>();
		Integer bookType = yearbook.getBookType();
		if(tool.collectionNotNull(dailyList)){
			
			if(bookType.equals(1))
			{
				//单站多年
				for(Map<String, Object> on:dailyList){
					String dt = on.get("DT")!=null?on.get("DT").toString():"";//日期
					String stcd = on.get("STCD")!=null?on.get("STCD").toString():"";
					String wsfe = on.get("WSFE")!=null?on.get("WSFE").toString():"";//降雨量
					String year = dt.substring(0, 4);//年份
					String month = dt.substring(5,7);//月份
					String day = dt.substring(8, 10);//日期
					int m = Integer.parseInt(month);
					int d = Integer.parseInt(day);
					
					if(dataMap.containsKey(year)){
						Map map = dataMap.get(year);
						if(map.containsKey("tableData"))
						{
							String[][] tableData = (String[][]) map.get("tableData");
							tableData[d-1][m-1] = wsfe;
						}
						else
						{
							String[][] tableData = new String[34][12];
							tableData[d-1][m-1] = wsfe;
							map.put("tableData", tableData);
						}
					}
					else
					{
						Map<String, Object> map = new HashMap<String,Object>();
						String RVNM = on.get("RVNM")!=null?on.get("RVNM").toString():"";
						String STNM = on.get("STNM")!=null?on.get("STNM").toString():"";
						map.put("STCD", stcd);
						map.put("RVNM", RVNM);
						map.put("STNM", STNM);
						map.put("YEAR", year);
						
						String[][] tableData = new String[34][12];
						tableData[d-1][m-1] = wsfe;
						map.put("tableData", tableData);
						
						dataMap.put(year, map);
					}
				}
			}
			else if(bookType.equals(2))
			{
				//单年多站
				for(Map<String,Object> on:dailyList){
					String dt = on.get("DT")!=null?on.get("DT").toString():"";
					String stcd = on.get("STCD")!=null?on.get("STCD").toString():"";
					String wsfe = on.get("WSFE")!=null?on.get("WSFE").toString():"";
					
					String year = dt.substring(0,4);//年份
					String month = dt.substring(5,7);//月份
					String day = dt.substring(8, 10);//日期
					
					int m = Integer.parseInt(month);
					int d = Integer.parseInt(day);
					
					if(dataMap.containsKey(stcd)){
						Map map = dataMap.get(stcd);
						if(map.containsKey("tableData"))
						{
							String[][] tableData = (String[][]) map.get("tableData");
							tableData[d-1][m-1] = wsfe;
						}
						else
						{
							String[][] tableData = new String[34][12];
							tableData[d-1][m-1] = wsfe;
							map.put("tableData", tableData);
						}
					}
					else
					{
						Map<String, Object> map = new HashMap<String,Object>();
						String RVNM = on.get("RVNM")!=null?on.get("RVNM").toString():"";
						String STNM = on.get("STNM")!=null?on.get("STNM").toString():"";
						map.put("STCD", stcd);
						map.put("STNM", STNM);
						map.put("YEAR", year);
						map.put("RVNM", RVNM);
						
						String[][] tableData = new String[34][12];
						tableData[d-1][m-1] = wsfe;
						map.put("tableData", tableData);
						
						dataMap.put(stcd, map);
					}
				}
			}
		}
		
		switch(bookType){
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
				for(int i=syear;i<eyear;i++){
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
		
		for(Entry<String,Map<String,Object>> entry:dataMap.entrySet()){
			Map<String,Object> map = entry.getValue();
			String perStcd = map.containsKey("STCD")?map.get("STCD").toString():null;
			String perYear = map.containsKey("YEAR")?map.get("YEAR").toString():null;
			
			for(Map<String,Object> mavg:monthList){
				String stcd = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
				String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
				int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
				String wsfe = mavg.get("WSFE")!=null?mavg.get("WSFE").toString():"";
				String mxdye = mavg.get("MXDYE")!=null?mavg.get("MXDYE").toString():"";
				String mndye = mavg.get("MNDYE")!=null?mavg.get("MNDYE").toString():"";
				if(stcd.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
					String[][] tdata = (String[][]) map.get("tableData");
					tdata[31][mth-1] = wsfe;
					tdata[32][mth-1] = mxdye;
					tdata[33][mth-1] = mndye;
				}
			}
			
			for(Map<String,Object> obj:yearList){
				String stcd = obj.containsKey("STCD")?obj.get("STCD").toString():null;
				String yr = obj.containsKey("YR")?obj.get("YR").toString():null;
				String wsfe = obj.get("WSFE")!=null?obj.get("WSFE").toString():"";//蒸发量
				String mxdye = obj.get("MXDYE")!=null?obj.get("MXDYE").toString():"";//最大日蒸发量
				String mxdyeodt = obj.get("MXDYEODT")!=null?CommonUtil.subDatestr(obj.get("MXDYEODT").toString()):"";//最大日蒸发量日期
				String mndye = obj.get("MNDYE")!=null?obj.get("MNDYE").toString():"";//最小日蒸发量
				String mndyeodt = obj.get("MNDYEODT")!=null?CommonUtil.subDatestr(obj.get("MNDYEODT").toString()):"";//最小日蒸发量日期
				String idsdt = obj.get("IDSDT")!=null?CommonUtil.subDatestr(obj.get("IDSDT").toString()):"";//冰终日期
				String icapd = obj.get("ICAPD")!=null?CommonUtil.subDatestr(obj.get("ICAPD").toString()):"";//初冰日期
				String eetp = obj.get("EETP")!=null?obj.get("EETP").toString():"";//蒸发器形式
				
				if(stcd.equals(perStcd) && yr.equals(perYear)){
					map.put("WSFE", wsfe);
					map.put("MXDYE", mxdye);
					map.put("MXDYEODT", mxdyeodt);
					map.put("MNDYE", mndye);
					map.put("MNDYEODT", mndyeodt);
					map.put("IDSDT", idsdt);
					map.put("ICAPD", icapd);
					map.put("EETP", eetp);
				}
			}
			
			for(JSONObject obj:remarkList){
				String stcd = obj.containsKey("STCD")?obj.getString("STCD").toString():null;
				String yr = obj.containsKey("YR")?obj.getString("YR").toString():null;
				String nt = obj.get("NT")!=null?obj.getString("NT").toString():"";
				if(stcd.equals(perStcd) && yr.equals(perYear)){
					map.put("NT", nt);
				}
			}
			
			if(bookType.equals(2)){
				dataList.add(map);
			}
		}
		
		if(bookType.equals(1)){
			int syear = Integer.parseInt(yearbook.getStartTime());
			int eyear = Integer.parseInt(yearbook.getEndTime());
			for(int i=syear;i<=eyear;i++){
				if(!dataMap.containsKey(i+"")){
					Map map = new HashMap<String,Object>();
					if(tool.collectionNotNull(dailyList)){
						String RVNM = dailyList.get(0)!=null?dailyList.get(0).get("RVNM").toString():"";
						String STNM = dailyList.get(0)!=null?dailyList.get(0).get("STNM").toString():"";
						map.put("STCD", dailyList.get(0).get("STCD").toString());
						map.put("RVNM", RVNM);
						map.put("STNM", STNM);
					}else{
						try {
							List<JSONObject> list = tbCommonService.queryDatasByTbName("HY_STSC_A", "{'STCD_EQ':'"+yearbook.getStcd()+"'}");
							String RVNM = list.get(0)!=null?list.get(0).get("RVNM").toString():"";
							String STNM = list.get(0)!=null?list.get(0).get("STNM").toString():"";
							map.put("STCD", yearbook.getStcds());
							map.put("RVNM", RVNM);
							map.put("STNM", STNM);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					map.put("YEAR", i+"");
					String[][] tableData = new String[34][12];
					map.put("tableData", tableData);
					dataMap.put(i+"",map);
				}
				dataList.add(dataMap.get(i+""));
			}
		}
		return dataList;
	}

}