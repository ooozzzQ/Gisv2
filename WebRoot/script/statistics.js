jQuery(document).ready(function() {
	var overSpeedDetail = (function() {

		init = function() {
			report.bindTips();
		};

		init();
	})();

	// 本地时间
	var btt = document.getElementById('beginTime');
	var ett = document.getElementById('endTime');

	if (btt.value == "" || ett.value == "" || btt.value == null || ett.value == null) {
		var def = new Date();
		ett.value = def.getFullYear() + '-' + 0 + (def.getMonth() + 1) + '-' + def.getDate() + ' 00:00:00';
		def.setDate(def.getDate() - 1);
		btt.value = def.getFullYear() + '-' + 0 + (def.getMonth() + 1) + '-' + def.getDate() + ' 00:00:00';
	}

});

// 开始时间小于结束时间的检查
function check_time(id_a, id_b) {
	var b_value = document.getElementById(id_a).value, e_value = document.getElementById(id_b).value;
	return new Date(e_value.replace(/-/g, '/')).getTime() > new Date(b_value.replace(/-/g, '/')).getTime();
}

// 时间间隔检查
// @param begin,开始时间
// @param end,结束时间
// @param diff，时间间隔
// @return true，差小于diff时返回；反之false
function check_time_diff(begin, end, diff) {
	return (end - begin) > diff ? false : true;
}

function onSubmitSpeed() {

	var carId = document.getElementById('searchinput').value.trim();
	var beginTime = document.getElementById("beginTime").value.trim();
	var endTime = document.getElementById("endTime").value.trim();

	if (carId == "") {
		alert("请输入车牌号!")
		return false;
	}

	if (beginTime == "" || endTime == "") {
		alert("开始时间或结束时间不能为空!")
		return false;
	}

	if (!check_time('beginTime', 'endTime')) {
		alert("开始时间不能大于结束时间!");
		return false;
	}

	var b_value = new Date(document.getElementById("beginTime").value.replace(/-/g, '/')).getTime(), e_value = new Date(document.getElementById("endTime").value.replace(/-/g, '/')).getTime();
	if (!check_time_diff(b_value, e_value, 16 * 24 * 60 * 60 * 1000)) {
		alert("时间跨度不能大于{0}天！".replace("{0}", 16));
		return false;
	}
}

function onSubmitStop() {

	var carId = document.getElementById('searchinput').value.trim();
	var beginTime = document.getElementById("beginTime").value.trim();
	var endTime = document.getElementById("endTime").value.trim();

	if (carId == "") {
		alert("请输入车牌号!")
		return false;
	}

	if (beginTime == "" || endTime == "") {
		alert("开始时间或结束时间不能为空!")
		return false;
	}

	if (!check_time('beginTime', 'endTime')) {
		alert("开始时间不能大于结束时间!");
		return false;
	}

	var b_value = new Date(document.getElementById("beginTime").value.replace(/-/g, '/')).getTime(), e_value = new Date(document.getElementById("endTime").value.replace(/-/g, '/')).getTime();
	if (!check_time_diff(b_value, e_value, 31 * 24 * 60 * 60 * 1000)) {
		alert("时间跨度不能大于{0}天！".replace("{0}", 31));
		return false;
	}
}

function dateFmt(datetime) {
	var rtime = datetime.replace(/-/g, '').replace(/:/g, '').replace(/ /g, '')
	return rtime;
}
