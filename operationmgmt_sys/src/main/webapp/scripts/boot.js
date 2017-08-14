/*__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}*/
var head = document.getElementsByTagName("head")[0];
var bootPATH = "";//__CreateJSPath("boot.js");
var version = "";
(function() {
	var scripts = head.getElementsByTagName("script");
	for (var i = 0; i < scripts.length; i++) {
		var script = scripts[i];
		var regex = /boot\.js(\?v=.*)?/ig;
		result = regex.exec(script.src);
		if (result) {
			bootPATH = script.src.substring(0,script.src.indexOf("scripts/boot.js"));
			if (result[1]) {
				version = result[1];
			}
			break;
		}
	}
})()


//debugger
mini_debugger = false;   

//miniui
document.write('<script src="' + bootPATH + 'scripts/jquery/jquery-1.11.2.min.js' + version+ '" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'scripts/miniui/miniui.js' + version+ '" type="text/javascript" ></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'scripts/lf/lf.common.js' + version+ '" type="text/javascript" ></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'scripts/miniui/themes/default/miniui.css' + version+ '" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'scripts/miniui/themes/icons.css' + version+ '" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'styles/whole.css' + version+ '" rel="stylesheet" type="text/css" />');

//skin
var skin = getCookie("miniuiSkin");
if (skin) {
    document.write('<link href="' + bootPATH + 'miniui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');
}


////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}