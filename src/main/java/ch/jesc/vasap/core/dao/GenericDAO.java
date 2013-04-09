package ch.jesc.vasap.core.dao;

import ch.jesc.vasap.core.model.BaseEntity;
import ch.jesc.vasap.core.tx.TxCallback;
import ch.jesc.vasap.core.tx.TxCallbackWithoutResult;
import ch.jesc.vasap.core.tx.TxTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

public abstract class GenericDAO<T extends BaseEntity> {

	private final Class<T> clz;

	@Autowired
	protected HibernateTemplate template;
	@Autowired
	protected TxTemplate txTemplate;

	public GenericDAO(Class<T> c) {
		clz = c;
	}

	public T getById(final long id) {
		final T user = txTemplate.doInTransaction(new TxCallback<T>() {
			@Override
			public T execute(TransactionStatus status) throws Exception {
				T t = template.get(clz, id);
				eagerLoad(t);
				return t;
			}
		});
		return user;
	}

	public void delete(final long id) {
		txTemplate.doInTransaction(new TxCallbackWithoutResult() {
			@Override
			public void execute(TransactionStatus status) throws Exception {
				template.delete(getById(id));
			}
		});
	}

	public List<T> getAll() {
		final List<T> models = txTemplate.doInTransaction(new TxCallback<List<T>>() {
			@Override
			public List<T> execute(TransactionStatus status) throws Exception {
				final List<T> models = template.find("from "+clz.getSimpleName()+" dm order by dm.id");
				for (T m : models) {
					eagerLoad(m);
				}
				return models;
			}
		});
		return models;
	}

	protected T eagerLoad(T be) {
		if (be != null) {
			be.eagerLoad();
		}
		return be;
	}

	protected List<T> eagerLoad(List<T> bes) {
		if (bes != null) {
			for (BaseEntity be : bes) {
				be.eagerLoad();
			}
		}
		return bes;
	}

}
