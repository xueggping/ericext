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
public class YearbookWaterTempReport extends YearbookAbstractService{

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
		String sqlList="exec UP_Get_DWT_C_By_Stations ?,?,?";//数据
		String sqlXpj ="exec UP_Get_DCWT_D_By_Stations ?,?,?";//旬平均
		String sqlZy ="exec UP_Get_MTWT_E_By_Stations ?,?,?";//逐月水温
		List<Map<String, Object>> xunAvgList=null;
		List<Map<String, Object>> monthAvglist=null;
		JSONObject res = new JSONObject();
		List<Map<String, Object>> dailyList=null;
		List<JSONObject> dataMap = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
			case 1://1:代表（单站多年）
				dailyList = ur.jdbcTemplate.queryForList(sqlList, stcd, startTime, endTime);//日水温
				xunAvgList = ur.jdbcTemplate.queryForList(sqlXpj, stcd,startTime, endTime);//旬平均
				monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//逐月水温
				int startYear = Integer.parseInt(yearbook.getStartTime());
				int endYear = Integer.parseInt(yearbook.getEndTime());
				JSONObject stsc = ds.getStsc(stcd);
				for (int i = startYear; i <=endYear ; i++) {
					String year=String.valueOf(i);
					dataMap.add(getReport(dailyList,xunAvgList,monthAvglist,yearbook,year,stsc) );
				}
				break;
			case 2 ://2：代表（多站单年）
				stcd ="'" + yearbook.getStcds().replace(",", "','") + "'";////多站
				startTime=yearbook.getYear()+"-01-01";
				endTime = yearbook.getYear()+"-12-31";
				dailyList = ur.jdbcTemplate.queryForList(sqlList, stcd, startTime, endTime);//日水温
				xunAvgList = ur.jdbcTemplate.queryForList(sqlXpj, stcd,startTime, endTime);//旬平均
				monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//逐月水温
				String[] stcdSz = yearbook.getStcds().split(",");
				for (int i = 0; i <stcdSz.length ; i++) {
					yearbook.setStcd(stcdSz[i]);
					dataMap.add(getReport(dailyList,xunAvgList,monthAvglist,yearbook,yearbook.getYear(),ds.getStsc("'"+stcdSz[i]+"'")    ));
				}
				break;
			default : break;
		};
		res.put("data", dataMap);
		res.put("yearbook", JSONObject.fromObject(yearbook));
		return res;
	}
	private JSONObject getReport(List<Map<String, Object>> dailyList,List<Map<String, Object>> xunAvgList,List<Map<String, Object>>  monthAvglist,
			Yearbook yearbook,String year, JSONObject stsc) {
		int type = yearbook.getBookType();
		String stcd = "'"+yearbook.getStcd()+"'";//单站
		JSONObject map = new JSONObject();
		map.put("STCD", yearbook.getStcd());
		map.put("stsc",stsc);
		map.put("YEAR", year);
		String[][] tableData = new String[39][12];//+3 旬平均
		for(Map<String, Object> on:dailyList){//日数据
			String dt = on.containsKey("DT")?on.get("DT").toString():null;
			String stcd2 = on.containsKey("STCD")?on.get("STCD").toString():null;
			String avq =on.get("WTMP")==null?"&nbsp;":on.get("WTMP").toString();
			String yearSel = dt.substring(0, 4);//年份
			int m = Integer.parseInt(dt.substring(5,7));//月份
			int d = Integer.parseInt(dt.substring(8, 10));//日期
			switch (type) {//1:代表（单站多年）
				case 1:
					if(map.get("YEAR").equals(yearSel)){
						tableData[d-1][m-1] = avq;
					}
					break;
				case 2://2：代表（多站单年）
					if(map.get("STCD").equals(stcd2)){
						tableData[d-1][m-1] = avq;
					}
					break;		
				default : break;
			};
		}
		
		for(Map<String,Object> mavg:xunAvgList){//旬平均
			String stcd1 = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
			String dt = mavg.get("PTBGDT").toString();//时间
			if(mavg.containsKey("PTBGDT")&&dt!=null){
				String yearXun = dt.substring(0, 4);//年份
				int m = Integer.parseInt(dt.substring(5,7));//月份
				int d = Integer.parseInt(dt.substring(8, 10));//日期
				if(stcd1.equals(map.get("STCD").toString()) && yearXun.equals(map.get("YEAR").toString())){
					tableData[d==1?31:d==11?32:33][m-1]=mavg.get("AVWTMP")==null?"&nbsp;":mavg.get("AVWTMP").toString();
				}
			}
		}
		for(Map<String,Object> mavg:monthAvglist){
			String stcd1 = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
			String yr = mavg.containsKey("YR")?mavg.get("YR").toString():"";
			int mth = mavg.containsKey("MTH")?Integer.parseInt(mavg.get("MTH").toString()):null;
			String avq = mavg.get("AVWTMP")==null?"&nbsp;":mavg.get("AVWTMP").toString();//月水温表   平均水温
			String mxq = mavg.get("MXWTMP")==null?"&nbsp;":mavg.get("MXWTMP").toString();//月  最高水温
			String mxqdt = mavg.get("MXWTMPDT")==null?"&nbsp;":mavg.get("MXWTMPDT").toString();//月  最高水温日期
			String mnq = mavg.get("MNWTMP")==null?"&nbsp;":mavg.get("MNWTMP").toString();//月  最低水温
			String mnqdt = mavg.get("MNWTMPDT")==null?"&nbsp;":mavg.get("MNWTMPDT").toString();//月  最低水温日期
			if(stcd1.equals(map.get("STCD")) && yr.equals(map.get("YEAR"))){
				tableData[34][mth-1] = avq;//月平均
				tableData[35][mth-1] = mxq;//最高
				tableData[36][mth-1] = CommonUtil.subDateStr1(mxqdt);//最高日期
				tableData[37][mth-1] = mnq;//最低
				tableData[38][mth-1] =CommonUtil.subDateStr1( mnqdt);//最低日期
			}
		}
		map.put("tableData",tableData);
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRWT_F_By_Stations ?,?,?","'"+yearbook.getStcd()+"'", year, year);//逐年水温
		String mxqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MXWTMPDT").toString());//最大水温日期
		String mnqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MNWTMPDT").toString());//最小水温日期
		map.put("znsw",yearList.size()<=0?null:yearList.get(0));
		map.put("MXWTMPDT", mxqdt);
		map.put("MNWTMPDT", mnqdt);
		try {//附注
			List<JSONObject> remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':"+stcd.replace("','", ",")+",'YR_DT':'"+year+"','YR_LT':'"+year+"','TBID_EQ':'HY_DWT_C'}");
			map.put("NT", remarkList.size()<=0?null:remarkList.get(0).get("NT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public String getTemplate() {
		return "yearbook/AvgWaterTempReport";
	}
}
