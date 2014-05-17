package edu.fdu.raysmond.store.resource;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.store.entity.Item;
import edu.fdu.raysmond.store.util.HibernateUtil;

@Path("/item")
public class ItemResource {

	@GET
	@Path("list")
	@Template(name = "list.jsp")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public Collection<Item> getAll() {
		return HibernateUtil.all(Item.class);
	}
}
