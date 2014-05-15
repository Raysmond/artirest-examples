package edu.fdu.raysmond.store.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.fdu.raysmond.store.entity.Item;
import edu.fdu.raysmond.store.util.HibernateUtil;

@Path("item")
public class ItemResources {

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAll() {
		return HibernateUtil.all(Item.class);
	}
}
