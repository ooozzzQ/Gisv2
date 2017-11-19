<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">   
    <title>登录</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<link rel="shortcut icon" href="<%=path %>/images/caron.gif" type="image/x-icon" />  
	<link href="<%=path %>/css/login.css" type=text/css rel=stylesheet>
	<script src="<%=path%>/script/jquery-1.7.2.min.js"></script>
	<script src="<%=path%>/script/list.js"></script>
	<script>
	 function reloadcode(){
                var verify=document.getElementById('code');
                verify.setAttribute('src','<%=path%>/makeCertPic.jsp?it='+Math.random());
        }  
        function showimg(){
              document.getElementById('code').style.display="block";
        }
        function testCertiPic(item){
            if(item.value==<%=session.getAttribute("certCode")%>){
              alert(<%=session.getAttribute("certCode")%>);
               return true;
            }
            else{
                return false;
            }
            //session.setAttribute("certCode",str);
        }
        $(function(){
        	$("input[name='username']").focus();
        });	
        </script>
</head> 
<body>
<div class="container">	
	<div id="box">
	<div id="logo"></div>
		<div class="loginBox">
		<div class="left"></div>
		<div class="main">
		<form name=admin_frm action=AdminUserAction!logincheck method=post onSubmit="">
			<table>
			<input type=hidden value=ok name=admin_log>
				<tr>
					<td class="leftText">用户名：</td>
					<td colspan='2'><input type="text" name='username' value="admin" class="inputText"/></td>
				</tr>
				<tr height='20'>
					<td ></td>
					<td colspan='2'><div class="userError"></div></td>
				</tr>
				<tr>
					<td class="leftText">密码：</td>
					<td colspan='2'><input class="inputText" type='password' value="admin" name='pwd'/></td>
				</tr>
				<tr height='20'>
					<td ></td>
					<td colspan='2'><div class="pwdError"></div></td>
				</tr>
				<tr>
					<td class="leftText">验证码：</td>
					<td ><input type="text" class="inputText tr100"  name="certinum" onfocus="showimg();" onblur="testCertiPic(this);"/><div id="mark"></div></td>
				    <td><img src="<%=path%>/makeCertPic.jsp" id="code" onclick="reloadcode()" style="cursor: pointer;float:left;" alt="看不清楚,换一张" /><s:actionerror  cssStyle="color:red;"/></td>
				</tr>
				<tr height='25'>
					<td ></td>
					<td colspan='2'><div class="markError"><span style="color:red;">${msg}</span></div></td>
				</tr>
				<tr height='35'>
					<td ></td>
					<td width='105'><input type='image' class="login btn" src="<%=path%>/images/login/login.jpg"/></td>
					<td width='195'><a href="http://www.olm.com.cn" class="home"></a></td>
				</tr>
				<tr height='20'>
					<td ></td>
					<td colspan='2'></td>
				</tr>
			</table>
			</form>
			<span class="bottom">Powered by OLM   热线电话：4008-906-960     传真：010-59070906 </span>
		</div>
		<div class="right"></div>
		</div>
		<div id="copyRight">版权所有 © 2011-2015 www.olm.com.cn All Right Reserved</div>
	</div>	
</div>
</body>
</html>
