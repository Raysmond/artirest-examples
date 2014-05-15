package edu.fdu.raysmond.loan.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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

import edu.fdu.raysmond.loan.controller.BankController;
import edu.fdu.raysmond.loan.controller.LoanController;
import edu.fdu.raysmond.loan.entity.Loan;
import edu.fdu.raysmond.loan.entity.LoanState;
import edu.fdu.raysmond.loan.entity.UserRole;

/**
 * @author Raysmond
 */
public class LoanInstanceResource {
	UriInfo uri;
	int loanId;
	Loan loan;

	@Context
	private SecurityContext security;

	public LoanInstanceResource(UriInfo uri, int loanId) {
		this.uri = uri;
		this.loanId = loanId;
		loan = LoanController.get(loanId);
	}

	/**
	 * GET loan detail in HTML
	 */
	@GET
	@Template(name = "loan.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Loan getLoanHtml() {
		return loan;
	}

	/**
	 * GET loan detail in JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getLoanJSON() throws JSONException {
		JSONObject json = new JSONObject().put("loan", loan);
		String url = uri.getBaseUri().toString() + "/loan/" + loanId;

		// 'apply' link
		if (loan.getState() == LoanState.CREATED)
			json.put("link", url + "/apply");

		// 'confirm' link
		if (security.isUserInRole(UserRole.MANAGER) && loan.getState() == LoanState.APPLIED)
			json.put("link", url + "/approve");

		return json;
	}

	@GET
	@Template(name = "apply.jsp")
	@Produces(MediaType.TEXT_HTML)
	@Path("apply")
	public Loan applyAsHtml() throws JSONException {
		return loan;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("apply")
	public JSONObject apply() throws JSONException {
		if (loan.getState() == LoanState.CREATED) {
			JSONArray banks = new JSONArray();
			for (String bank : BankController.controller().getAll()) {
				banks.put(bank);
			}
			
			JSONObject result = new JSONObject().put("state", "active");
			JSONObject input = new JSONObject().put("options", banks);
			result.put("input", input);
			result.put("output", new JSONArray().put("chosen_bank"));

			return result;
		}
		return null;
	}

	/**
	 * Complete application (choose a bank)
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("apply")
	public Response apply(JSONObject json) throws JSONException {
		int code = 400;
		String result = "";
		if (loan.getState() == LoanState.CREATED && json.has("chosen_bank")) {
			String bank = json.getString("chosen_bank");
			if (BankController.controller().exist(bank)) {
				loan.setState(LoanState.APPLIED);
				loan.setBank(bank);
				LoanController.update(loan);
				code = 200;
				result = "Loan (" + loan.getId() + ") was updated. Bank \"" + bank + "\" was chosen.";
			} else {
				result = "No such bank.";
			}
		} else {
			result = "Apply failed.";
		}
		return Response.status(code).entity(result).build();
	}

	// -----------------------------------------
	// Admin only actions
	// -----------------------------------------

	@GET
	@RolesAllowed(UserRole.MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("approve")
	public JSONObject approve() throws JSONException {
		if (loan.getState() == LoanState.APPLIED) {
			return new JSONObject().put("state", "active").put("input", loan)
					.put("output", new JSONArray().put("approve"));
		}
		return null;
	}

	@PUT
	@RolesAllowed(UserRole.MANAGER)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("approve")
	public Response approve(JSONObject json) throws JSONException {
		int code = 400;
		String result = null;
		if (loan.getState() == LoanState.APPLIED && json.has("approve")) {
			code = 200;
			if (json.getString("approve").equalsIgnoreCase("yes")) {
				loan.setState(LoanState.APPROVED);
				result = "Loan (" + loanId + ") was approved.";
			} else if (json.getString("approve").equalsIgnoreCase("no")) {
				loan.setState(LoanState.CANCELED);
				result = "Loan (" + loanId + ") was canceled.";
			}
			LoanController.update(loan);
		}
		return Response.status(code).entity(result != null ? result : "Approve failed").build();
	}

	@DELETE
	@RolesAllowed(UserRole.MANAGER)
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove() {
		LoanController.remove(loanId);
		return Response.status(200).entity("success").build();
	}
}
