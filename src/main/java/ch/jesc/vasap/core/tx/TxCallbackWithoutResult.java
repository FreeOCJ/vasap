package ch.jesc.vasap.core.tx;

import org.springframework.transaction.TransactionStatus;

public interface TxCallbackWithoutResult {

	void execute(TransactionStatus status) throws Exception;

}
