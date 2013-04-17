package ch.jesc.vasap.web.ui.view.dossier;

import ch.jesc.vasap.core.dao.DossierDAO;
import ch.jesc.vasap.core.model.Dossier;
import ch.jesc.vasap.web.ui.common.BasePanelView;
import ch.jesc.vasap.web.utils.SpringHelper;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class DossierDetailsView extends BasePanelView {

	public static final String NAME = "dossierDetails";

	private String id;

	@Override
	public String getTitle() {
		return "Dossier "+id;
	}

	@Override
	public boolean isAllowed() {
		// Allowed only to the owner
//		final Dossier d = getDossierDao().getById(Long.parseLong(id));
//		if (d != null) {
//			return d.getOwner().getEmail().equals(UserSecurity.getPrincipal());
//		}
//		return false; // denied
		return true;
	}

	public void doNavigateTo(String fragmentParameters) {
		final String[] parts = fragmentParameters.split("=");
		id = parts[1];

		final Dossier d = getDossierDao().getById(Long.parseLong(id));

		final HorizontalLayout block = new HorizontalLayout();
		layout.addComponent(block);
		block.setStyleName("dossier-block");
		block.addComponent(new Label(d.getFrom().toString()));
		block.addComponent(new Label(d.getCustomer()));
		block.addComponent(new Label(d.getDescription()));
	}

	private DossierDAO getDossierDao() {
		return SpringHelper.getBean(DossierDAO.class);
	}
}
