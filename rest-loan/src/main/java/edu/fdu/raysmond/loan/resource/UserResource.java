package edu.fdu.raysmond.loan.resource;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.mvc.Template;

import edu.fdu.raysmond.loan.controller.UserController;
import edu.fdu.raysmond.loan.entity.User;
import edu.fdu.raysmond.loan.entity.UserRole;
import edu.fdu.raysmond.loan.util.Encryption;

/**
 * @author Raysmond
 */
@Path("/user")
public class UserResource {

	@Context
	private SecurityContext security;

	/**
	 * Login - JSON
	 */
	@GET
	@Path("login")
	@RolesAllowed(UserRole.CUSTOMER)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject login() throws JSONException {
		JSONObject username = new JSONObject().put("field", "user_name").put("type", "text");
		JSONObject password = new JSONObject().put("field", "password").put("type", "password");
		return new JSONObject().put("input", new JSONArray().put(username).put(password));
	}

	/**
	 * Login - HTML
	 * 
	 * @return
	 */
	@GET
	@Path("login")
	@Template(name = "login.jsp")
	@Produces(MediaType.TEXT_HTML)
	public Response loginAsHtml() {
		return Response.ok().entity("").build();
	}

	/**
	 * Handling login request
	 */
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response handleLogin(JSONObject input, @Context HttpServletRequest request) throws JSONException {
		JSONObject success = new JSONObject().put("login_result", "success");
		JSONObject fail = new JSONObject().put("login_result", "fail");

		String username = input.getString("user_name");
		String password = input.getString("password");

		if (username == null || password == null || username.equals("") || password.equals("")) {
			return Response.ok().entity(fail).build();
		}

		User user = UserController.getUserByName(username);
		if (user != null && Encryption.MD5(password).equals(user.getPassword())) {
			final HttpSession session = request.getSession();
			session.setAttribute("USER_ROLE", user.getRole());
			session.setAttribute("USER_ID", user.getId().toString());
			return Response.ok().entity(success).build();
		}

		return Response.ok().entity(fail).build();
	}
}
