package edu.fdu.raysmond.loan;

import static org.junit.Assert.*;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import edu.fdu.raysmond.loan.controller.UserController;
import edu.fdu.raysmond.loan.entity.Loan;
import edu.fdu.raysmond.loan.entity.User;
import edu.fdu.raysmond.loan.entity.UserRole;
import edu.fdu.raysmond.loan.util.Encryption;
import edu.fdu.raysmond.loan.util.HibernateUtil;

public class HibernateTestCase {

//	@Test
//	public void test() {
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//
//		User user = new User();
//		user.setName("Raysmond");
//		user.setPassword(Encryption.MD5("111111"));
//		user.setRole(UserRole.USER);
//		session.save(user);
//
//		session.getTransaction().commit();
//
//		System.out.println(user.getId());
//
//		User u = (User) PersistenceUtilImpl.get(User.class, user.getId());
//		System.out.println(u.getId());
//		System.out.println(u.getName());
//		System.out.println(u.getPassword());
//		System.out.println(u.getRole());
//		
//		String username = "Raysmond";
//		String password = "111111";
//
//		if (username == null || password == null || username.equals("") || password.equals("")) {
//			System.out.println("fail");
//		}
//
//		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
//		s.beginTransaction();
//		Query q = s.createQuery("from User u where u.name = :name").setParameter("name", username);
//		List<User> users = q.list();
//		s.getTransaction().commit();
//		if (users != null && !users.isEmpty() && Encryption.MD5(password).equals(users.get(0).getPassword())) {
//			System.out.println(users.get(0).getName() + " has login.");
//		}
//
//		assertTrue(true);
//	}
	
//	@Test
//	public void testGetUserLoans(){
//		User user = UserController.get(6);
//		for(Loan l : user.getLoans()){
//			System.out.println(l.getId());
//		}
//		assertTrue(true);
//	}

}
