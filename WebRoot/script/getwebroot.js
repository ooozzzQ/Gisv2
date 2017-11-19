function getWebRootPath() {
	var webroot = document.location.href;
	webroot = webroot.substring(webroot.indexOf('//') + 2, webroot.length);
	webroot = webroot.substring(webroot.indexOf('/') + 1, webroot.length);
	webroot = webroot.substring(0, webroot.indexOf('/'));
	var rootpath = "/" + webroot;
	return rootpath;
}