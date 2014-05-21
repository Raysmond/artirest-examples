package edu.fdu.raysmond.store.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.store.entity.Item;
import edu.fdu.raysmond.store.util.HibernateUtil;
import edu.fdu.raysmond.store.util.JSONUtil;

@Path("/item")
public class ItemResource {
	@Context
	UriInfo uri;

	/**
	 * View item list in JSON
	 */
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Item> getAll() {
		return HibernateUtil.all(Item.class);
	}

	/**
	 * View item list in HTML
	 */
	@GET
	@Path("list")
	@Template(name = "list.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Collection<Item> getAllHTML() {
		return HibernateUtil.all(Item.class);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject get() throws JSONException {
		return new JSONObject().put("item_fields", JSONUtil.createInputs("name", "price"));
	}
	
	/**
	 * Create a new item
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response post(final JSONObject input) throws JSONException, URISyntaxException {
		if (!JSONUtil.hasFields(input, "name", "price"))
			return Response.status(Response.Status.BAD_REQUEST).build();
		Item item = new Item();
		item.setName(input.getString("name").trim());
		item.setPrice(input.getDouble("price"));
		HibernateUtil.save(item);
		return Response.created(new URI(uri.getBaseUri() + "item/" + item.getId())).build();
	}

}
