function changeSrc(obj) {
	var userId = obj.getAttribute("userId");
	var path = document.getElementById("path").value;
	if (userId == "summary") {
		document.getElementById("myframe").src = path + "/summary.jsp";
	} else if (userId == "overspeed") {
		document.getElementById("myframe").src = path + "/overspeed.jsp";
	} else if (userId == "stop") {
		document.getElementById("myframe").src = path + "/stop.jsp";
	} else if (userId == "mileage") {
		document.getElementById("myframe").src = path + "/mileage.jsp";
	} else {
		alert("系统繁忙，请联系管理员！")
		document.getElementById("myframe").src = "/error.jsp";
	}
}
jQuery.noConflict();
jQuery(document).ready(function() {
	var path = document.getElementById("path").value;
	document.getElementById("myframe").src = path + "/summary.jsp";
});
