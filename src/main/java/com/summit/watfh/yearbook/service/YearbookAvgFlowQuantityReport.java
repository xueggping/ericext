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
public class YearbookAvgFlowQuantityReport extends YearbookAbstractService {
	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private SummitTools tool;
	
	@Override
	public String getTemplate() {
		String template = "yearbook/AvgFlowQuantityReport";
		return template;
	}

	@Override
	public JSONObject getData(Yearbook yearbook){
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
		List<Map<String, Object>> dailyList = ur.jdbcTemplate.queryForList("exec UP_Get_DQ_C_By_Stations ?,?,?", qstcds, qstime, qetime);//日流量
		List<Map<String, Object>> monthAvglist = ur.jdbcTemplate.queryForList("exec UP_Get_MTQ_E_By_Stations ?,?,?", qstcds, qstime, qetime);//月流量
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRQ_F_By_Stations ?,?,?", qstcds, qstime, qetime);//年流量
		List<JSONObject> timeMaxList = null;
		List<JSONObject> remarkList = null;
		try {
			timeMaxList = tbCommonService.queryDatasByTbName("HY_IMXFW_F", "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"'}");//时段最大洪量
			remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':'"+stcds+"','YR_DT':'"+beginYear+"','YR_LT':'"+endYear+"','TBID_EQ':'HY_DQ_C'}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Map<String,Object>> dataList = avgFlowData(dailyList, yearList, monthAvglist, timeMaxList,remarkList, yearbook);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", dataList);
		jsonObj.put("yearbook", JSONObject.fromObject(yearbook));
		return jsonObj;
	}
	
	private List<Map<String,Object>> avgFlowData(List<Map<String, Object>> dailyList,List<Map<String, Object>> yearList,
			List<Map<String, Object>> monthAvglist,List<JSONObject> timeMaxList,List<JSONObject> remarkList,Yearbook yearbook){
		List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String,Map<String,Object>> dataMap = new HashMap<String, Map<String, Object>>();
		Integer type = yearbook.getBookType();
		
		if(tool.collectionNotNull(dailyList)){
			if(type.equals(1))
			{
				//单站多年
				for(Map<String, Object> on:dailyList){
					String dt = on.containsKey("DT")?on.get("DT").toString():null;
					String stcd = on.containsKey("STCD")?on.get("STCD").toString():null;
					String avq = on.containsKey("AVQ")?String.format("%.2f", Double.parseDouble(on.get("AVQ").toString())):null;
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
							tableData[d-1][m-1] = avq;
						}
						else
						{
							String[][] tableData = new String[36][12];
							tableData[d-1][m-1] = avq;
							map.put("tableData", tableData);
						}
					}
					else
					{
						Map<String, Object> map = new HashMap<String,Object>();
						String RVNM = on.containsKey("RVNM")?on.get("RVNM").toString():null;
						String STNM = on.containsKey("STNM")?on.get("STNM").toString():null;
						map.put("STCD", stcd);
						map.put("RVNM", RVNM);
						map.put("STNM", STNM);
						map.put("YEAR", year);
//						map.put("MONTH", m);
						
						String[][] tableData = new String[36][12];
						tableData[d-1][m-1] = avq;
						map.put("tableData", tableData);
						
						dataMap.put(year, map);
					}
				}
				
			}
			else if(type.equals(2))
			{
				//单年多站
				for(Map<String,Object> on:dailyList){
					String dt = on.containsKey("DT")?on.get("DT").toString():null;
					String stcd = on.containsKey("STCD")?on.get("STCD").toString():null;
					String avq = on.containsKey("AVQ")?String.format("%.2f", Double.parseDouble(on.get("AVQ").toString())):null;
					
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
							tableData[d-1][m-1] = avq;
						}
						else
						{
							String[][] tableData = new String[36][12];
							tableData[d-1][m-1] = avq;
							map.put("tableData", tableData);
						}
					}
					else
					{
						Map<String, Object> map = new HashMap<String,Object>();
						String RVNM = on.containsKey("RVNM")?on.get("RVNM").toString():null;
						String STNM = on.containsKey("STNM")?on.get("STNM").toString():null;
						map.put("STCD", stcd);
						map.put("STNM", STNM);
						map.put("YEAR", year);
						map.put("RVNM", RVNM);
						
						String[][] tableData = new String[36][12];
						tableData[d-1][m-1] = avq;
						map.put("tableData", tableData);
						
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
		
		for(Entry<String, Map<String, Object>> entry : dataMap.entrySet())
		{
				Map<String,Object> map = entry.getValue();
				String perStcd = map.containsKey("STCD")?map.get("STCD").toString():null;
				String perYear = map.containsKey("YEAR")?map.get("YEAR").toString():null;
				
				for(Map<String,Object> mavg:monthAvglist){
					String stcd = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
					String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
					int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
					String avq = mavg.containsKey("AVQ")?String.format("%.2f", Double.parseDouble(mavg.get("AVQ").toString())):"";
					String mxq = mavg.containsKey("MXQ")?String.format("%.2f", Double.parseDouble(mavg.get("MXQ").toString())):"";
					String mxqdt = mavg.containsKey("MXQDT")?CommonUtil.subDateStr1(mavg.get("MXQDT").toString()):"";
					String mnq = mavg.containsKey("MNQ")?String.format("%.2f", Double.parseDouble(mavg.get("MNQ").toString())):"";
					String mnqdt = mavg.containsKey("MNQDT")?CommonUtil.subDateStr1(mavg.get("MNQDT").toString()):"";
					if(stcd.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
						String[][] tdata = (String[][]) map.get("tableData");
						tdata[31][mth-1] = avq;
						tdata[32][mth-1] = mxq;
						tdata[33][mth-1] = mxqdt;
						tdata[34][mth-1] = mnq;
						tdata[35][mth-1] = mnqdt;
					}
				}
				
				for(JSONObject maxhf:timeMaxList){
					String stcd = maxhf.containsKey("STCD")?maxhf.getString("STCD"):"";
					String yr = maxhf.containsKey("YR")?maxhf.getString("YR"):"";
					String mxw = maxhf.containsKey("MXW")?maxhf.getString("MXW"):"";//最大洪量
					String mxwdr = maxhf.containsKey("MXWDR")?maxhf.getString("MXWDR"):"";//最大洪量时段长
					String bgdt = maxhf.containsKey("BGDT")?CommonUtil.subDatestr(maxhf.getString("BGDT")):"";//起始日期
					
					if(stcd.equals(perStcd) && yr.equals(perYear))
					{
						map.put("MXWDR"+mxwdr, mxw);
						map.put("BGDT"+mxwdr, bgdt);
					}
				}
				
				for(Map<String,Object> obj:yearList){
					String stcd = obj.containsKey("STCD")?obj.get("STCD").toString():null;
					String yr = obj.containsKey("YR")?obj.get("YR").toString():null;
					String avq = obj.containsKey("AVQ")?String.format("%.1f", Double.parseDouble(obj.get("AVQ").toString())):null;//平均流量
					String mxq = obj.containsKey("MXQ")?String.format("%.0f", Double.parseDouble(obj.get("MXQ").toString())):null;//最大流量
					String mxqdt = obj.containsKey("MXQDT")?CommonUtil.subDatestr(obj.get("MXQDT").toString()):null;//最大流量日期
					String mnq = obj.containsKey("MNQ")?String.format("%.2f", Double.parseDouble(obj.get("MNQ").toString())):null;//最小流量
					String mnqdt = obj.containsKey("MNQDT")?CommonUtil.subDatestr(obj.get("MNQDT").toString()):null;//最小流量日期
					String rw = obj.containsKey("RW")?obj.get("RW").toString():null;//径流量
					String rm = obj.containsKey("RM")?obj.get("RM").toString():null;//径流模数
					String rd = obj.containsKey("RD")?obj.get("RD").toString():null;//径流深度
					
					if(stcd.equals(perStcd) && yr.equals(perYear)){
						map.put("AVQ", avq);
						map.put("MXQ", mxq);
						map.put("MXQDT", mxqdt!=null?CommonUtil.subDatestr(mxqdt):null);
						map.put("MNQ", mnq);
						map.put("MNQDT", mnqdt);
						map.put("RW", rw);
						map.put("RM", rm);
						map.put("RD", rd);
					}
				}
				
				for(JSONObject obj:remarkList){
					String stcd = obj.containsKey("STCD")?obj.getString("STCD").toString():null;
					String yr = obj.containsKey("YR")?obj.getString("YR").toString():null;
					String nt = obj.containsKey("NT")?obj.getString("NT").toString():null;
					if(stcd.equals(perStcd) && yr.equals(perYear)){
						map.put("NT", nt);
					}
				}
				
//				dataList.add(dataMap.get(entry.getKey()));
			}
		return dataList;
	}

}