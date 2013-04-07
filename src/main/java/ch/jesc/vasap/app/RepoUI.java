package ch.jesc.vasap.app;

import ch.jesc.vasap.ui.view.dossier.DossierCreateView;
import ch.jesc.vasap.ui.view.dossier.DossierListView;
import com.vaadin.navigator.Navigator;
import net.ecozig.web.application.ApplicationUI;
import net.ecozig.web.application.PageBorder;

public class RepoUI extends ApplicationUI {

	public RepoUI() {
	}

	protected PageBorder createPageBorder() {
		return new RepoPageBorder();
	}

	protected void addViews(Navigator navigator) {
		super.addViews(navigator);

		// Vue par défaut
		addView("", DossierListView.class);

		addView(DossierListView.NAME, DossierListView.class);
		addView(DossierCreateView.NAME, DossierCreateView.class);
	}

}
