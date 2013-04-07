package ch.jesc.vasap.common;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import net.ecozig.web.application.PanelView;
import net.ecozig.web.application.WebView;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class RepoBaseView extends CustomComponent implements WebView {

	private static Logger log = Logger.getLogger(PanelView.class);

	protected RepoBaseView() {
	}

	@Override
	public boolean isAllowed() {
		return true;
	}

	@Override
	public final void enter(ViewChangeListener.ViewChangeEvent event) {
		String str = "Navigate to " + getClass().getSimpleName();
		String fragment = event.getParameters();
		if (StringUtils.isNotBlank(fragment)) {
			str += " fragment=" + fragment;
		}
		log.debug(str);
		doNavigateTo(fragment);
	}

	protected abstract void doNavigateTo(String fragmentParameters);

	protected Navigator getNavigator() {
		return UI.getCurrent().getNavigator();
	}
}
