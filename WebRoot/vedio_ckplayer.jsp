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
<title>4G图传</title>
</head>

<body>
	<h1>RTMP Sample Player CKPlayer</h1>
		<div id="a1"></div>
<script type="text/javascript" src="<%=path%>/script/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
    var flashvars={
        f:'rtmp://live.hkstv.hk.lxdns.com/live/hks',
        c:0
    };
    var video=['http://localhost/live.m3u8'];
    CKobject.embed('<%=path%>/script/ckplayer.swf','a1','ckplayer_a1','600','400',false,flashvars,video);
</script>
</body>
</html>