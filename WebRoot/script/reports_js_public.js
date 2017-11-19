// JavaScript Document
function $(id){return document.getElementById(id);};
function addListener(element,e,fn){ element.addEventListener?element.addEventListener(e,fn,false):element.attachEvent("on" + e,fn)};
function getHTTPObject()
{                       
	var http;
	var browser = navigator.appName;
	if(browser == "Microsoft Internet Explorer") 
	{
	http = new ActiveXObject("Microsoft.XMLHTTP"); 
	}
	else
	{
	http = new XMLHttpRequest(); 
	}
	return http;
}

function getCookieVal(offset) 
{
   var endstr = document.cookie.indexOf (";", offset);
   if (endstr == -1)
	  endstr = document.cookie.length;
   return unescape(document.cookie.substring(offset, endstr));
}

//获取Cookies的通用方法
function GetCookie (name) 
{
   var arg = name + "=";
   var alen = arg.length;
   var clen = document.cookie.length;
   var i = 0;
   while (i < clen) 
	  {
	  var j = i + alen;
	  if (document.cookie.substring(i, j) == arg)
		 return this.getCookieVal (j);
	  i = document.cookie.indexOf(" ", i) + 1;
	  if (i == 0) 
		 break; 
	  }
   return null;
}

//设置Cookies的通用方法 
function SetCookie(name, value) 
{
   var argv = this.SetCookie.arguments;
   var argc = this.SetCookie.arguments.length;
   var expires=new Date(); 
   expires.setTime(expires.getTime()+(3650*24*60*60*1000)); 
   var path = (3 < argc) ? argv[3] : null;
   var domain = (4 < argc) ? argv[4] : null;
   var secure = (5 < argc) ? argv[5] : false;
   document.cookie = name + "=" + escape (value) +
	 ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
	 ((path == null) ? "" : ("; path=" + path)) +
	 ((domain == null) ? "" : ("; domain=" + domain)) +
	((secure == true) ? "; secure" : "");
}

function randomURL(url)
{
	if(url.indexOf("javascript")==-1){
		var Current_Date = new Date();	
		var Current_Year = Current_Date.getYear(); 
		var Current_Month = Current_Date.getMonth(); 
		var Current_Today = Current_Date.getDate();
		var Current_Hours = Current_Date.getHours(); 
		var Current_Minutes = Current_Date.getMinutes();
		var Current_Seconds = Current_Date.getSeconds();	
		var randomStr = Current_Year+""+Current_Month+""+Current_Today+""+Current_Hours+""+Current_Minutes+""+Current_Seconds+Math.random()*20 + 1;
		if(url.indexOf("?")>1 ){
			url += "&commixrandom="+randomStr;
		}else{
			url += "?commixrandom="+randomStr;
		}
	}
	return url;
}

//开始时间小于结束时间的检查
function check_time(id_a,id_b){
	var b_value = document.getElementById(id_a).value,e_value = document.getElementById(id_b).value;
	return new Date(e_value.replace(/-/g,'/')).getTime()>new Date(b_value.replace(/-/g,'/')).getTime();
}

//时间间隔检查
//@param begin,开始时间
//@param end,结束时间
//@param diff，时间间隔
//@return true，差小于diff时返回；反之false
function check_time_diff(begin,end,diff){
	return (end-begin)>diff?false:true;
}
