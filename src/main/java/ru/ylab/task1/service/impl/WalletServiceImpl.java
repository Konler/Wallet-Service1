package ru.ylab.task1.service.impl;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.model.transaction.State;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.TransactionRepository;
import ru.ylab.task1.service.WalletService;

import java.util.List;

import static ru.ylab.task1.model.transaction.TransactionType.DEBIT;

/**
 * The type Wallet service.
 */
public class WalletServiceImpl implements WalletService {
    private final TransactionRepository transactionRepository;
    private final PlayerRepository playerRepository;

    /**
     * Instantiates a new Wallet service.
     *
     * @param transactionRepository the transaction repository
     * @param playerRepository      the player repository
     */
    public WalletServiceImpl(TransactionRepository transactionRepository, PlayerRepository playerRepository) {
        this.transactionRepository = transactionRepository;
        this.playerRepository = playerRepository;
    }

    public boolean activateTransaction(TransactionType type, double amount, Long playerId) throws DbException {
        Transaction transaction = transactionRepository.createTransaction(type, amount, playerId);
        if (DEBIT.equals(type)) {
            amount *= -1;
        }
        try {
            playerRepository.changePlayerBalanceById(playerId, amount);
            transaction.setState(State.SUCCESS);
            return true;
        } catch (ImpossibleTransactionException e) {
            return false;
        }
    }


    public List<String> findTransactionHistory(Long playerId) {
        return transactionRepository.getAllPlayerTransaction(playerId).stream().map(Transaction::toString).toList();
    }
}
