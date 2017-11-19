package serviceimp;

import java.util.List;

import dao.CarDaoInterface;
import service.CarServiceInterface;
import model.Car;

public class CarServiceImp implements CarServiceInterface {

	private CarDaoInterface carDaoInterface;

	public CarServiceImp(CarDaoInterface carDaoInterface) {
		super();
		this.carDaoInterface = carDaoInterface;
	}

	public CarServiceImp() {

	}

	public CarDaoInterface getCarDaoInterface() {
		return carDaoInterface;
	}

	public void setCarDaoInterface(CarDaoInterface carDaoInterface) {
		this.carDaoInterface = carDaoInterface;
	}

	public Car getLastInfoByCarId(String carId) {
		return carDaoInterface.getLastInfoByCarId(carId);

	}

	public List<Car> getLonLatList(final String start, final String end,String carId) {
		return carDaoInterface.getLonLatList(start, end,carId);
	}

}
