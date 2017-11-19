package daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import util.PagesSource;
import model.Car;
import dao.SearchCarDao;

public class SearchCarDaoImpl extends HibernateDaoSupport implements SearchCarDao {

	@SuppressWarnings("unchecked")
	public List<Car> getCarByPage(PagesSource page) {
		List<Car> list = new ArrayList<Car>();
		try {
			String hql = "SELECT DISTINCT c.CAR_ID from Car c";
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

	public int getCarTotalCount() {
		int totalCount = 0;
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Car>();
		try {
			// String hql =
			// "SELECT COUNT(DISTINCT car_id) FROM `base_info` WHERE 1 = 1";
			String hql = "SELECT COUNT(DISTINCT c.CAR_ID) FROM Car c WHERE 1 = 1";
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

	public List<String> getCarIdList(String wordText) {
		List<String>  list = new ArrayList<String>();
		try {
			//SELECT DISTINCT car_id FROM base_info  WHERE  car_id  LIKE '%6%';
			String hql = "SELECT DISTINCT c.CAR_ID from Car c where  c.CAR_ID like '%" + wordText +"%'";
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
