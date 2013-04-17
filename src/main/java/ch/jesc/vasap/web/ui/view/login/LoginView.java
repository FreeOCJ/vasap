package ch.jesc.vasap.web.ui.view.login;

import ch.jesc.vasap.core.dao.UserDAO;
import ch.jesc.vasap.core.model.User;
import ch.jesc.vasap.security.UserSecurity;
import ch.jesc.vasap.web.ui.common.BasePanelView;
import ch.jesc.vasap.web.utils.SpringHelper;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends BasePanelView {

	public static final String NAME = "login";

	private final VerticalLayout layout;
	private final TextField username;
	private final TextField password;

	public LoginView() {
		layout = new VerticalLayout();
		setCompositionRoot(layout);

		final Label userLabel = new Label("User");
		layout.addComponent(userLabel);
		username = new TextField();
		layout.addComponent(username);

		final Label pwdLabel = new Label("Password");
		layout.addComponent(pwdLabel);
		password = new TextField();
		layout.addComponent(password);

		Button login = new Button("Login");
		layout.addComponent(login);
		login.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				User u = getUserDao().getByEmail(username.getValue());
				if (u != null) {
					if (UserSecurity.login(u, password.getValue())) {
						Notification.show("Login succesful", Notification.Type.HUMANIZED_MESSAGE);
						UI.getCurrent().getNavigator().navigateTo("");
					}
					else {
						Notification.show("Wrong password", Notification.Type.ERROR_MESSAGE);
					}
				}
				else {
					Notification.show("User '"+username.getValue()+"' not found", Notification.Type.ERROR_MESSAGE);
				}
			}
		});

		Label explaination = new Label("You can login either as:<p>admin@master.com for admin rights or as<p>bla@bli.com for a standard John Doe account<p>Password is <i>hello</i> in either case");
		explaination.setContentMode(ContentMode.HTML);
		layout.addComponent(explaination);
	}

	@Override
	public String getTitle() {
		return "Login";
	}

	@Override
	public boolean isAllowed() {
		return true;
	}

	@Override
	protected void doNavigateTo(String fragmentParameters) {
	}

	private UserDAO getUserDao() {
		final UserDAO dao = SpringHelper.getBean(UserDAO.class);
		return dao;
	}

}
