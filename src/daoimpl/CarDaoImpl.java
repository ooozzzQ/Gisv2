package daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import util.GlobalConstant;

import com.alibaba.fastjson.JSONObject;

import dao.CarDaoInterface;
import model.Car;
import action.GpsUtil;

public class CarDaoImpl extends GenericDao<Car, Date> implements CarDaoInterface {

	public Car getLastInfoByCarId(final String carId) {

		@SuppressWarnings("unchecked")
		List<Car> lastInfo = (List<Car>) getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) {
				try {
					String hql = "from Car where ID=(select max(ID) from Car WHERE CAR_ID = '" + carId + "')";
					Query query = session.createQuery(hql);
					return query.list();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					session.close();
				}

			}
		});
		if ( lastInfo != null && lastInfo.size() > 0) {
			return lastInfo.get(0);
		} else {
			return null;
		}
	}

	/*
	 * @see dao.CarDaoInterface#getLonLatList(java.lang.String, java.lang.String, java.lang.String)
	 * 轨迹查询
	 * 1、查询一段时间内速度大于4.5的所有值，如果没有记录，认为该车辆在该时间段一直停留
	 * 2、如果第一步几率数为0，进一步查询，不加速度限制条件，并且只取10条记录。
	 */
	 
	public List<Car> getLonLatList(final String start, final String end,final String carId) {
		@SuppressWarnings("unchecked")
		List<Car> carList = (List<Car>) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				String hql = null;
				try {
					//hql = "SELECT * FROM (SELECT * FROM Car WHERE car_id = '" + carId +"') AS temp WHERE TIME>" + start +" AND TIME<" + stop;
					hql = "from Car c where c.CAR_ID = '" + carId + "' and c.TIME>" + start + " and c.TIME<" + end + " and c.SPEED > " + GlobalConstant.STASTICSPEED_THRESHOLD;
					List<Car> carListTempCars  =  session.createQuery(hql).list();
					if (carListTempCars.size() == 0 ) {
						//hql = "from Car c where c.ID in (SELECT MAX(ID) from Car c WHERE c.CAR_ID = '" + carId + "' and c.TIME > " + start + " and c.TIME < " + end +")";
						 hql = "from Car c WHERE c.CAR_ID = '" + carId + "' and c.TIME > " + start + " and c.TIME < " + end;
						carListTempCars = session.createQuery(hql).setFirstResult(0).setMaxResults(10).list();
					}
					System.out.println(hql);
					return  carListTempCars;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					session.close();
				}
			}
		});
		return carList;
	}
}