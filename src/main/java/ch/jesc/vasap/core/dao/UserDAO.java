package ch.jesc.vasap.core.dao;

import ch.jesc.vasap.core.model.User;
import ch.jesc.vasap.core.tx.TxCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import java.util.List;

public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
		super(User.class);
	}

	public User getByEmail(final String email) {
		final User user = txTemplate.doInTransaction(new TxCallback<User>() {
			@Override
			public User execute(TransactionStatus status) throws Exception {
				final List<User> users = template.find("from User where email = ?", email);
				if (!users.isEmpty()) {
					Assert.isTrue(users.size() == 1);
					User u = users.get(0);
					u.eagerLoad();
					return u;
				}
				return null;
			}
		});
		return user;
	}

	public User create(final String email, final String password, final String nom, final String prenom, final boolean isAdmin, final boolean isVerified) {
		final User user = txTemplate.doInTransaction(new TxCallback<User>() {
			@Override
			public User execute(TransactionStatus status) throws Exception {
				final User user = new User();
				updateFields(user, email, password, nom, prenom, isAdmin, isVerified);
				template.save(user);
				return user;
			}
		});
		return user;
	}

	public User update(final long id, final String email, final String password, final String nom, final String prenom, final boolean isAdmin, final boolean isVerified) {
		final User user = txTemplate.doInTransaction(new TxCallback<User>() {
			@Override
			public User execute(TransactionStatus status) throws Exception {
				final User user = getById(id);
				updateFields(user, email, password, nom, prenom, isAdmin, isVerified);
				return user;
			}
		});
		return user;
	}

	private void updateFields(User user, final String email, final String password, final String nom, final String prenom, final boolean isAdmin, final boolean isVerified) {
		user.setEmail(email);
		user.setPassword(password);
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setAdmin(isAdmin);
	}

}
