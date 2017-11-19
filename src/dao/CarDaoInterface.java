package dao;

import java.util.List;

import model.Car;

public interface CarDaoInterface {
	public Car getLastInfoByCarId(final String carId);

	// ��ʱ��β�ѯ��γ�ȵ�dao
	public List<Car> getLonLatList(final String start, final String end,String carId) ;
}
