<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<title>统计报表</title>
<link rel="shortcut icon" href="<%=path%>/images/caron.gif" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common_624bce0.css" />
<link rel="stylesheet" href="<%=path%>/css/panel/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="<%=path%>/css/panel/bootstrap-theme.min.css" media="screen">
<link href="<%=path%>/css/panel/main.css" rel="stylesheet" media="screen">
<!-- 左侧面板css -->
<link href="<%=path%>/css/panel/manage.css" rel="stylesheet" media="screen">
<link href="<%=path%>/css/report.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path%>/script/jquery-1.7.2.min.js"></script>


</head>

<body onload="init()">

	<div id="header">
		<div class="title">
			<img class="logo" src="<%=path%>/images/motorcycle.jpg"> <span class="headName">车辆监控平台</span>
		</div>
		<div id="query">
			<form action="<%=path%>/CarAction!lonlatlist?start=&stop=" method="get">
				<input id="username" type="hidden" value="<%=request.getSession().getAttribute("username")%>" /> &nbsp;&nbsp;<span>欢迎<%=request.getSession().getAttribute("username")%>,来到车辆监控平台！
				</span>
			</form>
		</div>

	</div>

	<div class="container main-div">
		<div class="row">
			<div class="track-list-div left">
				<div class="report-list-title">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed">
						<tr>
							<td width="30"><a id="summary" href="javascript:void(0)" onclick="changeSrc(this)" userId="summary" >运行总览</a></td>
						</tr>
						<tr>
							<td width="30"><a id="mileage" href="javascript:void(0)" onclick="changeSrc(this)" userId="mileage" >里程统计</a></td>
						</tr>
						<tr>
							<td width="30"><a id="overspeed" href="javascript:void(0)" onclick="changeSrc(this)" userId="overspeed"  >超速详单</a></td>
						</tr>
						<tr>
							<td width="30"><a id="stop"  href="javascript:void(0)" onclick="changeSrc(this)" userId="stop" >停留详单</a></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="map-nav-bar">
				<div class="panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<span><a href="<%=path%>/report.jsp">运行统计</a> </span>
							<span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
							<span><a href="<%=basePath%>SearchCarAction!getCarList">车辆监控</a><span>
						</h3>
					</div>
				</div>
			</div>
			<div class="map-div" id="report">
				<input id="path" type="hidden" value="<%=path%>" />
				<iframe name="myframe" id="myframe"  width="100%"  height="100%"  frameborder="0" src="" marginwidth="0" marginheight="0"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=path%>/script/iframe.js"></script>
</body>
</html>
