package daoimpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import dao.UserDao;
import model.AdminUser;

public class UserDaoImpl extends GenericDao<AdminUser, Date> implements UserDao {

	public boolean getLastInfo(final String user, final String pwd) {
		@SuppressWarnings("unchecked")
		List<AdminUser> lastInfo = (List<AdminUser>) getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					// String hql =
					// "from AdminUser where username='"+user+"' and password='"+pwd+"'";
					String hql = "from AdminUser where username=? and password=?";
					Query query = session.createQuery(hql);
					query.setString(0, user);
					query.setString(1, pwd);
					return query.list();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					session.close();
				}
			}
		});
		if (lastInfo.size() > 0) {
			return true;
		}
		return false;
	}

	public void deleteAdminUserByID(List<Integer> list) {
		
		Session session = this.getSession();
		try {
			String sql = "delete AdminUser au where au.adminUserId in (:alist)";
			Query query = getSession().createQuery(sql).setParameterList("alist", list);
			session.beginTransaction();
			query.executeUpdate();
			session.getTransaction().commit();
			session.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			session.close();
		}
	}

}