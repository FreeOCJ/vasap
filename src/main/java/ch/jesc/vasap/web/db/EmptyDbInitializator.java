package ch.jesc.vasap.web.db;

import ch.jesc.vasap.core.dao.DossierDAO;
import ch.jesc.vasap.core.dao.UserDAO;
import ch.jesc.vasap.core.model.User;
import ch.jesc.vasap.core.tx.TxCallbackWithoutResult;
import ch.jesc.vasap.core.tx.TxTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;

public class EmptyDbInitializator implements InitializingBean {

	private static final Logger log = Logger.getLogger(EmptyDbInitializator.class);

	@Autowired
	private TxTemplate txTemplate;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private DossierDAO dossierDAO;


	private User user1;
	private User user2;

	@Override
	public void afterPropertiesSet() throws Exception {
		txTemplate.doInTransaction(new TxCallbackWithoutResult() {
			@Override
			public void execute(TransactionStatus status) throws Exception {
				createUsers();

				createDossiers();
			}
		});
	}

	private void createDossiers() {
		int nb = dossierDAO.getAll().size();
		if (nb == 0) {
			dossierDAO.create(user1.getId(), "Nestlé", "Switzerland", "Contract to become a client");
			dossierDAO.create(user1.getId(), "Ericson", "Sweden", "Graduating java developers to Vaadin");
			dossierDAO.create(user1.getId(), "France Telecom", "France", "They need a Vaadin specialist ASAP");
			dossierDAO.create(user1.getId(), "NASA", "USA", "Spreading the word about Java on Mars");

			dossierDAO.create(user2.getId(), "Lonza", "Canada", "Trying to create Aluminium without electricity");

			log.debug("Dossiers created");
		}
		else {
			log.debug("Already "+nb+" dossiers in DB");
		}
	}

	private void createUsers() {
		int nb = userDao.getAll().size();
		if (nb == 0) {
			// 2 users
			user1 = userDao.create("admin@master.com", "hello", "Admin", "Guy", true, true);
			user2 = userDao.create("bla@bli.com", "hello", "Bla", "Bli", false, true);

			log.info("Users created");
		}
		else {
			user1 = userDao.getByEmail("admin@master.com");
			user2 = userDao.getByEmail("bla@bli.com");
			log.debug("Already "+nb+" users in DB");
		}
	}
}
