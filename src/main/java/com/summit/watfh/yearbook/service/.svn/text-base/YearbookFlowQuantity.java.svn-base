package com.summit.watfh.yearbook.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import com.summit.frame.repository.jdbc.UserRepository;
import com.summit.frame.util.SummitTools;
import com.summit.util.CommonUtil;
import com.summit.watfh.yearbook.bean.Yearbook;


/**   
*    
* 项目名称：watfh   
* 类名称：YearbookFlowQuantity   
* 类描述：实测流量成果年鉴
* 创建人：hhc  
* 创建时间：2017-8-11 下午03:57:09   
*    
*/
@Service
@Transactional
public class YearbookFlowQuantity extends YearbookAbstractService{

	@Autowired
	private UserRepository ur;
	
	@Autowired
	private SummitTools st;
	
	@Override
	public JSONObject getData(Yearbook yearbook){ 
		String stcd = "'"+yearbook.getStcd()+"'";
		String stcds ="";
		if(st.stringNotNull(yearbook.getStcds())){
			stcds = "'" + yearbook.getStcds().replace(",", "','") + "'";
		}
		String osmy_sql = "exec UP_Get_OBQ_G_By_Stations ?,?,?";
		String msoy_sql = "exec UP_Get_OBQ_G_By_Stations_Year ?,?";
		if(yearbook.getBookType() == null){
			try {
				throw new Exception("必须选择打印条件");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> x = new ArrayList<Map<String, Object>>();
		List<List<Map<String, Object>>> dataList = new ArrayList<List<Map<String, Object>>>();
		JSONObject json =new JSONObject();
		switch (yearbook.getBookType()) {
		case 1://单站多年
			//组织数据
			String endtm = yearbook.getEndTime().substring(0,4);
			String strtm = yearbook.getStartTime().substring(0,4);
			int difference = Integer.parseInt(endtm)-Integer.parseInt(strtm);
			String[] years = new String[difference+1];
			years[0] = strtm;
			for(int i=1;i<=difference;i++){
				years[i] = String.valueOf(Integer.parseInt(years[i-1])+1);
			}                                                                                                                                                                                                                                                                                                                                                                                
			x = ur.jdbcTemplate.queryForList(osmy_sql, stcd, yearbook.getStartTime(), yearbook.getEndTime());
			for(int i=0;i<years.length;i++){
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				List <String> mm = new ArrayList<String>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String dt = m.containsKey("MSQBGTM")?m.get("MSQBGTM")!=null?m.get("MSQBGTM").toString():"" : "";
					String year = dt.substring(0, 4);//年份
					if(year.equals(years[i])){
						String STCD = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						Float QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?Float.parseFloat(m.get("QOBNO").toString()):null : null;  //1
						String day = subDateforDay(dt);
						String month = subDateforMonth(dt);
						String stime = subDateforTime(dt);
						String  enddt = m.containsKey("MSQEDTM")?m.get("MSQEDTM")!=null?m.get("MSQEDTM").toString():"" : "";
						String etime = subDateforTime(enddt);
						String XSQLC = m.containsKey("XSQLC")?m.get("XSQLC")!=null?m.get("XSQLC").toString():"" : ""; //断面位置
						String MSQMT = m.containsKey("MSQMT")?m.get("MSQMT")!=null?m.get("MSQMT").toString():"" : ""; //测验方法
						String BSGGZ = m.containsKey("BSGGZ")?m.get("BSGGZ")!=null?m.get("BSGGZ").toString():"" : ""; //基本水尺水位
						BSGGZ = BSGGZ.substring(0,BSGGZ.indexOf(".")+3);
						String Q = m.containsKey("Q")?m.get("Q")!=null?m.get("Q").toString():"" : ""; //流量
						Q = Q.substring(0,Q.indexOf(".")+3);
						String XSA = m.containsKey("XSA")?m.get("XSA")!=null?m.get("XSA").toString():"" : ""; //断面面积
						XSA = XSA.substring(0,XSA.indexOf(".")+2);
						String XSAVV = m.containsKey("XSAVV")?m.get("XSAVV")!=null?m.get("XSAVV").toString():"" : ""; //平均流速
						XSAVV = XSAVV.substring(0,XSAVV.indexOf(".")+3);
						String XSMXV = m.containsKey("XSMXV")?m.get("XSMXV")!=null?m.get("XSMXV").toString():"" : ""; //最大流速
						XSMXV = XSMXV.substring(0,XSMXV.indexOf(".")+3);
						String TPWD = m.containsKey("TPWD")?m.get("TPWD")!=null?m.get("TPWD").toString():"" : ""; //水面宽
						TPWD = TPWD.substring(0,TPWD.indexOf(".")+2);
						String XSAVDP =m.containsKey("XSAVDP")?m.get("XSAVDP")!=null?m.get("XSAVDP").toString():"" : ""; //平均水深
						XSAVDP = XSAVDP.substring(0,XSAVDP.indexOf(".")+3);
						String XSMXDP = m.containsKey("XSMXDP")?m.get("XSMXDP")!=null?m.get("XSMXDP").toString():"" : ""; //最大水深
						XSMXDP = XSMXDP.substring(0,XSMXDP.indexOf(".")+3);
						String RVSFSL = m.containsKey("RVSFSL")?m.get("RVSFSL")!=null?m.get("RVSFSL").toString():"" : ""; //水面比降
						String N = m.containsKey("N")?m.get("N")!=null?m.get("N").toString():"" : ""; //糙率
						map.put("STCD", STCD);
						map.put("RVNM", RVNM); 
						map.put("STNM", STNM);
						map.put("YEAR", year);
						map.put("QOBNO", QOBNO);
						map.put("day", day);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("stime", stime);
						map.put("etime", etime);
						map.put("XSQLC", XSQLC);
						map.put("MSQMT", MSQMT);
						map.put("BSGGZ", BSGGZ);
						map.put("Q", Q);
						map.put("XSA", XSA);
						map.put("XSAVV", XSAVV);
						map.put("XSMXV", XSMXV);
						map.put("TPWD", TPWD);
						map.put("XSAVDP", XSAVDP);
						map.put("XSMXDP", XSMXDP);
						map.put("RVSFSL", RVSFSL);
						map.put("N", N);
						yeardata.add(map);
					}
				}
				if(!yeardata.isEmpty()){
					dataList.add(yeardata);
				}
			}
			break;
		case 2://多站单年
			String[] stcdarr = yearbook.getStcds().split(",");
			//组织数据
			x = ur.jdbcTemplate.queryForList(msoy_sql,stcds,yearbook.getYear());
			for(int i=0;i<stcdarr.length;i++){
				List<Map<String, Object>> yeardata = new ArrayList<Map<String, Object>>();
				List <String> mm = new ArrayList<String>();
				for (Map<String, Object> m : x) {
					Map<String, Object> map = new HashMap<String,Object>();
					String sd = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";
					if(sd.equals(stcdarr[i])){
						String STCD = m.containsKey("STCD")?m.get("STCD")!=null?m.get("STCD").toString():"" : "";
						String RVNM = m.containsKey("RVNM")?m.get("RVNM")!=null?m.get("RVNM").toString():"" : "";
						String STNM = m.containsKey("STNM")?m.get("STNM")!=null?m.get("STNM").toString():"" : "";
						Float QOBNO = m.containsKey("QOBNO")?m.get("QOBNO")!=null?Float.parseFloat(m.get("QOBNO").toString()):null : null;  //1
						String dt = m.containsKey("MSQBGTM")?m.get("MSQBGTM")!=null?m.get("MSQBGTM").toString():"" : "";
						String year = dt.substring(0, 4);//年份
						String day = subDateforDay(dt);
						String month = subDateforMonth(dt);
						String stime = subDateforTime(dt);
						String  enddt = m.containsKey("MSQEDTM")?m.get("MSQEDTM")!=null?m.get("MSQEDTM").toString():"" : "";
						String etime = subDateforTime(enddt);
						String XSQLC = m.containsKey("XSQLC")?m.get("XSQLC")!=null?m.get("XSQLC").toString():"" : ""; //断面位置
						String MSQMT = m.containsKey("MSQMT")?m.get("MSQMT")!=null?m.get("MSQMT").toString():"" : ""; //测验方法
						String BSGGZ = m.containsKey("BSGGZ")?m.get("BSGGZ")!=null?m.get("BSGGZ").toString():"" : ""; //基本水尺水位
						if(st.stringNotNull(BSGGZ)){
							BSGGZ = BSGGZ.substring(0,BSGGZ.indexOf(".")+3);
						}
						String Q = m.containsKey("Q")?m.get("Q")!=null?m.get("Q").toString():"" : ""; //流量
						if(st.stringNotNull(Q)){
							Q = Q.substring(0,Q.indexOf(".")+3);
						}
						String XSA = m.containsKey("XSA")?m.get("XSA")!=null?m.get("XSA").toString():"" : ""; //断面面积
						if(st.stringNotNull(XSA)){
							XSA = XSA.substring(0,XSA.indexOf(".")+2);
						}
						String XSAVV = m.containsKey("XSAVV")?m.get("XSAVV")!=null?m.get("XSAVV").toString():"" : ""; //平均流速
						if(st.stringNotNull(XSAVV)){
							XSAVV = XSAVV.substring(0,XSAVV.indexOf(".")+3);
						}
						String XSMXV = m.containsKey("XSMXV")?m.get("XSMXV")!=null?m.get("XSMXV").toString():"" : ""; //最大流速
						if(st.stringNotNull(XSMXV)){
							XSMXV = XSMXV.substring(0,XSMXV.indexOf(".")+3);
						}
						String TPWD = m.containsKey("TPWD")?m.get("TPWD")!=null?m.get("TPWD").toString():"" : ""; //水面宽
						if(st.stringNotNull(TPWD)){
							TPWD = TPWD.substring(0,TPWD.indexOf(".")+2);
						}
						String XSAVDP =m.containsKey("XSAVDP")?m.get("XSAVDP")!=null?m.get("XSAVDP").toString():"" : ""; //平均水深
						if(st.stringNotNull(XSAVDP)){
							XSAVDP = XSAVDP.substring(0,XSAVDP.indexOf(".")+3);
						}
						String XSMXDP = m.containsKey("XSMXDP")?m.get("XSMXDP")!=null?m.get("XSMXDP").toString():"" : ""; //最大水深
						if(st.stringNotNull(XSMXDP)){
							XSMXDP = XSMXDP.substring(0,XSMXDP.indexOf(".")+3);
						}
						String RVSFSL = m.containsKey("RVSFSL")?m.get("RVSFSL")!=null?m.get("RVSFSL").toString():"" : ""; //水面比降
						if(st.stringNotNull(RVSFSL)){
							RVSFSL = RVSFSL.substring(0,RVSFSL.indexOf(".")+2);
						}
						String N = m.containsKey("N")?m.get("N")!=null?m.get("N").toString():"" : ""; //糙率
						if(st.stringNotNull(N)){
							N = N.substring(0,N.indexOf(".")+4);
						}
						map.put("STCD", STCD);
						map.put("RVNM", RVNM); 
						map.put("STNM", STNM);
						map.put("YEAR", year);
						map.put("QOBNO", QOBNO);
						map.put("day", day);
						if(!mm.contains(month)){
							map.put("month", month);
							mm.add(month);
						}
						map.put("stime", stime);
						map.put("etime", etime);
						map.put("XSQLC", XSQLC);
						map.put("MSQMT", MSQMT);
						map.put("BSGGZ", BSGGZ);
						map.put("Q", Q);
						map.put("XSA", XSA);
						map.put("XSAVV", XSAVV);
						map.put("XSMXV", XSMXV);
						map.put("TPWD", TPWD);
						map.put("XSAVDP", XSAVDP);
						map.put("XSMXDP", XSMXDP);
						map.put("RVSFSL", RVSFSL);
						map.put("N", N);
						yeardata.add(map);
					}
				}
				if(!yeardata.isEmpty()){
					dataList.add(yeardata);
				}
			}
			break;
		default:
			break;
		}
		json.put("data", dataList);
		json.put("yearbook",JSONObject.fromObject(yearbook));
        return json;
	}
	
	@SuppressWarnings("unused")
	private static String subDateforDay(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d2 = Integer.parseInt(dateStr.substring(8, 10));
			return d2+"";
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	private static String subDateforMonth(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int d2 = Integer.parseInt(dateStr.substring(5, 7));
			return d2+"";
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	private static String subDateforTime(String dateStr){
		if(StringUtils.isNotBlank(dateStr) && dateStr.length()>=10){
			int hour = Integer.parseInt(dateStr.substring(11,13));
			String min =  dateStr.substring(14,16);
			return hour+":"+min;      
		}
		return "";
	}
	
	@Override
	public String getTemplate() {
		return "yearbook/FlowQuantity";
	}

}
