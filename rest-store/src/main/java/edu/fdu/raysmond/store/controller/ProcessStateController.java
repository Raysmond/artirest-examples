package edu.fdu.raysmond.store.controller;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import edu.fdu.raysmond.store.entity.ProcessState;
import edu.fdu.raysmond.store.util.HibernateUtil;

public class ProcessStateController {

	public static ProcessState log(Integer artifactId, String stateName, String user) {
		ProcessState state = new ProcessState(artifactId, stateName, user);
		HibernateUtil.save(state);
		return state;
	}

	public static Collection<ProcessState> getArtifactState(Integer artifactId) {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		Query q = session.createQuery("from Order o where o.artifactId = :aid");
		q.setParameter("aid", artifactId);
		List<ProcessState> result = q.list();
		session.getTransaction().commit();
		return result;
	}

}
