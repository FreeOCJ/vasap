package ch.jesc.vasap.web.ui.view.dossier;

import ch.jesc.vasap.core.dao.DossierDAO;
import ch.jesc.vasap.core.model.Dossier;
import ch.jesc.vasap.security.UserSecurity;
import ch.jesc.vasap.web.ui.common.BasePanelView;
import ch.jesc.vasap.web.utils.SpringHelper;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import java.util.List;

public class DossierListView extends BasePanelView {

	public static final String NAME = "dossierList";

	@Override
	public String getTitle() {
		return "Mes dossiers";
	}

	@Override
	public boolean isAllowed() {
		return true;
	}

	public void doNavigateTo(String fragmentParameters) {

		final List<Dossier> list = getDossierDao().listOpenForUser(UserSecurity.getPrincipal());
		if (list.isEmpty()) {
			layout.addComponent(new Label("No dossier for this user. Please Login."));
		}
		else {

			//Table table = new Table();

			for (final Dossier d : list) {
				final HorizontalLayout block = new HorizontalLayout();
				layout.addComponent(block);
				block.setStyleName("dossier-block");
				block.addComponent(new Link(d.getFrom().toString(), new ExternalResource("#!dossierDetails/id="+d.getId())));
				block.addComponent(new Label(d.getCustomer()));
				block.addComponent(new Label(d.getDescription()));
			}
		}
	}

	private DossierDAO getDossierDao() {
		return SpringHelper.getBean(DossierDAO.class);
	}
}
