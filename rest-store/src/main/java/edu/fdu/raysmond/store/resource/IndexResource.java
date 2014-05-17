package edu.fdu.raysmond.store.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * @author Raysmond
 */
@Path("/")
public class IndexResource {

	@GET
	public void index(@Context HttpServletResponse response) throws IOException {
		response.sendRedirect("order");
	}
}
