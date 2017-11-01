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
    <title>实测冰流量成果年报</title>
    <style media="print">
        .Noprint { DISPLAY: none }
        thead {display:table-header-group;}
        .nextpage { page-break-after: always;}
        rotated { size: landscape } 
        .prt table
        {
            cellPadding:2px;
            cellSpacing:0px;
            border:1;
            border-collapse:collapse;
            background-color:black;
        }
        .prt td,th
        {
            background-color:white;
            align:center;
        }
    </style>
    <style>
        body {
            margin-left: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background-color: #EDF4FD;
            SCROLLBAR-FACE-COLOR: #8AB9E8; FONT-SIZE: 9pt; BACKGROUND-ATTACHMENT: fixed; SCROLLBAR-HIGHLIGHT-COLOR: #fef7f7; SCROLLBAR-SHADOW-COLOR: #fef7f7; SCROLLBAR-3DLIGHT-COLOR: #035aa1; SCROLLBAR-ARROW-COLOR: #ffffff; SCROLLBAR-TRACK-COLOR: #F7F3F7; SCROLLBAR-DARKSHADOW-COLOR: #F7F3F7
        }
        
      .button.gray
        {
            color: black;
			text-shadow:1px 1px 1px #fff;
			border:1px solid #dce1e6;
			box-shadow: 0 1px 2px #fff inset,0 -1px 0 #a8abae inset;
			background: -webkit-linear-gradient(top,#f2f3f7,#e4e8ec);
			background: -moz-linear-gradient(top,#f2f3f7,#e4e8ec);
			background: linear-gradient(top,#f2f3f7,#e4e8ec);
        }
        
        .gray:hover{
			background: -webkit-linear-gradient(top,#fefefe,#ebeced);
			background: -moz-linear-gradient(top,#f2f3f7,#ebeced);
			background: linear-gradient(top,#f2f3f7,#ebeced);
		}
        
        .centertd td { text-align:center; } 
        
        .toolbar {
            font-size: 10pt;
            color:white;
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
<body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0" onload="page_load()"
    onselectstart="selectstart()">
    <form id="form1" runat="server">
        <input type="hidden" id="lastBreakRows" />
        <table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" class="toolbar">
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
                <td bgcolor="white" valign="top" align="center" id="tdReport" runat="server">
                <c:forEach items="${data.data}" var="item">
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="100%" border="0"
                        style="font-weight: bold;font-size:6.5pt;">
                        <thead>
                            <tr>
                                <td align="center" height="30">
                                    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
                                        实 测 冰 流 量 成 果 表
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: left; height: 26px;" valign="bottom" nowrap colspan="20">
                                   <span style="font-size:10px;">年份:${item[0].YEAR} &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;测站编码:${item[0].STCD}</span>
                                </td>
                            </tr>
                        </thead>
                        
                        <tbody>
                            <tr>
                                <td valign="top" id="printArea" runat="server" class="prt">
                                    <table class="centertd" bordercolordark="white" bordercolorlight="black" cellpadding="0"
                                        cellspacing="0" width="100%" style="font-weight: bold; font-size: 6.5pt;" border="1">
                                        <thead>
                                            <tr>
                                                <td rowspan="3">
                                                    施<br />
                                                    测<br />
                                                    号<br />
                                                    数
                                                </td>
                                                <td colspan="4">
                                                    施 测 时 间
                                                </td>
                                                <td rowspan="3">
                                                    断 面<br />
                                                    位 置
                                                </td>
                                                <td rowspan="3">
                                                    测验方法
                                                </td>
                                                <td rowspan="3">
                                                    水位<br />
                                                    （m）
                                                </td>
                                                <td rowspan="3">
                                                    冰流量<br />
                                                    （m3/s）
                                                </td>
                                                <td colspan="3">
                                                    平 均
                                                </td>
                                                <td rowspan="3">
                                                    敞露<br />
                                                    水面宽<br />
                                                    （m）
                                                </td>
                                                <td rowspan="3">
                                                    冰花<br />
                                                    密度<br />
                                                    （t/m3）
                                                </td>
                                                <td rowspan="3">
                                                    冰花<br />
                                                    折算<br />
                                                    系数
                                                </td>
                                                <td rowspan="3">
                                                    附 注
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">
                                                    月</td>
                                                <td rowspan="2">
                                                    日</td>
                                                <td>
                                                    起</td>
                                                <td>
                                                    止</td>
                                                <td rowspan="2">
                                                    疏密度</td>
                                                <td rowspan="2">
                                                    冰厚或<br />
                                                    冰花厚<br />
                                                    m）</td>
                                                <td rowspan="2">
                                                    冰速<br />
                                                    <br />
                                                    （m/s）</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    时分</td>
                                                <td>
                                                    时分</td>
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
												     	 <span style="font-size:10px;">${info.DAY}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.STIME}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.ETIME}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSLC}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.MSQMT}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.Z}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.IQ}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVICCM}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVICTK}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVICV}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.OPWTWD}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.XSAVFSDS}</span>
													 </td>
													  <td style="border-top:0px black solid;border-bottom:0px black solid;">
												     	 <span style="font-size:10px;">${info.FSK}</span>
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
