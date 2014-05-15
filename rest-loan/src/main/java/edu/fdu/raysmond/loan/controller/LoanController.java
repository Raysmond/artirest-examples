package edu.fdu.raysmond.loan.controller;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.fdu.raysmond.loan.entity.Loan;
import edu.fdu.raysmond.loan.entity.LoanState;
import edu.fdu.raysmond.loan.entity.User;
import edu.fdu.raysmond.loan.util.HibernateUtil;
import jersey.repackaged.com.google.common.collect.Maps;

public final class LoanController {

	public static int store(JSONObject json, UriInfo uriInfo) {
		if (!json.has("amount") || !json.has("customer_name"))
			return -1;
		Loan loan = new Loan();
		try {
			loan.setAmount(json.getDouble("amount"));
			loan.setCustomerName(json.getString("customer_name"));
			loan.setState(LoanState.CREATED);
			HibernateUtil.save(loan);
			return loan.getId();
		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void update(Loan loan) {
		HibernateUtil.save(loan);
	}

	public static Loan get(final Integer id) {
		return (Loan) HibernateUtil.get(Loan.class, id);
	}

	public static Collection<Loan> getAll() {
		return HibernateUtil.all(Loan.class);
	}

	public static void remove(final int id) {
		Loan loan = new Loan();
		loan.setId(id);
		HibernateUtil.remove(loan);
	}

}