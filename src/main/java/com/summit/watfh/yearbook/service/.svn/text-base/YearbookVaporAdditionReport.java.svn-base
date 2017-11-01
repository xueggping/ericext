package com.summit.watfh.yearbook.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;

import freemarker.template.utility.StringUtil;


@Service
@Transactional
public class YearbookVaporAdditionReport extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Override
	public JSONObject getData(Yearbook yearbook) {
		JSONObject o = new JSONObject();
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		switch (yearbook.getBookType()) {
		case 1://1:代表（单站多年）2：代表（多站单年）
			int startYear = Integer.parseInt(yearbook.getStartTime());
			int endYear = Integer.parseInt(yearbook.getEndTime());
			for (int i = startYear; i <= endYear; i++) {
				dataList.add(getReport(yearbook.getStcd(), String.valueOf(i)));
			}
			break;
		case 2:
			String[] stcds = yearbook.getStcds().split(",");
			for (int i = 0; i < stcds.length; i++) {
				dataList.add(getReport(stcds[i], yearbook.getYear()));
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
		return "yearbook/VaporAdditionReport";
	}
	
	private JSONObject getReport(String stcd ,String year){
		stcd = "'"+stcd+"'";
		String stime = year + "-01-01";
		String etime = year + "-12-31";
		JSONObject res = new JSONObject();
		String sql = "exec UP_Get_DCWEAP_D_By_Stations ?, ?, ?";
		List<JSONObject> dataTable = ur.queryAllCustom(sql,stcd,stime,etime);
		String monthsql = "exec UP_Get_MTWEAP_E_By_Stations ?, ?, ?";
		List<JSONObject> month = ur.queryAllCustom(monthsql,stcd,stime,etime);
		String yearsql = "exec UP_Get_YRWEAP_F_By_Stations ?, ?, ?";
		List<JSONObject> yearly = ur.queryAllCustom(yearsql,stcd,stime,etime);
		String[][] data = new String[21][13]; 
		for (int i = 1; i < 13; i++) {
			for (JSONObject o : dataTable) {
				String PTBGDT_shang = year + "-" + CommonUtil.padWhitespaceLeft(String.valueOf(i+1),2).replace(" ", "0") + "-01 00:00:00";
				String PTBGDT_zhong = year + "-" + CommonUtil.padWhitespaceLeft(String.valueOf(i+1),2).replace(" ", "0") + "-11 00:00:00";
				String PTBGDT_xia = year + "-" + CommonUtil.padWhitespaceLeft(String.valueOf(i+1),2).replace(" ", "0") + "-21 00:00:00";
				if(PTBGDT_shang.equals(o.getString("PTBGDT")) && "1.5".equals(o.getString("OBHGT"))){
					data[1][i] = this.trans(o,"AVATMP","AVATMPRCD");
					data[6][i] = this.trans(o,"AVVP", "AVVPRCD");
					data[11][i] = this.trans(o,"AVVPD", "AVVPRCD");
					data[16][i] = this.trans(o,"AVWDV", "AVVPRCD");
				}
				if(PTBGDT_zhong.equals(o.getString("PTBGDT")) && "1.5".equals(o.getString("OBHGT"))){
					data[2][i] = this.trans(o,"AVATMP", "AVVPRCD");
					data[7][i] = this.trans(o,"AVVP", "AVVPRCD");
					data[12][i] = this.trans(o,"AVVPD", "AVVPRCD");
					data[17][i] = this.trans(o,"AVWDV", "AVVPRCD");				
				}
				if(PTBGDT_xia.equals(o.getString("PTBGDT")) && "1.5".equals(o.getString("OBHGT"))){
					data[3][i] = this.trans(o,"AVATMP", "AVVPRCD");
					data[8][i] = this.trans(o,"AVVP", "AVVPRCD");
					data[13][i] = this.trans(o,"AVVPD","AVVPRCD");
					data[18][i] = this.trans(o,"AVWDV", "AVVPRCD");
				}
			}
			for (JSONObject mo : month) {
				if(mo.getInt("MTH") == i){
					data[4][i] = this.trans(mo,"AVATMP", "AVVPRCD");
					data[9][i] = this.trans(mo,"AVVP", "AVVPRCD");
					data[14][i] = this.trans(mo,"AVVPD","AVVPRCD");
					data[19][i] = this.trans(mo,"AVWDV", "AVVPRCD");
				}
			}
			for (JSONObject yo : yearly) {
				data[5][i] = this.trans(yo,"AVATMP", "AVVPRCD");
				data[10][i] = this.trans(yo,"AVVP", "AVVPRCD");
				data[15][i] = this.trans(yo,"AVVPD","AVVPRCD");
				data[20][i] = this.trans(yo,"AVWDV", "AVVPRCD");
			}
		}
		
		res.put("data", data);
		res.put("year", year);
		res.put("stsc",this.getStsc(stcd));
		res.put("note",this.getNote(stcd,year));
		return res;
	}

	private String getNote(String stcd, String year) {
		String sql = "select NT from HY_DAEX_I where stcd="+stcd +"and yr='" + year +  "' and tbid='HY_DCWEAP_D'";
		List<JSONObject> re = ur.queryAllCustom(sql);
		if(re!=null && re.size() > 0){
			return re.get(0).getString("NT");
		}
		return null;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			System.out.println(CommonUtil.padWhitespaceLeft(String.valueOf(i+1),2).replace(" ", "0"));
		}
	}
	
	protected String trans(JSONObject o ,String val, String rcd)
	{
		val = o.containsKey(val)?o.getString(val):"";
		rcd = o.containsKey(rcd)?o.getString(rcd):"";
		if (StringUtils.isEmpty(val))
		{
			return "&nbsp;";
		}
		if (rcd == "k")
		{
			return "&nbsp;";
		}
		if ((Double.valueOf(val.trim()) == 0.0) && (rcd.trim() == "/" || rcd.trim() == "-"))
		{
			return "&nbsp;";
		}
		if (rcd.trim() == ")")
		{
			return String.format("({0})", val);
		}
		return val;
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
