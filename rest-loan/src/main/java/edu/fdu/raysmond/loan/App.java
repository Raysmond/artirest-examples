package edu.fdu.raysmond.loan;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.message.filtering.SecurityEntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * @author Raysmond
 */
@ApplicationPath("/")
public class App extends ResourceConfig {

	public App() {
		// Register all resources under the package
		packages("edu.fdu.raysmond.loan");

		// Register entity-filtering security feature.
		register(SecurityEntityFilteringFeature.class);

		// MVC
		register(JspMvcFeature.class);

		register(LoggingFilter.class);

		// Jettison - JSON provider
		register(new JettisonFeature());

		// Configure MOXy Json provider.
		// register(new MoxyJsonConfig().setFormattedOutput(true).resolver());
	}
}