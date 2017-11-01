<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" class="toolbar">
            <tbody>
            <tr class="noprint" bordercolordark="white" bordercolorlight="#666666" bgcolor="#999999">
                <td align="left" valign="middle" style="height: 40px">
                    &nbsp;<input type="button" class="button" onclick="printit()" value=" 打 印 " style="width: 80px">
                    <input type="button" class="button" onclick="printpreview()" value="打印预览" style="width: 80px">
                    <input type="button" class="button" onclick="printsetup()" value="打印设置" style="width: 80px">
                    <input onclick="__doPostBack('hbtnExportExcel','')" name="hbtnExportExcel" type="button" id="hbtnExportExcel" class="button" value=" 导出Excel " style="width: 80px">
                    <input onclick="__doPostBack('hbtnExportWord','')" name="hbtnExportWord" type="button" id="hbtnExportWord" class="button" value=" 导出Word " style="width: 80px">
                    <input type="button" class="button" onclick="closeWindow()" value=" 关 闭 " style="width: 80px">
                </td>
            </tr>
            <tr>
                <td id="tdReport" bgcolor="white" valign="top" align="center">
                <table id="panel" cellpadding="0" cellspacing="0" width="98%" border="0" style="font-weight: bold; font-size:6pt;">
					<tbody>
					<tr>
						<td colspan="3" align="center" height="30">
	                        <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">
	                                <span id="lblRVNM">库依尔特斯河</span> <span id="lblSTNM">富蕴(二)</span> 逐日平均水位表
	                        </div>
                        </td>
					</tr>
					<tr>
						<td align="left" valign="bottom" nowrap="nowrap">
				                                    年 份:
                            <span id="lblYEAR">2011</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测站编码:
                            <span id="lblSTCD">00100100</span>
                        </td>
						<td align="right" valign="bottom" nowrap="nowrap">
				                                    表内水位(测站基面以上米数)+<span id="lblGC">1196.965</span>m=假定基面以上米数
                        </td>
					</tr>
	<tr>
		<td id="printArea" colspan="3" valign="top" class="prt">
			<table cellspacing="0" class="alltd" width="100%" cellpadding="0" border="0" style="font-weight: bold;font-size:6.5pt;border-collapse:collapse;border-top:solid 1px black;border-left:solid 1px black;border-bottom:solid 1px black;">
				<tbody>
				<tr>
					<td align="center">日\月</td>
					<td align="center" colspan="3">一 月</td>
                    <td align="center" colspan="3">二 月</td>
                    <td align="center" colspan="3">三 月</td>
                    <td align="center" colspan="3">四 月</td>
                    <td align="center" colspan="3">五 月</td>
                    <td align="center" colspan="3">六 月</td>
                    <td align="center" colspan="3">七 月</td>
                    <td align="center" colspan="3">八 月</td>
                    <td align="center" colspan="3">九 月</td>
                    <td align="center" colspan="3">十 月</td>
                    <td align="center" colspan="3">十一月</td>
                    <td align="center" colspan="3">十二月</td>
				</tr>
				<tr>
					<td align="center">1</td>
                    <td colspan="3" old="5.49">5.49</td>
                    <td colspan="3" old="5.30">5.30</td>
                    <td colspan="3" old="5.28">5.28</td>
                    <td colspan="3" old="5.66">5.66</td>
                    <td colspan="3" old="5.45">5.45</td>
                    <td colspan="3" old="5.96">5.96</td>
                    <td colspan="3" old="5.66">5.66</td>
                    <td colspan="3" old="5.36">5.36</td>
                    <td colspan="3" old="5.29">5.29</td>
                    <td colspan="3" old="5.16">5.16</td>
                    <td colspan="3" old="5.06">5.06</td>
                    <td colspan="3" old="5.25">5.25</td>
                </tr>
	<tr>
		<td align="center">2</td>
		<td colspan="3" old="5.46">&nbsp;&nbsp;&nbsp;46</td>
		<td colspan="3" old="5.31">&nbsp;&nbsp;&nbsp;31</td>
		<td colspan="3" old="5.29">&nbsp;&nbsp;&nbsp;29</td>
		<td colspan="3" old="5.73">&nbsp;&nbsp;&nbsp;73</td>
		<td colspan="3" old="5.44">&nbsp;&nbsp;&nbsp;44</td>
		<td colspan="3" old="5.88">&nbsp;&nbsp;&nbsp;88</td>
		<td colspan="3" old="5.72">&nbsp;&nbsp;&nbsp;72</td>
		<td colspan="3" old="5.34">&nbsp;&nbsp;&nbsp;34</td>
		<td colspan="3" old="5.28">&nbsp;&nbsp;&nbsp;28</td>
		<td colspan="3" old="5.17">&nbsp;&nbsp;&nbsp;17</td>
		<td colspan="3" old="5.06">&nbsp;&nbsp;&nbsp;06</td>
		<td colspan="3" old="5.24">&nbsp;&nbsp;&nbsp;24</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                平&nbsp; 均</td><td colspan="3">5.39</td><td colspan="3">5.27</td><td colspan="3">5.35</td><td colspan="3">5.51</td><td colspan="3">5.57</td><td colspan="3">5.84</td><td colspan="3">5.57</td><td colspan="3">5.41</td><td colspan="3">5.23</td><td colspan="3">5.12</td><td colspan="3">5.16</td><td colspan="3">5.36</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                最&nbsp; 高</td><td colspan="3">5.50</td><td colspan="3">5.32</td><td colspan="3">5.66</td><td colspan="3">5.85</td><td colspan="3">6.14</td><td colspan="3">6.23</td><td colspan="3">6.12</td><td colspan="3">5.61</td><td colspan="3">5.30</td><td colspan="3">5.19</td><td colspan="3">5.27</td><td colspan="3">5.44</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                日&nbsp; 期</td><td colspan="3">1</td><td colspan="3">1</td><td colspan="3">31</td><td colspan="3">28</td><td colspan="3">17</td><td colspan="3">5</td><td colspan="3">3</td><td colspan="3">10</td><td colspan="3">1</td><td colspan="3">4</td><td colspan="3">30</td><td colspan="3">28</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                最&nbsp; 低</td><td colspan="3">5.15</td><td colspan="3">5.23</td><td colspan="3">5.26</td><td colspan="3">5.24</td><td colspan="3">5.33</td><td colspan="3">5.57</td><td colspan="3">5.39</td><td colspan="3">5.30</td><td colspan="3">5.16</td><td colspan="3">5.06</td><td colspan="3">5.05</td><td colspan="3">5.22</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                日&nbsp; 期</td><td colspan="3">29</td><td colspan="3">12</td><td colspan="3">1</td><td colspan="3">12</td><td colspan="3">12</td><td colspan="3">27</td><td colspan="3">31</td><td colspan="3">31</td><td colspan="3">30</td><td colspan="3">31</td><td colspan="3">1</td><td colspan="3">6</td>
	</tr>
	<tr class="othertd">
		<td align="center">
                                                年统计</td><td nowrap="nowrap" colspan="3">
                                                最高水位
                                            </td><td colspan="6" style="border-right:none;">6.23</td><td colspan="3">6月5日</td><td nowrap="nowrap" colspan="3">
                                                最低水位
                                            </td><td colspan="6" style="border-right:none;">5.05</td><td colspan="3">11月1日</td><td colspan="3">
                                                平均水位
                                            </td><td colspan="9">5.40</td>
	</tr>
	<tr class="othertd">
		<td>
                                                各种保证率水位</td><td colspan="2">
                                                最高
                                            </td><td colspan="3">6.02</td><td nowrap="nowrap" colspan="2">
                                                第15天
                                            </td><td colspan="3">5.88</td><td nowrap="nowrap" colspan="2">
                                                第30天
                                            </td><td colspan="3">5.80</td><td nowrap="nowrap" colspan="2">
                                                第90天
                                            </td><td colspan="3">5.50</td><td nowrap="nowrap" colspan="2">
                                                第180天
                                            </td><td colspan="3">5.38</td><td nowrap="nowrap" colspan="2">
                                                第270天
                                            </td><td colspan="3">5.25</td><td colspan="2">
                                                最低</td><td colspan="4">5.05</td>
	</tr>
	<tr class="othertd">
		<td height="20" align="center">
                                                附 &nbsp; &nbsp; &nbsp; &nbsp; 注</td><td colspan="36" style="text-align: left">表内水位5月1日至7月31日采用自记资料整编，其他时间采用人工观测资料整编。</td>
	</tr>
</tbody></table></td>
	</tr>
</tbody></table>
<br>

<div class="nextpage" style="width:100%;height:1px;">&nbsp;</div></td>

            </tr>
        </tbody>
        </table>
</body>
</html>