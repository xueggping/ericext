package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.system.dictionary.service.DictionaryService;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;



@Service
@Transactional
public class AvgIceFlowReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	@Autowired
	private DictionaryService ds;
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String sql = "exec UP_Get_DIQ_C_By_Stations ?, ?, ? ";
		JSONObject o = new JSONObject();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）2：代表（多站单年）
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			JSONObject stsc = ds.getStsc( "'"+yearbook.getStcd()+"'");
			for (int i = startYear; i <= endYear; i++) {
				dataList.add(getReport(sql, yearbook.getStcd(), String.valueOf(i),stsc));
			}
			break;
		case 2:
			String[] stcds = yearbook.getStcds().split(",");
			for (int i = 0; i < stcds.length; i++) {
				dataList.add(getReport(sql, stcds[i], yearbook.getYear(),ds.getStsc("'"+stcds[i]+"'") ));
			}
			break;
		default:
			break;
		}
		o.put("dataList", dataList);
		o.put("yearbook", JSONObject.fromObject(yearbook));
		return o;
	}

	@Override
	public String getTemplate() {
		return "yearbook/AvgIceFlowReport";
	}
	
	private JSONObject getReport(String sql,String stcd ,String year, JSONObject stsc){
		stcd = "'"+stcd+"'";
		JSONObject res = new JSONObject();
		String startYear=year+"-01-01";
		String endYear=year+"-12-31";
		List<JSONObject> jx = ur.queryAllCustom(sql,stcd,startYear,endYear);
		double num2 = 14;//(double)(jx.size() / 5);
		int num3 = (int)num2;
//		if (jx.size() % 5 > 0)
//		{
//			num3++;
//		}
		res.put("rows", num3);
		String[][] data = new String[num3][15]; 
		int num4 = 0;
		int num5 = 0;
		String b = "";
		String b2 = "";
		int num5bj=0;
		int size=jx.size()>(5*14)?5*14:jx.size();
		for (int k = 0; k < size; k++){
			num5bj = num5;
			if (k % num3 == 0 && k != 0){
				num4 = 0;
				num5++;
			}
			int num6 = 0;
			num4++;
			String text3 = String.valueOf(Integer.parseInt(CommonUtil.padWhitespaceLeft(jx.get(k).getString("DT").substring(5,7), 2)));
			String text4 = String.valueOf(Integer.parseInt(CommonUtil.padWhitespaceLeft(jx.get(k).getString("DT").substring(8,10), 2)));;
			String text5 = jx.get(k).containsKey("AVIQ")?String.format("%.2f",jx.get(k).getDouble("AVIQ")):"";//平均冰流量
			if (k == 0){
				b = text3;
				b2 = text4;
			}
			else{
				if (b.equals(text3.trim())&&num5bj==num5){
					text3 = "&nbsp;";
				}else{
					b = text3;
				}
				if (b2.equals(text4.trim())){
					text4 = "&nbsp;";
				}else{
					b2 = text4;
				}
			}
			data[num4-1][num5 * 3 + num6++] = text3;
			data[num4-1][num5 * 3 + num6++] = text4;
			data[num4-1][num5 * 3 + num6++] = text5;
		}
		res.put("data", data);
		res.put("year", year);
		res.put("stsc",stsc);
		String sql2 = "exec UP_Get_YRIQ_F_By_Stations ?, ?, ? ";
		List<JSONObject> qn= ur.queryAllCustom(sql2,stcd,year,year);
		res.put("SMXIQDT",qn.size()==0?null:CommonUtil.subDatestr(qn.get(0).get("SMXIQDT").toString()));
		res.put("WMXIQD",qn.size()==0?null:CommonUtil.subDatestr(qn.get(0).get("WMXIQD").toString()));
		res.put("STIQ",qn.size()==0?null:String.format("%.1f",qn.get(0).getDouble("STIQ")) );
		res.put("WTIQ",qn.size()==0?null:String.format("%.1f",qn.get(0).getDouble("WTIQ")) );
		res.put("YTIQ",qn.size()==0?null:String.format("%.1f",qn.get(0).getDouble("YTIQ")) );
		res.put("nbll",qn.size()>0?qn.get(0):null);//年冰流量
		return res;
	}

}
