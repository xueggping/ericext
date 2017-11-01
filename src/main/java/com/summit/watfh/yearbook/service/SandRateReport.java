package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
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
public class SandRateReport extends YearbookAbstractService{

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
		String sql="exec UP_Get_DCS_C_By_Stations ?,?,?";
		String sqlZy ="exec UP_Get_MTCS_E_By_Stations ?,?,?";//逐月
		JSONObject res = new JSONObject();
		List<Map<String, Object>> dailyList=null;
		List<Map<String, Object>> monthAvglist=null;
		List<JSONObject> dataMap = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）
			dailyList = ur.jdbcTemplate.queryForList(sql, stcd, startTime, endTime);//日
			monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//月含沙量表
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
			dailyList = ur.jdbcTemplate.queryForList(sql, stcd, startTime, endTime);//日
			monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//月含沙量表
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
	private JSONObject getReport(List<Map<String, Object>> dailyList,List<Map<String, Object>>  monthAvglist,
			Yearbook yearbook,String year, JSONObject stsc) {
		int type = yearbook.getBookType();
		JSONObject map = new JSONObject();
		String stcd = "'"+yearbook.getStcd()+"'";//单站
		map.put("STCD", yearbook.getStcd());
		map.put("stsc", stsc);
		map.put("YEAR", year);
		String[][] tableData = new String[36][12];
		for(Map<String, Object> on:dailyList){//日数据
			String dt = on.containsKey("DT")?on.get("DT").toString():null;
			String stcd2 = on.containsKey("STCD")?on.get("STCD").toString():null;
			String avq =on.get("AVCS")==null?"":on.get("AVCS").toString();
			String yearSel = dt.substring(0, 4);//年份
			int m = Integer.parseInt(dt.substring(5,7));//月份
			int d = Integer.parseInt(dt.substring(8, 10));//日期
			switch (type) {
				case 1:
					if(map.get("YEAR").equals(yearSel)){
						tableData[d-1][m-1] = avq;
					}
					break;
				case 2:
					if(map.get("STCD").equals(stcd2)){
						tableData[d-1][m-1] = avq;
					}
					break;		
				default : break;
			};
		}
		for(Map<String,Object> mavg:monthAvglist){
			String stcd1 = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
			String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
			int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
			String avq = mavg.get("AVCS")==null?"":mavg.get("AVCS").toString();//月 平均含沙量
			String mxq = mavg.get("MXS")==null?"":mavg.get("MXS").toString();//月  最高
			String mxqdt = mavg.get("MXSDT")==null?"":mavg.get("MXSDT").toString();//月  最高日期
			String mnq = mavg.get("MNS")==null?"":mavg.get("MNS").toString();//月  最低
			String mnqdt = mavg.get("MNSDT")==null?"":mavg.get("MNSDT").toString();//月  最低日期
			if(stcd1.equals(map.get("STCD").toString()) && yr.equals(map.get("YEAR").toString())){
				tableData[31][mth-1] = avq;//月平均
				tableData[32][mth-1] = mxq;//最高
				tableData[33][mth-1] = CommonUtil.subDateStr1(mxqdt);//最高日期
				tableData[34][mth-1] = mnq;//最低
				tableData[35][mth-1] =CommonUtil.subDateStr1( mnqdt);//最低日期
			}
		}
		map.put("tableData", tableData);
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRCS_F_By_Stations ?,?,?", stcd,year,year);//逐年水温
		String mxqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MXSDT").toString());//最大水温日期
		String mnqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MNSDT").toString());//最小水温日期
		map.put("znsw",yearList.size()<=0?null:yearList.get(0));
		map.put("MXSDT", mxqdt);
		map.put("MNSDT", mnqdt);
		List<JSONObject> jx = ur.queryAllCustom("SELECT AVQ FROM HY_YRQ_F where stcd="+stcd+" and yr='"+year+"'");//年平均流量
		map.put("AVQ", jx.size()<=0?"&nbsp;":jx.get(0).get("AVQ"));
		List<JSONObject> nss = ur.queryAllCustom("SELECT AVQS FROM HY_YRQs_F where stcd="+stcd+" and yr='"+year+"'");//年平均输沙率
		map.put("AVQS", nss.size()<=0?"&nbsp;":nss.get(0).get("AVQS"));
		List<JSONObject> hsl = ur.queryAllCustom("SELECT AVCS FROM HY_YRcs_F where stcd="+stcd+" and yr='"+year+"'");//年平均含沙量
		map.put("AVCS",hsl.size()<=0?"&nbsp;": hsl.get(0).get("AVCS"));
		try {//附注
			List<JSONObject> remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':"+stcd.replace("','", ",")+",'YR_DT':'"+year+"','YR_LT':'"+year+"','TBID_EQ':'HY_DCS_C'}");
			map.put("NT", remarkList.size()<=0?null:remarkList.get(0).get("NT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public String getTemplate() {
		return "yearbook/SandRateReport";
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
