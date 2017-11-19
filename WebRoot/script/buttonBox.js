$("run").onclick = function() {
	lushu.start();
}
$("stop").onclick = function() {
	lushu.stop();
}
$("pause").onclick = function() {
	lushu.pause();
}
$("hide").onclick = function() {
	lushu.hideInfoWindow();
}
$("show").onclick = function() {
	lushu.showInfoWindow();
}
function $(element) {
	return document.getElementById(element);
}
