<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <html>
 <head>
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
 <meta http-equiv="Content-Type" content="text/html;" />
 <title>Car Trip</title>
 <style type="text/css">
 html{height:100%}
 body{height:100%;margin:0px;padding:0px}
 #controller{width:100%; border-bottom:3px outset; height:30px; filter:alpha(Opacity=100); -moz-opacity:1; opacity:1; z-index:10000; background-color:lightblue;}
 #container{height:100%}
 </style>
 <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script> 
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=rnCWGQLAf84XVcGTor1cWZw6X4zQeVkR"></script>
 <script type="text/javascript">
	//获取所有点的坐标
	//alert('<%=request.getAttribute("mylist")%>');
	
	var points = [
	<c:forEach items="${mylist}" var="result">
		new BMap.Point("${result.lon}","${result.lat}"),
	</c:forEach>
	];

        

	//alert("${mylist}");
	//var allstr="${mylist}";
	//var allList=new Array();
	//allList=allstr.replace('[','').replace(']','').split(', ');
	//alert(allList);
    //var points=allList;

	 //var points = [

		//new BMap.Point(119.998255,30.28285), new BMap.Point(119.997752,30.282772),
		//new BMap.Point(119.996872,30.282601), new BMap.Point(119.997034,30.282086),
		//new BMap.Point(119.997016,30.281899), new BMap.Point(119.997123,30.281416),
		//new BMap.Point(119.997231,30.281026), new BMap.Point(119.997285,30.280745),
		//new BMap.Point(119.998255,30.280215), new BMap.Point(119.996926,30.279529),
		//new BMap.Point(119.996117,30.280496), new BMap.Point(119.995381,30.280449),
		//new BMap.Point(119.994716,30.280418), new BMap.Point(119.993458,30.280309),
		//new BMap.Point(119.992883,30.280277), new BMap.Point(119.992506,30.280246)

	 //];
	 
	var map; //百度地图对象
	var centerPoint;
	var n =points.length-1;
	alert(points.length)
	var myP1 = points[0];    //起点
	var myP2 = points[n];    //终点
	var myIcon1 = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/Mario.png", new BMap.Size(32, 70), {   
			imageOffset: new BMap.Size(0, 0)  
		 });
    var myIcon2 = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(32, 70));

	function init() {
		 followChk = document.getElementById("follow");
		 playBtn = document.getElementById("play");
		 pauseBtn = document.getElementById("pause");
		 resetBtn = document.getElementById("reset");
		 map = new BMap.Map("container");
		 map.centerAndZoom(points[0], 15);
		 map.enableScrollWheelZoom();
		 map.addControl(new BMap.NavigationControl());
		 map.addControl(new BMap.ScaleControl());
		 centerPoint = new BMap.Point((points[0].lng + points[points.length - 1].lng) / 2, (points[0].lat + points[points.length - 1].lat) / 2);
		 map.panTo(centerPoint);
		 map.addOverlay(new BMap.Polyline(points, {strokeColor: "red", strokeWeight: 5, strokeOpacity: 1}));
		 label = new BMap.Label("", {offset: new BMap.Size(-20, -20)});
	     var myP1Marker = new BMap.Marker(myP1,{icon:myIcon1}); 
		 var myP2Marker = new BMap.Marker(myP2,{icon:myIcon2}); 
		 map.addOverlay(myP1Marker);
		 map.addOverlay(myP2Marker);

	 }
 </script>
 </head>

<body onload="init();">
<form action="/Gisv2/CarAction!lonlatlist?start=&stop=" method="get">
start<input type="text" name="start" id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
stop<input type="text" name="stop" id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
<input type="submit" value="Submit" /></br>   
</form>
 <div id="container"></div>
</body>
</html>