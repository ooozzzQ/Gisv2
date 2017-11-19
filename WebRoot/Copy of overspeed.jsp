<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="http://static.gpsoo.net/v3/css/ecar_v3.css?v=20170705.1111" />
<link rel="stylesheet" type="text/css" href="http://static.gpsoo.net/v3/css/report.css?v=20170705.1111" />
<style>
    .Wdate{height:20px !important;}
    body{position: relative;}
</style>
<script type="text/javascript" src="http://static.gpsoo.net/js/lang_reports_cn.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/public.js?v=20170705.1111" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/v3/js/jquery-1.7.2.min.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/date-util.js?v=20170705.1111" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/report.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/drag.js"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/excel.js"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/printer.js?v=20170705.1111"></script>
<title>超速详单</title>

<script type="text/javascript">
var channel = ""
var enterprise_id = "1122460"
var eid = "1122460"
var selectTarget = ""
var requestSource = "web"
</script>

<script type="text/javascript">
var userListData = [];
var loginUrl = decodeURIComponent("http%3A%2F%2Fwww.gpsoo.net%2F").replace("\\.","1.")
var logout = decodeURIComponent("").replace("\\.","1.")
var isloading = false;
var  print = false;
function onSubmit(){
    if(isloading == true){
        print = false;
    }
    if(!report.checkBeginDate()){
        return false;
    }
    
    var ele = document.getElementById('searchinput');
    if(!ele.value || ele.value == lg.inputTip){
        alert(lg.selectTarget);
        return false;
    }
    
    if(!check_time('beginTime','endTime')){
        alert(lg.distime);
        return false;
    }
    var b_value = new Date(document.getElementById("beginTime").value.replace(/-/g,'/')).getTime(),
        e_value = new Date(document.getElementById("endTime").value.replace(/-/g,'/')).getTime();
    if(!check_time_diff(b_value,e_value,16*24*60*60*1000)){
        alert("时间跨度不能大于{0}天！".replace("{0}",16));
        return false;
    }
    var selectTarget = document.getElementById("userid").value;
    if (selectTarget == "ALL" || selectTarget==""){
        alert("请输入您要统计设备的设备名称!");
        return false;
    }

    document.getElementById('beginTimeUtc').value = b_value;
    document.getElementById('endTimeUtc').value = e_value;

    var beginTimeTest = $("#beginTime").val();
    var endTimeTest = $("#endTime").val();
    var deviceName = $('#searchinput').attr('presearvalue');
    beginTimeBefore = beginTimeTest;
    endTimeBefore = endTimeTest;
    deviceNameBefore = deviceName;
    overSpeedDetail.load();

    return false;
}

var lang = 'cn';


</script>
</head>
<body>
<!-- bd [[ -->
<div id="bd">

    <!-- col-main [[ -->
    <div class="col-main" id="col-main" style="padding: 0!important;">
        <div class="mod-report">
            <div class="hd"><h2>超速详单[<span id="tip-btn" style="color: #0077FF;cursor: pointer;" title="">?</span>]</h2></div>
            <div class="bd">
                <div id="tip-content" class="description">
                
                    <h4>常见问题：</h4>
                    <dl>
                        <dt>问：为什么[运行总览]页面 和[超速详单]里的[超速次数]不一致？</dt>
                        <dd>
                            答：因为两个页面采用的统计方式不一样。<br>
                            1 总览统计里 是系统每晚，根据当时设置的超速值 来统计并保存当天的超速数据。(即如果第二天修改超速值也不会影响前一天的超速次数)<br>
                            2 详单页面 是实时去取GPS数据来进行统计，根据最新的超速值来进行统计。<br>
                            因此，查询多天时，其间如果设备超速值有修改过，则两边的次数就可能不一样。<br>
                        </dd>
                    </dl>
                
                </div>

                <div class="block-forms">

      <form id="submit-form" action="javascript://" method="get" onSubmit="return onSubmit();" autocomplete="off">
        <input name="enterprise_id" type="hidden" id="enterprise_id" value="1122460"/>
        <input id="channel" name="channel" type="hidden" value="">

    <div style="text-align:left;">
    <div id="searchListOutCanvas" style="margin-top:20px;margin-left:65px;"></div>


<div id="equip-list" class="show-loading">
    <div class="title">设备名称：</div>
    <div class="loading">loading</div>
    <div class="list"><input presearvalue="" autocomplete="off" name="searchinput" type="text" id="searchinput" onFocus="searchFocus(this)" onBlur="searchBlur(this)"  value="" /></div>
    <div class="btn"><a onClick="viewSearchListAll()" onBlur="isSearchFocus = false;" href="javascript:void(0);"><img height="20" border="0" align="absmiddle" width="33" src="http://static.gpsoo.net/reports/images/productsearch_03.png"></a></div>
</div>

<input name="selectTarget" type="hidden" id="userid" value="" />

<script type="text/javascript">
    (function(){
        var searchinput = report.decode('');
        $('#searchinput').val(searchinput).attr('presearvalue',searchinput);
    })();
report.getProductsByUserId();
</script>

          &nbsp;
    </div>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>开始时间：
         <input type="text" id="beginTime" size="15" readonly onClick="WdatePicker({isShowClear:false,maxDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:00:00'});" class="Wdate" style="width:130px;" value="">
          <input id="beginTimeUtc" type="hidden" name="beginTime" />
          &nbsp;结束时间：
          <input type="text" id="endTime" size="15"  readonly onClick="WdatePicker({isShowClear:false,maxDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:00:00'});" style="width:130px;" class="Wdate" value="">
          <input id="endTimeUtc" type="hidden" name="endTime" />
          <span class="btn-submit"><button type="submit" name="Submit">查 询</button></span>
          </td>
        </tr>
        <tr>
            <td style="padding-top: 5px;"><span style="visibility: hidden;">开始时间：</span>
                <a class="blue" href="javascript:void(0)" onclick="queryByDate(1);return false;">今天</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(2);return false;">昨天</a>&nbsp;|&nbsp;
                <a class="blue" href="javascript:void(0)" onclick="queryByDate(3);return false;">本周</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(4);return false;">上周</a>
            </td>
        </tr>

    </table>
      </form>

                </div>
                <div class="block-data">

     <div id="excelContainer">

        <div id="listing"></div>

        <div class="pager">
            <em>
                <span class="btn-excel" onclick="toRunPrint();"><button type="button">打印</button></span>
                <span class="btn-excel" onclick="overSpeedDetail.doExport()"><button type="button">Excel</button></span>
            </em>
        </div>

     </div>
     <form id="export-form" action="" method="post" name="exportFm" autocomplete="off">
     </form>
</div>
<script type="text/javascript">
var beginTime = '';//用户输入的时间
var endTime = '';//用户输入的时间
//本地时间
var btt = document.getElementById('beginTime');
var ett = document.getElementById('endTime');
if(!beginTime || !endTime){
    var def = new Date();
    ett.value = def.getFullYear() + '-' + (def.getMonth()+1) + '-' + def.getDate()+ ' 00:00:00';
    def.setDate(def.getDate()-1);
    btt.value = def.getFullYear() + '-' + (def.getMonth()+1) + '-' + def.getDate()+ ' 00:00:00';
}else{
    var beginTimeUtc = '';
    var endTimeUtc = '';
    if(endTimeUtc === beginTimeUtc){
        endTimeUtc = +beginTimeUtc + 24*3600*1000;
    }
    btt.value = report.num2time(beginTimeUtc,true);
    ett.value = report.num2time(endTimeUtc,true);
}

function toRunPrint(){
    timechang();
    if(isloading){
        var content = $('<div></div>').append($('#listtable').clone()).html();
        content = '<div style="text-align:center;">运行总览(按天统计) '+document.getElementById('beginTime').value+' - '+document.getElementById('endTime').value +'</div><br/>'+ content;
        Printer.view(content,false,'./');
    }
}
</script>

                </div>
            </div>

        </div>

    </div>
    <!-- col-main ]] -->
</div>
<!-- bd ]] -->
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/inputsearch2.js" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/fix-table-header.js?v=20170705.1111"></script>

<script type="text/javascript">
//ip-API_SERVICE_URL
var psip = $.cookie("psip");
if('in.gpsoo.net%2F' != '(none)'){
    psip = decodeURIComponent('in.gpsoo.net%2F');    
}
var API_SERVICE_URL = psip ? ("http://" + psip) : 'http://in.gpsoo.net/'; 
var overSpeedDetail = (function(){

    var
    eid = '1122460',
    $listing = $('#listing'),
    user_id = '';

    page = {
        pageno:0,
        pagesize:1000
    },

    load = function(pageno){
        pageno = pageno || 0;
        user_id =  $('#userid').val();
        var data = {
            id: user_id,
            type: 0,
            beginTime: report.time2num($('#beginTime').val(),10),
            endTime: report.time2num($('#endTime').val(),10),
            timezone: report.timezone(),
            maptype: 'google',
            pageno: pageno,
            pagesize: page.pagesize
        };
        report.showLoading($listing);
        $.ajax({
            dataType: 'jsonp',
            data: data,
            url: API_SERVICE_URL+"api/report/v1/runstatus?method=outspeed",
            success: function(json){
                if(json.errcode === 0){
                    render(json);
                }
                report.showLoaded($listing);
                if(print){
                    var content = $('<div></div>').append($('#listtable').clone()).html();
                    content = '<div style="text-align:center;">运行总览(按天统计) '+document.getElementById('beginTime').value+' - '+document.getElementById('endTime').value +'</div><br/>'+ content;
                    Printer.view(content,false,'./');
                    print = false;
                }
                if(json.summary.dataflag == 0 && confirm(lg.datatomuch)) {
                    var exportForm = document.forms["exportFm"];
                    var data = {
                        id: $('#userid').val(),
                        type: 0,
                        beginTime: report.time2num($('#beginTime').val(),10),
                        endTime: report.time2num($('#endTime').val(),10),
                        timezone: report.timezone(),
                        maptype: 'google',
                        pageno: 0,
                        pagesize: page.pagesize
                    };
                    exportForm.action = API_SERVICE_URL+'api/report/excel/runstatus?method=outspeed&locale=cn&'+ report.map2param(data);
                    exportForm.submit();
                    alert(lg.downloading);
                }
            }
        });
    },

    render = function(json){
        var data = json.data;
        var username = $('#searchinput').val();
        var len = data.length;
        var html = [];
        html.push('<table width="100%" border="0" cellpadding="3" cellspacing="0" class="listtable" id="listtable">');

         html.push([
            '<tr class="firstrow">',
            '   <td width="60" class="first"><span>序号</span></td>',
            '   <td width="140"><span>定位时间</span></td>',
            '   <td width="100"><span>速度</span></td>',
            '   <td width="120"><span>经度</span></td>',
            '   <td width="120"><span>纬度</span></td>',
            '   <td><span>地点</span></td>',
            '   <td width="80"><span>操作</span></td>',
            '</tr>'
        ].join('\n'));

        if(len > 0 ){
            var key = json.keys;
            for(var i=0,len=data.length;i<len;i++){
                var item = data[i];
                var cls = 'listrow' + i % 2;
                var time = item[key.time];
                var timePlayback = report.getPlaybackTime(time);
                var from = timePlayback.from;
                var to = timePlayback.to;
                var speed = item[key.speed];
                var lon = item[key.lon];
                var lat = item[key.lat];
                var playback =  '<a title="回放" href="../user/playback.shtml?v=20170705.1111&loginUrl='+ loginUrl.replace(/\./g, '1.') +'&requestSource=web&objectid='+ user_id +'&lang=cn&mapType=BAIDU&from='+ from +'&to='+ to +'&psip='+psip.replace(/\./g, '1.') +'" target="_blank">'+ time +'</a>';
                var overspeed = (overspeed == 0) ? '(none)' : '<a href="javascript:changeRefer(\'overspeed_detail.shtml?enterprise_id=1122460&selectTarget='+ user_id +'&searchinput='+ username +'&channel=\',\'(none)\',\'(none)\')">(none)</a>';
                html.push([
                    '<tr class="'+cls+'" lng="'+ lon +'" lat="'+ lat +'">',
                    '<td>'+(i+1)+'</td>',
                    '<td>'+ playback +'</td>',
                    '<td>'+ speed +'</td>',
                    '<td>'+ lon +'</td>',
                    '<td>'+ lat +'</td>',
                    '<td>'+ lon +','+ lat +'</td>',
                    '<td><a href="http://ditu.google.cn/maps?hl=zh-CN&amp;tab=wl&q='+lat+','+ lon +'" target="_blank">查看位置</a></td>',
                    '</tr>'
                ].join('\n'));
            }

        }else{
            html.push(report.getHtmlRowNoData(7));
        }
        html.push('</table>');
        $listing.html(html.join('\n'));

        if(len > 0){
            report.bindTableRow();

            fixTabledHeader.fix($('#listtable').find('.firstrow'),{
                wrapperStyle: 'left: 5px;',
                wrapperClass: 'listtable',
                container: $('body')
            });

            showListLocation(5);
        }
    },

    doExport = function(pageno){
        pageno = pageno || 0;
        user_id =  $('#userid').val();
        if (user_id == "ALL" || user_id==""){
            alert("请输入您要统计设备的设备名称!");
            return false;
        }
        var data = {
            id: user_id,
            type: 0,
            beginTime: report.time2num($('#beginTime').val(),10),
            endTime: report.time2num($('#endTime').val(),10),
            timezone: report.timezone(),
            maptype: 'google',
            pageno: pageno,
            pagesize: page.pagesize
        };
        url = API_SERVICE_URL+'api/report/excel/runstatus?method=outspeed&locale=cn&'+ report.map2param(data);
        window.open(url);
    },

    init = function(){
        report.bindTips();
        
        var ele = document.getElementById('searchinput'); 
        if(ele.value &&  ele.value != lg.inputTip){
            load();
        }
        
    };

    init();

    return {
        load : load,
        doExport: doExport
    }
})();
var beginTimeBefore='';
var endTimeBefore='';
var deviceNameBefore = '';
(function () {
    var beginTimeTest=$("#beginTime").val();
    var endTimeTest=$("#endTime").val();
    var deviceName = $('#searchinput').attr('presearvalue');
    beginTimeBefore=beginTimeTest;
    endTimeBefore=endTimeTest;
    deviceNameBefore = deviceName;


})();
function timechang(){
    var beginTimeNow=$("#beginTime").val();
    var endTimeNow=$("#endTime").val();
    var deviceNameNow = $('#searchinput').attr('presearvalue');
    if(beginTimeNow!=beginTimeBefore||endTimeNow!=endTimeBefore||deviceNameNow!=deviceNameBefore){
        print = true;
        isloading = false;
        onSubmit();
    }else{
        isloading = true;
    }
}
</script>

</body>
</html>
