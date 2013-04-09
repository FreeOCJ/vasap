package ch.jesc.vasap.core.tx;

import org.springframework.transaction.TransactionStatus;

public interface TxCallback<T> {

	T execute(TransactionStatus status) throws Exception;

}
