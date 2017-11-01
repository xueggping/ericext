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
    <title>水面蒸发量辅助项统计年报</title>
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
        
         .alltd td { 
            text-align:center; 
            border-right:solid 1px black;
        }
        
        .othertd td  { 
            text-align:center; 
            border-right:solid 1px black;
            border-top:solid 1px black;
        }
        
        .titletd  { 
            text-align:center; 
            border-right:solid 1px black;
            border-bottom:solid 1px black;
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
			window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/vaporAdditionReport.do&yearbook=${data.yearbook}&saveFileName=水面蒸发量辅助项目月年统计'); 
		}
		function hbtnExportWord_ServerClick(){
			window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/vaporAdditionReport.do&yearbook=${data.yearbook}&saveFileName=水面蒸发量辅助项目月年统计'); 
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
                    <table id="panel" runat="server" cellpadding="0" cellspacing="0" width="98%" border="0"
                        style="font-weight: bold; font-size: 6.5pt;">
                        <thead>
                            <tr>
                                <td colspan="2" align="center">
                                    <div id="printTitle" style="margin-top:15px;font-weight: bold; text-align: center; font-size: 16pt;">
                                       		 ${item.stsc.RVNM}&nbsp;&nbsp;${item.stsc.STNM}&nbsp;&nbsp;水面蒸发量辅助项目月年统计表
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left" valign="bottom" nowrap>
                                   	 年 份:${item.year}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测站编码:${item.stsc.STCD} 
                                </td>
                                <td align="right" valign="bottom" nowrap>
                                    &nbsp;
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="3" valign="top" id="printArea" runat="server" class="prt">
                                    <table class="alltd" cellpadding="0" cellspacing="0" width="100%" style="color:black;font-weight: bold;
                                        font-size: 6.5pt; border-collapse: collapse; border-top: solid 1px black; border-left: solid 1px black;
                                        border-bottom: solid 1px black;" border="0">
                                        <tr>
                                            <td align="center" colspan="3" class="titletd">  项目\月份</td>
                                            <td align="center" width="6.5%">   一 月</td>
                                            <td align="center" width="6.5%">   二 月</td>
                                            <td align="center" width="6.5%">   三 月</td>
                                            <td align="center" width="6.5%">   四 月</td>
                                            <td align="center" width="6.5%">   五 月</td>
                                            <td align="center" width="6.5%">   六 月</td>
                                            <td align="center" width="6.5%">   七 月</td>
                                            <td align="center" width="6.5%">   八 月</td>
                                            <td align="center" width="6.5%">   九 月</td>
                                            <td align="center" width="6.5%">   十 月</td>
                                            <td align="center" width="6.5%">   十一月</td>
                                            <td align="center" width="6.5%">   十二月</td>
                                        </tr>
                                        <c:forEach var="i" begin="1" end="20">
                                        	<tr>
	                                        	<c:forEach var="j" begin="1" end="15">
	                                        		<c:if test="${ j== 1}">
		                                        		<c:if test="${i == 1}">
														   <td align="center" rowspan="5" class="titletd" style="color:black;">
				                                                1.5m&nbsp; 高<br />
				                                                <br />
				                                                	处的气温<br />
				                                                <br />
				                                                (℃)
				                                            </td>
														</c:if>
														<c:if test="${i == 6}">
														  <td align="center" rowspan="5" class="titletd" style="color:black;">
				                                                1.5m高处<br />
				                                                <br />
				                                                	的水汽压<br />
				                                                <br />
				                                                (10<sup>2</sup>Pa)
				                                            </td>
														</c:if>
														<c:if test="${i == 11}">
														   <td align="center" rowspan="5" class="titletd" style="color:black;">
				                                                	水面和1.5m<br />
				                                                <br />
				                                                	高处的水<br />
				                                                <br />
				                                                	汽压力差<br />
				                                                <br />
				                                                (102Pa)
				                                            </td>
														</c:if>
														<c:if test="${i == 16}">
														   <td align="center" rowspan="5" class="titletd" style="color:black;">
				                                                1.5m &nbsp;高<br />
				                                                <br />
				                                               	 处的风速<br />
				                                                <br />
			                                                (m/s)</td>
														</c:if>
													</c:if>
													<c:if test="${j == 2}">
														<c:if test="${(i==1||i==6||i == 11||i==16)}">
															<td rowspan="3" class="titletd" style="color:black;">
	                                               			 	旬平均
	                                            			</td>
														</c:if>
														<c:if test="${(i==1||i==6||i == 11||i==16)}">
															<td class="titletd" style="color:black;">
				                                              	  上
				                                            </td>
														</c:if>
														<c:if test="${(i==2||i==7||i == 12||i==17)}">
															<td class="titletd" style="color:black;">
				                                              	 中
				                                            </td>
														</c:if>
														<c:if test="${(i==3||i==8||i == 13||i==18)}">
															<td class="titletd" style="color:black;">
				                                              	  下
				                                            </td>
														</c:if>
														<c:if test="${(i==4||i==9||i == 14||i==19)}">
															<td colspan="2" class="titletd" style="color:black;">
				                                               	 月平均
				                                            </td>
														</c:if>
														<c:if test="${(i==5||i==10||i == 15||i==20)}">
															<td colspan="2" class="titletd" style="color:black;">
				                                               	 年平均
				                                            </td>
														</c:if>
													</c:if>
													<c:if test="${j > 3}">
														<td style="color:black;">
															<c:if test="${item.data[i][j-3] != 'null'}">
				                                                ${item.data[i][j-3]}
															</c:if>
			                                            </td>
													</c:if>
	                                        	</c:forEach>
                                        	</tr>
                                        </c:forEach>
                                        <tr>
                                            <td align="center" height="40" style="border-bottom: solid 1px black;">
                                                	附 &nbsp; &nbsp; &nbsp; &nbsp; 注</td>
                                            <td colspan="14" style="color:black;border-bottom: solid 1px black; border-top: solid 1px black;text-align: left;" align="left">
                                                &nbsp;${item.note}</td>
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