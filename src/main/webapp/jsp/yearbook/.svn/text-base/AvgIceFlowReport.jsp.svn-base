<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../Base.jsp" flush="true"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>逐日平均冰流量年报</title>
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
        
        .centertd td { text-align:center; } 
        
        .frame td { border-right:solid 1px black; border-bottom:solid 1px black; }
               
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
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/avgIceFlowReport.do&yearbook=${data.yearbook}&saveFileName=逐日平均冰流量'); 
	}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/avgIceFlowReport.do&yearbook=${data.yearbook}&saveFileName=逐日平均冰流量'); 
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
            <c:forEach items="${data.dataList}" varStatus="status" var="item">
            <tr>
                <td bgcolor="white" valign="top" align="center" id="tdReport" runat="server">
                      <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold;font-size:6.5pt;">
                        <thead>
                            <tr>
                                <td align="center" height="30">
                                    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">逐日平均冰流量表</div>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: right;" valign="bottom"> 单位:m<sup>3</sup>/s
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td valign="top" id="printArea" runat="server" class="prt">
                                    <table border="0" class="centertd" cellpadding="0" cellspacing="0" width="100%" style="font-weight: bold;font-size:6.5pt;color:black;
                                        border: solid 1px black;">
                                        <thead class="frame">
                                            <tr class="frame">
                                                <td colspan="2"> 日 期</td>
                                                <td rowspan="2"> 冰流量</td>
                                                <td colspan="2"> 日 期</td>
                                                <td rowspan="2"> 冰流量</td>
                                                <td colspan="2"> 日 期</td>
                                                <td rowspan="2"> 冰流量</td>
                                                <td colspan="2"> 日 期</td>
                                                <td rowspan="2"> 冰流量</td>
                                                <td colspan="2"> 日 期</td>
                                                <td rowspan="2" style="border-right:0;">冰流量</td>
                                            </tr>
                                            <tr class="frame">
                                                <td>月</td>
                                                <td>日</td>
                                                <td>月</td>
                                                <td>日</td>
                                                <td>月</td>
                                                <td>日</td>
                                                <td>月</td>
                                                <td>日</td>
                                                <td>月</td>
                                                <td>日</td>
                                            </tr>
                                        </thead>
                                        <tbody
                                            <tr style="font-size:10pt;">
                                                <td style="height: 26px">
                                                </td>
                                                <td colspan="2">${item.year}年</td>
                                                <td colspan="9">${item.stsc.RVNM}&nbsp;&nbsp;${item.stsc.STNM}</td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                            </tr>
                                            <c:forEach var="i" begin="0" end="${item.rows==0?13:item.rows}">
										    	<tr> 
												    <c:forEach var="j" begin="0" end="14">
														  <c:choose>
														  	<c:when test="${j==14}">
														  		<td style="border-right:0;">
															  		<c:if test="${item.data[i][j]!='null' }">
																			  ${item.data[i][j]}
																	</c:if> 
																</td>
															</c:when>
															<c:when test="${item.data[i][j] != 'null'}">
															  <td style="border-right: black 1px solid">${item.data[i][j]}</td>
															</c:when>
															<c:otherwise>
															  <td style="border-right: black 1px solid">&nbsp;</td>
															</c:otherwise>
														  </c:choose>
										            </c:forEach>
								           		</tr>
											</c:forEach>
                                            <tr>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" rowspan="3"> 最大冰流量</td>
                                                <td>春</td>
                                                <td colspan="2" align="left">${item.nbll.SMXIQ}</td>
                                                <td colspan="2">
<!--                                                	<c:if test="${item.nbll.SMXIQDT!=null }">-->
<!--													${fn:substring(item.nbll.SMXIQDT,5,7)}月${fn:substring(item.nbll.SMXIQDT,8,10)}日-->
<!--													</c:if>-->${item.SMXIQDT}
                                                </td>
                                                <td></td>
                                                <td colspan="2" rowspan="3"> 总冰流量</td>
                                                <td> 春</td>
                                                <td colspan="4" align="right">
                                                	<c:if test="${item.STIQ!=null }">
														${item.STIQ}x10<sup>4</sup>m<sup>3
													</c:if>
												</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td>
                                                </td>
                                                <td> 冬</td>
                                                <td colspan="4" align="right">
                                                	<c:if test="${item.WTIQ!=null }">
														${item.WTIQ}x10<sup>4</sup>m<sup>3
													</c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td> 冬</td>
                                                <td colspan="2">${item.nbll.WMXIQ}</td>
                                                <td colspan="2">${item.WMXIQD}</td>
                                                <td>
                                                </td>
                                                <td>全年</td>
                                                <td colspan="4" align="right">
                                                	<c:if test="${item.YTIQ!=null }">
														${item.YTIQ}x10<sup>4</sup>m<sup>3
													</c:if>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="15" style="text-align:left;height: 40px; border-top: solid 1px black;">&nbsp;</td>
                                            </tr>
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
