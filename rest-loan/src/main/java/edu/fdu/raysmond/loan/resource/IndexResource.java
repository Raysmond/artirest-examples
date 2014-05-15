package edu.fdu.raysmond.loan.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

/**
 * @author Raysmond
 */
@Path("/")
public class IndexResource {

	@GET
	@Template(name = "/jsp/index.jsp")
	@Produces(MediaType.TEXT_HTML)
	public String index() {
		return "index";
	}
}
