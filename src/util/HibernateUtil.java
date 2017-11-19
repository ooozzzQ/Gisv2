package util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

		public class HibernateUtil {
		
			private static SessionFactory sessionFactory=null;
			
			private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();
			private HibernateUtil(){};
			static {
				sessionFactory=new Configuration().configure("config/hibernate.cfg.xml").buildSessionFactory();
				System.out.println("ssssssssssssssssssssssssssssssssssssss");
			}
			
			public static SessionFactory getSessionFactory(){
				return sessionFactory;
			}
			
			public static Session openSession(){
				return sessionFactory.openSession();
			}
		
			public static Session getCurrentSession(){
				
				Session session=threadLocal.get();
				
				if(session==null){
					session=sessionFactory.openSession();
					
					threadLocal.set(session);
				}
				return session;	
				
			}
	
			public static List executeQuery(String hql, String[] parameters){
				
				Session session =null;
				Transaction tr=null;
				List list=null;
				
				try{
					
					session=HibernateUtil.openSession();					
					tr=session.beginTransaction();
					Query query = session.createQuery(hql);					

					if(parameters!=null&&parameters.length!=0){
						for(int i=0;i<parameters.length;i++){
							query.setString(i, parameters[i]);
						}
						
					}
					
					list= query.list();
					tr.commit();
										
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
				System.out.println("HibernateUtilִ����");
				return list;
			}
			
			
			
/*�ṩһ��ͳһ�Ĳ�ѯ������ֻ���ڷ��ض�����Ψһ��********************************************/
public static Object uniqueQuery(String hql, String[] parameters){
				
				Session session =null;
				Transaction tr=null;
				Object obj=null;
				try{
					
					session=HibernateUtil.openSession();
					tr=session.beginTransaction();
					//do st............ҵ���߼�
					Query query = session.createQuery(hql);					
					

					//���ж��Ƿ��в����
					if(parameters!=null&&parameters.length!=0){
						for(int i=0;i<parameters.length;i++){
							query.setString(i, parameters[i]);
						}
						
					}
					
					obj= query.uniqueResult();
					tr.commit();
					//System.out.println("�������executeQuery������");
					
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
				
				return obj;
			}
			
			

/*�����ṩһ���ܷ�ҳ�Ĳ�ѯ����************************************************************/			
			public static List executeQueryByPage(String hql, String[] parameters,int pageSize,int pageNow){
				
				Session session =null;
				Transaction tr=null;
				List list=null;
				try{
					
					session=HibernateUtil.openSession();
					tr=session.beginTransaction();
					//do st............ҵ���߼�
					Query query = session.createQuery(hql);					
					

					//���ж��Ƿ��в����
					if(parameters!=null&&parameters.length!=0){
						for(int i=0;i<parameters.length;i++){
							query.setString(i, parameters[i]);
						}
						
					}
					//�Ͷ�����仰����������������������������������
					query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize);
					
					list= query.list();
					tr.commit();
					//System.out.println("�������executeQuery������");
					
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
				
				return list;
			}
			
			
/*�ṩͳһ����ӷ���**********************************************************************/
			public static void save(Object obj){
				
				Session session =null;
				Transaction tr=null;
				
				try{
					
					session =openSession();
					tr=session.beginTransaction();
					session.save(obj);
					tr.commit();
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
		
			}
			
/*�ṩͳһ��ɾ����·���**************************************************************************/
			public static void updateQuery(String hql,String[] parameters){
				
				Session session =null;
				Transaction tr=null;
				
				try{
					
					session=HibernateUtil.getCurrentSession();
					tr=session.beginTransaction();
					//do st............ҵ���߼�
					Query query = session.createQuery(hql);					
					

					//���ж��Ƿ��в����
					if(parameters!=null&&parameters.length!=0){
						for(int i=0;i<parameters.length;i++){
							query.setString(i, parameters[i]);
						}
						
					}
					
					query.executeUpdate();
					tr.commit();
					//System.out.println("�������executeQuery������");
					
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
				
								
			}
			
			
/*�����ṩһ�����id���ض���ķ���***************************************************************/
			public static Object findById(Class clazz,java.io.Serializable id){
				
				Session session =null;
				Transaction tr=null;
				Object obj=null;
				
				try{
					
					session=HibernateUtil.getCurrentSession();
					tr=session.beginTransaction();
					//do st............ҵ���߼�
					
					obj=session.get(clazz, id);
					
					tr.commit();				
					
					
				}catch(Exception ex){
					if(tr!=null){
						tr.rollback();
					}
					throw new RuntimeException(ex.getMessage());
					
				}finally{
					if(session!=null&session.isOpen()){
						session.close();
					}
				}
				return obj;
			}
	
	
}
