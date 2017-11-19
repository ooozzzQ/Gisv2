package service;

import java.util.List;

import util.PagesSource;
import model.Car;

public interface StatisticsServiceInferface {
	public int getOverSpeedCountByCarId (String carId, String start, String end);
	public List<Car> getOverSpeedRecordByCarId(String carId,PagesSource page, String start,  String end);
	
	public int getStopCountByCarIdAndTime(String carId, String start, String end);
	public List<Car> getStopStatisticsRecordByCarId(String carId, String start, String end);
}
