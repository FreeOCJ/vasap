package ch.jesc.vasap.web.app;

import ch.jesc.vasap.web.ui.view.dossier.DossierCreateView;
import ch.jesc.vasap.web.ui.view.dossier.DossierListView;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;


@SuppressWarnings("serial")
public class VasapMenu extends CustomComponent {

    private static final Logger LOGGER = Logger.getLogger(VasapMenu.class);

    public VasapMenu() {
        setSizeUndefined();

		final CssLayout menuLayoutContainer = new CssLayout();
		final HorizontalLayout menuTransporter = new HorizontalLayout();
		menuTransporter.setStyleName("sharedMenu");
        menuTransporter.setSizeUndefined();

		menuLayoutContainer.addComponent(menuTransporter);
        setCompositionRoot(menuLayoutContainer);

        menuLayoutContainer.setSizeUndefined();
        menuLayoutContainer.setStyleName("menuLayoutContainer");

        menuTransporter.setSpacing(false);
        menuTransporter.setMargin(false);
        menuTransporter.setSizeUndefined();

        // Personne
		{
			final MenuBar menuPersonne = new MenuBar();
			menuPersonne.setStyleName("sf-header-menu");
			menuPersonne.setSizeUndefined();

			final MenuItem personneTopMenu = menuPersonne.addItem("Dossier", null);
			{
				addLinkInMenu(menuPersonne, personneTopMenu, "List", DossierListView.NAME);
				addLinkInMenu(menuPersonne, personneTopMenu, "Create", DossierCreateView.NAME);
			}
			menuTransporter.addComponent(menuPersonne);
		}

        // Evenements
		{
            final MenuBar menuEvenements = new MenuBar();
            menuEvenements.setStyleName("sf-header-menu");
            menuEvenements.setSizeUndefined();

            final MenuItem evenementsTopMenu = menuEvenements.addItem("Menu 2", null);
			menuTransporter.addComponent(menuEvenements);
            {
                addLinkInMenu(menuEvenements, evenementsTopMenu, "Item 3", "TODO");
				addLinkInMenu(menuEvenements, evenementsTopMenu, "Item 4", "TODO");
            }
        }
    }

	protected void addLinkInMenu(final MenuBar menuBar, final MenuItem targetTopMenuItem, final String title, final String pageName) {
		targetTopMenuItem.addItem(title, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(pageName);
			}
		});
    }

}
