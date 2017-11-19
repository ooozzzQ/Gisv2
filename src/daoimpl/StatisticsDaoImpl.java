package daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.StatisticsDao;
import model.Car;
import service.StatisticsServiceInferface;
import util.GlobalConstant;
import util.PagesSource;

public class StatisticsDaoImpl extends HibernateDaoSupport implements StatisticsDao {

	public int getOverSpeedCountByCarId(String carId,String start,  String end) {
		int totalCount = 0;
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Car>();
		try {
			//SELECT COUNT(*)  FROM base_info WHERE car_id = '辽B12345' AND speed > 30 AND TIME > 20170614170000 AND TIME < 20170614180850 ;
			String hql = "SELECT COUNT(*) FROM Car c WHERE c.CAR_ID = '" + carId + "' and speed > " + GlobalConstant.OVERSPEED_THRESHOLD + " and c.TIME > " + start + " and c.TIME < " + end;
			Query query = this.getSession().createQuery(hql);
			list = query.list();
			Number num = (Number) list.get(0);
			totalCount = num.intValue();
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
			return totalCount;
		} finally {
			this.getSession().close();
		}
	}

	public List<Car> getOverSpeedRecordByCarId(String carId,PagesSource page,String start,  String end) {
		List<Car> list = new ArrayList<Car>();
		try {
			//SELECT * FROM base_info WHERE car_id = '辽B12345' AND speed > 30 AND TIME > 20170614170000 AND TIME < 20170614180850 ;
			String hql = "FROM Car c WHERE c.CAR_ID = '" + carId + "' and speed > " + GlobalConstant.OVERSPEED_THRESHOLD + " and c.TIME > " + start + " and c.TIME < " + end;
			//System.out.println("hql === " + hql);
			Query query = this.getSession().createQuery(hql);
			query.setFirstResult((page.getNowPage() - 1) * page.getNumberPage());
			query.setMaxResults(page.getNumberPage());
			list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		} finally {
			this.getSession().close();
		}
	}

	public int getStopCountByCarIdAndTime(String carId, String start, String end) {
		int totalCount = 0;
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Car>();
		try {
			String hql = "SELECT COUNT(*) FROM Car c WHERE c.CAR_ID = '" + carId + "' and c.TIME > " + start + " and c.TIME < " + end;
			Query query = this.getSession().createQuery(hql);
			list = query.list();
			Number num = (Number) list.get(0);
			totalCount = num.intValue();
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
			return totalCount;
		} finally {
			this.getSession().close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Car> getStopStatisticsRecordByCarId(String carId, String start, String end) {
		List<Car> list = new ArrayList<Car>();
		try {
			//SELECT * FROM base_info WHERE car_id = '辽B12345' AND speed > 30 AND TIME > 20170614170000 AND TIME < 20170614180850 ;
			String hql = "FROM Car c WHERE c.CAR_ID = '" + carId + "' and c.TIME > " + start + " and c.TIME < " + end;
			//System.out.println("hql === " + hql);
			Query query = this.getSession().createQuery(hql);
			list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		} finally {
			this.getSession().close();
		}
	}
}
