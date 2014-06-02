package edu.fdu.raysmond.loan.provider;

//import java.io.IOException;
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.StringTokenizer;
//
//import javax.annotation.security.RolesAllowed;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.PreMatching;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.SecurityContext;
//import javax.ws.rs.ext.Provider;
//
//import edu.fdu.raysmond.loan.entity.UserRole;

/**
 * @author Raysmond
 */
// @Provider
// @PreMatching
public class SecurityRequestFilter {
// implements ContainerRequestFilter {
//	@Context
//	HttpServletRequest webRequest;
//
//	@Override
//	public void filter(final ContainerRequestContext requestContext) throws IOException {
//		final HttpSession session = webRequest.getSession();
//
//		requestContext.setSecurityContext(new SecurityContext() {
//			@Override
//			public Principal getUserPrincipal() {
//				return new Principal() {
//					@Override
//					public String getName() {
//						return (String) session.getAttribute("USER_ID");
//					}
//				};
//			}
//
//			@Override
//			public boolean isUserInRole(final String role) {
//				
//				String r = (String) session.getAttribute("USER_ROLE");
//				return role.equals((r == null ? UserRole.CUSTOMER : r));
//			}
//
//			@Override
//			public boolean isSecure() {
//				return false;
//			}
//
//			@Override
//			public String getAuthenticationScheme() {
//				return null;
//			}
//		});
//	}
}