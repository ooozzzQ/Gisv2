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
<title>车辆监控平台</title>
<link rel="shortcut icon" href="<%=path %>/images/caron.gif" type="image/x-icon" />  
<link rel="stylesheet" type="text/css" href="<%=path%>/css/common_624bce0.css" />
<link rel="stylesheet" href="<%=path%>/css/panel/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="<%=path%>/css/panel/bootstrap-theme.min.css" media="screen">
<link href="<%=path%>/css/panel/main.css" rel="stylesheet" media="screen">
<!-- 左侧面板css -->
<link href="<%=path%>/css/panel/manage.css" rel="stylesheet" media="screen">

<link href="<%=path%>/css/autopromot.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path%>/script/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script> 

 
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}

#allmap {
	height: 620px;
	margin-left: 262px;
	position: relative;
	z-index: 10;
	margin-top: 0;
	width: auto;
}

#buttonBox{width:380px;height:28px;position:absolute;right:0;bottom:0;}

#header {
	background-color: #004163;
	color: #ffff;
	background-repeat: repeat-x;
	height: 64px;
	margin: 0 auto;
	width: 100%;
	z-index: 100;
}

#query {
	top: 20px;
	color: #fff;
	float: right;
	height: 100%;
	text-align: center;
	position: relative;
	margin-right: 2.5%;
}

#track-panel{
font-size: 14px;
font-weight: 900;
width:130px; 
margin-right:10px;
font-color: #333;
}

</style>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2&ak=cnG69M9AWuP8G9X96hGW1rxAaGGb8MyE" charset="gb2312"></script>
<script type="text/javascript" language="javascript" charset="gb2312"  src="<%=basePath%>/script/autopromot.js"> </script>  

</head>

<body  onload="init()">

	<div id="header">
		<div class="title">
			<img class="logo" src="<%=path%>/images/motorcycle.jpg"> <span class="headName">车辆监控平台</span>
		</div>
		<div id="query">
			<form action="<%=path%>/CarAction!lonlatlist?start=&stop=" method="get">
				<span id="track-panel" style="color: #333;" ><a href="<%=path%>/report.jsp" path="<%=path%>">统计报表</a></span> | 
				<span id="track-panel" > &nbsp;&nbsp;轨迹查询面板</span>
				车辆牌照 &nbsp;&nbsp;<input type="text" name="searchinput" id="searchinput"  autocomplete="off" style="width:130px; margin-right:10px;color: #333;"   placeholder="输入车牌号"> 
					
				开始时间 &nbsp;&nbsp;<input type="text" id="start" name="start" style="width:130px; margin-right:10px;color: #333;" id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
				结束时间&nbsp;&nbsp;<input type="text" id="end" name="end" style="width:130px; margin-right:10px;color: #333;"  id="qwe" onclick="WdatePicker({dateFmt:'yyyyMMddHHmmss'})"/>
				<input type="button" id="guijiQuery" value="查询" style="color: #333;" onclick="queryHistoryTrackByCarId()" />
				<input id="username" type="hidden"  value="<%=request.getSession().getAttribute("username")%>" />
				&nbsp;&nbsp;<span>欢迎<%=request.getSession().getAttribute("username")%>！</span>
				
			</form>
		</div>
		
	</div>
		<div id="auto">  
        </div>  
	<div class="container main-div">

		<div class="row">
			<div class="track-list-div left">
				<div class="tracks-loading">
					<img src="<%=path%>/images/loading.gif" alt="数据加载中...">
				</div>
				<div class="tracks-list-title">
					<div class="head-line"></div>
					<input name="chkAllItem" id="chk_all_track" type="checkbox"> <span class="title">车辆列表 (共计<s:property value="#page.totalCount" />辆)
					</span>
				</div>
				<div id="tracks-list-panel" class="panel-body tracks-list-panel frame">
					<ul class="tracks-ul">
						<s:iterator id="li" value="allCarList" status="">
							<li><input id="chk_item" name="chkItem"  type="checkbox" value="<s:property value="li"></s:property>"> <span class="fdsf" style="width:14px;top:-1px"> <img src="<%=path%>/images/caron.gif" alt="<s:property value="li"></s:property>">
							</span> <span> <s:property value="li"></s:property>
							</span></li>
						</s:iterator>
					</ul>
				</div>
				<div id="tracks-list-panel" class="panel-body tracks-list-panel frame">
					<s:if test="#pageList.size==0">

						<div id='errorSearch'>
							<h2>抱歉！暂无车辆！</h2>
						</div>
					</s:if>
					<s:else>
						<s:if test="#page.totalPage > 1">
							<ul class="linkList horizontalLinkList pagination pages">
								<input id="nowPage" type="text" style="width:20px; margin-right:1px;" value="<s:property value="#page.nowPage" />" /> /
								<input id="totalPage"  type="hidden" value="<s:property value="#page.totalPage" />" />
								<span><s:property value="#page.totalPage" />页</span>
								<span><input type="button" value="GO" style=" color: #333;" onclick="goPage()" /></span>
								<s:if test="#page.nowPage > 1">
									<a href="<%=path%>/SearchCarAction!getCarList?username=${username}&pageNum=${page.nowPage -1}">上一页</a>
								</s:if>
								<s:else>
									<span>上一页</span>
								</s:else>
								<s:if test="#page.nowPage < #page.totalPage">
									<a href="<%=path%>/SearchCarAction!getCarList?username=${username}&pageNum=${ page.nowPage + 1 }">下一页</a>
								</s:if>
								<s:else>
									<span>下一页</span>
								</s:else>
							</ul>
						</s:if>
					</s:else>
				</div>
			</div>
			<div class="map-nav-bar">
				<div class="panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">实时监控面板</h3>
						<div class="input-group track-search-input">

							<input type="text" name="searchtext_carid" id="track-search-text" class="form-control" placeholder="输入车牌号"> <span class="input-group-btn">
								<button class="btn btn-default" type="button" id="track-search-btn" data-loading-text="查询中......">搜索</button>
							</span>

						</div>
					</div>
				</div>
			</div>
			<div class="map-div" id="allmap"></div>
		</div>
	</div>

	<div id="allmap"></div>
	<div id="result"></div>	
<div id="buttonBox">
	<button id="run">开始</button> 
	<button id="stop">停止</button> 
	<button id="pause">暂停</button> 
	<button id="hide">隐藏信息窗口</button> 
	<button id="show">展示信息窗口</button> 
</div>
<script type="text/javascript" src="<%=path%>/script/map.js" charset="UTF-8"></script> 
<script type="text/javascript" src="<%=path%>/script/LuShu_min.js"></script>
</body>
</html>
