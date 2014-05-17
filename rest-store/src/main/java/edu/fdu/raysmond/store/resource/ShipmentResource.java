package edu.fdu.raysmond.store.resource;

import java.net.URISyntaxException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.OrderState;
import edu.fdu.raysmond.store.entity.Shipment;
import edu.fdu.raysmond.store.entity.ShipmentState;
import edu.fdu.raysmond.store.util.HibernateUtil;
import edu.fdu.raysmond.store.util.JSONUtil;

public class ShipmentResource {
	@Context
	UriInfo uriInfo;

	private Order order;
	private Shipment shipment;

	public ShipmentResource(Order order, Shipment shipment) {
		this.order = order;
		this.shipment = shipment;
	}

	@GET
	@Path("create_shipping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createShipping() throws JSONException {
		if (!((order.getState() == OrderState.Order_confirmed || order.getState() == OrderState.Manager_creating_shipping) && shipment
				.getState() != ShipmentState.Waiting_for_ship_item)) {
			return Response.status(403).entity(JSONUtil.WRONG_STATE).build();
		}
		JSONObject json = new JSONObject();
		JSONObject orderInfo = new JSONObject().put("order_id", order.getId())
				.put("customer_name", order.getCustomerName()).put("address", order.getCustomerAddress());
		json.put("order", orderInfo);
		json.put("optional_input", JSONUtil.createInputs("customer_name", "address"));
		return Response.ok().entity(json).build();
	}

	@GET
	@Path("create_shipping")
	@Template(name = "manager_create_shipping.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Shipment getShippingHtml() {
		if (!((order.getState() == OrderState.Order_confirmed || order.getState() == OrderState.Manager_creating_shipping) && shipment
				.getState() != ShipmentState.Waiting_for_ship_item)) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		order.setState(OrderState.Manager_creating_shipping);
		HibernateUtil.save(order);
		return shipment;
	}

	@PUT
	@Path("create_shipping")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createShipping(final JSONObject input) throws JSONException, URISyntaxException {
		if (!((order.getState() == OrderState.Order_confirmed || order.getState() == OrderState.Manager_creating_shipping) && shipment
				.getState() != ShipmentState.Waiting_for_ship_item)) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		if (input.has("customer_name") && !input.getString("customer_name").equals(""))
			shipment.setCustomerName(input.getString("customer_name"));
		if (input.has("address") && !input.getString("address").equals(""))
			shipment.setShippingAddress(input.getString("address"));

		shipment.setState(ShipmentState.Ready_for_shipping);
		HibernateUtil.save(shipment);

		order.setState(OrderState.Ready_for_shipping);
		HibernateUtil.save(order);

		// String uri = uriInfo.getBaseUri().toString() + "order/" +
		// order.getId() + "/shipment/" + shipment.getId();
		return Response.ok().entity(JSONUtil.SUCCESS).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Shipment get() {
		if (shipment.getState() != ShipmentState.Waiting_for_ship_item) {
			return shipment;
		} else
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("Shipment, " + shipment.getId() + ", is not found").build());
	}

	@GET
	@Path("in_shipping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inShipping() throws JSONException {
		if (shipment.getState() != ShipmentState.Ready_for_shipping) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		JSONObject input = new JSONObject().put("input", new JSONArray().put("confirm"));
		return Response.ok().entity(input).build();
	}

	@GET
	@Path("in_shipping")
	@Template(name = "in_shipping_confirm.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Shipment inShippingHtml() {
		if (shipment.getState() != ShipmentState.Ready_for_shipping) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		return shipment;
	}

	@PUT
	@Path("in_shipping")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inShipping(final JSONObject input) throws JSONException {
		if (shipment.getState() != ShipmentState.Ready_for_shipping) {
			return Response.status(403).entity(JSONUtil.WRONG_STATE).build();
		}
		if (input.has("confirm") && input.getString("confirm").equals("yes")) {
			shipment.setState(ShipmentState.In_shipping);
			shipment.setShipStartDate(new Date());
			HibernateUtil.save(shipment);

			order.setState(OrderState.In_shipping);
			HibernateUtil.save(order);
			return Response.ok().entity(JSONUtil.SUCCESS).build();
		}
		return Response.ok().entity(JSONUtil.FAILURE).build();
	}

	@GET
	@Path("shipped")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipped() throws JSONException {
		if (order.getState() != OrderState.In_shipping) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		JSONObject json = new JSONObject();

		JSONObject input = new JSONObject();
		input.put("order_id", order.getId());
		input.put("shipment_id", shipment.getId());

		JSONArray output = new JSONArray();
		output.put("confirm");

		json.put("input", input);
		json.put("output", output);
		return Response.ok().entity(json).build();
	}

	@GET
	@Path("shipped")
	@Template(name = "shipped_confirm.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Shipment shippedHtml() {
		if (shipment.getState() != ShipmentState.In_shipping) {
			throw new ForbiddenException(Response.status(403).entity(JSONUtil.WRONG_STATE).build());
		}
		return shipment;
	}

	@PUT
	@Path("shipped")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipped(final JSONObject input) throws JSONException {
		if (shipment.getState() != ShipmentState.In_shipping && order.getState() != OrderState.In_shipping) {
			return Response.status(403).entity(JSONUtil.WRONG_STATE).build();
		}
		if (input.has("confirm") && input.getString("confirm").equals("yes")) {
			shipment.setState(ShipmentState.Shipping_completed);
			shipment.setShipEndDate(new Date());
			HibernateUtil.save(shipment);

			order.setState(OrderState.Shipped);
			HibernateUtil.save(order);
			return Response.ok().entity(JSONUtil.SUCCESS).build();
		}
		return Response.ok().entity(JSONUtil.FAILURE).build();
	}

}
