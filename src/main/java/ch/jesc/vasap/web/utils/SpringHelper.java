package ch.jesc.vasap.web.utils;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class SpringHelper {

	public static ApplicationContext getContext() {
//		final Application application = Application.getCurrent();
//		final ServletContext servletContext = ((WebApplicationContext) application.getContext()).getHttpSession().getServletContext();
//		final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//		return context;

		final ServletContext servletContext = RequestHelper.getSession().getServletContext();
		final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return context;

	}

	public static <T> T getBean(Class<T> clz, String name) {
		return (T)getContext().getBean(name);
	}

	public static <T> T getBean(Class<T> clz) {
		return (T)getContext().getBean(clz);
	}

	// Injection
	public static void inject(Object component) {
		inject(component, getContext());
	}
	private static void inject(Object obj, ApplicationContext applicationContext) {
		AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBeanProperties(obj, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);
	}

}
