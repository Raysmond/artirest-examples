package edu.fdu.raysmond.loan.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.fdu.raysmond.loan.entity.User;
import edu.fdu.raysmond.loan.util.HibernateUtil;

public class UserController {

	/**
	 * Get user by name
	 */
	public static User getUserByName(String name) {
		if (name != null && !name.equals("")) {
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			Query q = s.createQuery("from User u where u.name = :name").setParameter("name", name).setMaxResults(1);
			List<User> users = q.list();
			s.getTransaction().commit();

			if (users != null && !users.isEmpty())
				return users.get(0);
		}

		return null;
	}
	
	public static User get(int id){
		return (User) HibernateUtil.get(User.class, id);
	}
}
