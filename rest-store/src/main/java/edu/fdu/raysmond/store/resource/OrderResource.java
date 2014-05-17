package edu.fdu.raysmond.store.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.store.controller.OrderController;
import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.OrderState;
import edu.fdu.raysmond.store.entity.UserRole;
import edu.fdu.raysmond.store.util.HibernateUtil;
import edu.fdu.raysmond.store.util.JSONUtil;

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

	@GET
	@Template(name = "index.jsp")
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {
		return "";
	}

	@PUT
	@Path("set_current_order")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject setCurrentOrder(final JSONObject input, @Context HttpServletRequest request) throws JSONException {
		if (input.has("order_id")) {
			int orderId = input.getInt("order_id");
			if (orderId > 0) {
				request.getSession().setAttribute("ORDER_ID", orderId);
				return JSONUtil.SUCCESS;
			}
		}
		return JSONUtil.FAILURE;
	}

	/**
	 * Create a new order
	 * 
	 * @throws JSONException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newOrder(final JSONObject inputs, @Context HttpServletRequest request) throws URISyntaxException,
			JSONException {
		if (!inputs.has("customer_name") || inputs.get("customer_name").equals("")) {
			return Response.status(403).entity("Customer name is required").build();
		}
		Collection<Order> orders = OrderController.getOrders(inputs.getString("customer_name"));
		Order order = null;
		if (!orders.isEmpty()) {
			for (Order o : orders) {
				if (o.getState() == OrderState.Adding_order_item) {
					order = o;
					break;
				}
			}
		}
		if (order == null)
			order = new Order();
		order.setState(OrderState.Adding_order_item);
		order.setCustomerName(inputs.getString("customer_name"));
		HibernateUtil.save(order);

		final HttpSession session = request.getSession();
		session.setAttribute("ORDER_ID", order.getId());
		session.setAttribute("CUSTOMER_NAME", order.getCustomerName());

		return Response.created(new URI("order/" + order.getId())).build();
	}

	@GET
	@RolesAllowed(UserRole.MANAGER)
	@Path("list")
	@Template(name = "admin_list.jsp")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
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
