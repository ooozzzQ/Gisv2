<%@ page language="java" import="java.util.*" contentType="image/jpeg" pageEncoding="gbk"%>
����<jsp:useBean id="image" scope="page" class="util.makeCertPic" />

<%
String str = image.getCertPic(0,0,response.getOutputStream());
session.setAttribute("certCode",str);
out.clear();
out = pageContext.pushBody();	
%>
