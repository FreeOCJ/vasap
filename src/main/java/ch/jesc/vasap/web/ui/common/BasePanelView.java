package ch.jesc.vasap.web.ui.common;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public abstract class BasePanelView extends BaseView {

	protected final Panel panel = new Panel();
	protected final VerticalLayout layout = new VerticalLayout();

	public BasePanelView() {
		setCompositionRoot(panel);
		panel.setContent(layout);
	}
}
