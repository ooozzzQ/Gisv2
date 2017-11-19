<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head> 
	<meta charset="utf-8" /> 
	<title>路书</title> 
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#map_canvas{width:100%;height:500px;}
		#result {width:100%}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE" charset="gb2312"></script>
	<script type="text/javascript" src="<%=path%>/script/LuShu.js"></script>
</head> 
<body> 
车辆牌照 &nbsp;&nbsp;<input type="text" name="searchinput" id="searchinput" style="width:130px; margin-right:10px;color: #333;"   placeholder="输入车牌号"> 
				开始时间 &nbsp;&nbsp;<input type="text" id="start" name="start" style="width:130px; margin-right:10px;color: #333;" id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
				结束时间&nbsp;&nbsp;<input type="text" id="stop" name="stop" style="width:130px; margin-right:10px;color: #333;"  id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
				<input type="button" value="查询" style="color: #333;" onclick="queryHistoryTrackByCarId()" />
	<div id="map_canvas"></div> 
	<div id="result"></div>
	<button id="run">开始</button> 
	<button id="stop">停止</button> 
	<button id="pause">暂停</button> 
	<button id="hide">隐藏信息窗口</button> 
	<button id="show">展示信息窗口</button> 
	<script> 
	var map = new BMap.Map('map_canvas');
	map.enableScrollWheelZoom();
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);
	var lushu;
	var moveIcon = new BMap.Icon("/Gisv2/images/motorcycle.ico", new BMap.Size(52, 26), {
		imageOffset : new BMap.Size(0, 0)
	});
	// 实例化一个驾车导航用来生成路线
  
	//绑定事件
	$("run").onclick = function(){
		lushu.start();
	}
	$("stop").onclick = function(){
		lushu.stop();
	}
	$("pause").onclick = function(){
		lushu.pause();
	}
	$("hide").onclick = function(){
		lushu.hideInfoWindow();
	}
	$("show").onclick = function(){
		lushu.showInfoWindow();
	}
	function $(element){
		return document.getElementById(element);
	}
	/**
	 * @param carArry
	 *            通过百度地图路书功能实现轨迹查询与回放
	 */
	function lushuMove(carArry) {
		var points = [];
		for (var i = 0; i < carArry.length; i++) {
			// var carId = carArry[i].carId;
			var longitude = carArry[i].x;
			var latitude = carArry[i].y;
			var point = new BMap.Point(longitude, latitude);
			points.push(point);
			points.join(',');
		}
		var n = points.length - 1;
		var myP1 = points[0];
		var myP2 = points[n];
		// 实例化一个驾车导航用来生成路线
		var drv = new BMap.DrivingRoute('北京', {
			onSearchComplete : function(res) {
				if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
					// var arrPois = res.getPlan(0).getRoute(0).getPath();
					map.addOverlay(new BMap.Polyline(points, {
						strokeColor : '#111'
					}));
					map.setViewport(points);

					lushu = new BMapLib.LuShu(map, points, {
						defaultContent : "从天安门到百度大厦",
						speed : 4500,
						icon : moveIcon,
						autoView : true,// 是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
						enableRotation : true,// 是否设置marker随着道路的走向进行旋转
					});
				}
			}
		});
		drv.search(myP1, myP2);
	}
	/**
	 * 通过车牌号查询车辆历史轨迹
	 */
	function queryHistoryTrackByCarId() {
		var carId = document.getElementById("searchinput").value.trim();
		var start = document.getElementById("start").value.trim();
		var stop = document.getElementById("stop").value.trim();

		if (carId == "") {
			alert("车牌号不能为空！");
		} else if (start == "" || stop == "") {
			alert("起始日期或结束日期不能为空");
		} else {

			var req = createXMLHTTPRequest();
			if (req) {
				req.open("POST", "/Gisv2/CarAction!lonlatlist?start=" + start + "&stop=" + stop + "&carId=" + carId, true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8;");
				req.send("");
				req.onreadystatechange = function() {
					if (req.readyState == 4) {
						if (req.status == 200) {
							if (req.responseText != null && req.responseText.length > 2) {
								var objArr = JSON.parse(req.responseText);
								// drawTrackByCarArry(objArr);
								lushuMove(objArr);
							} else {
								alert("暂无该车辆轨迹信息!");
							}
						}
					}
				}
			}
		}
	}
	/**
	 * ajax 共用方法
	 * 
	 * @returns {___anonymous2299_2312}
	 */
	function createXMLHTTPRequest() {
		// 1.创建XMLHttpRequest对象
		// 这是XMLHttpReuquest对象无部使用中最复杂的一步
		// 需要针对IE和其他类型的浏览器建立这个对象的不同方式写不同的代码
		var xmlHttpRequest;
		if (window.XMLHttpRequest) {
			// 针对FireFox，Mozillar，Opera，Safari，IE7，IE8
			xmlHttpRequest = new XMLHttpRequest();
			// 针对某些特定版本的mozillar浏览器的BUG进行修正
			if (xmlHttpRequest.overrideMimeType) {
				xmlHttpRequest.overrideMimeType("text/xml");
			}
		} else if (window.ActiveXObject) {
			// 针对IE6，IE5.5，IE5
			// 两个可以用于创建XMLHTTPRequest对象的控件名称，保存在一个js的数组中
			// 排在前面的版本较新
			var activexName = [ "MSXML2.XMLHTTP", "Microsoft.XMLHTTP" ];
			for (var i = 0; i < activexName.length; i++) {
				try {
					// 取出一个控件名进行创建，如果创建成功就终止循环
					// 如果创建失败，回抛出异常，然后可以继续循环，继续尝试创建
					xmlHttpRequest = new ActiveXObject(activexName[i]);
					if (xmlHttpRequest) {
						break;
					}
				} catch (e) {
				}
			}
		}
		return xmlHttpRequest;
	}
</script> 
</body> 
</html>