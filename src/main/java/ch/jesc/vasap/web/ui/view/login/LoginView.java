package ch.jesc.vasap.web.ui.view.login;

import ch.jesc.vasap.web.ui.common.BasePanelView;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends BasePanelView {

	public static final String NAME = "login";

	private final VerticalLayout layout;
	private final TextField username;
	private final TextField password;

	public LoginView() {
		layout = new VerticalLayout();
		setCompositionRoot(layout);

		username = new TextField();
		layout.addComponent(username);
		password = new TextField();
		layout.addComponent(password);

		Button login = new Button();
		layout.addComponent(login);
		login.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				toString();
			}
		});
	}

	@Override
	public String getTitle() {
		return "Login";
	}

	@Override
	protected void doNavigateTo(String fragmentParameters) {
	}

}
