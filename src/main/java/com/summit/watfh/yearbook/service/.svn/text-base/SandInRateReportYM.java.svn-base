package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.common.tbOperate.service.TbCommonInter;
import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.system.dictionary.service.DictionaryService;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;

@Service
@Transactional
public class SandInRateReportYM extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	@Autowired
	private TbCommonInter tbCommonService;
	@Autowired
	private DictionaryService ds;
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String stcd = "'"+yearbook.getStcd()+"'";//单站
		String startTime=yearbook.getStartTime();
		String endTime=yearbook.getEndTime();
		String sql="exec UP_Get_DCQS_D_By_Stations ?,?,?";//旬数据
		String sqlYavg ="exec UP_Get_MTQS_E_By_Stations ?,?,?";//月平均 月最大
		List<Map<String, Object>> dailyList=null;
		List<Map<String, Object>> monthAvglist=null;
		List<JSONObject> dataMap = new ArrayList<JSONObject>();
		
		JSONObject res = new JSONObject();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）
			dailyList = ur.jdbcTemplate.queryForList(sql, stcd, startTime, endTime);//旬数据
			monthAvglist = ur.jdbcTemplate.queryForList(sqlYavg, stcd, startTime, endTime);//月平均 月最大
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			JSONObject stsc = ds.getStsc(stcd);
			for (int i = startYear; i <=endYear ; i++) {
				String year=String.valueOf(i);
				dataMap.add(getReport(dailyList,monthAvglist,yearbook,year,stsc));
			}
			break;
		case 2 ://2：代表（多站单年）
			stcd ="'" + yearbook.getStcds().replace(",", "','") + "'";////多站
			startTime=yearbook.getYear()+"-01-01";
			endTime = yearbook.getYear()+"-12-31";
			dailyList = ur.jdbcTemplate.queryForList(sql, stcd, startTime, endTime);//旬数据
			monthAvglist = ur.jdbcTemplate.queryForList(sqlYavg, stcd, startTime, endTime);//月平均 月最大
			String[] stcdSz = yearbook.getStcds().split(",");
			for (int i = 0; i <stcdSz.length ; i++) {
				yearbook.setStcd( stcdSz[i]);
				dataMap.add(getReport(dailyList,monthAvglist,yearbook,yearbook.getYear(),ds.getStsc("'"+stcdSz[i]+"'") ));
			}
			break;	
			default : break;
		};
		res.put("data", dataMap);
		res.put("yearbook", JSONObject.fromObject(yearbook));
		return res;
	}
	@Override
	public String getTemplate() {
		return "yearbook/SandInRateReportYM";
	}
	private JSONObject getReport(List<Map<String, Object>> dailyList,List<Map<String, Object>>  monthAvglist,
			Yearbook yearbook,String year, JSONObject stsc) {
		int type = yearbook.getBookType();
		JSONObject map = new JSONObject();
		String stcd = "'"+yearbook.getStcd()+"'";//单站
		map.put("STCD", yearbook.getStcd());
		map.put("stsc", stsc);
		map.put("YEAR", year);
		String[][] tableData = new String[34][12];
		for(Map<String, Object> on:dailyList){//日数据
			String dt = on.containsKey("PTBGDT")?on.get("PTBGDT").toString():null;
			String stcd2 = on.containsKey("STCD")?on.get("STCD").toString():null;
			String yearSel = dt.substring(0, 4);//年份
			int m = Integer.parseInt(dt.substring(5,7));//月份
			int d = Integer.parseInt(dt.substring(8, 10));//日期
			String avq =on.get("AVQS")==null?"":on.get("AVQS").toString();
			switch (type) {
				case 1:
					if(map.get("YEAR").equals(yearSel)){
						tableData[d==1?0:d==11?1:2][m-1]=avq;
					}
					break;
				case 2:
					if(map.get("STCD").equals(stcd2)){
						tableData[d==1?0:d==11?1:2][m-1]=avq;
					}
					break;		
				default : break;
			};
		}
		for(Map<String,Object> mavg:monthAvglist){
			String stcd1 = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
			String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
			int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
			String avq = mavg.get("AVQS")==null?"":mavg.get("AVQS").toString();//月  平均
			String mxq = mavg.get("MXDYQS")==null?"":mavg.get("MXDYQS").toString();//月  最大
//			String mxqdt = mavg.get("MXDYQSODT")==null?"":mavg.get("MXDYQSODT").toString();//月  最高日期
			if(stcd1.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
				tableData[3][mth-1] = avq;//月平均
				tableData[4][mth-1] = mxq;//最高
			}
		}
		map.put("tableData", tableData);
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRQS_F_By_Stations ?,?,?", stcd,year,year);//年统计
		String mxqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MXDYQSODT").toString());//最大平均输沙率日期
		map.put("znsw",yearList.size()<=0?null:yearList.get(0));
		map.put("MXDYQSODT", mxqdt);
		try {//附注
			List<JSONObject> remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':"+stcd.replace("','", ",")+",'YR_DT':'"+year+"','YR_LT':'"+year+"','TBID_EQ':'HY_DQS_C'}");
			map.put("NT", remarkList.size()<=0?null:remarkList.get(0).get("NT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	private JSONObject getStsc(String stcd) {
		String sql = "select * from HY_STSC_A where stcd = " + stcd;
		List<JSONObject> s = ur.queryAllCustom(sql);
		if(s!= null && s.size() > 0){
			return s.get(0);
		}
		return null;
	}
}
