package ch.jesc.vasap.ui.view.dossier;

import ch.jesc.carenet.core.dao.DossierDAO;
import ch.jesc.carenet.core.model.Customer;
import ch.jesc.carenet.core.model.Dossier;
import ch.jesc.vasap.ui.view.BasePanelView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import net.ecozig.web.security.UserSecurity;
import net.ecozig.web.utils.SpringHelper;

import java.util.List;

public class DossierListView extends BasePanelView {

	public static final String NAME = "dossierList";

	@Override
	public String getTitle() {
		return "Mes dossiers";
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
			Customer c = d.getCustomer();
			block.addComponent(new Label(c.getNom()+" "+c.getPrenom()));
		}
	}

	private DossierDAO getDossierDao() {
		return SpringHelper.getBean(DossierDAO.class);
	}
}
