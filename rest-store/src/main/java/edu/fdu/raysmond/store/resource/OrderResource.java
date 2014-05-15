package edu.fdu.raysmond.store.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.OrderState;
import edu.fdu.raysmond.store.entity.UserRole;
import edu.fdu.raysmond.store.util.HibernateUtil;

@Path("/order")
@RolesAllowed({ UserRole.CUSTOMER })
public class OrderResource {

	@Context
	UriInfo uriInfo;

	@Context
	private SecurityContext security;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject get() throws JSONException {
		return new JSONObject().put("inputs", new JSONArray().put("customer_name"));
	}

	/**
	 * Create a new order
	 * 
	 * @throws JSONException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newOrder(final JSONObject inputs) throws URISyntaxException, JSONException {
		if (!inputs.has("customer_name") || inputs.get("customer_name").equals("")) {
			return Response.status(403).entity("Customer name is required").build();
		}
		Order order = new Order();
		order.setState(OrderState.Adding_order_item);
		order.setCustomerName(inputs.getString("customer_name"));
		HibernateUtil.save(order);
		return Response.created(new URI("order/" + order.getId())).build();
	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getAll() {
		return HibernateUtil.all(Order.class);
	}

	@Path("{id: \\d+}")
	public OrderInstanceResource getInstance(@PathParam("id") final int id) {
		if (HibernateUtil.get(Order.class, id) == null) {
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("Order, " + id + ", is not found").build());
		}
		return new OrderInstanceResource(id);
	}

}
