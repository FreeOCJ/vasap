package ch.jesc.vasap.web.ui.view.dossier;

import ch.jesc.vasap.core.dao.DossierDAO;
import ch.jesc.vasap.core.model.Dossier;
import ch.jesc.vasap.security.UserSecurity;
import ch.jesc.vasap.web.ui.common.BasePanelView;
import ch.jesc.vasap.web.utils.SpringHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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

		Button create = new Button("Créer un dossier");
		layout.addComponent(create);
		create.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				getNavigator().navigateTo(DossierCreateView.NAME);
			}
		});

		List<Dossier> list = getDossierDao().listOpenForUser(UserSecurity.getPrincipal());
		for (Dossier d : list) {
			VerticalLayout block = new VerticalLayout();
			layout.addComponent(block);
			block.setStyleName("dossier-block");
			block.addComponent(new Label(d.getFrom().toString()));
			block.addComponent(new Label(d.getCustomer()));
			block.addComponent(new Label(d.getDescription()));
		}
	}

	private DossierDAO getDossierDao() {
		return SpringHelper.getBean(DossierDAO.class);
	}
}
