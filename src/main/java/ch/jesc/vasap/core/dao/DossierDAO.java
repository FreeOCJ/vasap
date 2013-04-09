package ch.jesc.vasap.core.dao;

import ch.jesc.vasap.core.model.Dossier;
import ch.jesc.vasap.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DossierDAO extends GenericDAO<Dossier> {

	@Autowired
	private UserDAO userDao;

	public DossierDAO() {
		super(Dossier.class);
	}

	public Dossier create(String email, String nom, String prenom, String rue, String numero, Integer npa, String localite) {

		User user = userDao.getByEmail(email);
		if (user == null) {
			user = userDao.create("sara.cuendet@gmail.com", "welcome", "Cuendet", "Sara", false, true);
		}

		if (user != null) {

			final Dossier d = new Dossier();
			d.setOwner(user);
			d.setFrom(new Date());
			template.save(d);
			return d;
		}
		return null;
	}

	public List<Dossier> listOpenForUser(String username) {
		final List<Dossier> list = new ArrayList<>();
		for (Dossier d : getAll()) {
			if (d.getOwner().getEmail().equals(username)) {
				list.add(d);
			}
		}
		return list;
	}

}
