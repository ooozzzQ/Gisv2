package action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import model.Car;
import model.StopRecord;
import service.CarServiceInterface;
import service.StatisticsServiceInferface;
import util.BaiduApiUtil;
import util.DateUtil;
import util.GlobalConstant;
import util.PagesSource;
import util.ScriptEngineUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.MiniAdmin;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticsAction extends ActionSupport {

	private String carId;
	private HttpServletResponse response;
	private String beginTime;
	private String endTime;
	private PagesSource pageSource;
	private int pageNum;
	private StatisticsServiceInferface statisticsServiceInferface;
	private CarServiceInterface carServiceInterface;

	List<Car> allCarList = new ArrayList<Car>();

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public StatisticsServiceInferface getStatisticsServiceInferface() {
		return statisticsServiceInferface;
	}

	public void setStatisticsServiceInferface(StatisticsServiceInferface statisticsServiceInferface) {
		this.statisticsServiceInferface = statisticsServiceInferface;
	}

	public CarServiceInterface getCarServiceInterface() {
		return carServiceInterface;
	}

	public void setCarServiceInterface(CarServiceInterface carServiceInterface) {
		this.carServiceInterface = carServiceInterface;
	}

	public List<Car> getAllCarList() {
		return allCarList;
	}

	public void setAllCarList(List<Car> allCarList) {
		this.allCarList = allCarList;
	}

	public PagesSource getPageSource() {
		return pageSource;
	}

	public void setPageSource(PagesSource pageSource) {
		this.pageSource = pageSource;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getMileageStatisticsByCarId() {
		try {
			int overSpeedCount = 0;
			int stopCount = 0;
			double mileage = 0;
			String carIdTemp = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
			String beginTimeTemp = dateFmtByReplace(beginTime);
			String endTimeTemp = dateFmtByReplace(endTime);
			System.out.println(beginTimeTemp + "==" + endTimeTemp);
			List<String> listDates = DateUtil.cutDate("D", beginTimeTemp, endTimeTemp);
			for (int i = 0; i < listDates.size() - 1; i++) {
				// 超速次数
				String start = listDates.get(i);
				String end = listDates.get(i + 1);
				overSpeedCount = statisticsServiceInferface.getOverSpeedCountByCarId(carIdTemp, start, end);
				stopCount = getStopCount(carIdTemp, start, end);
				mileage = getMileageOneDay(carIdTemp, start, end);
				Car car = new Car();
				car.setCAR_ID(carIdTemp);
				car.setTIME(start);
				car.setStopCount(stopCount);
				car.setOverSpeedCount(overSpeedCount);
				car.setMileage(mileage);
				allCarList.add(car);
			}

			ActionContext.getContext().put("carIdTemp", carIdTemp);
			ActionContext.getContext().put("beginTimeTemp", beginTime.replace(",", ""));
			ActionContext.getContext().put("endTimeTemp", endTime.replace(",", ""));
			ActionContext.getContext().put("overspeedThreshold", GlobalConstant.OVERSPEED_THRESHOLD);
			ActionContext.getContext().put("allCarList", allCarList);
			return "mileageAllCarList";

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "获取车辆里程统计失败";
			ActionContext.getContext().put("msg", msg);
			return "mileageStatisticError";
		}
	}

	public String getOverSpeedListByCarId() {
		try {
			// pageSource.setNumberPage(8);

			String carIdTemp = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
			String beginTimeTemp = dateFmtByReplace(beginTime);
			String endTimeTemp = dateFmtByReplace(endTime);
			System.out.println(carIdTemp + beginTime + endTime);
			pageSource = new PagesSource(1, GlobalConstant.NUMBERPAGE);
			if (pageNum != 0) {
				pageSource.setNowPage(pageNum);
			}
			int overspeedCount = statisticsServiceInferface.getOverSpeedCountByCarId(carIdTemp, beginTimeTemp, endTimeTemp);
			pageSource.setTotalCount(overspeedCount);
			allCarList = statisticsServiceInferface.getOverSpeedRecordByCarId(carIdTemp, pageSource, beginTimeTemp, endTimeTemp);
			System.out.println(allCarList.toString());
			ActionContext.getContext().put("carIdTemp", carIdTemp);
			ActionContext.getContext().put("beginTimeTemp", beginTime.replace(",", ""));
			ActionContext.getContext().put("endTimeTemp", endTime.replace(",", ""));
			ActionContext.getContext().put("overspeedCount", overspeedCount);
			ActionContext.getContext().put("page", pageSource);
			ActionContext.getContext().put("allCarList", allCarList);
			return "overSpeedAllCarList";

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "获取车辆超速列表失败";
			ActionContext.getContext().put("msg", msg);
			return "overSpeedError";
		}

	}

	public double getMileageOneDay(String carIdTemp, String beginTimeTemp, String endTimeTemp) {
		List<Car> carList = carServiceInterface.getLonLatList(beginTimeTemp, endTimeTemp, carIdTemp.trim());
		if (carList.size() > 0 ) {
			return BaiduApiUtil.getMileage(carList);
		}else {
			return 0;
		}
		
	}

	public int getStopCount(String carIdTemp, String beginTimeTemp, String endTimeTemp) {
		// 停留次数
		int stopCount = 0;
		List<Car> originalStopCarList = statisticsServiceInferface.getStopStatisticsRecordByCarId(carIdTemp, beginTimeTemp, endTimeTemp);
		if (originalStopCarList.size() > 2) {
			List<Car> processedStopCarList = getStopDataProcessedCarList(originalStopCarList);
			stopCount = processedStopCarList.size();
		}
		return stopCount;
	}

	public String getStopStatisticsByCarId() {
		try {
			String carIdTemp = new String(carId.trim().getBytes("ISO-8859-1"), "UTF-8");
			String beginTimeTemp = dateFmtByReplace(beginTime);
			// 测试数据开始String beginTimeTemp ="20170614173602";
			String endTimeTemp = dateFmtByReplace(endTime);

			List<Car> carList = statisticsServiceInferface.getStopStatisticsRecordByCarId(carIdTemp, beginTimeTemp, endTimeTemp);
			if (carList.size() > 2) {
				// System.out.println("查询完数据库的数据:" + carList.toString());
				allCarList = getStopDataProcessedCarList(carList);
			}
			// System.out.println("停止总数=" + stopCount + allCarList.toString());
			ActionContext.getContext().put("carIdTemp", carIdTemp);
			ActionContext.getContext().put("beginTimeTemp", beginTime.replace(",", ""));
			ActionContext.getContext().put("endTimeTemp", endTime.replace(",", ""));
			ActionContext.getContext().put("allCarList", allCarList);
			return "stopAllCarList";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "获取车辆停留统计失败";
			ActionContext.getContext().put("msg", msg);
			return "stopStatisticError";
		}

	}

	public List<Car> getStopDataProcessedCarList(List<Car> carList) {
		// 控制速度段还是静止段的切换
		boolean staticStackBeginFirstFlag = true;
		// 控制首次如果是静止点,开始停留时间设置为list中第一个元素
		boolean locationFlag = false;
		// 控制首次如果是速度点,开始停留时间设置为之后循环遇到的第一个静止点
		boolean speedStackBeginFirstFlag = true;

		List<Car> stopAllCarList = new ArrayList<Car>();
		List<Car> stopBeginTimeCarList = new ArrayList<Car>();
		Car carBeginTimeCar = null;
		for (int i = 0; i < carList.size() - 1; i++) {
			Car car1 = carList.get(i);
			Car car2 = carList.get(i + 1);
			String lonsub = getLonsub(car2, car1);
			String latsub = getLatsub(car2, car1);
			if (i == 0) {
				if (lonsub.contains("E-4") || latsub.contains("E-4")) {
					speedStackBeginFirstFlag = true; // 首次进入、首个点位置变化、speedStackBeginFirstFlag
														// 设置true
				} else {
					speedStackBeginFirstFlag = false; // 首次进入、首个点位置没有变化、speedStackBeginFirstFlag
														// 设置false,说明静止的点
				}
			}
			// System.out.println("经度差=" + lonsub + "，纬度差=" + latsub + " ,id=" +
			// car2.getID() + ",car2速度" + car2.getSPEED() +
			// ",speedStackBeginFirstFlag : " + speedStackBeginFirstFlag +
			// " ,staticStackBeginFirstFlag: " + staticStackBeginFirstFlag +
			// " ,locationFlag :" + locationFlag);

			if (speedStackBeginFirstFlag) {
				if (lonsub.contains("E-4") || latsub.contains("E-4")) {
					continue; // 若首次进入为速度点，则继续continue,直到遇到静止点
				} else {
					stopBeginTimeCarList.add(car1);
					speedStackBeginFirstFlag = false;
				}
			} else {
				if (staticStackBeginFirstFlag) {
					// System.out.println("速度静止的点");
					if (lonsub.contains("E-4") || latsub.contains("E-4")) {
						if (!locationFlag && speedStackBeginFirstFlag == false) {
							carBeginTimeCar = carList.get(0);
							locationFlag = true;
						} else {
							// System.out.println("每次开始的数据:" +
							// stopBeginTimeCarList.toString());
							carBeginTimeCar = stopBeginTimeCarList.get(stopBeginTimeCarList.size() - 1);
						}
						Date stopBeginTimeDate = stringToDate(carBeginTimeCar.getTIME());
						Date stopEndTimeDate = stringToDate(car1.getTIME());

						String stopTime = getBetweenTime(stopEndTimeDate, stopBeginTimeDate);
						if (stopTime != "") {
							String stopBeginTime = dateToString(stopBeginTimeDate);
							String stopEndTime = dateToString(stopEndTimeDate);
							StopRecord stopRecord = new StopRecord();
							stopRecord.setStopBeginTime(stopBeginTime);
							stopRecord.setStopEndTime(stopEndTime);
							stopRecord.setStopTime(stopTime);
							car1.setStopRecord(stopRecord);
							// System.out.println("开始时间id： " +
							// carBeginTimeCar.getID() + " ,开始时间：" +
							// stopBeginTime + "  ,结束时间id: " + car1.getID() +
							// " ,结束时间:" + stopEndTime + " ,停留时间:" + stopTime);
							// System.out.println("---------------------------------------------------------------------------------");
							stopAllCarList.add(car1);
						}
						staticStackBeginFirstFlag = false;
					}
				} else {
					// System.out.println("运动．．．．．．");
					if (!lonsub.contains("E-4") && !latsub.contains("E-4")) {
						stopBeginTimeCarList.add(car1);
						staticStackBeginFirstFlag = true;
						// System.out.println("stack-------------");
					}
				}

			}

		}
		return stopAllCarList;
	}

	public String getLonsub(Car car2, Car car1) {
		BigDecimal bdcar2lon = new BigDecimal(car2.getLON());
		BigDecimal bdcar1lon = new BigDecimal(car1.getLON());
		String lonsub = Double.toString(bdcar2lon.subtract(bdcar1lon).doubleValue());
		return lonsub;
	}

	public String getLatsub(Car car2, Car car1) {
		BigDecimal bdcar2lat = new BigDecimal(car2.getLAT());
		BigDecimal bdcar1lat = new BigDecimal(car1.getLAT());
		String latsub = Double.toString(bdcar2lat.subtract(bdcar1lat).doubleValue());
		return latsub;
	}

	public String dateFmtByReplace(String datetime) {
		String rtime = datetime.replace(",", "").replace("-", "").replace(":", "").replace(" ", "");
		return rtime;
	}

	public String dateToString(Date date) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf2.format(date);
	}

	public Date stringToDate(String s) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
			Date date = (Date) sdf1.parse(s);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String getBetweenTime(Date end, Date begin) {
		long between = (end.getTime() - begin.getTime());// 除以1000是为了转换成秒
		long day1 = between / (24 * 60 * 60 * 1000);
		long hour1 = (between / (60 * 60 * 1000) - day1 * 24);
		long minute1 = ((between / (60 * 1000)) - day1 * 24 * 60 - hour1 * 60);
		long second1 = (between / 1000 - day1 * 24 * 60 * 60 - hour1 * 60 * 60 - minute1 * 60);
		day1 = Math.abs(day1);
		hour1 = Math.abs(hour1);
		minute1 = Math.abs(minute1);
		second1 = Math.abs(second1);
		// System.out.println("-------------=day1=" + day1 + ",hour1= " + hour1
		// + " ,minite1 =" + minute1 + " ,second=" + second1);
		if (day1 != 0) {
			return day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (hour1 != 0) {
			return hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (minute1 != 0) {
			if (minute1 > GlobalConstant.STOPTIME_MINUTE_THRESHOLD) {
				return minute1 + "分" + second1 + "秒";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getBetweenTime_bak(Date end, Date begin) {
		long between = (end.getTime() - begin.getTime());// 除以1000是为了转换成秒
		long day1 = between / (24 * 60 * 60 * 1000);
		long hour1 = (between / (60 * 60 * 1000) - day1 * 24);
		long minute1 = ((between / (60 * 1000)) - day1 * 24 * 60 - hour1 * 60);
		long second1 = (between / 1000 - day1 * 24 * 60 * 60 - hour1 * 60 * 60 - minute1 * 60);

		System.out.println("-------------=day1=" + day1 + ",hour1= " + hour1 + " ,minite1 =" + minute1 + " ,second=" + second1);
		if (day1 != 0) {
			return day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (hour1 != 0) {
			return hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (minute1 != 0) {
			return minute1 + "分" + second1 + "秒";
		} else if (second1 != 0) {
			return second1 + "秒";
		} else {
			return "停留时间零";
		}
	}

}
