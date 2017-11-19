package util;

public class GlobalConstant {
	//超速阈值设置,生产环境应设置为60
	public static final float OVERSPEED_THRESHOLD = 30;
	//分页中每页数据记录数,生产环境设置8-10个为宜
	public static final int  NUMBERPAGE = 100;
	//静止停留时速度阈值,小于该值人为车辆在该点一直停留4.5
	public static final double  STASTICSPEED_THRESHOLD = 4.5;
	//停留时间判断阈值,停留时间小于3分钟认为等红绿灯，不计入统计，超过3分钟认为停留一次，计入统计
	public static final int  STOPTIME_MINUTE_THRESHOLD = 3;
}
