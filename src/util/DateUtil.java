package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	/**
	 * 切割时间段
	 *
	 * @param dateType
	 *            交易类型 M/D/H/N -->每月/每天/每小时/每分钟
	 * @param start
	 *            yyyyMMddhhmmss
	 * @param end
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static List<String> cutDate(String dateType, String start, String end) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			Date dBegin = sdf.parse(start);
			Date dEnd = sdf.parse(end);
			return findDates(dateType, dBegin, dEnd);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> findDates(String dateType, Date dBegin, Date dEnd) throws Exception {
		List<String> listDate = new ArrayList<String>();
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		 System.out.println("calBegin =" + calBegin.getTime() + ", calEnd=" + calEnd.getTime()  + ",=" + calEnd.after(calBegin));
		while (calEnd.after(calBegin)) {
			if (calEnd.after(calBegin)) {
				listDate.add(new SimpleDateFormat("yyyyMMddHHmmss").format(calBegin.getTime()));
			}
			switch (dateType) {
			case "M":
				calBegin.add(Calendar.MONTH, 1);
				break;
			case "D":
				calBegin.add(Calendar.DAY_OF_YEAR, 1);
				break;
			case "H":
				calBegin.add(Calendar.HOUR, 1);
				break;
			case "N":
				calBegin.add(Calendar.SECOND, 1);
				break;
			}
		}
		listDate.add(new SimpleDateFormat("yyyyMMddHHmmss").format(calEnd.getTime()));
		return listDate;
	}
}
