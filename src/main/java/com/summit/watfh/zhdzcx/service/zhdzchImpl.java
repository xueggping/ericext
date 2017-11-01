package com.summit.watfh.zhdzcx.service;


import org.springframework.stereotype.Service;

@Service
public class zhdzchImpl implements zhdzcxInter {

	@Override
	public String getSql(Integer type,String stcd,String times) {
		StringBuffer sql = new StringBuffer();
		String[] ts = times.split(",");
		for (int i = 0; i < ts.length; i++) {
			if(i>1){
				sql.append(" UNION ALL ");
			}
			switch (type) {
			case 1://降水量
					sql.append("SELECT STCD, STNM, RVNM, DT, P, PRCD, MXP, MXPDR, MXPRC FROM V_HY_DP_C " +
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 2://水位
					sql.append("SELECT STCD, STNM, RVNM, DT, AVZ, AVZRCD, s1, s15, s30, s90, s180, s270, s365 FROM V_HY_DZ_C " +
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 3://流量
					sql.append("SELECT STCD, STNM, RVNM, DT, AVQ, AVQRCD FROM V_HY_DQ_C " + 
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 4://蒸发量
					sql.append("SELECT STCD, STNM, RVNM, DT,EETP,WSFE,WSFERCD FROM V_HY_DWE_C" + 
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 5://气温
					sql.append("SELECT STCD, STNM, RVNM, DT,OBHGT,ATMP,ATMPRCD FROM V_HY_DAT_C" + 
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 6://含沙量
					sql.append("SELECT STCD, STNM, RVNM, DT, AVCS, AVCSRCD FROM V_HY_DCS_C " + 
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			case 7://输沙率
					sql.append("SELECT STCD, STNM, RVNM, DT, AVQS, SDTP FROM V_HY_DQS_C" +
							" WHERE STCD = '"+stcd+"' and DT >= '"+ts[i].substring(0,ts[i].indexOf(" - "))+"' and DT <= '"+ts[i].substring(ts[i].lastIndexOf(" - ")+3)+"' ");
				break;
			default:
				break;
			}
		}
		return sql.toString();
	}




}
