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
            color:white;
        }
    </style> 
  <script language="javascript"> 
        var st;
　　    function printsetup(){ 
　　        document.getElementById('wb').execwb(8,1); 
　　    } 
　　    function printpreview(){ 
　　        document.getElementById('wb').execwb(7,1); 
　　    } 
　　    function printit() 
　　    { 
　　        if (confirm('确定打印吗？')) { 
　　            document.getElementById('wb').execwb(6,6) 
　　        } 
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
  <form name="form1" method="post" action="WaterLevelReport.aspx?stcd=00100300&amp;year=2011%2c2017&amp;r=0.8552426865810001" id="form1"> 
   <div> 
    <input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" /> 
    <input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" /> 
   </div> 
   <script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
</script> 
   <div> 
    <input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEWAwKegpesAgLxp6yxBQLr6NS2C7n1vkb0O9hVOADRcMtOl0nmr1XT" /> 
   </div> 
   <object id="wb" style="display: none;" height="0" width="0" viewastext="" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"> </object> 
   <input type="hidden" id="lastBreakRows" /> 
   <table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" class="toolbar"> 
    <tbody>
     <tr class="noprint" bordercolordark="white" bordercolorlight="#666666" bgcolor="#999999"> 
      <td align="left" valign="middle" style="height: 40px"> &nbsp;
	      <input type="button" class="button" onclick="printit()" value=" 打 印 " style="width: 80px" /> 
	      <input type="button" class="button" onclick="printpreview()" value="打印预览" style="width: 80px" /> 
	      <input type="button" class="button" onclick="printsetup()" value="打印设置" style="width: 80px" /> 
	      <input onclick="__doPostBack('hbtnExportExcel','')" name="hbtnExportExcel" type="button" id="hbtnExportExcel" class="button" value=" 导出Excel " style="width: 80px" /> 
	      <input onclick="__doPostBack('hbtnExportWord','')" name="hbtnExportWord" type="button" id="hbtnExportWord" class="button" value=" 导出Word " style="width: 80px" /> 
	      <input type="button" class="button" onclick="closeWindow()" value=" 关 闭 " style="width: 80px" /> 
      </td> 
     </tr> 
     <tr> 
      <td id="tdReport" bgcolor="white" valign="top" align="center">
       <table id="panel" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold; font-size:6pt;"> 
        <tbody>
         <tr> 
          <td colspan="3" align="center" height="30"> 
           <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;"> 
            <span id="lblRVNM">额尔齐斯河</span> 
            <span id="lblSTNM">布尔津</span> 逐日平均水位表 
           </div> </td> 
         </tr> 
         <tr> 
          <td align="left" valign="bottom" nowrap="nowrap"> 年 份: <span id="lblYEAR">2011</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测站编码: <span id="lblSTCD">00100300</span> </td> 
          <td align="right" valign="bottom" nowrap="nowrap"> 表内水位(测站基面以上米数)+<span id="lblGC">503.785</span>m=假定基面以上米数 </td> 
         </tr> 
         <tr> 
          <td id="printArea" colspan="3" valign="top" class="prt">
           <table cellspacing="0" class="alltd" width="100%" cellpadding="0" border="0" style="font-weight: bold;font-size:6.5pt;border-collapse:collapse;border-top:solid 1px black;border-left:solid 1px black;border-bottom:solid 1px black;"> 
            <tbody>
             <tr> 
              <td align="center"> 日\月</td>
              <td align="center" colspan="3"> 一 月</td>
              <td align="center" colspan="3"> 二 月</td>
              <td align="center" colspan="3"> 三 月</td>
              <td align="center" colspan="3"> 四 月</td>
              <td align="center" colspan="3"> 五 月</td>
              <td align="center" colspan="3"> 六 月</td>
              <td align="center" colspan="3"> 七 月</td>
              <td align="center" colspan="3"> 八 月</td>
              <td align="center" colspan="3"> 九 月</td>
              <td align="center" colspan="3"> 十 月</td>
              <td align="center" colspan="3"> 十一月</td>
              <td align="center" colspan="3"> 十二月</td> 
             </tr>
             <c:forEach var="i" begin="1" end="31">
			    <tr> 
			     	<c:forEach var="j" begin="1" end="13">
			     	  <c:choose>
					   <c:when test="${j == 1}">  
			              <td align="center"> ${i}</td>
					   </c:when>
					   <c:otherwise> 
			              <td colspan="3" old="${name[i-1][j-2]}">${name[i-1][j-2]}</td>
					   </c:otherwise>
					  </c:choose>
	              	</c:forEach>
	             </tr>
			 </c:forEach>
             <tr class="othertd"> 
              <td align="center"> 平&nbsp; 均</td>
              <td colspan="3">5.61</td>
              <td colspan="3">5.80</td>
              <td colspan="3">6.08</td>
              <td colspan="3">6.29</td>
              <td colspan="3">6.02</td>
              <td colspan="3">6.13</td>
              <td colspan="3">5.56</td>
              <td colspan="3">5.17</td>
              <td colspan="3">5.34</td>
              <td colspan="3">5.59</td>
              <td colspan="3">5.66</td>
              <td colspan="3">5.71</td> 
             </tr>
             <tr class="othertd"> 
              <td align="center"> 最&nbsp; 高</td>
              <td colspan="3">5.74</td>
              <td colspan="3">5.86</td>
              <td colspan="3">6.18</td>
              <td colspan="3">6.57</td>
              <td colspan="3">6.86</td>
              <td colspan="3">6.68</td>
              <td colspan="3">6.37</td>
              <td colspan="3">5.65</td>
              <td colspan="3">5.61</td>
              <td colspan="3">5.69</td>
              <td colspan="3">5.78</td>
              <td colspan="3">6.11</td> 
             </tr>
             <tr class="othertd"> 
              <td align="center"> 日&nbsp; 期</td>
              <td colspan="3">29</td>
              <td colspan="3">24</td>
              <td colspan="3">24</td>
              <td colspan="3">25</td>
              <td colspan="3">18</td>
              <td colspan="3">20</td>
              <td colspan="3">2</td>
              <td colspan="3">14</td>
              <td colspan="3">27</td>
              <td colspan="3">19</td>
              <td colspan="3">20</td>
              <td colspan="3">13</td> 
             </tr>
             <tr class="othertd"> 
              <td align="center"> 最&nbsp; 低</td>
              <td colspan="3">5.46</td>
              <td colspan="3">5.72</td>
              <td colspan="3">5.84</td>
              <td colspan="3">6.06</td>
              <td colspan="3">5.64</td>
              <td colspan="3">5.55</td>
              <td colspan="3">5.09</td>
              <td colspan="3">5.01</td>
              <td colspan="3">5.04</td>
              <td colspan="3">5.47</td>
              <td colspan="3">5.53</td>
              <td colspan="3">5.45</td> 
             </tr>
             <tr class="othertd"> 
              <td align="center"> 日&nbsp; 期</td>
              <td colspan="3">1</td>
              <td colspan="3">4</td>
              <td colspan="3">1</td>
              <td colspan="3">15</td>
              <td colspan="3">15</td>
              <td colspan="3">2</td>
              <td colspan="3">31</td>
              <td colspan="3">19</td>
              <td colspan="3">9</td>
              <td colspan="3">13</td>
              <td colspan="3">10</td>
              <td colspan="3">6</td> 
             </tr>
             <tr class="othertd"> 
              <td align="center"> 年统计</td>
              <td nowrap="nowrap" colspan="3"> 最高水位 </td>
              <td colspan="6" style="border-right:none;">6.86</td>
              <td colspan="3">5月18日</td>
              <td nowrap="nowrap" colspan="3"> 最低水位 </td>
              <td colspan="6" style="border-right:none;">5.01</td>
              <td colspan="3">8月19日</td>
              <td colspan="3"> 平均水位 </td>
              <td colspan="9">5.75</td> 
             </tr>
             <tr class="othertd"> 
              <td> 各种保证率水位</td>
              <td colspan="2"> 最高 </td>
              <td colspan="3">6.75</td>
              <td nowrap="nowrap" colspan="2"> 第15天 </td>
              <td colspan="3">6.43</td>
              <td nowrap="nowrap" colspan="2"> 第30天 </td>
              <td colspan="3">6.28</td>
              <td nowrap="nowrap" colspan="2"> 第90天 </td>
              <td colspan="3">6.00</td>
              <td nowrap="nowrap" colspan="2"> 第180天 </td>
              <td colspan="3">5.71</td>
              <td nowrap="nowrap" colspan="2"> 第270天 </td>
              <td colspan="3">5.55</td>
              <td colspan="2"> 最低</td>
              <td colspan="4">5.07</td> 
             </tr>
             <tr class="othertd"> 
              <td height="20" align="center"> 附 &nbsp; &nbsp; &nbsp; &nbsp; 注</td>
              <td colspan="36" style="text-align: left">表内水位采用人工观测资料整编。</td> 
             </tr> 
            </tbody>
           </table></td> 
         </tr> 
        </tbody>
       </table> 
	   <br />
       <div class="nextpage" style="width:100%;height:1px;">&nbsp;</div>
	   </td> 
     </tr> 
    </tbody>
   </table> 
  </form>   
 </body>
</html>
