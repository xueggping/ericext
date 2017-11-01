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
public class YearbookWaterTempReportYM extends YearbookAbstractService{

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
		String sqlXpj ="exec UP_Get_DCWT_D_By_Stations ?,?,?";//旬平均
		String sqlZy ="exec UP_Get_MTWT_E_By_Stations ?,?,?";//逐月水温
		JSONObject res = new JSONObject();
		List<Map<String, Object>> xunAvgList=null;
		List<Map<String, Object>> monthAvglist=null;
		List<JSONObject> dataMap = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）
			xunAvgList = ur.jdbcTemplate.queryForList(sqlXpj, stcd,startTime, endTime);//旬平均
			monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//逐月水温
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			JSONObject stsc = ds.getStsc(stcd);
			for (int i = startYear; i <=endYear ; i++) {
				String year=String.valueOf(i);
				dataMap.add(getReport(xunAvgList,monthAvglist,yearbook,year,stsc) );
			}
			break;
		case 2 ://2：代表（多站单年）
			stcd ="'" + yearbook.getStcds().replace(",", "','") + "'";////多站
			startTime=yearbook.getYear()+"-01-01";
			endTime = yearbook.getYear()+"-12-31";
			xunAvgList = ur.jdbcTemplate.queryForList(sqlXpj, stcd,startTime, endTime);//旬平均
			monthAvglist = ur.jdbcTemplate.queryForList(sqlZy, stcd, startTime, endTime);//逐月水温
			String[] stcdSz = yearbook.getStcds().split(",");
			for (int i = 0; i <stcdSz.length ; i++) {
				yearbook.setStcd( stcdSz[i]);
				dataMap.add(getReport(xunAvgList,monthAvglist,yearbook,yearbook.getYear(),ds.getStsc("'"+stcdSz[i]+"'") ));
			}
			break;	
			default : break;
		};
		res.put("data", dataMap);
		res.put("yearbook", JSONObject.fromObject(yearbook));
		return res;
	}
	private JSONObject getReport(List<Map<String, Object>> xunAvgList,List<Map<String, Object>>  monthAvglist,
			Yearbook yearbook,String year, JSONObject stsc) {
		JSONObject map = new JSONObject();
		int type = yearbook.getBookType();
		map.put("STCD", yearbook.getStcd());
		map.put("stsc", stsc);
		map.put("YEAR", year);
		String[][] tableData = new String[8][12];//+3 旬平均
		for(Map<String, Object> mavg:xunAvgList){//旬平均
			String stcd = mavg.containsKey("STCD")?mavg.get("STCD").toString():"";
			String dt = mavg.get("PTBGDT").toString();//时间
			String yearSel = dt.substring(0, 4);//年份
			int m = Integer.parseInt(dt.substring(5,7));//月份
			int d = Integer.parseInt(dt.substring(8, 10));//日期
			String avq =mavg.get("AVWTMP")==null?"":mavg.get("AVWTMP").toString();
			switch (type) {
				case 1:
					if(map.get("YEAR").equals(yearSel)){
						tableData[d==1?0:d==11?1:2][m-1]=avq;
					}
					break;
				case 2:
					if(map.get("STCD").equals(stcd)){
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
			String avq = mavg.get("AVWTMP")==null?"&nbsp;":mavg.get("AVWTMP").toString();//月水温表   平均水温
			String mxq = mavg.get("MXWTMP")==null?"&nbsp;":mavg.get("MXWTMP").toString();//月  最高水温
			String mxqdt = mavg.get("MXWTMPDT")==null?"&nbsp;":mavg.get("MXWTMPDT").toString();//月  最高水温日期
			String mnq = mavg.get("MNWTMP")==null?"&nbsp;":mavg.get("MNWTMP").toString();//月  最低水温
			String mnqdt = mavg.get("MNWTMPDT")==null?"&nbsp;":mavg.get("MNWTMPDT").toString();//月  最低水温日期
			if(stcd1.equals(map.get("STCD")) && yr.equals(map.get("YEAR"))){
				tableData[3][mth-1] = avq;//月平均
				tableData[4][mth-1] = mxq;//最高
				tableData[5][mth-1] = CommonUtil.subDateStr1(mxqdt);//最高日期
				tableData[6][mth-1] = mnq;//最低
				tableData[7][mth-1] =CommonUtil.subDateStr1( mnqdt);//最低日期
			}
		}
		map.put("tableData", tableData);
		List<Map<String, Object>> yearList = ur.jdbcTemplate.queryForList("exec UP_Get_YRWT_F_By_Stations ?,?,?","'"+yearbook.getStcd()+"'", year, year);//逐年
		String mxqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MXWTMPDT").toString());//最大水温日期
		String mnqdt = yearList.size()<=0?"&nbsp;":CommonUtil.subDatestr(yearList.get(0).get("MNWTMPDT").toString());//最小水温日期
		map.put("znsw",yearList.size()<=0?null:yearList.get(0));
		map.put("MXWTMPDT", mxqdt);
		map.put("MNWTMPDT", mnqdt);
		try {
			List<JSONObject> remarkList = tbCommonService.queryDatasByTbName("HY_DAEX_I", "{'STCD_IN':"+"'"+yearbook.getStcd()+"'".replace("','", ",")+",'YR_DT':'"+year+"','YR_LT':'"+year+"','TBID_EQ':'HY_DWT_C'}");
			map.put("NT", remarkList.size()<=0?null:remarkList.get(0).get("NT"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//附注
		return map;
	}
	@Override
	public String getTemplate() {
		return "yearbook/AvgWaterTempReportYM";
	}
}
