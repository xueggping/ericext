package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;


@Service
@Transactional
public class YearbookFloodReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Override
	public JSONObject getData(Yearbook yearbook) {
		String sql = "exec UP_Get_FDHEEX_B_By_Stations_Year ?, ?";
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
		return "yearbook/FloodReport";
	}
	
	private JSONObject getReport(String sql,String stcd ,String year){
		stcd = "'"+stcd+"'";
		JSONObject res = new JSONObject();
		List<JSONObject> jx = ur.queryAllCustom(sql,stcd,year);
		double num2 = (double)(jx.size() / 4);
		int num3 = (int)num2;
		if (jx.size() % 4 > 0)
		{
			num3++;
		}
		res.put("rows", num3);
		String[][] data = new String[num3][20]; 
		int num4 = 0;
		int num5 = 0;
		String b = "";
		String b2 = "";
		String text2 = "";
		for (int k = 0; k < jx.size(); k++)
		{
			if (k % num3 == 0 && k != 0)
			{
				num4 = 0;
				num5++;
			}
			int num6 = 0;
			num4++;
			String text3 = String.valueOf(Integer.parseInt(CommonUtil.padWhitespaceLeft(jx.get(k).getString("TM").substring(5,7), 2)));
			String text4 = String.valueOf(Integer.parseInt(CommonUtil.padWhitespaceLeft(jx.get(k).getString("TM").substring(8,10), 2)));;
			String text5 = String.format("%.2f",jx.get(k).getDouble("Z"));
			if (k == 0)
			{
				b = text3;
				b2 = text4;
				text2 = text5;
			}
			else
			{
				if (text2.equals(text5.trim()))
				{
					text5 = "&nbsp;";
				}
				else
				{
					String[] array = text2.split("\\.");
					text2 = text5;
					if (text5.startsWith(array[0]))
					{
						text5 = text5.replace(String.format("%s.", array[0]), "");
						text5 = CommonUtil.padWhitespaceLeft(text5, text5.length() + 1 + array[0].length() + 1);
						text5 = text5.replace(" ", "&nbsp;");
					}
				}
				if (b.equals(text3.trim()))
				{
					text3 = "&nbsp;";
				}
				else
				{
					b = text3;
				}
				if (b2.equals(text4.trim()))
				{
					text4 = "&nbsp;";
				}
				else
				{
					b2 = text4;
				}
			}
			String text6 = jx.get(k).getString("TM").substring(11,16).replace(":00", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			if (text6.startsWith("0"))
			{
				text6 = "&nbsp;" + text6.substring(1);
			}
			data[num4-1][num5 * 5 + num6++] = text3;
			data[num4-1][num5 * 5 + num6++] = text4;
			data[num4-1][num5 * 5 + num6++] = text6;
			data[num4-1][num5 * 5 + num6++] = text5;
			String[] array2 = String.valueOf(jx.get(k).getDouble("Q")).split("\\.");
			array2[0] = CommonUtil.padWhitespaceLeft(array2[0], 5).replace(" ", "&nbsp; ");
			String arg = "";
			String text7 = "";
			if (array2.length == 1)
			{
				text7 = text7 + array2[0] + "&nbsp;&nbsp; &nbsp; &nbsp; ";
			}
			else
			{
				arg = CommonUtil.padWhitespaceRight(array2[1], 3).replace(" ", "&nbsp;");
				text7 += String.format("%s.%s", array2[0], arg);
			}
			data[num4-1][num5 * 5 + num6++] = text7;
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

}
