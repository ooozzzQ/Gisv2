function higlightRow() {
    var e = this.className;
    e.indexOf("highlight") < 0 && (this.className += " highlight")
}
function removeHighlight() {
    var e = this.className;
    e.indexOf("highlight") > 0 && (this.className = e.replace("highlight", ""))
}
function getAddress(e, t) {
    t = t || 4;
    var n = e.getAttribute("lat") + "," + e.getAttribute("lng");
    if ("en" != lang) {
		var r = "cn" == lang ? parent.API_SERVICE_URL + "/o.jsp?i=" + e.getAttribute("lng") + "," + e.getAttribute("lat") + ",0&map=google": "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + e.getAttribute("lat") + "&lon=" + e.getAttribute("lng") + "&zoom=18&addressdetails=0",
		a = jQuery.ajax({
		    url: r,
		    dataType: "jsonp",
		    jsonp: "cn" == lang ? "callback": "json_callback",
		    success: function(n) {
		        try {
		            e.cells[t].innerHTML = "cn" == lang ? n: n.display_name
		        } catch(r) {
		            try {
		                e.cells[t].innerHTML = lg.AddressConfidentiality
		            } catch(r) {}
		        }
		    },
		    error: function() {
		        try {
		            e.cells[t].innerHTML = lg.AddressConfidentiality
		        } catch(n) {}
		    },
		    complete: function() {
		        a = null
		    }
		});
	} else if (window.google) {
        var i = new google.maps.Geocoder;
        i.geocode({
            address: n
        },
        function(n, r) {
            if (r == google.maps.GeocoderStatus.OK) {
                var a = n[0].formatted_address;
                a && (e.cells[t].title = a, e.cells[t].innerHTML = a)
            }
        })
    }
}
function showListLocation(e) {
    for (var t = "en" == lang ? 1e3: 50, n = document.getElementById("listtable").getElementsByTagName("tr"), r = 1; r < n.length; r++) {!
    function(r) {
        setTimeout(function() {
            getAddress(n[r], e)
        },
        (r - 1) * t)
    } (r)}
}
function queryByDate(e, t, n) {
    var r = new DatePicker({
        beginTimeInput: $("#beginTime"),
        endTimeInput: $("#endTime")
    });
    switch (e) {
    case 1:
        r.setToday(t);
        break;
    case 2:
        r.setYesterday(t);
        break;
    case 3:
        r.setThisWeek(t, n);
        break;
    case 4:
        r.setLastWeek(t);
        break;
    case 5:
        r.setThisMonth(t, n);
        break;
    case 6:
        r.setLastMonth(t)
    }
    $("#submit-form").submit()
}
var report = {
    pad: function(e, t) {
        var n = "",
        r = 0 > e,
        a = t || 2,
        i = String(Math.abs(e));
        return i.length < a && (n = new Array(a - i.length + 1).join("0")),
        (r ? "-": "") + n + i
    },
    time10to13: function(e) {
        return 10 === e.toString().length ? 1e3 * e: e
    },
    time13to10: function(e) {
        return e.toString().substr(0, 10)
    },
    time2num: function(e, t) {
        var n = e.toString().replace(/-/g, "/"),
        r = new Date(n),
        a = r.getTime();
        return t = t || 13,
        a.toString().substr(0, t)
    },
    num2time: function(e, t) {
        if (!e) {
			return "";
		}
        e = report.time10to13( + e);
        var n = new Date(e),
        r = n.getFullYear(),
        a = n.getMonth(),
        i = n.getDate(),
        o = n.getHours(),
        s = n.getMinutes(),
        c = n.getSeconds(),
        u = t ? " " + [report.pad(o, 2), report.pad(s, 2), report.pad(c, 2)].join(":") : "",
        l = [r, report.pad(a + 1, 2), report.pad(i, 2)].join("-") + u;
        return l
    },
    timelen2str: function(e, t) {
        t = t || "cn",
        e = +e;
        var n = {
            en: {
                d: "Day ",
                h: "Hour ",
                m: "Minute ",
                s: "Second "
            },
            cn: {
                d: "天",
                h: "小时",
                m: "分",
                s: "秒"
            }
        },
        r = n[t],
        a = [],
        i = Math.floor(e / 86400);
        i > 0 && (a.push(i.toString() + r.d), e -= 24 * i * 3600);
        var o = Math.floor(e / 3600);
        o > 0 && (a.push(o.toString() + r.h), e -= 3600 * o);
        var s = Math.floor(e / 60);
        s > 0 && (a.push(s.toString() + r.m), e -= 60 * s);
        var c = e;
        return c > 0 && a.push(c.toString() + r.s),
        a.join("")
    },
    timezone: function() {
        return - (new Date).getTimezoneOffset() / 60
    },
    timestamp: function() {
        return (new Date).getTime()
    },
    points2array: function(e) {
        for (var t, n = [], r = 0, a = e.length; a > r; r++) {t = e[r],
        n.push(t.lng + "," + t.lat);}
        return n
    },
    trim: function(e) {
        return e.replace(/^\s*|\s*$/g, "")
    },
    $_GET: function(e, t) {
        t = t || location.href.toString(),
        t = t.replace(/#.*/, "");
        var n = t.substring(t.indexOf("?") + 1, t.length).split("&"),
        r = {};
        for (i = 0, l = n.length; l > i; i++) {
            var a = n[i],
            o = a.substring(0, a.indexOf("=")).toLowerCase(),
            s = a.substring(a.indexOf("=") + 1, a.length);
            r[o] = s
        }
        var c = r[e.toLowerCase()];
        return "undefined" == typeof c ? "": c
    },
    map2param: function(e) {
        var t = [];
        return $.each(e,
        function(e, n) {
            t.push(e + "=" + n)
        }),
        t.join("&")
    },
    ajax: function(e, t, n, r) {
        if (r !== !1) {
            var a = $.cookie("sign");
            if (!a) {
				return alert("请先登录."),
				g.user.logout(),
				!1;
			}
            t.sign = a
        }
        $.ajax({
            url: e,
            data: t,
            dataType: "jsonp",
            jsonp: "callback",
            success: n
        })
    },
    encode: function(e) {
        return encodeURIComponent(e)
    },
    decode: function(e) {
        try {
            return decodeURIComponent(decodeURIComponent(e))
        } catch(t) {}
        return e
    },
    showLoading: function(e) {
        var t = '<span class="loading">loading</span>';
        e.html(t)
    },
    showLoaded: function() {},
    showErr: function(e, t) {
        var n = ["操作失败", "[" + e.errcode + "]" + e.msg];
        t && n.push("api:" + t),
        alert(n.join("\n"))
    },
    getHtmlRowNoData: function(e) {
        return e = e || 1,
        ['<tr class="listrow">', '    <td colspan="' + e + '">', '        <div class="info-msg"><span>' + lg.nodata + "</span></div>", "    </td>", "</tr>"].join("\n")
    },
    bindTableRow: function() {
        for (var e = document.getElementById("listtable").getElementsByTagName("tr"), t = 1, n = e.length; n - 1 > t; t++) {e[t].onmouseover = higlightRow,
        e[t].onmouseout = removeHighlight}
    },
    bindTips: function() {
        $("#tip-btn").click(function() {
            var e = $("#tip-content");
            e.is(":visible") ? e.slideUp() : e.slideDown()
        })
    },
    hideChart: function() {
        var e = $("#chart-wrapper");
        e.data("inited", !1),
        e.data("highcharts-chart", ""),
        e.html("").hide()
    },
    getPlaybackTime: function(e) {
        var t = report.time2num(e),
        n = t + 864e5,
        r = (new Date).getTime();
        return n > r && (n = r),
        {
            from: t,
            to: n
        }
    },
    getProductsByUserId: function(e) {
        function t(e) {
            var t = [];
            return $.each(e,
            function(e, n) {
                t.push([n.uid, n.name, n.imei])
            }),
            t
        }
        var n = {},
        r = report.$_GET("enterprise_id");
        "USER" !== channel ? n.eid = r: n.uid = r,
        $.ajax({
            dataType: "jsonp",
            data: n,
            url: parent.API_SERVICE_URL + "/api/report/v1/device?method=getProductsByUserId",
            success: function(n) {
                if (0 === n.errcode) {
                    var r = t(n.data);
                    userListData = r,
                    $("#equip-list").attr("class", "show-loaded"),
                    e && e(r)
                }
            }
        })
    },
    checkBeginDate: function(e) {
        var t = (new Date).getTime(),
        n = $("#beginTime");
        return e = e || n.val(),
        e = report.time2num(e),
        t - e > 160704e5 ? (alert(lg.reportBeginDateTip), n.focus(), !1) : !0
    },
    pager: function() {
        var e = page.pagecount;
        if ("undefined" != typeof e && 0 != e) {
            var t = 20,
            n = +page.pageno,
            r = [];
            n > e && (n = e),
            0 > n && (n = 0);
            var a = n - Math.ceil(t / 2);
            1 > a && (a = 1);
            var i = n + t / 2;
            i > e && (i = e + 1),
            n > 1 && (currentPage = n - 1, r.push("<a href='javascript:loadByEid(" + currentPage + ")'>&lt;</a>"));
            for (var o = a; i > o; o++) {
                var s = o - 1;
                r.push(s != n ? "<a href='javascript:loadByEid(" + s + ")'>" + o + "</a>": "<span class='current'>" + o + "</span>")
            }
            n + 1 != e && (currentPage = n + 1, r.push("<a href='javascript:loadByEid(" + currentPage + ")'>&gt;</a>")),
            $("#pages").html(r.join("\n"))
        }
    }
};