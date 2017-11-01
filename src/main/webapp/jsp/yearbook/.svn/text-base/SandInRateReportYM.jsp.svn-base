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
 <title>悬移质输沙率月年统计表</title>
<base href="<%=basePath%>">
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
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/sandInRateReportYM.do&yearbook=${data.yearbook}&saveFileName=逐日平均悬移质输沙率(月年)'); 
	}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/sandInRateReportYM.do&yearbook=${data.yearbook}&saveFileName=逐日平均悬移质输沙率(月年)'); 
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
<body bottommargin="0" leftmargin="0" rightmargin="0" topmargin="0" onload="page_load()" onselectstart="selectstart()">
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
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%" border="0"
                        style="font-weight: bold;font-size:6.5pt;">
                        <thead>
                            <tr>
                                <td colspan="7" align="center" height="40">
                                    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
                                            <asp:Label ID="lblRVNM" runat="server">${item.stsc.RVNM}</asp:Label> 
                                            <asp:Label ID="lblSTNM" runat="server">${item.stsc.STNM}</asp:Label> 
                                            <asp:Label ID="lblYEAR" runat="server">${item.YEAR}</asp:Label>年 悬移质输沙率月年统计表
                                    </div>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td valign="top" id="printArea" runat="server" class="prt" colspan="7">
                                    <table class="alltd" cellpadding="0" cellspacing="0" width="100%" style="font-weight: bold;
                                        font-size: 6.5pt; border-collapse: collapse; border-top: solid 1px black; border-left: solid 1px black;
                                        border-bottom: solid 1px black;color:black;" border="0">
                                        <tr>
                                            <td align="center" style="width: 140px;">项 &nbsp; &nbsp; &nbsp; 目</td>
											<td align="center" width="7%">一 月</td>
						                    <td align="center" width="7%">二 月</td>
						                    <td align="center" width="7%">三 月</td>
						                    <td align="center" width="7%">四 月</td>
						                    <td align="center" width="7%">五 月</td>
						                    <td align="center" width="7%">六 月</td>
						                    <td align="center" width="7%">七 月</td>
						                    <td align="center" width="7%">八 月</td>
						                    <td align="center" width="7%">九 月</td>
						                    <td align="center" width="7%">十 月</td>
						                    <td align="center" width="7%">十一月</td>
						                    <td align="center" width="7%">十二月</td>
                                        </tr>
                                        <c:forEach var="k" begin="1" end="5">
                                        	<tr class="othertd">
                                        	<c:forEach var="w" begin="1" end="13">
                                        	<c:choose>
                                        			<c:when test="${k==1&&w == 1}"><!-- 第一行一列 -->
										              <td style="border-top: black 1px solid">上 &nbsp;&nbsp; &nbsp;旬</td>
												   </c:when>
												   <c:when test="${k==2&&w == 1}"><!-- 第二行一列 -->
										              <td>中 &nbsp; &nbsp; 旬</td>
												   </c:when>
												   <c:when test="${k==3&&w == 1}"><!-- 第三行一列 -->
										              <td>下 &nbsp; &nbsp; 旬</td>
												   </c:when>
												   <c:when test="${k==4&&w == 1}"><!-- 第四行一列 -->
										              <td>月平均</td>
												   </c:when>
												   <c:when test="${k==5&&w == 1}"><!-- 第五行一列 -->
										              <td>月最大</td>
												   </c:when>
												   <c:otherwise> 
									              		<td>
									              			<c:if test="${item.tableData[k-1][w-2]!='null' }">
														  		${item.tableData[k-1][w-2]}
															</c:if>
														</td>
											   		</c:otherwise>
											</c:choose>
                                        	</c:forEach>
                                        	</tr>
                                        </c:forEach>
                                        <tr class="othertd">
                                            <td align="center">年统计</td>
                                            <td colspan="12">
	                                                                                                                                    最大日平均输沙率 &nbsp;&nbsp;${item.znsw.MXDYQS}&nbsp;&nbsp;&nbsp;${item.MXDYQSODT}
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平均输沙率&nbsp;&nbsp; ${item.znsw.AVQS}公斤/秒
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输沙量 &nbsp;&nbsp;${item.znsw.SW}万吨
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;侵蚀模数&nbsp;&nbsp; ${item.znsw.SM}公吨/平方公里
                                            </td>
                                        </tr>
                                        <tr class="othertd">
                                            <td align="center">附 &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 注</td>
                                            <td colspan="12" style="text-align: left">${item.NT}</td>
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