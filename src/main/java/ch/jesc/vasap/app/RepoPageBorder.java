package ch.jesc.vasap.app;

import ch.jesc.vasap.ui.view.dossier.DossierListView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import net.ecozig.web.application.PageBorder;

public class RepoPageBorder extends PageBorder {

	public RepoPageBorder() {
		toString(); // For BP
	}

	protected void addLeftMenus(VerticalLayout menuLayout) {

		// Home
		{
			final MenuBar menuBar = new MenuBar();
			menuBar.setSizeFull();
			menuBar.addItem("Mes dossiers", new MenuBar.Command() {
				@Override
				public void menuSelected(MenuBar.MenuItem selectedItem) {
					UI.getCurrent().getNavigator().navigateTo(DossierListView.NAME);
				}
			});
			menuLayout.addComponent(menuBar);
		}
	}

}
