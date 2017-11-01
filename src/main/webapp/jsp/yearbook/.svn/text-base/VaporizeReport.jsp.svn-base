<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../Base.jsp" flush="true"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>逐日水面蒸发量年报</title>

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
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/vaporizeReport.do&yearbook=${data.yearbook}&saveFileName=逐日水面蒸发量年报'); 
	}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/vaporizeReport.do&yearbook=${data.yearbook}&saveFileName=逐日水面蒸发量年报'); 
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

            <c:forEach items="${data.data}" var="item">
            <tr>
                <td bgcolor="white" valign="top" align="center" id="tdReport" runat="server">
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold;font-size:6.5pt;">
                        <thead>
                            <tr>
                                <td colspan="3" align="center" height="40">
                                    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
                                    	<span style="font-size: 16.5pt">${item.RVNM}&nbsp;${item.STNM}</span>站逐日水面蒸发量表
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left" valign="bottom" nowrap width="30%">
                                    年 份:${item.YEAR}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    测站编码:${item.STCD}
                                </td>
                                <td align="center" width="40%">蒸发器位置特征:陆上水面蒸发场&nbsp;&nbsp;&nbsp;&nbsp;蒸发器型式:${item.EETP}</td>
                                <td align="right" valign="bottom" nowrap>
                                    水面蒸发量&nbsp;mm &nbsp;
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="3" valign="top" id="printArea" runat="server" class="prt" enableviewstate="false">
                                    <table class="alltd" cellpadding="0" cellspacing="0" width="100%" style="font-weight: bold;font-size:6.5pt;border-collapse:collapse;border-top:solid 1px black;border-left:solid 1px black;border-bottom:solid 1px black;" border="0">
                                        <tr>
                                            <td align="center">
                                                日\月</td>
                                            <td align="center" width="7%">
                                                一 月</td>
                                            <td align="center" width="7%">
                                                二 月</td>
                                            <td align="center" width="7%">
                                                三 月</td>
                                            <td align="center" width="7%">
                                                四 月</td>
                                            <td align="center" width="7%">
                                                五 月</td>
                                            <td align="center" width="7%">
                                                六 月</td>
                                            <td align="center" width="7%">
                                                七 月</td>
                                            <td align="center" width="7%">
                                                八 月</td>
                                            <td align="center" width="7%">
                                                九 月</td>
                                            <td align="center" width="7%">
                                                十 月</td>
                                            <td align="center" width="7%">
                                                十一月</td>
                                            <td align="center" width="7%">
                                                十二月</td>
                                        </tr>
                                        <!-- 日降水量 -->
                                        <c:forEach begin="1" end="31" var="i">
	                                        <tr>
	                                        <c:forEach begin="1" end="13" var="j">
	                                        	<c:choose>
		                                        	<c:when test="${j!=1}">
			                                        	<td style="text-align: right;" align="right">
			                                        		<c:if test="${item.tableData[i-1][j-2]!= 'null'}">${item.tableData[i-1][j-2]}</c:if>
			                                            </td>
		                                        	</c:when>
		                                        	<c:otherwise>
		                                        		<td align="center" style="height: 12px">${i}</td>
		                                        	</c:otherwise>
	                                        	</c:choose>
	                                        </c:forEach>
	                                        </tr>
                                        </c:forEach>
                                        
                                        <!-- 月降水量 --> 
                                        <c:forEach begin="32" end="34" var="k">
                                        	<tr class="othertd">
                                        	<c:forEach begin="1" end="13" var="w">
                                       			<c:choose>
                                       				<c:when test="${k==32 && w==1}">
                                           				<td align="center">水面蒸发量</td>
                                       				</c:when>
                                       				<c:when test="${k==33 && w==1}">
                                       					<td align="center">最大</td>
                                       				</c:when>
                                       				<c:when test="${k==34 && w==1}">
                                       					<td align="center"> 最小</td>
                                       				</c:when>
                                       				<c:otherwise>
                                        				<td>
                                        					<c:if test="${item.tableData[k-1][w-2]!= 'null'}">${item.tableData[k-1][w-2]}</c:if>
			                                            </td>
                                       				</c:otherwise>
                                       			</c:choose>
                                        	</c:forEach>
                                        	</tr>
                                        </c:forEach>
                                        
                                        
                                        <tr class="othertd">
                                            <td rowspan="2" align="center">年 统 计</td>
                                            <td colspan="2">水面蒸发量</td>
                                            <td colspan="2">
                                            	<c:if test="${item.WSFE != 'null'}">
                                            		${item.WSFE}
                                            	</c:if>
                                            </td>
                                            <td colspan="2">最大日水面蒸发量</td>
                                            <td colspan="2">
                                            	<c:if test="${item.MXDYE != 'null'}">
                                            		${item.MXDYE}
                                            	</c:if>
                                            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                            <c:if test="${item.MXDYEODT != 'null'}">
	                                            	${item.MXDYEODT}
	                                            </c:if>
                                            </td>
                                            <td colspan="2">最小日水面蒸发量</td>
                                            <td colspan="2">
                                            	<c:if test="${item.MNDYE != 'null'}">
	                                            	${item.MNDYE}
	                                            </c:if>
                                           		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                               	<c:if test="${item.MNDYEODT != 'null'}">
	                                            	${item.MNDYEODT}
	                                            </c:if>
                                            </td>
                                        </tr>
                                        <tr class="othertd">
                                            <td colspan="2" align="center">冰终</td>
                                            <td colspan="4">
                                           		<c:if test="${item.IDSDT != 'null'}">
	                                            	${item.IDSDT}
	                                            </c:if>
                                            </td>
                                            <td colspan="2" align="center">初冰</td>
                                            <td colspan="4">
                                            	<c:if test="${item.ICAPD != 'null'}">
	                                            	${item.ICAPD}
	                                            </c:if>
                                            </td>
                                        </tr>
                                        <tr class="othertd">
                                            <td align="center">附 &nbsp; &nbsp; &nbsp; &nbsp; 注</td>
                                            <td colspan="12" style="text-align: left">
                                            	<c:if test="${item.NT != 'null'}">
	                                            	${item.NT}
	                                            </c:if>
                                            </td>
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
