<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="../Base.jsp" flush="true" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head id="Head1" runat="server">
		<title>实测大断面成果年报</title>
		<style media="print">
.Noprint {
	DISPLAY: none
}

thead {
	display: table-header-group;
}

.nextpage {
	page-break-after: always;
}

rotated {
	size: landscape
}

.prt table {
	cellPadding: 2px;
	cellSpacing: 0px;
	border: 1;
	border-collapse: collapse;
	background-color: black;
}

.prt td,th {
	background-color: white;
	align: center;
}
</style>
		<style>
body {
	margin-left: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #EDF4FD;
	SCROLLBAR-FACE-COLOR: #8AB9E8;
	FONT-SIZE: 9pt;
	BACKGROUND-ATTACHMENT: fixed;
	SCROLLBAR-HIGHLIGHT-COLOR: #fef7f7;
	SCROLLBAR-SHADOW-COLOR: #fef7f7;
	SCROLLBAR-3DLIGHT-COLOR: #035aa1;
	SCROLLBAR-ARROW-COLOR: #ffffff;
	SCROLLBAR-TRACK-COLOR: #F7F3F7;
	SCROLLBAR-DARKSHADOW-COLOR: #F7F3F7
}

.button.gray {
	color: black;
	text-shadow: 1px 1px 1px #fff;
	border: 1px solid #dce1e6;
	box-shadow: 0 1px 2px #fff inset, 0 -1px 0 #a8abae inset;
	background: -webkit-linear-gradient(top, #f2f3f7, #e4e8ec);
	background: -moz-linear-gradient(top, #f2f3f7, #e4e8ec);
	background: linear-gradient(top, #f2f3f7, #e4e8ec);
}

.gray:hover {
	background: -webkit-linear-gradient(top, #fefefe, #ebeced);
	background: -moz-linear-gradient(top, #f2f3f7, #ebeced);
	background: linear-gradient(top, #f2f3f7, #ebeced);
}

.centertd td {
	text-align: center;
}

.cnttd td {
	text-align: center;
	border-bottom: 0;
}

.toolbar {
	font-size: 10pt;
	color: white;
}
</style>

<script language="javascript">
function printit(){ 
　　if (confirm('确定打印吗？')) { 
　　    window.print();
　　} 
 } 
function hbtnExportExcel_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/bigsectionreport.do&yearbook=${data.yearbook}&saveFileName=实测大断面成果年鉴'); 
}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/bigsectionreport.do&yearbook=${data.yearbook}&saveFileName=实测大断面成果年鉴'); 
}
function closeWindow() {
　　    window.opener = null;
　　    window.close();
}		
　　    
function selectstart() {
        event.returnValue=false;
        return false;
}
　　    
function page_load() {
　　    window.focus();
        window.resizeTo(screen.availWidth,screen.availHeight); 
}
</script>

	</head>
	<body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0"
		onload="page_load()" onselectstart="selectstart()">
		<form id="form1" runat="server">
			<input type="hidden" id="lastBreakRows" />
			<table cellpadding="0" cellspacing="0" width="100%" height="100%"
				border="0" class="toolbar">
				<tr class="noprint" bordercolordark="white"
					bordercolorlight="#666666" bgcolor="white">
					<td align="left" valign="middle" style="height: 40px">
						&nbsp;
						<input type="button" class="button gray" onclick="printit()"
							value=" 打 印 " style="width: 80px" />
						<input type="button" class="button gray" id="hbtnExportExcel"
							value=" 导出Excel " style="width: 80px"
							onclick="hbtnExportExcel_ServerClick()" runat="server" />
						<input type="button" class="button gray" id="hbtnExportWord"
							value=" 导出Word " style="width: 80px"
							onclick="hbtnExportWord_ServerClick()" runat="server" />
						<input type="button" class="button gray" onclick="closeWindow()"
							value=" 关 闭 " style="width: 80px" />
					</td>
				</tr>
				<tr>
					<td bgcolor="white" valign="top" align="center" id="tdReport"
						runat="server">
						<table id="panel" runat="server" cellpadding="0" cellspacing="0"
							width="98%" border="0" style="font-weight: bold;">
							<thead>
								<tr>
									<td align="center" height="40">
										<div id="printTitle"
											style="font-weight: bold; text-align: center; font-size: 16pt;">
											实 测 大 断 面 成 果 表
										</div>
									</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td valign="top" id="printArea" runat="server" class="prt">
										<table class="centertd" bordercolordark="white"
											bordercolorlight="black" cellpadding="0" cellspacing="0"
											width="100%" style="font-weight: bold; font-size: 6.5pt;"
											border="1">
											<thead>
												<tr>
													<td height="80px">
														垂
														<br />
														<br />
														线
														<br />
														<br />
														号
													</td>
													<td>
														起点距
														<br />
														<br />
														（m）
													</td>
													<td>
														河底高程
														<br />
														<br />
														（m）
													</td>
													<td>
														垂
														<br />
														<br />
														线
														<br />
														<br />
														号
													</td>
													<td>
														起点距
														<br />
														<br />
														（m）
													</td>
													<td>
														河底高程
														<br />
														<br />
														（m）
													</td>
													<td>
														垂
														<br />
														<br />
														线
														<br />
														<br />
														号
													</td>
													<td>
														起点距
														<br />
														<br />
														（m）
													</td>
													<td>
														河底高程
														<br />
														<br />
														（m）
													</td>
													<td>
														垂
														<br />
														<br />
														线
														<br />
														<br />
														号
													</td>
													<td>
														起点距
														<br />
														<br />
														（m）
													</td>
													<td>
														河底高程
														<br />
														<br />
														（m）
													</td>
													<td>
														垂
														<br />
														<br />
														线
														<br />
														<br />
														号
													</td>
													<td>
														起点距
														<br />
														<br />
														（m）
													</td>
													<td>
														河底高程
														<br />
														<br />
														（m）
													</td>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${data.data}" var="item">
													<tr>
														<td colspan="15" align="center" height="36px"
															valign="middle" style="font-size: 10pt; border: 0;">
															${item[0][0][0].YEAR}年&nbsp;&nbsp;${item[0][0][0].RVNM}&nbsp;&nbsp;${item[0][0][0].STNM}
														</td>
													</tr>
													<c:forEach items="${item}" var="data">
														<c:forEach items="${data}" var="yeardata">
															<c:if test="${yeardata[0].change==true}">
																<tr>
																	<td colspan="3" style="border: 0px; text-align: left;"
																		height="18px" valign="bottom">
																		实测日期:${yeardata[0].DATE}
																	</td>
																	<td colspan="9"
																		style="border: 0px; text-align: center;"
																		valign="bottom">
																		断面名称及位置:${yeardata[0].XSNMLC}
																	</td>
																	<td colspan="3" style="border: 0px; text-align: right;"
																		valign="bottom">
																		测时水位:${yeardata[0].OBDRZ}m
																	</td>
																</tr>
															</c:if>
														</c:forEach>
														<tr>
															<td colspan="3" width="20%" style="border: 0px;">
																<table width="100%"
																	style="font-weight: bold; font-size: 6.5pt;" border="1"
																	cellspacing="0" border frame="void">
																	<c:forEach items="${data[0]}" var="cols1">
																		<tr>
																			<td width="14%" style="border: 0px; height: 18px;">
																				${cols1.VTNO}
																			</td>
																			<td width="38%"
																				style="border-top: 0px; border-bottom: 0px;">
																				${cols1.DI}
																			</td>
																			<td
																				style="border-top: 0px; border-bottom: 0px; border-left: 0px;">
																				${cols1.RVBDEL}
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
															<td colspan="3" style="border: 0px;">
																<table width="100%"
																	style="font-weight: bold; font-size: 6.5pt;" border="1"
																	cellspacing="0" border frame="void">
																	<c:forEach items="${data[1]}" var="cols2">
																		<tr>
																			<td width="14%" style="border: 0px; height: 18px;">
																				${cols2.VTNO}
																			</td>
																			<td width="38%"
																				style="border-bottom: 0px; border-top: 0px;">
																				${cols2.DI}
																			</td>
																			<td
																				style="border-top: 0px; border-bottom: 0px; border-left: 0px;">
																				${cols2.RVBDEL}
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
															<td colspan="3" style="border: 0px;">
																<table width="100%"
																	style="font-weight: bold; font-size: 6.5pt;" border="1"
																	cellspacing="0" border frame="void">
																	<c:forEach items="${data[2]}" var="cols3">
																		<tr>
																			<td width="14%" style="border: 0px; height: 18px;">
																				${cols3.VTNO}
																			</td>
																			<td width="38%"
																				style="border-bottom: 0px; border-top: 0px;">
																				${cols3.DI}
																			</td>
																			<td
																				style="border-top: 0px; border-bottom: 0px; border-left: 0px;">
																				${cols3.RVBDEL}
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
															<td colspan="3" style="border: 0px;">
																<table width="100%"
																	style="font-weight: bold; font-size: 6.5pt;" border="1"
																	cellspacing="0" border frame="void">
																	<c:forEach items="${data[3]}" var="cols4">
																		<tr>
																			<td width="14%" style="border: 0px; height: 18px;">
																				${cols4.VTNO}
																			</td>
																			<td width="38%"
																				style="border-bottom: 0px; border-top: 0px;">
																				${cols4.DI}
																			</td>
																			<td
																				style="border-top: 0px; border-bottom: 0px; border-left: 0px;">
																				${cols4.RVBDEL}
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
															<td colspan="3" valign="top" style="border: 0px;">
																<table width="100%"
																	style="font-weight: bold; font-size: 6.5pt;" border="1"
																	cellspacing="0" border frame="void">
																	<c:forEach items="${data[4]}" var="cols5">
																		<tr>
																			<td width="14%" style="border: 0px; height: 18px;">
																				${cols5.VTNO}
																			</td>
																			<td width="38%"
																				style="border-bottom: 0px; border-top: 0px;">
																				${cols5.DI}
																			</td>
																			<td style="border: 0px;">
																				${cols5.RVBDEL}
																			</td>
																		</tr>
																	</c:forEach>
																</table>
															</td>
														</tr>
													</c:forEach>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>