package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.watfh.yearbook.bean.Yearbook;

@Service
@Transactional
public class YearbookIceReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String sql = "exec UP_Get_YRICCO_F_By_Stations ?, ?, ?";
		JSONObject o = new JSONObject();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）2：代表（多站单年）
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			for (int i = startYear; i <= endYear; i++) {
				JSONObject j = getReport(sql, yearbook.getStcd(), String.valueOf(i));
				if(j!=null){
					dataList.add(j);
				}
			}
			break;
		case 2:
			String[] stcds = yearbook.getStcds().split(",");
			for (int i = 0; i < stcds.length; i++) {
				JSONObject j = getReport(sql, stcds[i], yearbook.getYear());
				if(j!=null){
					dataList.add(j);
				}
			}
			break;
		default:
			break;
		}
		o.put("dataList", dataList);
		o.put("yearbook", JSONObject.fromObject(yearbook));
		return o;
	}
	
	private JSONObject getReport(String sql,String stcd ,String year){
		stcd = "'"+stcd+"'";
		String stime = year + "-01-01";
		String etime = year + "-12-31";
		JSONObject res = new JSONObject();
		List<JSONObject> jx = ur.queryAllCustom(sql,stcd,stime,etime);
		String[] data = new String[17];
		int j = 0;
		if(jx.isEmpty()){
			return null;
		}
		for (JSONObject json : jx) {
			data[0] = String.valueOf(j);
			data[1] = json.containsKey("RVNM")?json.getString("RVNM"):"";
			data[2] = json.containsKey("STCD")?json.getString("STCD"):"";
			data[3] = json.containsKey("STNM")?json.getString("STNM"):"";
			if (json.containsKey("IBUPDT") && StringUtils.isNotEmpty(json.getString("IBUPDT")))
			{
				data[4] = StringUtils.substring(json.getString("IBUPDT"), 5, 10);
			}
			else
			{
				data[4] = "&nbsp;";
			}
			if (json.containsKey("ENDIRDT") && StringUtils.isNotEmpty(json.getString("ENDIRDT")))
			{
				data[5] = StringUtils.substring(json.getString("ENDIRDT"), 5, 10);
			}
			else
			{
				data[5] = "&nbsp;";
			}
			if (json.containsKey("IDSDT") && StringUtils.isNotEmpty(json.getString("IDSDT")))
			{
				data[6] = StringUtils.substring(json.getString("IDSDT"), 5, 10);
			}
			else
			{
				data[6] = "&nbsp;";
			}
			if (json.containsKey("IAPDT") && StringUtils.isNotEmpty(json.getString("IAPDT")))
			{
				data[7] = StringUtils.substring(json.getString("IAPDT"), 5, 10);
			}
			else
			{
				data[7] = "&nbsp;";
			}
			if (json.containsKey("BGIRDT") && StringUtils.isNotEmpty(json.getString("BGIRDT")))
			{
				data[8] = StringUtils.substring(json.getString("BGIRDT"), 5, 10);
			}
			else
			{
				data[8] = "&nbsp;";
			}
			if (json.containsKey("IFRZDT") && StringUtils.isNotEmpty(json.getString("IFRZDT")))
			{
				data[9] = StringUtils.substring(json.getString("IFRZDT"), 5, 10);
			}
			else
			{
				data[9] = "&nbsp;";
			}
			data[10] = this.getCellText(json.containsKey("FHFRDN")?json.getString("FHFRDN"):"");
			data[11] = this.getCellText(json.containsKey("FHFRDN")?json.getString("SHFRDYS"):"");
			data[12] = this.getCellText(json.containsKey("FHFRDN")?json.getString("RVMXITHK"):"");
			if (json.containsKey("RVMXITHKODT") && StringUtils.isNotEmpty(json.getString("RVMXITHKODT")))
			{
				data[13] = StringUtils.substring(json.getString("RVMXITHKODT"), 5, 10);
			}
			else
			{
				data[13] = "&nbsp;";
			}
			String text = json.containsKey("MXBDITHK")?json.getString("MXBDITHK"):"";
			if (StringUtils.isEmpty(text))
			{
				text = "&nbsp;";
			}
			else if (Double.valueOf(text) == 0)
			{
				text = "&nbsp;";
			}
			else
			{
				text = String.format("%.2f",json.getDouble("MXBDITHK"));
			}
			data[14] = text;
			if (json.containsKey("MXBDITHKODT") && StringUtils.isNotEmpty(json.getString("MXBDITHKODT")))
			{
				data[15] = StringUtils.substring(json.getString("MXBDITHKODT"), 5, 10);
			}
			else
			{
				data[15] = "&nbsp;";
			}
			data[16] = "&nbsp;";
		}
		res.put("data", data);
		res.put("year", year);
		res.put("stsc",this.getStsc(stcd));
		return res;
	}

	@Override
	public String getTemplate() {
		return "yearbook/IceReport";
	}
	
	private JSONObject getStsc(String stcd) {
		String sql = "select * from HY_STSC_A where stcd = " + stcd;
		List<JSONObject> s = ur.queryAllCustom(sql);
		if(s!= null && s.size() > 0){
			return s.get(0);
		}
		return null;
	}
	
	protected String getCellText(String val)
	{
		if (StringUtils.isEmpty(val) || Double.valueOf(val.trim()) == 0)
		{
			return "&nbsp;";
		}
		return val;
	}

}
