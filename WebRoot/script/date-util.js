function DateUtil(time) {
    this.time = time;
}

/***
 * 获得当前时间
 */
DateUtil.prototype.getCurrentDate = function () {
    return this.time || new Date();
};
/***
 * 获得本周起止时间
 */
DateUtil.prototype.getCurrentWeek = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //返回date是一周中的某一天
    var week = currentDate.getDay();

    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //减去的天数
    var minusDay = week != 0 ? week - 1 : 6;
    //alert(minusDay);
    //本周 周一
    var monday = new Date(currentDate.getTime() - (minusDay * millisecond));
    //本周 周日
    var sunday = new Date(monday.getTime() + (6 * millisecond));
    //添加本周时间
    startStop.push(monday); //本周起始时间
    //添加本周最后一天时间
    startStop.push(sunday); //本周终止时间
    //返回
    return startStop;
};

DateUtil.prototype.getMonthEndDay = function (year, month) {
    var d = new Date(year + '/' + month + '/01');
    return this.getCurrentMonth(d)[1];
};

/***
 * 获得本月的起止时间
 */
DateUtil.prototype.getCurrentMonth = function (m) {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = m || this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //求出本月第一天
    var firstDay = new Date(currentYear, currentMonth, 1);

    //当为12月的时候年份需要加1
    //月份需要更新为0 也就是下一年的第一个月
    if (currentMonth == 11) {
        currentYear++;
        currentMonth = 0; //就为
    } else {
        //否则只是月份增加,以便求的下一月的第一天
        currentMonth++;
    }

    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //下月的第一天
    var nextMonthDayOne = new Date(currentYear, currentMonth, 1);
    //求出上月的最后一天
    var lastDay = new Date(nextMonthDayOne.getTime() - millisecond);

    //添加至数组中返回
    startStop.push(firstDay);
    startStop.push(lastDay);
    //返回
    return startStop;
};

/**
 * 得到本季度开始的月份
 * @param month 需要计算的月份
 ***/
DateUtil.prototype.getQuarterSeasonStartMonth = function (month) {
    var spring = 0; //春
    var summer = 3; //夏
    var fall = 6; //秋
    var winter = 9; //冬
    //月份从0-11
    if (month < 3) {
        return spring;
    }

    if (month < 6) {
        return summer;
    }

    if (month < 9) {
        return fall;
    }

    return winter;
};

/**
 * 获得该月的天数
 * @param year 年份
 * @param month 月份
 * */
DateUtil.prototype.getMonthDays = function (year, month) {
    //本月第一天 1-31
    var relativeDate = new Date(year, month, 1);
    //获得当前月份0-11
    var relativeMonth = relativeDate.getMonth();
    //获得当前年份4位年
    var relativeYear = relativeDate.getFullYear();

    //当为12月的时候年份需要加1
    //月份需要更新为0 也就是下一年的第一个月
    if (relativeMonth == 11) {
        relativeYear++;
        relativeMonth = 0;
    } else {
        //否则只是月份增加,以便求的下一月的第一天
        relativeMonth++;
    }
    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //下月的第一天
    var nextMonthDayOne = new Date(relativeYear, relativeMonth, 1);
    //返回得到上月的最后一天,也就是本月总天数
    return new Date(nextMonthDayOne.getTime() - millisecond).getDate();
};

/**
 * 获得本季度的起止日期
 */
DateUtil.prototype.getCurrentSeason = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //获得本季度开始月份
    var quarterSeasonStartMonth = this.getQuarterSeasonStartMonth(currentMonth);
    //获得本季度结束月份
    var quarterSeasonEndMonth = quarterSeasonStartMonth + 2;

    //获得本季度开始的日期
    var quarterSeasonStartDate = new Date(currentYear, quarterSeasonStartMonth, 1);
    //获得本季度结束的日期
    var quarterSeasonEndDate = new Date(currentYear, quarterSeasonEndMonth, this.getMonthDays(currentYear, quarterSeasonEndMonth));
    //加入数组返回
    startStop.push(quarterSeasonStartDate);
    startStop.push(quarterSeasonEndDate);
    //返回
    return startStop;
};

/***
 * 得到本年的起止日期
 *
 */
DateUtil.prototype.getCurrentYear = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();

    //本年第一天
    var currentYearFirstDate = new Date(currentYear, 0, 1);
    //本年最后一天
    var currentYearLastDate = new Date(currentYear, 11, 31);
    //添加至数组
    startStop.push(currentYearFirstDate);
    startStop.push(currentYearLastDate);
    //返回
    return startStop;
};

/**
 * 返回上一个月的第一天Date类型
 * @param year 年
 * @param month 月
 **/
DateUtil.prototype.getPriorMonthFirstDay = function (year, month) {
    //年份为0代表,是本年的第一月,所以不能减
    if (month == 0) {
        month = 11; //月份为上年的最后月份
        year--; //年份减1
        return new Date(year, month, 1);
    }
    //否则,只减去月份
    month--;
    return new Date(year, month, 1);
};

/**
 * 获得上一月的起止日期
 * ***/
DateUtil.prototype.getPreviousMonth = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //获得上一个月的第一天
    var priorMonthFirstDay = this.getPriorMonthFirstDay(currentYear, currentMonth);
    //获得上一月的最后一天
    var priorMonthLastDay = new Date(priorMonthFirstDay.getFullYear(), priorMonthFirstDay.getMonth(), this.getMonthDays(priorMonthFirstDay.getFullYear(), priorMonthFirstDay.getMonth()));
    //添加至数组
    startStop.push(priorMonthFirstDay);
    startStop.push(priorMonthLastDay);
    //返回
    return startStop;
};

/**
 * 获得上一周的起止日期
 * **/
DateUtil.prototype.getPreviousWeek = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //返回date是一周中的某一天
    var week = currentDate.getDay();
    //一天的毫秒数
    var millisecond = 1000 * 60 * 60 * 24;
    //减去的天数
    var minusDay = week != 0 ? week - 1 : 6;
    //获得当前周的第一天
    var currentWeekDayOne = new Date(currentDate.getTime() - (millisecond * minusDay));
    //上周最后一天即本周开始的前一天
    var priorWeekLastDay = new Date(currentWeekDayOne.getTime() - millisecond);
    //上周的第一天
    var priorWeekFirstDay = new Date(priorWeekLastDay.getTime() - (millisecond * 6));

    //添加至数组
    startStop.push(priorWeekFirstDay);
    startStop.push(priorWeekLastDay);

    return startStop;
};

/**
 * 得到上季度的起始日期
 * year 这个年应该是运算后得到的当前本季度的年份
 * month 这个应该是运算后得到的当前季度的开始月份
 * */
DateUtil.prototype.getPriorSeasonFirstDay = function (year, month) {
    var spring = 0; //春
    var summer = 3; //夏
    var fall = 6; //秋
    var winter = 9; //冬
    //月份从0-11
    switch (month) { //季度的其实月份
        case spring:
            //如果是第一季度则应该到去年的冬季
            year--;
            month = winter;
            break;
        case summer:
            month = spring;
            break;
        case fall:
            month = summer;
            break;
        case winter:
            month = fall;
            break;

    }
    return new Date(year, month, 1);
};

/**
 * 得到上季度的起止日期
 * **/
DateUtil.prototype.getPreviousSeason = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前月份0-11
    var currentMonth = currentDate.getMonth();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    //上季度的第一天
    var priorSeasonFirstDay = this.getPriorSeasonFirstDay(currentYear, currentMonth);
    //上季度的最后一天
    var priorSeasonLastDay = new Date(priorSeasonFirstDay.getFullYear(), priorSeasonFirstDay.getMonth() + 2, this.getMonthDays(priorSeasonFirstDay.getFullYear(), priorSeasonFirstDay.getMonth() + 2));
    //添加至数组
    startStop.push(priorSeasonFirstDay);
    startStop.push(priorSeasonLastDay);
    return startStop;
};

/**
 * 得到去年的起止日期
 * **/
DateUtil.prototype.getPreviousYear = function () {
    //起止日期数组
    var startStop = [];
    //获取当前时间
    var currentDate = this.getCurrentDate();
    //获得当前年份4位年
    var currentYear = currentDate.getFullYear();
    currentYear--;
    var priorYearFirstDay = new Date(currentYear, 0, 1);
    var priorYearLastDay = new Date(currentYear, 11, 1);
    //添加至数组
    startStop.push(priorYearFirstDay);
    startStop.push(priorYearLastDay);
    return startStop;
};
/**
 * 格式化时间
 */
DateUtil.format = DateUtil.prototype.format = function (date, opts) {
    opts = opts||{};
    var utc = opts.utc ? 'getUTC' : 'get';
    var fstr = opts.format || '%y-%M-%d %h:%m:%s';
    return fstr.replace(/%[yMdhms]/g, function (m) {
        switch (m) {
            case '%y':
                return date[utc + 'FullYear']();
            case '%M':
                m = 1 + date[utc + 'Month']();
                break;
            case '%d':
                m = date[utc + 'Date']();
                break;
            case '%h':
                m = date[utc + 'Hours']();
                break;
            case '%m':
                m = date[utc + 'Minutes']();
                break;
            case '%s':
                m = date[utc + 'Seconds']();
                break;
            default:
                return m.slice(1);
        }
        return ((opts.padding===false?'':'0') + m).slice(-2);
    });
};

function DatePicker(opts){
    this.$beginTime = opts.beginTimeInput;
    this.$endTime = opts.endTimeInput;
    this.dateUtil = new DateUtil();
}

DatePicker.prototype.setToday = function(short){
    this.$beginTime.val(this.dateUtil.format(new Date(),{format:'%y-%M-%d'})+(short?'':' 00:00:00'));
    this.$endTime.val(this.dateUtil.format(new Date(),{format:short?'%y-%M-%d':null}));
};
DatePicker.prototype.setYesterday = function(short){
    var last = this.dateUtil.format(new Date(new Date()-24*60*60*1000),{format:'%y-%M-%d'});
    this.$beginTime.val(last+(short?'':' 00:00:00'));
    this.$endTime.val(last+(short?'':' 23:59:59'));
};
DatePicker.prototype.setThisWeek = function(short, removeToday){
    //由于后台数据是每天凌晨统计前一天，所以当前没有数据，需要排除今天
    //每周第一天不排除
    var thisWeek = this.dateUtil.getCurrentWeek(),
        endDay = new Date().getTime();
    if(removeToday && this.dateUtil.format(thisWeek[0],{format:'%y-%M-%d'}) != this.dateUtil.format(new Date(),{format:'%y-%M-%d'})){
        endDay = endDay-24*60*60*1000;
    }
    this.$beginTime.val(this.dateUtil.format(thisWeek[0],{format:'%y-%M-%d'})+(short?'':' 00:00:00'));
    this.$endTime.val(this.dateUtil.format(new Date(endDay-1000),{format:short?'%y-%M-%d':null}));
};
DatePicker.prototype.setLastWeek = function(short){
    var last = this.dateUtil.getPreviousWeek();
    this.$beginTime.val(this.dateUtil.format(last[0],{format:'%y-%M-%d'})+(short?'':' 00:00:00'));
    this.$endTime.val(this.dateUtil.format(last[1],{format:'%y-%M-%d'})+(short?'':' 23:59:59'));
};
DatePicker.prototype.setThisMonth = function(short, removeToday){
    //由于后台数据是每天凌晨统计前一天，所以当前没有数据，需要排除今天
    //每月第一天不排除
    var thisMonth = this.dateUtil.getCurrentMonth(),
        endDay = new Date().getTime();
    if(removeToday && this.dateUtil.format(thisMonth[0],{format:'%y-%M-%d'}) != this.dateUtil.format(new Date(),{format:'%y-%M-%d'})){
        endDay = endDay-24*60*60*1000;
    }
    this.$beginTime.val(this.dateUtil.format(thisMonth[0],{format:'%y-%M-%d'})+(short?'':' 00:00:00'));
    this.$endTime.val(this.dateUtil.format(new Date(endDay-1000),{format:short?'%y-%M-%d':null}));
};
DatePicker.prototype.setLastMonth = function(short){
    var last = this.dateUtil.getPreviousMonth();
    this.$beginTime.val(this.dateUtil.format(last[0],{format:'%y-%M-%d'})+(short?'':' 00:00:00'));
    this.$endTime.val(this.dateUtil.format(last[1],{format:'%y-%M-%d'})+(short?'':' 23:59:59'));
};