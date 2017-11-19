<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String lon=request.getParameter("lon");
	String lat=request.getParameter("lat");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE" charset="gb2312"></script>
<title>停留位置定位</title>
<link rel="shortcut icon" href="<%=path%>/images/caron.gif" type="image/x-icon" />
</head>
<body>
	<div id="allmap"></div>
	<input id="lon" type="text"  value="<%=lon%>" />
	<input id="lat" type="text"  value="<%=lat%>" />
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
	map.addControl(new BMap.NavigationControl()); // 添加地图缩放控件
	var lon = document.getElementById("lon").value;
	var lat = document.getElementById("lat").value;
	var point = new BMap.Point(lon, lat);
	map.centerAndZoom(point, 20);

	var marker = new BMap.Marker(point);
	map.addOverlay(marker);
	alert('您的位置：' + lon + ',' + lat);

	//关于状态码
	//BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
	//BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
	//BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
	//BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
	//BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
	//BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
	//BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
	//BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
	//BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)
</script>
