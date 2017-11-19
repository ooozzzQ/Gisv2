<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/css/statistics.css" rel="stylesheet" media="screen">
<link href="<%=path%>/css/ecar_v3.css" rel="stylesheet" media="screen">
<link href="<%=path%>/css/autopromot.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/script/report.js"></script>
<script type="text/javascript" src="<%=path%>/script/date-util.js"></script>
<script type="text/javascript" src="<%=path%>/script/statistics.js"></script>
<script type="text/javascript" src="<%=path%>/script/autopromot.js"></script>
<style>
.Wdate {
	height: 20px !important;
}

body {
	position: relative;
}
</style>


<title>里程统计</title>

</head>
<body>
	<div id="bd">
		<div class="col-main" id="col-main" style="padding: 0!important;">
			<div class="mod-report">
				<div class="hd">
					<h2>
						里程统计[<span id="tip-btn" style="color: #0077FF;cursor: pointer;" title="">?</span>]
					</h2>
				</div>
				<div class="bd">
					<div id="tip-content" class="description">
						<h4>常见问题：</h4>
						<dl>
							<dt>关于"统计报表"中里程统计中里程数的说明</dt>
							<dd>
								答：是后台系统定时统计的，后台系统每天凌晨会进行前一天的数据统计，然后写入数据库固定保存起来供查询的。<br> 如果在统计时，车辆的GPS数据由于信号盲区等原因还没有补传给服务器时，这一部分数据就不会进入这一天的里程统计中。<br>
							</dd>
						</dl>
					</div>

					<div class="block-forms">

						<form id="submit-form" action="<%=path%>/StatisticsAction!getMileageStatisticsByCarId?carId=&beginTime=&endTime=" method="get" target="myframe" onSubmit="return onSubmitSpeed();" autocomplete="off">
							<div style="text-align:left;">
								<div id="searchListOutCanvas" style="margin-top:20px;margin-left:65px;"></div>
								<div>
									<span style="display:inline-block;width:65px;">统计方式：</span> <input id="radiobutton1" name="radiobutton" value="radiobutton" checked="" type="radio"> <label for="radiobutton1">按天统计</label> 
								</div>
								<div id="equip-list" class="show-loading">
									<div class="title">车辆牌照：</div>
									<div>
										<input presearvalue="" autocomplete="off" name="carId" type="text" id="searchinput" value="${carIdTemp}" placeholder="输入车牌号" />
									</div>
									<div id="auto"></div>
								</div>
							</div>

							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>开始时间： <input type="text" id="beginTime" name="beginTime" size="15" readonly onClick="WdatePicker({isShowClear:false,maxDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd 00:00:00'});" class="Wdate" style="width:100px;" value="${beginTimeTemp}"> <input id="beginTimeUtc" type="hidden" name="beginTime" /> &nbsp;结束时间： <input type="text" id="endTime" name="endTime" size="15" readonly onClick="WdatePicker({isShowClear:false,maxDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd 00:00:00'});" style="width:100px;" class="Wdate" value="${endTimeTemp}"> <input id="endTimeUtc" type="hidden" name="endTime" /> <span class="btn-submit"><button type="submit" name="Submit">查 询</button></span>
									</td>
								</tr>
								<tr>
									<td style="padding-top: 5px;"><span style="visibility: hidden;">开始时间：</span> <a class="blue" href="javascript:void(0)" onclick="queryByDate(1);return false;">今天</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(2);return false;">昨天</a>&nbsp;|&nbsp; <a class="blue" href="javascript:void(0)" onclick="queryByDate(3);return false;">本周</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(4);return false;">上周</a></td>
								</tr>

							</table>
						</form>

					</div>
					<div class="block-data">

						<div id="excelContainer">

							<div id="listing">
								<table id="listtable" class="listtable" width="100%" cellspacing="0" cellpadding="3" border="0">
									<tbody>
										<tr class="firstrow">
											<td class="first" width="60"><span>序号</span></td>
											<td width="157"><span>车辆牌照</span></td>
											<td width="157"><span>日期</span></td>
											<td width="157"><span>里程(公里)</span></td>
											<td width="157"><span>超速(次)</span></td>
											<td width="157"><span>停留(次)</span></td>
											<td width="157"><span>超速设置(公里/小时)</span></td>
										</tr>
										<s:if test="allCarList !=null">

											<s:if test="allCarList.size()>0">
												<s:iterator id="id" var="car" value="allCarList" status="status">
													<tr class="<s:if test="#status.even">listrow1</s:if><s:else>listrow0</s:else>">
														<td><s:property value="#status.index+1" /></td>
														<td>${carIdTemp}</td>
														<td>${car.TIME}</td>
														<td>${car.mileage}</td>
														<td>${car.overSpeedCount}</td>
														<td>${car.stopCount}</td>
														<td>${overspeedThreshold}</td>
													</tr>
												</s:iterator>
											</s:if>
											<s:else>
												<tr class="listrow">
													<td colspan="7">
														<div class="info-msg">
															<span>没有数据</span>
														</div>
													</td>
												</tr>
											</s:else>
										</s:if>
									</tbody>
								</table>
							</div>
							<div class="markError">
								<span style="color:red;">${msg}</span>
							</div>
							<div class="pager" style="display: none">
								<em> <span class="btn-excel" onclick="toRunPrint();"><button type="button">打印</button></span> <span class="btn-excel" onclick="overSpeedDetail.doExport()"><button type="button">Excel</button></span>
								</em>
							</div>

						</div>
						<form id="export-form" action="" method="post" name="exportFm" autocomplete="off"></form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
