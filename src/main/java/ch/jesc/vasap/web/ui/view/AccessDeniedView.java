package ch.jesc.vasap.web.ui.view;

import ch.jesc.vasap.web.ui.common.BasePanelView;
import com.vaadin.ui.Label;

public class AccessDeniedView extends BasePanelView {

	public static final String NAME = "accessDenied";

	@Override
	public String getTitle() {
		return "Access denied";
	}

	@Override
	public boolean isAllowed() {
		return true;
	}

	@Override
	protected void doNavigateTo(String fragmentParameters) {
		layout.addComponent(new Label("Access denied"));
	}
}
