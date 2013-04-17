package ch.jesc.vasap.web.app;

import ch.jesc.vasap.web.ui.common.WebView;
import ch.jesc.vasap.web.ui.view.AccessDeniedView;
import ch.jesc.vasap.web.ui.view.dossier.DossierCreateView;
import ch.jesc.vasap.web.ui.view.dossier.DossierListView;
import ch.jesc.vasap.web.ui.view.login.LoginView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("vasap")
public class VasapUI extends UI {

	public VasapUI() {
	}

	@Override
	protected void init(VaadinRequest request) {

		final PageBorder display = new PageBorder();
		setContent(display);

		// Create Navigator, make it control the ViewDisplay
		Navigator navigator = new Navigator(UI.getCurrent(), (ViewDisplay)display);
		setNavigator(navigator);
		navigator.addViewChangeListener(new ViewChangeListener() {
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return ((WebView)event.getNewView()).isAllowed();
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				toString();
			}
		});

		addViews(navigator);
	}

	protected void addViews(Navigator navigator) {
		// Vue par défaut
		addView("", DossierListView.class);

		addView(DossierListView.NAME, DossierListView.class);
		addView(DossierCreateView.NAME, DossierCreateView.class);
		addView(LoginView.NAME, LoginView.class);
		addView(AccessDeniedView.NAME, AccessDeniedView.class);
	}

	protected void addView(String viewName, Class<? extends WebView> viewClass) {
		getNavigator().addView(viewName, viewClass);
	}

}
