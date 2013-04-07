package ch.jesc.vasap.ui.view;

import ch.jesc.vasap.common.RepoBaseView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public abstract class BasePanelView extends RepoBaseView {

	protected final Panel panel = new Panel();
	protected final VerticalLayout layout = new VerticalLayout();

	public BasePanelView() {
		setCompositionRoot(panel);
		panel.setContent(layout);
	}
}
