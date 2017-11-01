package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;


@Service
@Transactional
public class YearbookIceAdditionReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String sql = "exec UP_Get_ICEX_B_By_Stations ?, ?, ?";
		JSONObject o = new JSONObject();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）2：代表（多站单年）
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			for (int i = startYear; i <= endYear; i++) {
				dataList.add(getReport(sql, yearbook.getStcd(), String.valueOf(i)));
			}
			break;
		case 2:
			String[] stcds = yearbook.getStcds().split(",");
			for (int i = 0; i < stcds.length; i++) {
				dataList.add(getReport(sql, stcds[i], yearbook.getYear()));
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
		return "yearbook/IceAdditionReport";
	}
	
	private JSONObject getReport(String sql,String stcd ,String year){
		stcd = "'"+stcd+"'";
		String stime = year + "-01-01";
		String etime = year + "-12-31";
		JSONObject res = new JSONObject();
		List<JSONObject> jx = ur.queryAllCustom(sql,stcd,stime,etime);
		double num = (double)(jx.size() / 2);
		int num2 = (int)num;
		if (jx.size() % 2 > 0)
		{
			num2++;
		}
		res.put("rows", num2);
		String[][] data = new String[num2][16]; 
		int num3 = 0;
		String text = "";
		String text2 = "";
		String a = "";
		for (int k = 0; k < jx.size(); k++)
		{
			if (k == num2)
			{
				num3 = 0;
			}
			int index;
			if (k >= num2)
			{
				index = 8;
			}
			else
			{
				index = 0;
			}
			String dateTime = jx.get(k).getString("TM");
			String text3 = String.valueOf(Integer.parseInt(dateTime.substring(5,7)));
			String text4 = String.valueOf(Integer.parseInt(dateTime.substring(8,10)));
			if (num3 == 0)
			{
				text = "";
				text2 = "";
			}
			if (text.trim().equals(text3.trim()))
			{
				text3 = "&nbsp;";
			}
			else
			{
				text = text3;
			}
			if (text2.trim().equals(text4.trim()))
			{
				text4 = "&nbsp;";
			}
			else
			{
				text2 = text4;
			}
			data[num3][index++] = text3;
			data[num3][index++] = text4;
			data[num3][index++] = dateTime.substring(11,13);
			data[num3][index++] = jx.get(k).containsKey("ICRCD")?StringUtils.isEmpty(jx.get(k).getString("ICRCD")) ? "&nbsp;" : jx.get(k).getString("ICRCD"):"&nbsp;";
			data[num3][index++] = jx.get(k).containsKey("RVCITHK")?"0".equals(jx.get(k).getString("RVCITHK")) || StringUtils.isEmpty(jx.get(k).getString("RVCITHK")) ? "&nbsp;" : jx.get(k).getString("RVCITHK"):"&nbsp;";
			data[num3][index++] = jx.get(k).containsKey("OISNDP")?"0".equals(jx.get(k).getString("OISNDP")) || StringUtils.isEmpty(jx.get(k).getString("OISNDP")) ? "&nbsp;" : jx.get(k).getString("OISNDP"):"&nbsp;";
			data[num3][index++] = jx.get(k).containsKey("BKAIRTMP")?"0".equals(jx.get(k).getString("BKAIRTMP")) || StringUtils.isEmpty(jx.get(k).getString("BKAIRTMP")) ? "&nbsp;" : String.format("%.1f",jx.get(k).getDouble("BKAIRTMP")):"&nbsp;";
			String text5 = jx.get(k).containsKey("Z")?StringUtils.isEmpty(jx.get(k).getString("Z")) ? "0" : String.format("%.2f",jx.get(k).getDouble("Z")):"0";
			String[] array = text5.split("\\.");
			if (num3 > 0)
			{
				if (a.equals(array[0]))
				{
					text5 = "." + array[1];
				}
				else
				{
					a = array[0];
				}
				if (Double.valueOf(text5.replace("'", "0")) == 0)
				{
					a = StringUtils.EMPTY;
				}
			}
			else if (Double.valueOf(text5) != 0)
			{
				a = array[0];
			}
			text5 = "0".equals(text5.trim())?jx.get(k).containsKey("ZRCD")?StringUtils.isEmpty(jx.get(k).getString("ZRCD").trim())?text5:"&nbsp;":text5:text5;
			data[num3][index] = snapNumber(text5).replace("'", "&nbsp; ");
			num3++;
		}
		res.put("data", data);
		res.put("year", year);
		res.put("stsc",this.getStsc(stcd));
		return res;
	}

	private JSONObject getStsc(String stcd) {
		String sql = "select * from HY_STSC_A where stcd = " + stcd;
		List<JSONObject> s = ur.queryAllCustom(sql);
		if(s!= null && s.size() > 0){
			return s.get(0);
		}
		return null;
	}
	
	public static String snapNumber(String val)
	{
		String[] qa = val.split("\\.");
		qa[0] = CommonUtil.padWhitespaceLeft(qa[0],5).replace(" ", "&nbsp; ");
		String qa2 = StringUtils.EMPTY;
		if (qa.length == 1)
		{
			val = qa[0] + "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ";
		}
		else
		{
			char a = qa[1].charAt(qa[1].length() - 1);
			int num;
			if (a >= '0' && a <= '9')
			{
				num = 5;
			}
			else
			{
				num = 4;
			}
			if (qa[1].length() > 1)
			{
				char b = qa[1].charAt(qa[1].length() - 2);
				if (b < '0' || b > '9')
				{
					num = 3;
				}
			}
			qa2 = CommonUtil.padWhitespaceRight(qa[1],num).replace(" ", "&nbsp; ");
			val = String.format("%s.%s", qa[0], qa2);
		}
		return val;
	}

}
