<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="../Base.jsp" flush="true" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head id="Head1" runat="server">
		<base href="<%=basePath%>">
		<title>实测流量成果年报</title>
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
	font-size: 10pt; <%--
	color: white;
	--%>
}
</style>
<script language="javascript">
function printit(){ 
　　if (confirm('确定打印吗？')) { 
　　    window.print();
　　} 
 } 
function hbtnExportExcel_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/flowQuantity.do&yearbook=${data.yearbook}&saveFileName=实测流量成果年鉴'); 
}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/flowQuantity.do&yearbook=${data.yearbook}&saveFileName=实测流量成果年鉴'); 
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
	<!--  <body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0" onload="page_load()" onselectstart="selectstart()">-->
	<body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0">
		<form id="form1" runat="server">
			<input type="hidden" id="lastBreakRows" />
			<table cellpadding="0" cellspacing="0" width="100%" height="100%"
				border="0" class="toolbar">
				<tr class="noprint" bordercolordark="white" bordercolorlight="#666666" bgcolor="white">
	                <td align="left" valign="middle" style="height: 40px">
	                    &nbsp;<input type="button" class="button gray" onclick="printit()" value=" 打 印 " style="width: 80px" />
	                    <input type="button" class="button gray" id="hbtnExportExcel" value=" 导出Excel " style="width: 80px"
	                        onclick="hbtnExportExcel_ServerClick()" runat="server" />
	                    <input type="button" class="button gray" id="hbtnExportWord" value=" 导出Word " style="width: 80px"
	                        onclick="hbtnExportWord_ServerClick()" runat="server" />
	                    <input type="button" class="button gray" onclick="closeWindow()" value=" 关 闭 " style="width: 80px" />
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
											style="font-weight: bold; text-align: center; font-size: 16.5pt;">
											实 测 流 量 成 果 表
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
													<td rowspan="3">
														施
														<br />
														测
														<br />
														号
														<br />
														数
													</td>
													<td colspan="4">
														施测时间
													</td>
													<td rowspan="3">
														断&nbsp; 面
														<br />
														位&nbsp; 置
													</td>
													<td rowspan="3">
														测 验 方 法
													</td>
													<td rowspan="3">
														基本水
														<br />
														尺水位
														<br />
														（m）
													</td>
													<td rowspan="3">
														流量
														<br />
														（m
														<sup>
															3
														</sup>
														/s）
													</td>
													<td rowspan="3">
														断面面积
														<br />
														（m
														<sup>
															2
														</sup>
														）
													</td>
													<td colspan="2" rowspan="2">
														流速
														<br />
														(m/s)
													</td>
													<td rowspan="3">
														水
														<br />
														面
														<br />
														宽
														<br />
														(m)
													</td>
													<td colspan="2" rowspan="2">
														水 深
														<br />
														（m）
													</td>
													<td rowspan="3">
														水面比降
														<br />
														（10
														<sup>
															-4
														</sup>
														）
													</td>
													<td rowspan="3">
														糙
														<br />
														率
													</td>
													<td rowspan="3">
														附&nbsp; 注
													</td>
												</tr>
												<tr>
													<td rowspan="2">
														月
													</td>
													<td rowspan="2">
														日
													</td>
													<td colspan="1">
														起
													</td>
													<td colspan="1">
														止
													</td>
												</tr>
												<tr>
													<td>
														时分
													</td>
													<td>
														时分
													</td>
													<td>
														平均
													</td>
													<td>
														最大
													</td>
													<td>
														平均
													</td>
													<td>
														最大
													</td>
												</tr>
												<c:forEach items="${data.data}" var="item">
												  <tr>
													 <td colspan="18" align="center" style="height: 18px;line-height:36px;">${item[0].RVNM} &nbsp;&nbsp;${item[0].STNM}</td>
												  </tr>
												  <tr>
													 <td style="text-align: left; height: 24px; border:1px black solid;" valign="bottom" nowrap colspan="18">
													   <span style="font-size:10px;">年份:${item[0].YEAR} &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;测站编码:${item[0].STCD}</span>
													 </td>
												  </tr>
												  <c:forEach items="${item}" var="info">
												  <tr>
												  	 <td style="border-top:0px black solid;border-bottom:0px black solid;height:18px;">
											     	 <span style="font-size:10px;">${info.QOBNO}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.month}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.day}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.stime}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.etime}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSQLC}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.MSQMT}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.BSGGZ}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.Q}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSA}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVV}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSMXV}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.TPWD}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVDP}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSMXDP}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.RVSFSL}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.N}</span>
													 </td>
													 <td style="border-top:0px black solid;border-bottom:0px black solid;">
													    &nbsp;
													 </td>
												  </tr>
												 	</c:forEach>
												</c:forEach>
											</thead>
											<tbody>
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
