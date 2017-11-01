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
	<title>逐日平均流量</title>
	<style>
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
		window.open(getRootPath() + '/yearbook/exportExcel.do?url=' + getRootPath() + '/yearbook/avgFlowQuantityReport.do&yearbook="${data.yearbook}"&saveFileName=逐日平均流量年报'); 
	}
	function hbtnExportWord_ServerClick(){
		window.open(getRootPath() + '/yearbook/exportWord.do?url=' + getRootPath() + '/yearbook/avgFlowQuantityReport.do&yearbook="${data.yearbook}"&saveFileName=逐日平均流量年报'); 
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
<body>
	<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" class="toolbar">
            <tbody>
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
                <td id="tdReport" bgcolor="white" valign="top" align="center">
                <table id="panel" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold; font-size:6pt;">
					<tbody>
					<tr>
						<td colspan="3" align="center" height="30">
	                        <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
	                        	<span id="lblRVNM">${item.RVNM}</span><span id="lblSTNM">${item.STNM}</span> 逐日平均流量表
	                        </div>
                        </td>
					</tr>
					
					<tr>
						<td align="left" valign="bottom" nowrap="nowrap">
				                                    年 份:
                            <span id="lblYEAR">${item.YEAR }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            测站编码:<span id="lblSTCD">${item.STCD}</span>
                        </td>
						<td align="right" valign="bottom" nowrap="nowrap">
                                    集水面积：<span id="lblArea"></span>km<sup>2</sup>&nbsp;&nbsp;
                                    流量&nbsp;m<sup>3</sup>/s &nbsp;
                                </td>
					</tr>
					
	<tr>
		<td id="printArea" colspan="3" valign="top" class="prt">
			<table cellspacing="0" class="alltd" width="100%" cellpadding="0" border="0" style="font-weight: bold;font-size:6.5pt;border-collapse:collapse;border-top:solid 1px black;border-left:solid 1px black;border-bottom:solid 1px black;">
				<tbody>
				<tr>
					<td align="center">日\月</td>
					<td align="center">一 月</td>
                    <td align="center">二 月</td>
                    <td align="center">三 月</td>
                    <td align="center">四 月</td>
                    <td align="center">五 月</td>
                    <td align="center">六 月</td>
                    <td align="center">七 月</td>
                    <td align="center">八 月</td>
                    <td align="center">九 月</td>
                    <td align="center">十 月</td>
                    <td align="center">十一月</td>
                    <td align="center">十二月</td>
				</tr>
				
				<!-- 循环表格数据 -->
                <c:forEach var="i" begin="1" end="31">
				    <tr>
				     	<c:forEach var="j" begin="1" end="13">
				     	  <c:choose>
						   <c:when test="${j == 1}">
				              <td align="center">${i}</td>
						   </c:when>
						   <c:otherwise> 
				              <td>
				              	<c:if test="${item.tableData[i-1][j-2]!='null' }">
				              		${item.tableData[i-1][j-2]}
				              	</c:if>
				              </td>
						   </c:otherwise>
						  </c:choose>
		              	</c:forEach>
		             </tr>
				 </c:forEach>
				 
				 <c:forEach var="k" begin="32" end="36">
				 	<tr class="othertd">
				 		<c:forEach var="w" begin="1" end="13">
							<c:choose>
								<c:when test="${k == 32 && w==1}">
									<td align="center">平&nbsp; 均</td>
								</c:when>
								<c:when test="${k == 33 && w==1}">
									<td align="center">最&nbsp; 高</td>
								</c:when>
								<c:when test="${k == 34 && w==1}">
									<td align="center">日&nbsp; 期</td>
								</c:when>
								<c:when test="${k == 35 && w==1}">
									<td align="center">最&nbsp; 低</td>
								</c:when>
								<c:when test="${k == 36 && w==1}">
									<td align="center"> 日&nbsp; 期</td>
								</c:when>
								<c:otherwise> 
									<td>
										<c:if test="${item.tableData[k-1][w-2]!='null'}">
											${item.tableData[k-1][w-2]}
										</c:if>
									</td>
								</c:otherwise>
							</c:choose>
				 		</c:forEach>
				 	</tr>
				 </c:forEach>
                
				<tr class="othertd">
					<td rowspan="2">年 统 计</td>
					<td>最大流量</td>
					<td colspan="2" style="border-right:none;">
						<c:if test="${item.MXQ!='null'}">
							${item.MXQ}
						</c:if>
					</td>
					<td>
						<c:if test="${item.MXQDT!='null'}">
							${item.MXQDT}
						</c:if>
					</td>
					<td>最小流量</td>
					<td colspan="2" style="border-right:none;"><c:if test="${item.MNQ!='null'}">${item.MNQ}</c:if></td>
					<td><c:if test="${item.MNQDT!='null'}">${item.MNQDT}</c:if></td>
					<td>平均流量</td>
					<td colspan="3"><c:if test="${item.AVQ!='null'}">${item.AVQ}</c:if></td>
				</tr>
				<tr class="othertd">
					<td>径 流 量</td>
					<td style="border-right:none;"><c:if test="${item.RW!='null'}">${item.RW}</c:if></td>
					<td colspan="2">&nbsp;10<sup>8</sup>m<sup>3</sup></td>
					<td>径流模数</td><td style="border-right:none;"><c:if test="${item.RM!='null'}">${item.RM}</c:if></td>
					<td colspan="2">10<sup>-3</sup>m<sup>3</sup>/s.km<sup>2</sup></td>
					<td>径流深度</td><td style="border-right:none;"><c:if test="${item.RD!='null'}">${item.RD}</c:if></td>
					<td colspan="2">&nbsp;mm</td>
				</tr>
				
				<!-- 洪量统计 -->
				<tr class="othertd">
					<td align="center">&nbsp;洪量（10<sup>4</sup>m<sup>3</sup>）</td>
					<td>最大1日:</td><td><c:if test="${item.MXWDR1!='null'}">${item.MXWDR1}</c:if></td>
					<td>3日:</td><td><c:if test="${item.MXWDR3!='null'}">${item.MXWDR3}</c:if></td>
					<td>7日:</td><td><c:if test="${item.MXWDR7!='null'}">${item.MXWDR7}</c:if></td>
					<td>15日:</td><td><c:if test="${item.MXWDR15!='null'}">${item.MXWDR15}</c:if></td>
					<td>30日:</td><td><c:if test="${item.MXWDR30!='null'}">${item.MXWDR30}</c:if></td>
					<td>60日:</td><td><c:if test="${item.MXWDR60!='null'}">${item.MXWDR60}</c:if></td>
				</tr>
	
	<tr class="othertd">
		<td align="center">洪量日期</td>
		<td colspan="2"><c:if test="${item.BGDT1!='null'}">${item.BGDT1}</c:if></td>
		<td colspan="2"><c:if test="${item.BGDT3!='null'}">${item.BGDT3}</c:if></td>
		<td colspan="2"><c:if test="${item.BGDT7!='null'}">${item.BGDT7}</c:if></td>
		<td colspan="2"><c:if test="${item.BGDT15!='null'}">${item.BGDT15}</c:if></td>
		<td colspan="2"><c:if test="${item.BGDT30!='null'}">${item.BGDT30}</c:if></td>
		<td colspan="2"><c:if test="${item.BGDT60!='null'}">${item.BGDT60}</c:if></td>
	</tr>
	
	<tr class="othertd">
		<td align="center">附 &nbsp; &nbsp;注</td><td colspan="12" style="text-align:left;"><c:if test="${item.NT!='null'}">${item.NT}</c:if></td>
	</tr>
	
	
</tbody></table></td>
	</tr>
	
</tbody></table>
<br>

<div class="nextpage" style="width:100%;height:1px;">&nbsp;</div>
</td>

            </tr>
            </c:forEach>
        </tbody>
        </table>
</body>
</html>