package ru.ylab.task1.repository;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;

import java.util.List;

/**
 * The interface Transaction repository.
 */
public interface TransactionRepository {


    Transaction createTransaction(Transaction transaction) throws DbException;

    /**
     * Gets all player transaction.
     *
     * @param playerId the player id
     * @return the all player transaction
     */
    List<Transaction> getAllPlayerTransaction(Long playerId);

}
