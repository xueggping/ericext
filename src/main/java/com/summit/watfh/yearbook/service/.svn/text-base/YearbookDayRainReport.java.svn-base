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
public class YearbookDayRainReport extends YearbookAbstractService {
	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private SummitTools tool;

	@Override
	public String getTemplate() {
		String template = "yearbook/DayRainReport";
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
		List<Map<String,Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_DP_C_By_Stations ?,?,?",
				qstcds,qstime,qetime);
		List<Map<String,Object>> monthList = ur.jdbcTemplate.queryForList("exec UP_Get_MTP_E_By_Stations ?,?,?",
				qstcds,qstime,qetime);
		List<Map<String,Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRP_F_By_Stations ?,?,?",
				qstcds,qstime,qetime);
//		List<Map<String,Object>> dmxpList = ur.jdbcTemplate.queryForList("exec Get_DMXP_F_By_Station_Year ?,?,?",
//				yearbook.getStcds(),yearbook.getStartTime(),yearbook.getEndTime());//最大时段降雨量
		
		String param = "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"'}";
		
		List<JSONObject> dmxpList = null;
		List<JSONObject> remarkList = null;
		try {
			dmxpList = tbCommonService.queryDatasByTbName("HY_DMXP_F",param);//最大时段降雨量
			remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"','TBID_EQ':'HY_YRP_F'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Map<String,Object>> dataList = dayRainData(dailyList,monthList,yearList,dmxpList,remarkList,yearbook);
		JSONObject obj = new JSONObject();
		obj.put("data", dataList);
		obj.put("yearbook", JSONObject.fromObject(yearbook));
		return obj;
	}

	private List<Map<String, Object>> dayRainData(List<Map<String, Object>> dailyList,
			List<Map<String, Object>> monthList, List<Map<String, Object>> yearList, List<JSONObject> dmxpList,
			List<JSONObject> remarkList, Yearbook yearbook) {
		
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String,Map<String,Object>> dataMap = new HashMap<String, Map<String, Object>>();
		Integer bookType = yearbook.getBookType();
		if(tool.collectionNotNull(dailyList)){
			if(bookType.equals(1))
			{
				//单站多年
				for(Map<String, Object> on:dailyList){
					String dt = on.get("DT")!=null?on.get("DT").toString():"";//日期
					String stcd = on.get("STCD")!=null?on.get("STCD").toString():"";
					String p = on.get("P")!=null?on.get("P").toString():"";//降雨量
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
							tableData[d-1][m-1] = p;
						}
						else
						{
							String[][] tableData = new String[34][12];
							tableData[d-1][m-1] = p;
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
//						map.put("MONTH", m);
						
						String[][] tableData = new String[34][12];
						tableData[d-1][m-1] = p;
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
					String p = on.get("P")!=null?on.get("P").toString():"";
					
					String year = dt.substring(0, 4);//年份
					String month = dt.substring(5,7);//月份
					String day = dt.substring(8, 10);//日期
					
					int m = Integer.parseInt(month);
					int d = Integer.parseInt(day);
					
					if(dataMap.containsKey(stcd)){
						Map map = dataMap.get(stcd);
						if(map.containsKey("tableData"))
						{
							String[][] tableData = (String[][]) map.get("tableData");
							tableData[d-1][m-1] = p;
						}
						else
						{
							String[][] tableData = new String[36][12];
							tableData[d-1][m-1] = p;
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
//						map.put("MONTH", m);
						
						String[][] tableData = new String[36][12];
						tableData[d-1][m-1] = p;
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
				for(int i=syear;i<=eyear;i++){
					if(!dataMap.containsKey(i+"")){
						Map map = new HashMap<String,Object>();
						map.put("STCD", stcd);
						map.put("RVNM", jo.containsKey("RVNM")?jo.getString("RVNM"):null);
						map.put("STNM", jo.containsKey("STNM")?jo.getString("STNM"):null);
						map.put("YEAR", i+"");
						String[][] tableData = new String[34][12];
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
						String[][] tableData = new String[34][12];
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
				
				for(Map<String,Object> mavg:monthList){
					String stcd = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
					String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
					int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
					String p = mavg.get("P")!=null?mavg.get("P").toString():"&nbsp";
					String pdynum = mavg.get("PDYNUM")!=null?mavg.get("PDYNUM").toString():"&nbsp";
					String mxdyp = mavg.get("MXDYP")!=null?mavg.get("MXDYP").toString():"&nbsp";
//					String mxdypodt = mavg.get("MXDYPODT")!=null?subDateStr1(mavg.get("MXDYPODT").toString()):"";
					if(stcd.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
						String[][] tdata = (String[][]) map.get("tableData");
						tdata[31][mth-1] = p;
						tdata[32][mth-1] = pdynum;
						tdata[33][mth-1] = mxdyp;
//						tdata[34][mth-1] = mxdypodt;
					}
				}
				
				for(Map<String,Object> obj:yearList){
					String stcd = obj.containsKey("STCD")?obj.get("STCD").toString():null;
					String yr = obj.containsKey("YR")?obj.get("YR").toString():null;
					String p = obj.get("P")!=null?obj.get("P").toString():"&nbsp";//降水量
					String pdynum = obj.get("PDYNUM")!=null?obj.get("PDYNUM").toString():"&nbsp";//降水日数
					
					if(stcd.equals(perStcd) && yr.equals(perYear)){
						map.put("P", p);
						map.put("PDYNUM", pdynum);
					}
				}
				
				for(Map<String,Object> maxhf:dmxpList){
					String stcd = maxhf.containsKey("STCD")?maxhf.get("STCD").toString():"";
					String yr = maxhf.containsKey("YR")?maxhf.get("YR").toString():"";
					String mxp = maxhf.containsKey("MXP")?maxhf.get("MXP").toString():"&nbsp";//最大降水量
					String mxpdr = maxhf.containsKey("MXPDR")?maxhf.get("MXPDR").toString():"&nbsp";//最大降水量时段长
					String bgdt = maxhf.containsKey("BGDT")?CommonUtil.subDateStr2(maxhf.get("BGDT").toString()):"&nbsp";//起始日期
					
					if(stcd.equals(perStcd) && yr.equals(perYear))
					{
						map.put("MXPDR"+mxpdr, mxp);
						map.put("BGDT"+mxpdr, bgdt);
					}
				}
				
				for(JSONObject obj:remarkList){
					String stcd = obj.containsKey("STCD")?obj.getString("STCD").toString():null;
					String yr = obj.containsKey("YR")?obj.getString("YR").toString():null;
					String nt = obj.get("NT")!=null?obj.getString("NT").toString():"&nbsp";
					if(stcd.equals(perStcd) && yr.equals(perYear)){
						map.put("NT", nt);
					}
				}
			}
		
		return dataList;
	}
	
}