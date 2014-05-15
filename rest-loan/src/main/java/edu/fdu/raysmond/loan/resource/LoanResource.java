package edu.fdu.raysmond.loan.resource;

import java.util.Collection;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.loan.controller.LoanController;
import edu.fdu.raysmond.loan.entity.Loan;
import edu.fdu.raysmond.loan.entity.User;
import edu.fdu.raysmond.loan.entity.UserRole;
import edu.fdu.raysmond.loan.util.HibernateUtil;
import edu.fdu.raysmond.loan.util.Util;

/**
 * Loan Resource
 * 
 * @author Raysmond
 */
@Path("/loan")
public class LoanResource {
	@Context
	UriInfo uriInfo;

	@Context
	private SecurityContext security;

	private static final String inputs[] = { "customer_name", "amount" };

	@GET
	public Response get() {
		return Response.ok().entity("To apply a new loan, please goto URI: " + uri("loan/create")).build();
	}

	@GET
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getInputs() throws JSONException {
		JSONArray arr = new JSONArray();
		for (String s : inputs) {
			arr.put(s);
		}
		return new JSONObject().put("input", arr);
	}

	@GET
	@Path("create")
	@Template(name = "create.jsp")
	@Produces({ MediaType.TEXT_HTML })
	public String[] getInputsAsHtml() {
		return inputs;
	}

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response store(final JSONObject json) {
		int id = LoanController.store(json, uriInfo);
		String result = "Location: " + uri("loan/" + id);
		return Response.status(201).entity(result).build();

	}

	@POST
	@Path("create_post")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Template(name = "created.jsp")
	// @ErrorTemplate(name = "/error-form")
	public Loan createLoan(@FormParam("amount") final String amount,
			@FormParam("customer_name") final String customerName) throws JSONException {
		JSONObject json = new JSONObject().put("amount", Double.valueOf(amount)).put("customer_name", customerName);
		return LoanController.get(LoanController.store(json, uriInfo));
	}

	@GET
	@Path("list")
	@RolesAllowed(UserRole.MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Loan> getAll() {
		return LoanController.getAll();
	}

	@GET
	@Path("list")
	@RolesAllowed(UserRole.MANAGER)
	@Template(name = "list.jsp")
	@Produces({ MediaType.TEXT_HTML })
	public Collection<Loan> getListAsHtml() {
		return LoanController.getAll();
	}

	@Path("{id: \\d+}")
	public LoanInstanceResource get(@PathParam("id") final int id) {
		if (LoanController.get(id) == null) {
			throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
					.entity("Loan instance, " + id + ", is not found").build());
		}
		return new LoanInstanceResource(uriInfo, id);
	}

	private String uri(String path) {
		return uriInfo.getBaseUri().toString() + path;
	}
}
