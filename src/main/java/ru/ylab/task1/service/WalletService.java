package ru.ylab.task1.service;

import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.exception.DbException;

import java.util.List;


/**
 * The interface Wallet service.
 */
public interface WalletService {

    boolean activateTransaction(TransactionDto transactionDto) throws DbException;


    /**
     * Find transaction history list.
     *
     * @param playerId the player id
     * @return the list
     */
    List<TransactionDto> findTransactionHistory(Long playerId);
}
