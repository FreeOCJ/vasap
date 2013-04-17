package ch.jesc.vasap.web.app;

import ch.jesc.vasap.security.UserSecurity;
import ch.jesc.vasap.web.ui.common.WebView;
import ch.jesc.vasap.web.ui.view.dossier.DossierListView;
import ch.jesc.vasap.web.ui.view.login.LoginView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@Theme("vasap")
public class PageBorder extends AbsoluteLayout implements ViewDisplay {

	private final VerticalLayout bodyLayout = new VerticalLayout();

	public PageBorder() {
		final AbsoluteLayout fullLayout = this;
		fullLayout.setSizeFull();

		final VerticalLayout headerContainer = new VerticalLayout();
		headerContainer.setSizeFull();
		headerContainer.setHeight("110px");
		headerContainer.setStyleName("sf-headerLayout");

		int contentPadding = 5;
		fullLayout.addComponent(headerContainer, "top: 0px; left: " + contentPadding + " px; right: " + contentPadding + "px;");

		{ // headerLayout

			HorizontalLayout header = new HorizontalLayout();
			header.setSizeFull();
			headerContainer.addComponent(header);

			Embedded vdLogo = new Embedded(null, new ThemeResource("images/logo.png"));
			vdLogo.setSizeUndefined();

			header.addComponent(vdLogo);
			header.setComponentAlignment(vdLogo, Alignment.MIDDLE_LEFT);

			{ // headerSplit

				VerticalLayout headerSplit = new VerticalLayout();
				headerSplit.setSizeFull();
				header.addComponent(headerSplit);
				header.setComponentAlignment(headerSplit, Alignment.MIDDLE_RIGHT);
				header.setExpandRatio(headerSplit, 1);

				HorizontalLayout appNameAndNavButtonsLayout = new HorizontalLayout();
				appNameAndNavButtonsLayout.setSizeFull();

				headerSplit.addComponent(appNameAndNavButtonsLayout);
				headerSplit.setExpandRatio(appNameAndNavButtonsLayout, 1);

				{ // appNameAndAdminSlogan

					VerticalLayout appName = new VerticalLayout();
					appNameAndNavButtonsLayout.addComponent(appName);
					appNameAndNavButtonsLayout.setExpandRatio(appName, 1);

					String appNameStr = "VaSAp";
					Label rcpersLabel = new Label(appNameStr);
					rcpersLabel.addStyleName("sf-header-appTitle");
					appName.addComponent(rcpersLabel);

					final Label acvLabel = new Label("Vaadin Sample Application");
					acvLabel.addStyleName("sf-header-appSubTitle");
					appName.addComponent(acvLabel);

					{ // navigation buttons

						final HorizontalLayout topRightButtonsBar = new HorizontalLayout();
						topRightButtonsBar.setMargin(true);
						topRightButtonsBar.setSpacing(true);
						appNameAndNavButtonsLayout.addComponent(topRightButtonsBar);

						appNameAndNavButtonsLayout.setComponentAlignment(topRightButtonsBar, Alignment.MIDDLE_RIGHT);

						// Login
						{
							final Button accueilButton = new Button("Login");
							accueilButton.setStyleName(Reindeer.BUTTON_SMALL);
							topRightButtonsBar.addComponent(accueilButton);
							topRightButtonsBar.setComponentAlignment(accueilButton, Alignment.MIDDLE_LEFT);
							accueilButton.addClickListener(new Button.ClickListener() {
								@Override
								public void buttonClick(Button.ClickEvent event) {
									UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
								}
							});
						}

						// Home
						{
							final Button portailButton = new Button("Home");
							portailButton.setStyleName(Reindeer.BUTTON_SMALL);
							topRightButtonsBar.addComponent(portailButton);
							topRightButtonsBar.setComponentAlignment(portailButton, Alignment.MIDDLE_RIGHT);
							portailButton.addClickListener(new Button.ClickListener() {
								@Override
								public void buttonClick(Button.ClickEvent event) {
									UI.getCurrent().getNavigator().navigateTo(DossierListView.NAME);
								}
							});
						}

					} // navigation buttons

				} // appNameAndAdminSlogan

				// Menu
				final VasapMenu menuBar = new VasapMenu();
				headerSplit.addComponent(menuBar);

			} // headerSplit
		}
		
		// Header
		{
			final HorizontalLayout header = new HorizontalLayout();
			//fullLayout.addComponent(header, "top:5px; left:5px; right: 5px; height: 800px;");
			header.addStyleName("header-layout");
			header.setMargin(true);

			// Title
			{
				final Label title = new Label("VaSAp - Vaadin Sample Application");
				header.addComponent(title);
				header.setExpandRatio(title, 10.0f);
				header.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
				title.setStyleName("title-label");
			}

			// Espcement
			{
				final Label title = new Label();
				header.addComponent(title);
				header.setExpandRatio(title, 5.0f);
				header.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
				//title.setStyleName("title-label");
				title.setSizeUndefined();
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
			}
			else {
				final Button login = new Button("Login");
				header.addComponent(login);
				header.setComponentAlignment(login, Alignment.MIDDLE_RIGHT);
				header.setExpandRatio(login, 10.0f);
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
//
//		// Menu du haut
//		{
//			final HorizontalLayout menuLayout = new HorizontalLayout();
//			fullLayout.addComponent(menuLayout, "top:100px; left:5px; right:5px; height: 30px;");
//			menuLayout.setStyleName("top-menu-layout");
//
//			{
//				final MenuBar menuBar = new MenuBar();
//				menuLayout.addComponent(menuBar);
//				//menuBar.setSizeFull();
//				menuBar.addItem("List", new MenuBar.Command() {
//					@Override
//					public void menuSelected(MenuBar.MenuItem selectedItem) {
//						UI.getCurrent().getNavigator().navigateTo(DossierListView.NAME);
//					}
//				});
//				menuLayout.addComponent(menuBar);
//			}
//			{
//				final MenuBar menuBar = new MenuBar();
//				menuLayout.addComponent(menuBar);
//				//menuBar.setSizeFull();
//				menuBar.addItem("Create", new MenuBar.Command() {
//					@Override
//					public void menuSelected(MenuBar.MenuItem selectedItem) {
//						UI.getCurrent().getNavigator().navigateTo(DossierCreateView.NAME);
//					}
//				});
//				menuLayout.addComponent(menuBar);
//			}
//		}

		// Partie centrale
		{
			final HorizontalLayout middleLayout = new HorizontalLayout();
			fullLayout.addComponent(bodyLayout, "top:140px; left:5px; right: 5px; bottom: 40px;");
			bodyLayout.setStyleName("body-layout");
			bodyLayout.setSizeFull();
		}

		{ // FOOTER SECTION

			HorizontalLayout footer = new HorizontalLayout();
			footer.addStyleName("footerLayout");
			footer.setSizeFull();
			footer.setHeight("20px");
			footer.setSpacing(true);
			fullLayout.addComponent(footer, "bottom:5px; left:" + contentPadding + "px; right:" + contentPadding + "px;");

			{ // leftLabelsLayout
				HorizontalLayout footerLeft = new HorizontalLayout();
				footerLeft.setSpacing(true);
				footer.addComponent(footerLeft);
				footer.setExpandRatio(footerLeft, 1.0f);

				{ // env
					Label environmentLbl = new Label("Env:");
					environmentLbl.addStyleName("whiteText");
					footerLeft.addComponent(environmentLbl);

					Label environmentValue = new Label("Production");
					footerLeft.addComponent(environmentValue);
				} // env

				{ // Version

					Label snapshotLbl = new Label("Version:");
					snapshotLbl.addStyleName("whiteText");
					footerLeft.addComponent(snapshotLbl);

					Label snapshotValue = new Label("The version 1.0");
					footerLeft.addComponent(snapshotValue);

				} // Version

				{ // Build date time

					Label snapshotLbl = new Label("Build:");
					snapshotLbl.addStyleName("whiteText");
					footerLeft.addComponent(snapshotLbl);

					Label snapshotValue = new Label("Time of build");
					footerLeft.addComponent(snapshotValue);

				} // Build date time

			} // leftLabelsLayout

			{ // Right side

				{ // User

					Label userLbl = new Label("Utilisateur: ");
					userLbl.addStyleName("whiteText");
					footer.addComponent(userLbl);

					Label userValue = new Label(UserSecurity.getPrincipal());
					footer.addComponent(userValue);

				} // User

				{ // logout button

					final Button logoutButton = new Button("Quitter");
					logoutButton.setStyleName(Reindeer.BUTTON_SMALL);
					footer.addComponent(logoutButton);
					footer.setComponentAlignment(logoutButton, Alignment.MIDDLE_RIGHT);
					{
						logoutButton.addClickListener(new Button.ClickListener() {
							@Override
							public void buttonClick(Button.ClickEvent event) {
								//RcpersUI.getRcpersUI().logout();
							}
						});
					}

				} // logout

			} // Right side

		} // FOOTER SECTION
	}

	@Override
	public void showView(View vview) {
		final WebView webView = (WebView) vview;
		final CustomComponent viewComponent = (CustomComponent) vview;

		bodyLayout.removeAllComponents();
		bodyLayout.addComponent(viewComponent);

		viewComponent.setSizeFull();
		viewComponent.setStyleName("body-view");
	}


}
