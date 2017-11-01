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
public class YearbookAvgTempReport extends YearbookAbstractService {
	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private SummitTools tool;
	
	@Override
	public String getTemplate() {
		String template = "yearbook/AvgTempReport";
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
		List<Map<String, Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_DAT_C_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String, Object>> ptList = ur.jdbcTemplate.queryForList("exec UP_Get_DCAT_D_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String, Object>> monthList = ur.jdbcTemplate.queryForList("exec UP_Get_MTAT_E_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRAT_F_By_Stations ?,?,?",qstcds,qstime,qetime);
		List<JSONObject> remarkList = null;
		try {
			remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"','TBID_EQ':'HY_YRWEAP_F'}");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		List<Map<String, Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_YRAT_F_By_Stations ?,?,?");
		List<Map<String,Object>> data = avgTempData(dailyList,ptList,monthList,yearList,remarkList,yearbook);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", data);
		jsonObj.put("yearbook", JSONObject.fromObject(yearbook));
		return jsonObj;
	}

	private List<Map<String, Object>> avgTempData(List<Map<String, Object>> dailyList,
			List<Map<String, Object>> ptList,
			List<Map<String, Object>> monthList, List<Map<String, Object>> yearList, 
			List<JSONObject> remarkList, Yearbook yearbook) {
		Integer bookType = yearbook.getBookType();
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String,Map<String,Object>> dataMap = new HashMap<String, Map<String, Object>>();
		
		if(tool.collectionNotNull(dailyList)){
			for(Map<String,Object> on:dailyList){
				String stcd = on.get("STCD")!=null ? on.get("STCD").toString():"";
				String dt = on.get("DT")!=null ? on.get("DT").toString():"";
				String atmp = on.get("ATMP")!=null ? on.get("ATMP").toString():"";//日平均气温
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
						if(d>0 && d<=10){
							tableData[d-1][m-1] = atmp;
						}else if(d>10 && d<=20){
							tableData[d][m-1] = atmp;
						}else{
							tableData[d+1][m-1] = atmp;
						}
					}
					else
					{
						String[][] tableData = new String[39][12];
						if(d>0 && d<=10){
							tableData[d-1][m-1] = atmp;
						}else if(d>10 && d<=20){
							tableData[d][m-1] = atmp;
						}else{
							tableData[d+1][m-1] = atmp;
						}
						
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
//					map.put("MONTH", m);
					
					String[][] tableData = new String[39][12];
					if(d>0 && d<=10){
						tableData[d-1][m-1] = atmp;
					}else if(d>10 && d<=20){
						tableData[d][m-1] = atmp;
					}else{
						tableData[d+1][m-1] = atmp;
					}
					map.put("tableData", tableData);
					
					dataMap.put(year, map);
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
				for(int i=syear;i<=eyear;i++){
					if(!dataMap.containsKey(i+"")){
						Map map = new HashMap<String,Object>();
						map.put("STCD", stcd);
						map.put("RVNM", jo.containsKey("RVNM")?jo.getString("RVNM"):null);
						map.put("STNM", jo.containsKey("STNM")?jo.getString("STNM"):null);
						map.put("YEAR", i+"");
						String[][] tableData = new String[39][12];
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
						String[][] tableData = new String[39][12];
						map.put("tableData", tableData);
						dataMap.put(stcd,map);
					}
					dataList.add(dataMap.get(stcd));
				}
			}
		}
		
		for(Entry<String, Map<String, Object>> entry : dataMap.entrySet())
		{
				Map<String,Object> map = entry.getValue();
				String perStcd = map.containsKey("STCD")?map.get("STCD").toString():null;
				String perYear = map.containsKey("YEAR")?map.get("YEAR").toString():null;
				
				for(Map<String,Object> ptavg:ptList){
					String stcd = ptavg.containsKey("STCD")?ptavg.get("STCD").toString():"";
					String dt = ptavg.containsKey("PTBGDT")?ptavg.get("PTBGDT").toString():"";
					String avatmp = ptavg.get("AVATMP")!=null?ptavg.get("AVATMP").toString():"";
					
					String year = dt.substring(0, 4);//年份
					String month = dt.substring(5,7);//月份
					String day = dt.substring(8, 10);//日期
					
					int m = Integer.parseInt(month);
					
					if(stcd.equals(map.get("STCD").toString()) && year.equals(map.get("YEAR").toString())){
						String[][] tableData = (String[][])map.get("tableData");
						if("01".equals(day)){
							tableData[10][m-1]=avatmp;
						}else if("11".equals(day)){
							tableData[21][m-1]=avatmp;
						}else if("21".equals(day)){
							tableData[32][m-1]=avatmp;
						}
					}
				}
				
				for(Map<String,Object> mavg:monthList){
					String stcd = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
					String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
					int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
					String AVATMP = mavg.containsKey("AVATMP")?mavg.get("AVATMP").toString():"";//平均气温
					String MXATMP = mavg.containsKey("MXATMP")?mavg.get("MXATMP").toString():"";//最高气温
					String MXATMPDT = mavg.containsKey("MXATMPDT")? CommonUtil.subDateStr1(mavg.get("MXATMPDT").toString()):"";//最高气温日期
					String MNATMP = mavg.containsKey("MNATMP")?mavg.get("MNATMP").toString():"";//最低气温
					String MNATMPDT = mavg.containsKey("MNATMPDT")? CommonUtil.subDateStr1(mavg.get("MNATMPDT").toString()):"";//最低气温日期
					if(stcd.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
						String[][] tdata = (String[][]) map.get("tableData");
						tdata[34][mth-1] = AVATMP;
						tdata[35][mth-1] = MXATMP;
						tdata[36][mth-1] = MXATMPDT;
						tdata[37][mth-1] = MNATMP;
						tdata[38][mth-1] = MNATMPDT;
					}
				}
				
				for(Map<String,Object> obj:yearList){
					String stcd = obj.containsKey("STCD")?obj.get("STCD").toString():null;
					String yr = obj.containsKey("YR")?obj.get("YR").toString():null;
					String AVATMP = obj.get("AVATMP")!=null?obj.get("AVATMP").toString():"";//平均气温
					String MXATMP = obj.get("MXATMP")!=null?obj.get("MXATMP").toString():"";//最高气温
					String MXATMPDT = obj.get("MXATMPDT")!=null?obj.get("MXATMPDT").toString():"";//最高气温日期
					String MNATMP = obj.get("MNATMP")!=null?obj.get("MNATMP").toString():"";//最低气温
					String MNATMPDT = obj.get("MNATMPDT")!=null?obj.get("MNATMPDT").toString():"";//最低气温日期
					
					if(stcd.equals(perStcd) && yr.equals(perYear)){
						map.put("AVATMP", AVATMP);
						map.put("MXATMP", MXATMP);
						map.put("MXATMPDT", CommonUtil.subDatestr(MXATMPDT));
						map.put("MNATMP", MNATMP);
						map.put("MNATMPDT", CommonUtil.subDatestr(MNATMPDT));
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
			}
		
		return dataList;
	}

}