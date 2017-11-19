package serviceimp;

import java.util.List;

import model.Car;
import dao.StatisticsDao;
import service.StatisticsServiceInferface;
import util.PagesSource;

public class StatisticsServiceImpl implements StatisticsServiceInferface{
	private StatisticsDao statisticsDao;
	
	public StatisticsDao getStatisticsDao() {
		return statisticsDao;
	}

	public void setStatisticsDao(StatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}

	public List<Car> getOverSpeedRecordByCarId(String carId,PagesSource page,String start,  String end) {
		return statisticsDao.getOverSpeedRecordByCarId(carId,page,start,end);
	}

	public int getOverSpeedCountByCarId(String carId,String start,  String end) {
		return statisticsDao.getOverSpeedCountByCarId(carId,start,end);
	}

	public int getStopCountByCarIdAndTime(String carId, String start, String end) {
		return statisticsDao.getStopCountByCarIdAndTime(carId,start,end);
	}

	public List<Car> getStopStatisticsRecordByCarId(String carId, String start, String end) {
		return statisticsDao.getStopStatisticsRecordByCarId(carId,start,end);
	}

}
