<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<script type="text/javascript" src="<%=path%>/script/flowplayer-3.2.8.min.js"></script>
<title>4G图传</title>
</head>

<body>
	<h1>RTMP Sample Player FlowPlayer</h1>

	<p>
		zhoulei<br />
		<!-- this A tag is where your Flowplayer will be placed. it can be anywhere -->
		<a href="#" style="display:block;width:520px;height:330px" id="player"> </a>
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
	flowplayer("player", "<%=path%>/script/flowplayer-3.2.8.swf",{ 
		clip: { 
		  url: 'hks',
		  provider: 'rtmp',
		  live: true, 
		},  
		plugins: {
		   rtmp: {  
			 url: '<%=path%>/script/flowplayer.rtmp-3.2.8.swf',  
			 netConnectionUrl: 'rtmp://live.hkstv.hk.lxdns.com/live/hks'
		   } 
	   } 
	});
	</script>
</body>
</html>