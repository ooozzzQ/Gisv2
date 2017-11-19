<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + ":" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<title>4G图传NMMP</title>
</head>

<body>

	<object classid="clsid:D7CBB81A-1A2C-4FA7-AB12-DDC381FC7570" codebase="./WebOCX.cab#version=1,0,0,277" id="OCXTest" width=100% height=100% align=center hspace=0 vspace=0>
		<param name="SkinSelectIdx" value="32 ">
		<!--  功會这索弓丨号-->
		<param name="ServerIP" value="127.0.0.1">
		<!--视频监控平台服务器 IP 地址-->
		<param name="ServerPort" value="3000">
		<!--视频监控平台服务器端口号-->
		<param name="UserName" value="admin">
		<!--如果bMustWebServers=l表示WebServers用户名反之表示视频监控平台登录用户名-->
		<param name="UserPassword" value="12345">
		<!--视频监控平台登录密码一>
		<!--当 bMustWebServers=l 是有效，表示 WebServers 服务器地址-->
		<param name="WebServers_Get_Org" value="http://43.1.34.80:8080/csjj/servicesl/synOrgForDevice?wsdl">
		<!-- value=l设备从WebServers加载反之从视频监控平台加载-->
		<param name="bMustWebServers" value=0>
		<!-- value =1应用终端通过代理服务器访问反之直接登录-->
		
		<param name="ProxyStartup" value=0>
		<!--  代理服务器IP地址-->
		<param name="ProxyServerIP" value="43.1.34.85">
		<!-- 代理服务器端口 -->
		<param name="ProxyServerPort" value=3030>
	</object>
</body>
</html>
<script type="text/javascript">
	Ocxtest.InitUI(1,'192. 168.0.100','3000','admin','12345');
</script>
