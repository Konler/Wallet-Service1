package ru.ylab.task1.service;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.transaction.TransactionType;

import java.util.List;


/**
 * The interface Wallet service.
 */
public interface WalletService {

    /**
     * Activate transaction boolean.
     *
     * @param type     the type
     * @param amount   the amount
     * @param playerId the player id
     * @return the boolean
     */
    boolean activateTransaction(TransactionType type, double amount, Long playerId) throws DbException;


    /**
     * Find transaction history list.
     *
     * @param playerId the player id
     * @return the list
     */
    List<String> findTransactionHistory(Long playerId);
}
