<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../Base.jsp" flush="true"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>逐日平均水位年报</title>
    <style>
        body {
            margin-left: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background-color: #EDF4FD;
            SCROLLBAR-FACE-COLOR: #8AB9E8; FONT-SIZE: 9pt; BACKGROUND-ATTACHMENT: fixed; SCROLLBAR-HIGHLIGHT-COLOR: #fef7f7; SCROLLBAR-SHADOW-COLOR: #fef7f7; SCROLLBAR-3DLIGHT-COLOR: #035aa1; SCROLLBAR-ARROW-COLOR: #ffffff; SCROLLBAR-TRACK-COLOR: #F7F3F7; SCROLLBAR-DARKSHADOW-COLOR: #F7F3F7
        }
        
        .button
        {
            border-right: #2C59AA 1px solid;
            padding-right: 2px;
            border-top: #2C59AA 1px solid;
            padding-left: 2px;
            font-size: 14px;
            filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5);
            border-left: #2C59AA 1px solid;
            cursor: hand;
            color: black;
            padding-top: 2px;
            border-bottom: #2C59AA 1px solid;
        }
        
        .alltd td { 
            text-align:center; 
            border-right:solid 1px black;
        }
        
        .othertd td  { 
            text-align:center; 
            border-right:solid 1px black;
            border-top:solid 1px black;
        }
       
        .toolbar {
            font-size: 10pt;
/*             color:white; */
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
        
    </style>
    <script language="javascript"> 
　　    function printit() 
　　    { 
　　        if (confirm('确定打印吗？')) { 
　　            window.print();
　　        } 
　　    } 
　　    function hbtnExportExcel_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/waterLevelReport.do&yearbook=${data.yearbook}&saveFileName=逐日平均水位年报'); 
	}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/waterLevelReport.do&yearbook=${data.yearbook}&saveFileName=逐日平均水位年报'); 
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

            <c:forEach items="${data.data}" varStatus="status" var="item">
            <tr>
                <td bgcolor="white" valign="top" align="center" id="tdReport" runat="server">
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold; font-size:6pt;">
                        <thead>
                            <tr>
                                <td colspan="3" align="center" height="30">
                                    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
                                            <span id="lblRVNM">
                                            	<c:if test="${item.RVNM!='null'}">
                                            ${item.RVNM}</c:if></span><span id="lblSTNM">${item.STNM}</span> 逐日平均水位表
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left" valign="bottom" nowrap>
                                    年 份:<span id="lblYEAR">${item.YEAR }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            测站编码:<span id="lblSTCD">${item.STCD}</span>
                                </td>
                                <td align="right" valign="bottom" nowrap>
                                    表内水位(测站基面以上米数)+<asp:Label ID="lblGC" runat="server"></asp:Label>m=假定基面以上米数
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="3" valign="top" id="printArea" runat="server" class="prt">
                                    <table class="alltd" cellpadding="0" cellspacing="0" width="100%" style="font-weight: bold;font-size:6.5pt;border-collapse:collapse;border-top:solid 1px black;border-left:solid 1px black;border-bottom:solid 1px black;" border="0">
                                        <tr>
                                            <td align="center">
                                                日\月</td>
                                            <td align="center" colspan="3">
                                                一 月</td>
                                            <td align="center" colspan="3">
                                                二 月</td>
                                            <td align="center" colspan="3">
                                                三 月</td>
                                            <td align="center" colspan="3">
                                                四 月</td>
                                            <td align="center" colspan="3">
                                                五 月</td>
                                            <td align="center" colspan="3">
                                                六 月</td>
                                            <td align="center" colspan="3">
                                                七 月</td>
                                            <td align="center" colspan="3">
                                                八 月</td>
                                            <td align="center" colspan="3">
                                                九 月</td>
                                            <td align="center" colspan="3">
                                                十 月</td>
                                            <td align="center" colspan="3">
                                                十一月</td>
                                            <td align="center" colspan="3">
                                                十二月</td>
                                        </tr>
                        <c:forEach var="y" begin="1" end="31">
                        	<tr>
                        	
                        	<c:forEach var="x" begin="1" end="13">
                        		<c:choose>
                        			<c:when test="${x!=1}">
                        				<td colspan="3">
	                        				<c:if test="${item.tableData[y-1][x-2]!='null' }">
	                        					${item.tableData[y-1][x-2]}
	                        				</c:if>
                        				</td>
                        			</c:when>
                        			<c:otherwise>
                        				<td align="center">${y}</td>
                        			</c:otherwise>
                        		</c:choose>
                        	</c:forEach>
                        	
                        	</tr>
                        </c:forEach>
                        
                        <c:forEach var="y" begin="32" end="36">
                        	<tr class="othertd">
                        		<c:forEach var="x" begin="1" end="13">
                        			<c:choose>
                        				<c:when test="${y==32 && x==1}">
                        					<td align="center">
                                                	平&nbsp; 均</td>
                        				</c:when>
                        				<c:when test="${y==33 && x==1}">
                        					<td align="center">
                                                	最&nbsp; 高</td>
                        				</c:when>
                        				<c:when test="${y==34 && x==1}">
                        					<td align="center">
                                                	日&nbsp; 期</td>
                        				</c:when>
                        				<c:when test="${y==35 && x==1}">
                        					<td align="center">
                                                	最&nbsp; 低</td>
                        				</c:when>
                        				<c:when test="${y==36 && x==1}">
                        					<td align="center">
                                                	日&nbsp; 期</td>
                        				</c:when>
                        				<c:otherwise>
                        					<td colspan="3">${item.tableData[y-1][x-2]}</td>
                        				</c:otherwise>
                        			</c:choose>
                        		</c:forEach>
                        	</tr>
                        </c:forEach>
                          
                                        <tr class="othertd">
                                            <td align="center">
                                                年统计</td>
                                            <td colspan="3" nowrap="noWrap">
                                                最高水位
                                            </td>
                                            <td colspan="6" style="border-right:none;">
                                                ${item.HTZ}
                                            </td>
                                            <td colspan="3">
                                                ${item.HTZDT}
                                            </td>
                                            <td colspan="3" nowrap="noWrap">
                                                最低水位
                                            </td>
                                            <td colspan="6" style="border-right:none;">
                                                ${item.MNZ}
                                            </td>
                                            <td colspan="3">
                                                ${item.MNZDT}
                                            </td>
                                            <td colspan="3">
                                                平均水位
                                            </td>
                                            <td colspan="9">
                                                ${item.AVZ}
                                            </td>
                                        </tr>
                                        <tr class="othertd">
                                            <td>
                                                各种保证率水位</td>
                                            <td colspan="2">
                                                最高
                                            </td>
                                            <td colspan="3">
                                            	${item.S1 }
                                            </td>
                                            <td colspan="2" nowrap="noWrap">
                                                第15天
                                            </td>
                                            <td colspan="3">
                                                ${item.S15 }
                                            </td>
                                            <td colspan="2" nowrap="noWrap">
                                                第30天
                                            </td>
                                            <td colspan="3">
                                                ${item.S30 }
                                            </td>
                                            <td colspan="2" nowrap="noWrap">
                                                第90天
                                            </td>
                                            <td colspan="3">
                                                ${item.S90 }
                                            </td>
                                            <td colspan="2" nowrap="noWrap">
                                                第180天
                                            </td>
                                            <td colspan="3">
                                                ${item.S180 }
                                            </td>
                                            <td colspan="2" nowrap="noWrap">
                                                第270天
                                            </td>
                                            <td colspan="3">
                                                ${item.S270 }
                                            </td>
                                            <td colspan="2">
                                                最低</td>
                                            <td colspan="4">
                                            	${item.S365 }
                                            </td>
                                        </tr>
                                        <tr class="othertd">
                                            <td align="center" height="20">
                                                附 &nbsp; &nbsp; &nbsp; &nbsp; 注</td>
                                            <td colspan="36" style="text-align: left">${item.NT}</td>
                                        </tr>
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