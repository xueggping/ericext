<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../Base.jsp" flush="true"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>冰厚及冰情要素摘录</title>
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
        .centertd td { text-align:center;} 
        
        .cnttd td { 
            text-align:center; 
            border:0;
        }
        
        .toolbar {
            font-size: 10pt;
            color:white;
        }
    </style>

    <script language="javascript"> 
	    function printit() 
	　　    { 
	　　        if (confirm('确定打印吗？')) { 
	　　            window.print();
	　　        } 
	　　    } 
	　　    function hbtnExportExcel_ServerClick(){
			window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/iceAdditionReport.do&yearbook=${data.yearbook}&saveFileName=冰厚及冰情要素摘录'); 
		}
		function hbtnExportWord_ServerClick(){
			window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/iceAdditionReport.do&yearbook=${data.yearbook}&saveFileName=冰厚及冰情要素摘录'); 
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
            <c:forEach items="${data.dataList}" var="item" varStatus="status">
            <tr>
                <td bgcolor="white" valign="top" align="center" id="tdReport" runat="server">
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%"  style="font-weight: bold; font-size: 6.5pt;">
                        <thead>
                            <tr>
                                <td align="center" height="30">
                                    <div id="printTitle" style="margin-top:15px;font-weight: bold; text-align: center; font-size: 16pt;">
                                       	 冰厚及冰情要素摘录表
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: left; height: 26px;" valign="bottom">
                                    	年 份:${item.year}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测站编号:${item.stsc.STCD}
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td valign="top" id="printArea" runat="server" class="prt">
                                    <table class="centertd" bordercolordark="white" bordercolorlight="black" enableviewstate="false"
                                        cellpadding="0" cellspacing="0" width="100%" style="color:black;font-weight: bold;font-size:6.5pt;" border="1">
                                        <thead>
                                            <tr>
                                                <td colspan="3"> 日 期</td>
                                                <td rowspan="2">冰情</td>
                                                <td rowspan="2"> 冰厚<br />
                                                    (m)</td>
                                                <td rowspan="2">冰上雪深<br />
                                                    (m)</td>
                                                <td rowspan="2">岸上气温<br />
                                                    (℃)</td>
                                                <td rowspan="2">水 位<br />
                                                    (m)</td>
                                                <td colspan="3">日 期</td>
                                                <td rowspan="2">冰情</td>
                                                <td rowspan="2"> 冰厚<br />
                                                    (m)</td>
                                                <td rowspan="2">冰上雪深<br />
                                                    (m)</td>
                                                <td rowspan="2">岸上气温<br />
                                                    (℃)</td>
                                                <td rowspan="2">水 位<br />
                                                    (m)</td>
                                            </tr>
                                            <tr>
                                                <td> 月</td>
                                                <td> 日</td>
                                                <td>时分</td>
                                                <td> 月</td>
                                                <td> 日</td>
                                                <td>时分</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="i" begin="0" end="${item.rows}">
										    	<tr class="cnttd"> 
												    <c:forEach var="j" begin="0" end="15">
														  <c:choose>
															<c:when test="${item.data[i][j] != 'null'}">
															  <td style="color:black;border-left: black 1px solid;border-right: black 1px solid;">${item.data[i][j]}</td>
															</c:when>
															<c:otherwise>
															  <td style="color:black;border-left: black 1px solid;border-right: black 1px solid;"></td>
															</c:otherwise>
														  </c:choose>
										            </c:forEach>
								           		</tr>
										 	</c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
			</c:forEach>
        </table>
    </form>
</body>
</html>
