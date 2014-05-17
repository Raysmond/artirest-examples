package edu.fdu.raysmond.store.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.fdu.raysmond.store.entity.Invoice;
import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.Shipment;
import edu.fdu.raysmond.store.util.HibernateUtil;

public class OrderController {

	public static Collection<Order> getOrders(String customerName) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("from Order o where o.customerName = :name");
		q.setParameter("name", customerName);
		List<Order> result = q.list();
		session.getTransaction().commit();
		if (null == result)
			result = new ArrayList<Order>();
		return result;
	}

	public static Shipment getShipment(Order order) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("from Shipment o where o.orderId = :orderId");
		q.setParameter("orderId", order.getId());
		q.setMaxResults(1);
		List<Shipment> result = q.list();
		session.getTransaction().commit();
		if (null == result || result.isEmpty())
			return null;
		return result.get(0);

	}

	public static Invoice getInvoice(Order order) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("from Invoice o where o.orderId = :orderId");
		q.setParameter("orderId", order.getId());
		q.setMaxResults(1);
		List<Invoice> result = q.list();
		session.getTransaction().commit();
		if (null == result || result.isEmpty())
			return null;
		return result.get(0);
	}

}
