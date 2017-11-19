package dao;

import java.util.List;

import util.PagesSource;
import model.Car;

public interface SearchCarDao {
	public  List<Car> getCarByPage(PagesSource page);
	public  List<String> getCarIdList(String wordText);
	public int getCarTotalCount();
}
