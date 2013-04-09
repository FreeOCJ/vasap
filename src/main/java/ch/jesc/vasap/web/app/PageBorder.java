package ch.jesc.vasap.web.app;

import ch.jesc.vasap.security.UserSecurity;
import ch.jesc.vasap.web.ui.common.WebView;
import ch.jesc.vasap.web.ui.view.dossier.DossierListView;
import ch.jesc.vasap.web.ui.view.login.LoginView;
import ch.jesc.vasap.web.utils.RequestHelper;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import java.util.Date;

@Theme("vasap")
public class PageBorder extends AbsoluteLayout implements ViewDisplay {

	private final VerticalLayout bodyLayout = new VerticalLayout();

	public PageBorder() {
		final AbsoluteLayout fullLayout = this;
		fullLayout.setSizeFull();

		// Header
		{
			final HorizontalLayout header = new HorizontalLayout();
			fullLayout.addComponent(header, "top:5px; left:5px; right: 5px; height: 60px;");
			header.addStyleName("header-layout");
			// Title
			{
				final Label title = new Label("CareNet");
				header.addComponent(title);
				header.setExpandRatio(title, 10.0f);
				header.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
				title.setStyleName("title-label");
			}

			// Login
			if (UserSecurity.isLogged()) {
				final Label firstLast = new Label(UserSecurity.getFirst() + " " + UserSecurity.getName());
				header.addComponent(firstLast);
				//header.setComponentAlignment(firstLast, Alignment.TOP_RIGHT);
				header.setExpandRatio(firstLast, 1.0f);
				firstLast.setStyleName("login-label");

				final Button logout = new Button("Logout");
				header.addComponent(logout);
				//header.setComponentAlignment(logout, Alignment.TOP_RIGHT);
				header.setExpandRatio(logout, 1.0f);
				logout.addStyleName("login-label");
				logout.addStyleName(Reindeer.BUTTON_LINK);
				logout.addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(Button.ClickEvent event) {
						UserSecurity.logout();
						UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
					}
				});
			} else {
				final Button login = new Button("Login");
				header.addComponent(login);
				header.setComponentAlignment(login, Alignment.MIDDLE_RIGHT);
				header.setExpandRatio(login, 1.0f);
				login.addStyleName("login-label");
				login.addStyleName(Reindeer.BUTTON_LINK);
				login.addClickListener(new Button.ClickListener() {
					@Override
					public void buttonClick(Button.ClickEvent event) {
						UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
					}
				});
			}
		}

		// Menu du haut
		{
			final HorizontalLayout menuLayout = new HorizontalLayout();
			fullLayout.addComponent(menuLayout, "top:100px; left:5px; right:5px; height: 30px;");
			menuLayout.setStyleName("top-menu-layout");

			menuLayout.addComponent(new Label("Menu 1"));
		}

		// Partie centrale
		{
			final HorizontalLayout middleLayout = new HorizontalLayout();
			fullLayout.addComponent(bodyLayout, "top:140px; left:5px; right: 5px; bottom: 40px;");
			bodyLayout.setStyleName("body-layout");
			bodyLayout.setSizeFull();
		}

		// Footer
		{
			final HorizontalLayout footerPanel = new HorizontalLayout();
			fullLayout.addComponent(footerPanel, "bottom: 5px; left: 5px; right: 5px; height: 30px;");
			footerPanel.addStyleName("footer-layout");
			//footerPanel.setHeight("30px");

			Label ul1 = new Label(" ");
			footerPanel.addComponent(ul1);
			footerPanel.setComponentAlignment(ul1, Alignment.MIDDLE_CENTER);
			footerPanel.setExpandRatio(ul1, 3.0f);
			ul1.setSizeFull();

			Label dateTime = new Label(new Date().toString());
			footerPanel.addComponent(dateTime);
			footerPanel.setComponentAlignment(dateTime, Alignment.MIDDLE_CENTER);
			footerPanel.setExpandRatio(dateTime, 3.0f);
			dateTime.setSizeUndefined();

			Label ul2 = new Label(" ");
			footerPanel.addComponent(ul2);
			footerPanel.setComponentAlignment(ul2, Alignment.MIDDLE_CENTER);
			footerPanel.setExpandRatio(ul2, 3.0f);
			ul2.setSizeFull();

			Label language = new Label(RequestHelper.getLocale().getDisplayLanguage());
			footerPanel.addComponent(language);
			footerPanel.setComponentAlignment(language, Alignment.MIDDLE_RIGHT);
			footerPanel.setExpandRatio(language, 3.0f);
			language.setSizeFull();

		}
	}

	@Override
	public void showView(View vview) {
		final WebView webView = (WebView) vview;
		final CustomComponent viewComponent = (CustomComponent) vview;

		bodyLayout.removeAllComponents();
		bodyLayout.addComponent(viewComponent);

		viewComponent.setSizeFull();
		viewComponent.setStyleName("body-view");

		Page.getCurrent().setTitle("CareNet - " + webView.getTitle());
	}

	private void addLeftMenus(VerticalLayout menuLayout) {

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
