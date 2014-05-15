package edu.fdu.raysmond.loan.util;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			// return new Configuration().configure().buildSessionFactory(new
			// StandardServiceRegistryBuilder().build());
			Configuration cfg = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			return cfg.buildSessionFactory(serviceRegistry);

			// return new AnnotationConfiguration().configure().
			// //addPackage("edu.fdu.raysmond.loan.entity").
			// addAnnotatedClass(User.class).buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	
	public static BaseModel get(Class c, Integer id) {
		Session session = getSession();
		session.beginTransaction();
		BaseModel entity = (BaseModel) getSession().get(c, id);
		session.getTransaction().commit();
		return entity;
	}

	public static BaseModel save(BaseModel model) {
		Session session = getSession();
		session.beginTransaction();
		session.saveOrUpdate(model);
		session.getTransaction().commit();
		return model;
	}

	public static <T extends BaseModel> Collection<T> all(Class<T> clazz) {
		Session session = getSession();
		session.beginTransaction();
		Query query = getSession().createQuery("from " + clazz.getName());
		Collection<T> result = query.list();
		session.getTransaction().commit();
		return result;
	}

	public static void remove(BaseModel model) {
		Session session = getSession();
		session.beginTransaction();
		session.delete(model);
		session.getTransaction().commit();
	}

}
