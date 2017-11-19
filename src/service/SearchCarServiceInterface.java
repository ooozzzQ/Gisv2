package service;

import java.util.List;

import model.Car;
import util.PagesSource;

public interface SearchCarServiceInterface {
	public  List<Car> getCarByPage(PagesSource page);

	public int getCarTotalCount();
}
