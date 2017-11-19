package daoimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;





import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author cwh created on 2010-01-25
 * @param <T>
 *            POJO
 * @param <PK>
 *            primary key
 */
public abstract class GenericDao<T extends Serializable, PK extends Serializable>
		extends HibernateDaoSupport {

	private Class<T> pojoClass;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.pojoClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Required
	
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Class<T> getPojoClass() {
		return this.pojoClass;
	}

	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		return (List<T>) getHibernateTemplate().loadAll(pojoClass);
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		return (T) getHibernateTemplate().get(pojoClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public T load(PK id) {
		return (T) getHibernateTemplate().load(pojoClass, id);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		return (PK) getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@SuppressWarnings("rawtypes")
	public List find(String hql, Object... values) {
		return getHibernateTemplate().find(hql, values);
	}
}
