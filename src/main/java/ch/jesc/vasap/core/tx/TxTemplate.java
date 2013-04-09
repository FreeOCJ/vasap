package ch.jesc.vasap.core.tx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.UndeclaredThrowableException;

public class TxTemplate {

	private static final Log log = LogFactory.getLog(TxTemplate.class);

	private PlatformTransactionManager transactionManager;

	public TxTemplate(PlatformTransactionManager ptm) {
		transactionManager = ptm;
	}

	public void doInTransaction(TxCallbackWithoutResult action) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(dtd);
		try {
			action.execute(status);
		}
		catch (RuntimeException ex) {
			// Transactional code threw application exception -> rollback
			rollbackOnException(status, ex);
			throw ex;
		}
		catch (Error err) {
			// Transactional code threw error -> rollback
			rollbackOnException(status, err);
			throw err;
		}
		catch (Exception ex) {
			// Transactional code threw unexpected exception -> rollback
			rollbackOnException(status, ex);
			throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
		}
		this.transactionManager.commit(status);
	}

	public <T> T doInTransaction(TxCallback<T> action) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		TransactionStatus status = this.transactionManager.getTransaction(dtd);
		T result;
		try {
			result = action.execute(status);
		}
		catch (RuntimeException ex) {
			// Transactional code threw application exception -> rollback
			rollbackOnException(status, ex);
			throw ex;
		}
		catch (Error err) {
			// Transactional code threw error -> rollback
			rollbackOnException(status, err);
			throw err;
		}
		catch (Exception ex) {
			// Transactional code threw unexpected exception -> rollback
			rollbackOnException(status, ex);
			throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
		}
		this.transactionManager.commit(status);
		return result;
	}

	private void rollbackOnException(TransactionStatus status, Throwable ex) throws TransactionException {
		log.debug("Initiating transaction rollback on application exception", ex);
		try {
			this.transactionManager.rollback(status);
		}
		catch (TransactionSystemException ex2) {
			log.error("Application exception overridden by rollback exception", ex);
			ex2.initApplicationException(ex);
			throw ex2;
		}
		catch (RuntimeException ex2) {
			log.error("Application exception overridden by rollback exception", ex);
			throw ex2;
		}
		catch (Error err) {
			log.error("Application exception overridden by rollback error", ex);
			throw err;
		}
	}

}
