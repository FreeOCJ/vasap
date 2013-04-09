package ch.jesc.vasap.security;

import ch.jesc.vasap.core.model.User;
import ch.jesc.vasap.web.utils.RequestHelper;
import org.apache.log4j.Logger;

public class UserSecurity {

	private static Logger log = Logger.getLogger(UserSecurity.class);

	public static void logout() {
		RequestHelper.getSession().removeAttribute("security-details");
	}

	public static boolean login(User u, String password) {
		if (u != null && u.getPassword().equals(password)) {
			createDetails(u.getId(), u.getEmail(), u.getNom(), u.getPrenom());
			return true;
		}
		return false;
	}

	public static String getFirst() {
		return getDetails().getFirst();
	}
	public static String getName() {
		return getDetails().getName();
	}
	public static String getPrincipal() {
		if (getDetails() != null) {
			return getDetails().getPrincipal();
		}
		return "<unknown>";
	}

	protected static SecurityDetails getDetails() {
		final SecurityDetails details = (SecurityDetails)RequestHelper.getSession().getAttribute("security-details");
		return details;
	}
	public static SecurityDetails createDetails(long userId, String principal, String name, String first) {
		final SecurityDetails details = new SecurityDetails(userId, principal, name, first);
		RequestHelper.getSession().setAttribute("security-details", details);
		log.info("Login de "+principal+" (id="+userId+")");
		return details;
	}

	public static boolean isLogged() {
		return getDetails() != null;
	}
}
