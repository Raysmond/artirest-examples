package edu.fdu.raysmond.store;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import edu.fdu.raysmond.store.controller.UserController;
import edu.fdu.raysmond.store.entity.Item;
import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.OrderState;
import edu.fdu.raysmond.store.entity.Shipment;
import edu.fdu.raysmond.store.entity.User;
import edu.fdu.raysmond.store.entity.UserRole;
import edu.fdu.raysmond.store.util.Encryption;
import edu.fdu.raysmond.store.util.HibernateUtil;

public class HibernateTestCase {

//	@Test
//	public void test1() {
//		User user = new User();
//		user.setName("Raysmond");
//		user.setPassword(Encryption.MD5("111111"));
//		HibernateUtil.save(user);
//		
//		Item item = new Item();
//		item.setName("Item" + (int) (Math.random() * 1000));
//		HibernateUtil.save(item);
//
//		Order order = new Order();
//		order.setBillDate(new Date());
//		order.setCustomerName("Raysmond");
//		order.setCustomerAddress("Pudong New Area, Shanghai, China");
//		order.setState(OrderState.Adding_order_item);
//		order.setTotal(0);
//		order.addItem(item);
//		HibernateUtil.save(order);
//		
//		assertTrue(true);
//	}

}
