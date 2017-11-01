package com.summit.common.portData.service;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.summit.common.portData.util.Column;
import com.summit.common.portData.util.ExcelImportConfig;

public class ExportData {
	private XSSFCellStyle head_Style;
	private SXSSFWorkbook workbook;
	// 当前sheet
	private SXSSFSheet sheet;
	private SXSSFRow row = null;// 创建一行
	private SXSSFCell cell = null;
	private int currentRow = 0;
	private XSSFCellStyle date_Style;
	private XSSFCellStyle time_Style;
	private XSSFCellStyle string_style;
	/**
	 * 构造函数初始化参数
	 * 
	 * @param out
	 * @param title
	 * @param headers
	 * @param sheeatName
	 */
	public ExportData() {
		try {
			workbook = new SXSSFWorkbook();
			this.head_Style = (XSSFCellStyle) this.workbook.createCellStyle();
			head_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			head_Style.setFillForegroundColor(IndexedColors.AQUA.getIndex());//第一行第一列
//			head_Style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			head_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			
			XSSFFont head_font = (XSSFFont) workbook.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
//			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 11);
			this.head_Style.setFont(head_font);// 单元格样式使用字体

			XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();

			XSSFFont data_font = (XSSFFont) workbook.createFont();
			data_font.setFontName("宋体");// 设置头部字体为宋体
			data_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			data_font.setFontHeightInPoints((short) 20); // 字体高度  
			
			
			this.date_Style = (XSSFCellStyle) this.workbook.createCellStyle();
			date_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			date_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			date_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			date_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			date_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			date_Style.setFont(head_font);// 单元格样式使用字体
			date_Style.setDataFormat(format.getFormat("yyyy-m-d"));

			this.time_Style = (XSSFCellStyle) this.workbook.createCellStyle();//第一行
			time_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			time_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			time_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			time_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			time_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			time_Style.setFont(data_font);// 单元格样式使用字体
			time_Style.setDataFormat(format.getFormat("yyyy-m-d h:mm:s"));

			this.string_style = (XSSFCellStyle) this.workbook.createCellStyle();//第一行
			string_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			string_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			string_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			string_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			string_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			string_style.setFont(data_font);// 单元格样式使用字体

//			createSheet(sheeatName, headers);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}


	/**
	 * 创建表头
	 * @param el 
	 * 
	 * @param sheetName
	 * @param headers
	 */
	/*private void createSheet(String sheetName, String headers[][]) {
		sheet = (SXSSFSheet) workbook.createSheet(sheetName);
		row = (SXSSFRow) sheet.createRow(currentRow);
		for (int i = 0; i < headers.length; i++) {
			cell = (SXSSFCell) row.createCell(i);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(headers[i][0]);
			cell.setCellStyle(head_Style);
		}
		currentRow++;
	}
*/
	 /* 
	  * Region region5 = new Region(0, (short)4, 0, (short)6);
    * 设定合并单元格区域范围 
    *  firstRow  0-based 
    *  lastRow   0-based 
    *  firstCol  0-based 
    *  lastCol   0-based 
    */
	public void createSheet(ExcelImportConfig el, XSSFSheet sheet2) {
		sheet = (SXSSFSheet) workbook.createSheet();
		List<Column> list = el.getData().getColumn();
		int startLine = el.getData().getFromLine();
		for(int i=0;i<startLine;i++){//行
			Row rowold = sheet2.getRow(i);
			row = (SXSSFRow) sheet.createRow(i);
			for (int i1 = 0; i1 < list.size(); i1++) {//列
				cell = (SXSSFCell) row.createCell(i1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(rowold.getCell(i1).getStringCellValue());
				cell.setCellStyle(rowold.getCell(i1).getCellStyle());
				if(rowold.getCell(i1).getStringCellValue().length()<=0 && i1==list.size()-1 ){
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, i1));
				}
			}
			
		}
	}
	/**
	 * 导出excel
	 * 
	 * @param listRows
	 * @param el 
	 * @param mp 
	 * @param sheet 
	 * @throws ParseException
	 */
	public  void PoiWriteExcel_To2007(List<JSONObject> listRows,
			OutputStream out, ExcelImportConfig el, Map<Integer, Column> mp) throws ParseException {
		currentRow = el.getData().getFromLine();
		for (int i = 0; i < listRows.size(); i++) {
			row = (SXSSFRow) sheet.createRow(currentRow);
			JSONObject t= (JSONObject) listRows.get(i);
//			JSONArray ListCells = JSONArray.fromObject(t);  
			for (int j = 0; j < t.size(); j++) {
				//赋值
				Column column = mp.get(j);
				if(column != null){
					String property = column.getProperty();
					Object value;
					try {
						value = t.get(property);//fMethod.invoke(t);
						cell = (SXSSFCell) row.createCell(j);
						if(column.getType() != null && column.getType()> 0 && value!=null){
							cell.setCellValue(value.toString());
						}else{
							cell.setCellValue("");
						}
//						cell.setCellStyle(string_style);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			currentRow++;
		}
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
	public  void PoiWriteExcel_To2007(List listRows,
			OutputStream out) throws ParseException {
		for (int i = 0; i < listRows.size(); i++) {
			row = (SXSSFRow) sheet.createRow(currentRow);
			ArrayList ListCells = (ArrayList) listRows.get(i);
			for (int j = 0; j < ListCells.size(); j++) {
				Object obj = ListCells.get(j);
				cell = (SXSSFCell) row.createCell(j);
				cell.setCellValue((String) obj);
				cell.setCellStyle(string_style);
			}
			currentRow++;
		}
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 测试导出
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 *//*
	public static void main(String[] args) throws IOException, ParseException {
		String headers[][] = { { "日期", "DATE" }, { "标题", "TIME" },
				{ "其他", null } };
		File file = new File("D://test.xlsx");
		if (file.exists())
			file.delete();
		file.createNewFile();

		ExportData exportData = new ExportData("test", headers, "test");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-m-d h:mm:s");
		ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
		for (int i = 0; i < 1048575; i++) {
			ArrayList<String> cellList = new ArrayList<String>();
			for (int j = 0; j < 3; j++) {
				cellList.add(sdf.format(new Date()));
			}
			data.add(cellList);
		}
		OutputStream out = new FileOutputStream(file);
		exportData.PoiWriteExcel_To2007(data, out);
	}*/
}