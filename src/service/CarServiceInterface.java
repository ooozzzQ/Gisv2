package service;

import java.util.List;

import model.Car;

public interface CarServiceInterface {
	/*
	 * �÷����ǻ�ȡ�����������Ϣ
	 */
	public Car getLastInfoByCarId(String carId);

	/**
	 * @param start
	 * @param end
	 * @param carId
	 * @return
	 * ��ʱ��β�ѯ��γ�ȵ�dao
	 */
	public List<Car> getLonLatList(final String start, final String end,String carId);

}
