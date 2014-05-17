package edu.fdu.raysmond.store.resource;

import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
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
import edu.fdu.raysmond.store.controller.ProcessStateController;
import edu.fdu.raysmond.store.entity.Invoice;
import edu.fdu.raysmond.store.entity.InvoiceState;
import edu.fdu.raysmond.store.entity.Item;
import edu.fdu.raysmond.store.entity.Order;
import edu.fdu.raysmond.store.entity.OrderState;
import edu.fdu.raysmond.store.entity.Shipment;
import edu.fdu.raysmond.store.entity.ShipmentState;
import edu.fdu.raysmond.store.entity.UserRole;
import edu.fdu.raysmond.store.util.HibernateUtil;
import edu.fdu.raysmond.store.util.JSONUtil;
import edu.fdu.raysmond.store.util.Util;

public class OrderInstanceResource {
	@Context
	UriInfo uriInfo;

	@Context
	private SecurityContext security;

	private int orderId;
	private Order order;
	private Invoice invoice;
	private Shipment shipment;

	public OrderInstanceResource(int id) {
		orderId = id;
		order = (Order) HibernateUtil.get(Order.class, orderId);
		shipment = OrderController.getShipment(order);
		invoice = OrderController.getInvoice(order);
	}

	/**
	 * View order details
	 */
	@GET
	@Template(name = "order.jsp")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
	public Order get() {
		return order;
	}

	@GET
	@Path("add_item")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAddItem() throws JSONException {
		if (order.getState() != OrderState.Adding_order_item) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		JSONObject json = new JSONObject().put("input", JSONUtil.createInputs("item_id"));
		return Response.ok().entity(json).build();
	}

	/**
	 * Add an item to an order
	 */
	@PUT
	@Path("add_item")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(final JSONObject input) throws JSONException {
		if (order.getState() != OrderState.Adding_order_item) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		JSONObject result = new JSONObject();
		if (input.has("item_id")) {
			Item item = (Item) HibernateUtil.get(Item.class, input.getInt("item_id"));
			if (null == item) {
				result.put("result", false).put("reason", "Item not found");
			}
			order.addItem(item);
			HibernateUtil.save(order);
			
			result.put("result", true);
		} else {
			result.put("result", false).put("reason", "An item is required");
		}

		return Response.ok().entity(result).build();
	}

	@DELETE
	@Path("add_item/{id: \\d+}")
	public Response removeItem(@PathParam("id") final int id) {
		if (order.getState() != OrderState.Adding_order_item) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		Item item = new Item();
		item.setId(id);
		boolean result = order.getItems().remove(item);
		if (result)
			order.setTotal(order.getTotal() - item.getPrice());
		HibernateUtil.save(order);
		return Response.ok().entity(result ? JSONUtil.SUCCESS : JSONUtil.FAILURE).build();
	}

	@PUT
	@Path("add_item_complete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemComplete() {
		if (order.getState() == OrderState.Adding_order_item && !order.getItems().isEmpty()) {
			order.setState(OrderState.Customer_creating_shipping);
			HibernateUtil.save(order);
			return Response.ok().entity(JSONUtil.SUCCESS).build();
		} else {
			return Response.status(403).entity(JSONUtil.FAILURE).build();
		}
	}

	// Customer - create shipment

	@GET
	@Path("create_shipping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShippingFields() throws JSONException {
		if (order.getState() == OrderState.Customer_creating_shipping) {
			JSONObject json = new JSONObject().put("input", new JSONArray().put("customer_name").put("address"));
			return Response.ok().entity(json).build();
		}
		return Response.status(403).entity(Util.wrongState()).build();
	}

	@GET
	@Path("create_shipping")
	@Template(name = "create_shipping.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Shipment getShippingHtml() {
		if (order.getState() == OrderState.Customer_creating_shipping) {
			if (shipment != null)
				return shipment;
			else {
				shipment = new Shipment();
				shipment.setCustomerName(order.getCustomerName());
				shipment.setShippingAddress(order.getCustomerAddress() == null ? "" : order.getCustomerAddress());
				shipment.setOrderId(order.getId());
				shipment.setState(ShipmentState.Waiting_for_ship_item);
				HibernateUtil.save(shipment);
				return shipment;
			}
		} else {
			throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Cannot create a shipment")
					.build());
		}
	}

	@PUT
	@Path("create_shipping")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createShipping(final JSONObject input) throws JSONException {
		if (order.getState() != OrderState.Customer_creating_shipping) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		if (JSONUtil.notEmptyStrings(input, "customer_name", "customer_address")) {
			if (null == shipment)
				shipment = new Shipment();
			shipment.setCustomerName(input.getString("customer_name"));
			shipment.setShippingAddress(input.getString("customer_address"));
			shipment.setOrderId(order.getId());
			shipment.setState(ShipmentState.Waiting_for_ship_item);
			HibernateUtil.save(shipment);

			order.setCustomerName(input.getString("customer_name"));
			order.setCustomerAddress(input.getString("customer_address"));
			HibernateUtil.save(order);

			// create an invoice
			if (invoice == null) {
				invoice = new Invoice();
				invoice.setOrderId(order.getId());
				invoice.setTotal(order.getTotal());
				invoice.setState(InvoiceState.unpaied);
				HibernateUtil.save(invoice);
			}

			return Response.ok().entity(JSONUtil.SUCCESS).build();
		}
		return Response.ok().entity(JSONUtil.FAILURE).build();
	}

	// Customer - bill

	@GET
	@Path("bill")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBill() throws JSONException {
		if (order.getState() != OrderState.Customer_creating_shipping) {
			return Response.status(403).entity(Util.wrongState()).build();
		}

		if (shipment == null) {
			JSONObject result = new JSONObject().put("result", "fail").put("reason", "Must create a shipment first");
			return Response.status(403).entity(result).build();
		}

		JSONObject result = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("total_amount", order.getTotal());
		info.put("order_id", order.getId());

		JSONArray output = new JSONArray().put("billed").put("amount_paied");
		result.put("information", info).put("output", output);

		return Response.ok().entity(result).build();
	}

	@GET
	@Template(name = "bill.jsp")
	@Path("bill")
	@Produces(MediaType.TEXT_HTML)
	public Order getBillHtml() throws JSONException {
		if (order.getState() == OrderState.Customer_creating_shipping) {
			return order;
		} else {
			throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Wrong order state").build());
		}
	}

	@PUT
	@Path("bill")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bill(final JSONObject input) throws JSONException {
		if (order.getState() != OrderState.Customer_creating_shipping) {
			return Response.status(403).entity(Util.wrongState()).build();
		}

		if (shipment == null) {
			JSONObject result = JSONUtil.resultFail().put("reason", "Must create a shipment first");
			return Response.status(403).entity(result).build();
		}

		if (input.has("billed") && input.has("amount_paied") && !input.getString("billed").trim().equals("")) {
			String op = input.getString("billed");
			if (op.equalsIgnoreCase("yes")) {
				// update order
				order.setBillDate(new Date());
				order.setState(OrderState.Billed);
				HibernateUtil.save(order);

				// update invoice
				invoice.setAmountPaied(input.getDouble("amount_paied"));
				invoice.setState(InvoiceState.paied);
				invoice.setInvoiceDate(new Date());
				invoice.setBillingAddress(order.getCustomerAddress());
				HibernateUtil.save(invoice);

				return Response.ok().entity(JSONUtil.SUCCESS).build();
			}
			// if (op.equalsIgnoreCase("no")) {}
		}
		return Response.ok().entity(JSONUtil.FAILURE).build();
	}

	// Manager - process/confirm item

	@GET
	@RolesAllowed(UserRole.MANAGER)
	@Path("confirm")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProcessItem() throws JSONException {
		if (order.getState() != OrderState.Billed) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		JSONObject json = new JSONObject().put("output", new JSONArray().put("confirm"));
		return Response.ok().entity(json).build();
	}

	@GET
	@RolesAllowed(UserRole.MANAGER)
	@Template(name = "confirm.jsp")
	@Path("confirm")
	@Produces(MediaType.TEXT_HTML)
	public Order getProcessHtml() throws JSONException {
		if (order.getState() != OrderState.Billed) {
			throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Wrong order state").build());
		}
		return order;
	}

	/**
	 * Process a order item. For example to verify whether there's a
	 * corresponding stuff actually
	 */
	@PUT
	@RolesAllowed(UserRole.MANAGER)
	@Path("confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response confirmOrder(final JSONObject input) throws JSONException {
		if (order.getState() != OrderState.Billed) {
			return Response.status(403).entity(Util.wrongState()).build();
		}
		if (input.has("confirm") && !input.getString("confirm").equals("")) {
			if (input.getString("confirm").equalsIgnoreCase("yes")) {
				order.setState(OrderState.Order_confirmed);
				HibernateUtil.save(order);

				shipment.setState(ShipmentState.Ready_for_shipping);
				HibernateUtil.save(shipment);
				return Response.ok().entity(JSONUtil.SUCCESS).build();
			}
		}
		return Response.ok().entity(JSONUtil.FAILURE).build();
	}

	/**
	 * Manager - shipment management
	 */
	@RolesAllowed(UserRole.MANAGER)
	@Path("shipment")
	public ShipmentResource shipment() {
		return new ShipmentResource(order, shipment);
	}
}
