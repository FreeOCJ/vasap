package ch.jesc.vasap.web.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class RequestHelper {

	public static String getContext() {
		return getRequest().getContextPath();
	}

	public static void setLocale(Locale l) {
		getSession().setAttribute("locale", l);
		Locale lo = (Locale)getSession().getAttribute("locale");
		lo = null;
	}

	public static Locale getLocale() {
		Locale l = (Locale)getSession().getAttribute("locale");
		if (l == null) {
			l = LocaleContextHolder.getLocale();
		}
		if (l == null) {
			l = Locale.ENGLISH;
		}
		return l;
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attr == null) {
			attr = null;
		}
		Assert.notNull(attr);
		return attr.getRequest();
	}
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	private static ServletRequestAttributes getAttributes() {
		return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	}

}
