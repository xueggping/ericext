package com.summit.watfh.yearbook.web;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.summit.watfh.yearbook.bean.Yearbook;
import com.summit.watfh.yearbook.service.YearbookAbstractServiceInter;



@Controller
@RequestMapping("yearbook")
public class YearbookController {
	
	
	@Autowired
	private YearbookAbstractServiceInter yearbookFloodReport;
	
	@Autowired
	private YearbookAbstractServiceInter yearbookFlowQuantity;
	@Autowired
	private YearbookAbstractServiceInter yearbookBigSectionReport;
	@Autowired
	private YearbookAbstractServiceInter realSandInRateReport;
	@Autowired
	private YearbookAbstractServiceInter realIceFlowReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookVaporAdditionReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookWaterTempReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookWaterTempReportYM;
	@Autowired
	private YearbookAbstractServiceInter yearbookDayRainReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookIceAdditionReport; 
	@Autowired 
	private YearbookAbstractServiceInter sandRateReport;
	@Autowired 
	private YearbookAbstractServiceInter sandInRateReport;
	@Autowired 
	private YearbookAbstractServiceInter sandInRateReportYM;
	@Autowired 
	private YearbookAbstractServiceInter yearbookAvgTempReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookIceReport;
	@Autowired
	private YearbookAbstractServiceInter  avgIceFlowReport;
	
	@Autowired
	private YearbookAbstractServiceInter yearbookVaporizeReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookVaporizeReportYM;
	@Autowired
	private YearbookAbstractServiceInter yearbookAvgFlowQuantityReport;
	@Autowired
	private YearbookAbstractServiceInter yearbookWaterLevelReport;
	
	/**
	 * 洪水水文要素摘录表
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("floodReport")  
    public ModelAndView floodReport(Yearbook yearbook,String yearbookStr) { 
        return yearbookFloodReport.getPage(yearbook, yearbookStr);
    }
	
	/**
	 * 实测流量成果
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("flowQuantity")  
    public ModelAndView getFlowQuantity(Yearbook yearbook,String yearbookStr){  
        return yearbookFlowQuantity.getPage(yearbook,yearbookStr);  
    }
	
	/**
	 * 实测大断面成果
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("bigsectionreport")  
    public ModelAndView bigSectionReport(Yearbook yearbook,String yearbookStr){  
        return yearbookBigSectionReport.getPage(yearbook,yearbookStr);  
    }
	
	/**
	 * 实测悬移质输沙率成果
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("realsandinratereport")
    public ModelAndView realSandInRateReport(Yearbook yearbook,String yearbookStr){  
        return realSandInRateReport.getPage(yearbook,yearbookStr);  
    }
	
	/**
	 * 实测冰流量成果
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("realiceflowreport")  
    public ModelAndView realIceFlowReport(Yearbook yearbook,String yearbookStr){  
        return realIceFlowReport.getPage(yearbook,yearbookStr);  
    }
	
	/**
	 * 逐日平均冰流量
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("avgIceFlowReport")  
    public ModelAndView avgIceFlowReport(Yearbook yearbook,String yearbookStr) {  
        return avgIceFlowReport.getPage(yearbook, yearbookStr);  
    }
	
	/**
	 * 逐日水温
	 * （日月）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("waterTempReport")  
    public ModelAndView waterTempReport(Yearbook yearbook,String yearbookStr) {  
        return yearbookWaterTempReport.getPage(yearbook, yearbookStr);  
    }
	/**
	 * 逐日水温
	 * （月年）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("waterTempReportYM")  
    public ModelAndView waterTempReportYM(Yearbook yearbook,String yearbookStr) {  
        return yearbookWaterTempReportYM.getPage(yearbook, yearbookStr);  
    }
	/**
	 * 逐日平均含沙量
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("sandRateReport")  
    public ModelAndView sandRateReport(Yearbook yearbook,String yearbookStr) {  
        return sandRateReport.getPage(yearbook, yearbookStr);  
    }
	/**
	 * 逐日平均悬移质输沙率
	 * （日月）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("sandInRateReport")  
    public ModelAndView sandInRateReport(Yearbook yearbook,String yearbookStr) {  
        return sandInRateReport.getPage(yearbook, yearbookStr);  
    }
	/**
	 * 逐日平均悬移质输沙率
	 * （月年）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("sandInRateReportYM")  
    public ModelAndView sandInRateReportYM(Yearbook yearbook,String yearbookStr) {  
        return sandInRateReportYM.getPage(yearbook, yearbookStr);  
    }
	
	/**
	 * 水面蒸发量辅助项统计年报
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("vaporAdditionReport")  
    public ModelAndView vaporAdditionReport(Yearbook yearbook,String yearbookStr) {  
        return yearbookVaporAdditionReport.getPage(yearbook, yearbookStr);  
    }
	
	/**
	 * 逐日平均水位
	 * @param stcds
	 * @param beginYear
	 * @param endYear
	 * @param type
	 * @return
	 */
	@RequestMapping("waterLevelReport")
	public ModelAndView waterLevelReport(Yearbook yearbook,String yearbookStr){
		return yearbookWaterLevelReport.getPage(yearbook, yearbookStr);
	}
	
	/**
	 * 逐日平均流量
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("avgFlowQuantityReport")
	public ModelAndView avgFlowReport(Yearbook yearbook,String yearbookStr){
		return yearbookAvgFlowQuantityReport.getPage(yearbook,yearbookStr);
	}
	
	/**
	 * 逐日降水量
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("dayRainReport")  
    public ModelAndView dayRainReport(Yearbook yearbook,String yearbookStr) {
        return yearbookDayRainReport.getPage(yearbook, yearbookStr);  
    }
	
	/**
	 * 冰厚及冰情要素摘录
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("iceAdditionReport") 
	public ModelAndView iceAdditionReport(Yearbook yearbook,String yearbookStr) {  
	       return yearbookIceAdditionReport.getPage(yearbook, yearbookStr);  
    }
	
	/**
	 * 逐日平均气温
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("avgTempReport")
	public ModelAndView avgTempReport(Yearbook yearbook,String yearbookStr){
		return yearbookAvgTempReport.getPage(yearbook, yearbookStr);
	}
	
	/**
	 * 冰清统计
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("iceReport")
	public ModelAndView iceReport(Yearbook yearbook,String yearbookStr){
		return yearbookIceReport.getPage(yearbook,yearbookStr);
	}
	
	/**
	 * 水面蒸发量（年月日）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("vaporizeReport")
	public ModelAndView vaporizeReport(Yearbook yearbook,String yearbookStr){
		return yearbookVaporizeReport.getPage(yearbook,yearbookStr);
	}
	
	/**
	 * 水面蒸发量（年月）
	 * @param yearbook
	 * @return
	 */
	@RequestMapping("vaporizeReportYM")
	public ModelAndView vaporizeReportYM(Yearbook yearbook,String yearbookStr){
		return yearbookVaporizeReportYM.getPage(yearbook, yearbookStr);
	}
	
	@RequestMapping("exportExcel")  
	@ResponseBody
    public void exportExcel(HttpServletResponse res,String url,String yearbook,String saveFileName) { 
		try {
			Document doc1 = Jsoup
			.connect(url)
			.data("yearbookStr", yearbook)
			.maxBodySize(1024*1024*10)
			.post();
			Elements tmp = doc1.select("table");
			Element tmpEl = tmp.get(0);
			Elements trs = tmpEl.select("tr");
			trs.get(0).remove();
			res.setContentType("application/vnd.ms-excel;");
//			res.setHeader("Content-disposition","attachment;filename="+ new String(saveFileName.getBytes("UTF-8"), "ISO8859-1") + ".xls");
			res.setHeader("Content-disposition","attachment;filename="+ saveFileName + ".xls");  
			
			OutputStream out = res.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.print(doc1.html().toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping("exportWord")
	@ResponseBody
    public void exportWord(HttpServletResponse res,String url,String yearbook,String saveFileName) { 
		try {
			Document doc1 = Jsoup
			.connect(url)
			.data("yearbookStr", yearbook)
			.maxBodySize(1024*1024*50)
			.post();
			Elements tmp = doc1.select("table");
			Element tmpEl = tmp.get(0);
			Elements trs = tmpEl.select("tr");
			trs.get(0).remove();
			res.setContentType("application/msword;");
//			res.setHeader("Content-disposition","attachment;filename="+ new String(saveFileName.getBytes("UTF-8"), "ISO8859-1") + ".doc");  
			res.setHeader("Content-disposition","attachment;filename="+ saveFileName + ".doc");  
			OutputStream out = res.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.print(doc1.html().toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}