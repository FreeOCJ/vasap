package ch.jesc.vasap.web.ui.common;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;

public abstract class BaseView extends CustomComponent implements WebView {

	private static Logger log = Logger.getLogger(BaseView.class);

	protected BaseView() {
	}

	@Override
	public final void enter(ViewChangeListener.ViewChangeEvent event) {
		String str = "Navigate to " + getClass().getSimpleName();
		String fragment = event.getParameters();
//		if (StringUtils.isNotBlank(fragment)) {
//			str += " fragment=" + fragment;
//		}
//		log.debug(str);
		doNavigateTo(fragment);
	}

	protected abstract void doNavigateTo(String fragmentParameters);

	protected Navigator getNavigator() {
		return UI.getCurrent().getNavigator();
	}
}
