<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
































    


    


    



    










    









    


    


    


    


    



    



    





    



























































































































































































































<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>运行总览</title>
    <link rel="stylesheet" type="text/css" href="http://static.gpsoo.net/v3/css/ecar_v3.css?v=20170705.1111" />
    <link rel="stylesheet" type="text/css" href="http://static.gpsoo.net/v3/css/report.css?v=20170705.1111" />
    <style>
        .a{width: auto}
        .b{width: 60px;}
        .c{width: 560px;}
        .Wdate{height: 22px !important;}
        #beginTime{width:90px !important;}
        #endTime{width:90px !important;}
        body{position: relative;}
        .faq{clear:both;background:#fff;padding:10px 10px 10px 20px;line-height:1.8;color:#666;}
        .faq h4{margin:0;padding:0 0 5px;border-bottom:1px solid #ccc;font-weight:bold;}
        .faq dl{margin:0;padding:5px 0 10px;}
        .faq dt{border:1px solid #A3B4C8;background:#E6EBF1;padding:6px 4px;cursor:pointer;}
        .faq dd{display:none;padding:6px 20px;}
    </style>
<script type="text/javascript" src="http://static.gpsoo.net/js/lang_reports_cn.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/v3/js/jquery-1.7.2.min.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/js/underscore.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/js/dialog.v2.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/date-util.js?v=20170705.1111" ></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/report.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/printer.js?v=20170705.1111"></script>
<script type="text/javascript" src="http://static.gpsoo.net/reports/js/highcharts-3.0.7.js?v=20170705.1111"></script>
<script>
var lang = 'cn';
//入口页面，locale设置到cookie中，ie不行
//_.cookie('locale',(lang=='en'?'en-us':'zh-cn'),{path:'/'});

var userListData = [];
var isloading = false;
var  print = false;
    function onSubmit(){
        if(isloading == true){
            print = false;
        }
        if(!report.checkBeginDate()){
            return false;
        }
        if(!check_time('beginTime','endTime')){
            //alert(lg.distime);
            return false;
        }

        var bt = document.getElementById('beginTime');
        document.getElementById('beginTimeUtc').value = new Date(bt.value.replace(/-/g,'/')).getTime();
        var et = document.getElementById('endTime');
        document.getElementById('endTimeUtc').value = new Date(et.value.replace(/-/g,'/')).getTime();

        var beginTimeTest = bt.value;
        var endTimeTest = et.value;
        beginTimeBefore = beginTimeTest;
        endTimeBefore = endTimeTest;
        runSummary.load();
        return false;
    }

    function check_time(id_a,id_b){
        var b_value = new Date(document.getElementById(id_a).value.replace(/-/g,'/')).getTime(),
                e_value = new Date(document.getElementById(id_b).value.replace(/-/g,'/')).getTime();
        if(e_value < b_value){
            alert(lg.distime);
            return false;
        }
        if((e_value - b_value)>31*24*60*60*1000){
            alert("时间跨度不能大于{0}天！".replace("{0}",31));
            return false;
        }
        return true;
    }

    function changeType(){
        var beginTime = new Date(document.getElementById("beginTime").value.replace(/-/g,'/')).getTime();
        var endTime = new Date(document.getElementById("endTime").value.replace(/-/g,'/')).getTime();
        window.location = "run_summaryByHour.shtml?enterprise_id=1122460&selectTarget=&channel=&beginTime="+beginTime+"&endTime="+endTime+"&locale="+lang+"&psip="+psip;
    }

    function changeRefer(url, name){
        url = url +'&searchinput='+ report.encode(name);
        url = encodeURI(url);
        var beginTime = new Date(document.getElementById("beginTime").value.replace(/-/g,'/')).getTime();
        var endTime = new Date(document.getElementById("endTime").value.replace(/-/g,'/')).getTime();
        if(url.indexOf('run_index.shtml') == -1){
            endTime += 1000*3600*24;
        }
        var beginTimeUser = document.getElementById("beginTime").value;
        var endTimeUser = document.getElementById("endTime").value;
        window.location = url + "&beginTime="+beginTime+"&endTime="+endTime+"&beginTimeUser="+beginTimeUser+"&endTimeUser="+endTimeUser+"&locale="+lang+"&psip="+psip;
    }

    function toRunPrint(){
        timechang();
if(isloading){
    var content = $('<div></div>').append($('#listtable').clone()).html();
    content = '<div style="text-align:center;">运行总览(按天统计) '+document.getElementById('beginTime').value+' - '+document.getElementById('endTime').value +'</div><br/>'+ content;
    Printer.view(content,false,'./');
}
    }

    function viewChart(){
        var $table = $('#listtable'),
                $chartWrapper = $('#chart-wrapper'),
                xAxis = [],
                mileageSerie = {name:'里程(公里)',data:[]},
                outSpeedSerie = {name:'超速(次)',data:[]},
                staySerie = {name:'停留(次)',data:[]};
        $table.find('tr[data-user-name]').each(function(i,v){
            var $this = $(this),
                    userName = $this.data('user-name'),
                    mileage = +$this.data('mileage'),
                    outSpeed = +$this.data('out-speed'),
                    stay = +$this.data('stay');
            xAxis.push(userName);
            mileageSerie.data.push(mileage);
            outSpeedSerie.data.push(outSpeed);
            staySerie.data.push(stay);
        });
        if($chartWrapper.is(':visible')){
            $chartWrapper.slideUp();
        }else{
            $chartWrapper.slideDown();
        }
        if(!$chartWrapper.data('inited')) {
            $chartWrapper.highcharts({
                credits: {
                    enabled: false
                },
                chart: {
                    type: 'column'
                },
                title: {
                    text: '运行总览'
                },
                xAxis: {
                    categories: xAxis
                },
                yAxis: {
                    min: 0,
                    title: ''
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [mileageSerie, outSpeedSerie, staySerie]
            }).data('inited', true);
        }
    }

</script>
</head>
<body id="run_summary">
<!-- bd [[ -->
<div id="bd">
    <!-- col-main [[ -->
    <div class="col-main" id="col-main" style="padding: 0!important;">
        <div class="mod-report">
            <div class="hd">
                <h2>
                    运行统计总览
                    [<span id="tip-btn" style="color: #0077FF;cursor: pointer;" title="">?</span>]
                </h2>
            </div>
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
                    <div class="rr">
                        <form id="submit-form" onSubmit="return onSubmit();" action="javascript://"
                              method="get" autocomplete="off">
                            <input name="enterprise_id" type="hidden" id="enterprise_id" value="1122460" />
                            <input id="channel" name="channel" type="hidden" value=""> <input
                                id="selectTarget" name="selectTarget" type="hidden"
                                value="">

                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="right" class="a">
                                        <input name="radiobutton" type="radio" id="radiobutton_a"
                                            onClick="window.location='run_summary.shtml?enterprise_id=1122460&selectTarget=&channel='"
                                            value="radiobutton" checked />
                                        <label for="radiobutton_a">按天统计</label>
                                        <input type="radio" name="radiobutton" id="radiobutton_b" value="radiobutton" onClick="changeType()" />
                                        <label for="radiobutton_b">按时段统计</label>
                                    </td>
                                    <td class="b">&nbsp;</td>
                                    <td class="c">
                                        开始时间：
                                        <input title="00:00:00" type="text" id="beginTime" size="15" readonly
                                            onClick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')||%y-%M-%d}'})"
                                            class="Wdate" value="">
                                        <input id="beginTimeUtc" type="hidden" name="beginTime" /> &nbsp;
                                        结束时间：
                                        <input title="23:59:59" type="text" id="endTime" size="15" readonly
                                            onClick="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})"
                                            class="Wdate" value="">
                                        <input id="endTimeUtc" type="hidden" name="endTime" />
                                        <span class="btn-submit"><button type="submit" name="Submit">查 询</button></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2"></td>
                                    <td style="padding-top: 5px;"><span style="visibility: hidden;">开始时间： </span>
                                        <a class="blue" href="javascript:void(0)" onclick="queryByDate(2,true);return false;">昨天</a>&nbsp;|&nbsp;
                                        <a class="blue" href="javascript:void(0)" onclick="queryByDate(3,true,true);return false;">本周</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(4,true);return false;">上周</a>&nbsp;|&nbsp;
                                        <a class="blue" href="javascript:void(0)" onclick="queryByDate(5,true,true);return false;">本月</a>&nbsp;<a class="blue" href="javascript:void(0)" onclick="queryByDate(6,true);return false;">上月</a>
                                    </td>
                                </tr>

                            </table>
                        </form>
                    </div>
                </div>
                <div class="block-data">

                    <!-- listing [[ -->
                    <div class="rr">
                        <div class="yunxing">

                            <div id="listing"></div>

                            <div id="chart-wrapper"></div>

                            <div class="pager">
                                <span style="display:none" id="btn-chart" class="btn-submit" onclick="viewChart();">
                                    <button type="button">图表</button>
                                </span>

                                <em>
                                    <span class="btn-excel" onclick="toRunPrint();"><button type="button">打印</button></span>
                                    <span class="btn-excel" onclick="runSummary.doExport();"><button type="button">Excel</button></span>
                                </em>
                            </div>

                        </div>

                    </div>
                    <!-- listing ]] -->

                    <script type="text/javascript">

        var timeLastDay = report.num2time(new Date().getTime()-1000*24*3600);//前一天
        var beginTimeUtc = timeLastDay;
        var endTimeUtc = timeLastDay;

                        //本地时间
                        var btt = document.getElementById('beginTime');
                        var ett = document.getElementById('endTime');
                        var f = new Date(Number(report.time2num(beginTimeUtc)));
                        var mm = f.getMinutes() , ss = f.getSeconds();
                        btt.value = f.getFullYear() + '-' + (f.getMonth()+1) + '-' + f.getDate();
                        var s = new Date(Number(report.time2num(endTimeUtc)));
                        var mmm = s.getMinutes(), sss = s.getSeconds();
                        ett.value = s.getFullYear() + '-'+ (s.getMonth() + 1) + '-'+ s.getDate();
                    </script>
                </div>
            </div>
        </div>
    </div>
    <!-- col-main ]] -->
</div>
<!-- bd ]] -->

<script type="text/javascript" src="http://static.gpsoo.net/reports/js/fix-table-header.js?v=20170705.1111"></script>
<script type="text/javascript">
var psip = $.cookie("psip");
if('in.gpsoo.net%2F' != '(none)'){
    psip = decodeURIComponent('in.gpsoo.net%2F');    
}
var API_SERVICE_URL = psip ? ("http://" + psip) : 'http://in.gpsoo.net/';
var runSummary = (function(){

    var
    eid = '1122460',
    $listing = $('#listing'),

    page = {
        pageno:0,
        pagesize:1000
    },

    load = function(pageno){

        pageno = pageno || 0;
        var data = {

            
            eid: eid,
            

            type: 0,
            beginTime: report.time2num($('#beginTime').val() + ' 00:00:00',10),
            endTime: +report.time2num($('#endTime').val() + ' 23:59:59',10)+1,
            timezone: report.timezone(),
            maptype: 'google',
            pageno: pageno,
            pagesize: page.pagesize
        };
        report.showLoading($listing);
        $.ajax({
            dataType: 'jsonp',
            data: data,

            
            url: API_SERVICE_URL+"api/report/v1/runstatus?method=runOverViewNew",
            

            success: function(json){
                if(json.errcode === 0){
                    render(json);
                    report.hideChart();
                }else{
                    //g.showErr(json);
                }
                report.showLoaded($listing);
                if(print){
                    var content = $('<div></div>').append($('#listtable').clone()).html();
                    content = '<div style="text-align:center;">运行总览(按天统计) '+document.getElementById('beginTime').value+' - '+document.getElementById('endTime').value +'</div><br/>'+ content;
                    Printer.view(content,false,'./');
                    print = false;
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
            '    <td class="first"><span>序号</span></td>',
            '    <td><span>设备名称</span></td>',
            '    <td><span>里程(公里)</span></td>',
            '    <td><span>超速</span></td>',
            '    <td><span>停留(次)</span></td>',
            '</tr>'
        ].join('\n'));

        if(len > 0 ){

            var key = json.keys;
            for(var i=0,len=data.length;i<len;i++){
                var item = data[i];
                var customer = '<a href="listByTree.php?uid='+item.id+'">'+item.count+'</a>';
                var strLocation = item.lng+','+item.lat;
                var cls = 'listrow' + i % 2;
                var equipid = item[key.id];
                var name = item[key.name];
                var milestat = item[key.milestat];
                var outspeed = item[key.outspeed];
                var stop = item[key.stop];
                var _outspeed = (outspeed == 0) ? outspeed : '<a href="javascript:changeRefer(\'overspeed_detail.shtml?enterprise_id=1122460&selectTarget='+ equipid +'&channel=\',\''+name+'\')">'+ outspeed +'</a>';
                var _stop = (stop == 0) ? stop : '<a href="javascript:changeRefer(\'stay_detail.shtml?enterprise_id=1122460&selectTarget='+ equipid +'&channel=\',\''+name+'\')">'+ stop +'</a>';
                html.push([
                    '<tr class="'+cls+'" data-user-name="'+ name +'" data-mileage="'+ milestat +'" data-out-speed="'+ outspeed +'" data-stay="'+ stop +'">',
                    '<td>'+(i+1)+'</td>',
                    '<td><a href="javascript:changeRefer(\'run_index.shtml?enterprise_id=1122460&selectTarget='+ equipid +'&channel=\',\''+name+'\')">'+ name +'</a></td>',
                    '<td>'+ milestat +'</td>',
                    '<td>'+ _outspeed +'</td>',
                    '<td>'+ _stop +'</td>',
                    '</tr>'
                ].join('\n'));
            }

            var total = json.summary;
            html.push([
                '<tr class="listrow total">',
                    '<td align="right"></td>',
                    '<td></td>',
                    '<td>'+ total.totalmilestat +'</td>',
                    '<td>'+ total.totaloutspeed +'</td>',
                    '<td>'+ total.totalstop +'</td>',
                '</tr>'
            ].join('\n'));
        }else{
            html.push(report.getHtmlRowNoData(6));
        }
        html.push('</table>');
        $listing.html(html.join('\n'));

        if(len > 0){
            report.bindTableRow();
            $('#btn-chart').show();

            fixTabledHeader.fix($('#listtable').find('.firstrow'),{
                wrapperStyle: 'left: 5px;',
                wrapperClass: 'listtable',
                container: $('body')
            });
        }
    },
    doExport = function(pageno){
        pageno = pageno || 0;
        var
        data = {
            eid: eid,
            type: 0,
            beginTime: report.time2num($('#beginTime').val() + ' 00:00:00',10),
            endTime: +report.time2num($('#endTime').val() + ' 23:59:59',10)+1,
            timezone: report.timezone(),
            maptype: 'google',
            pageno: pageno,
            pagesize: page.pagesize
        },
        url = API_SERVICE_URL+'api/report/excel/runstatus?method=runOverView&locale=cn&'+ report.map2param(data);
        window.open(url);
    },

    init = function(){
        report.bindTips();
        load();
    };

    init();

    return {
        load : load,
        doExport : doExport
    }
})();
    var beginTimeBefore='';
    var endTimeBefore='';
(function () {
    var beginTimeTest=$("#beginTime").val();
    var endTimeTest=$("#endTime").val();
    beginTimeBefore=beginTimeTest;
    endTimeBefore=endTimeTest;

})();
function timechang(){
    var beginTimeNow=$("#beginTime").val();
    var endTimeNow=$("#endTime").val();
if(beginTimeNow!=beginTimeBefore||endTimeNow!=endTimeBefore){
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

</body>
</html>
