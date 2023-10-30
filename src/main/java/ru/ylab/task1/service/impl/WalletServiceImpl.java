package ru.ylab.task1.service.impl;

import org.mapstruct.Mapper;
import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.mapper.TransactionMapper;
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

    private TransactionMapper mapper;

    /**
     * Instantiates a new Wallet service.
     *
     * @param transactionRepository the transaction repository
     * @param playerRepository      the player repository
     */
    public WalletServiceImpl(TransactionRepository transactionRepository, PlayerRepository playerRepository) {
        this.transactionRepository = transactionRepository;
        this.playerRepository = playerRepository;
        mapper = TransactionMapper.INSTANCE;
    }

    public boolean activateTransaction(TransactionDto transactionDto) throws DbException {
        Transaction newTransaction = mapper.transactionDtoToTransaction(transactionDto);
        Transaction transaction = transactionRepository.createTransaction(newTransaction);
        if (DEBIT.equals(newTransaction.getType())) {
            transaction.setAmount(transaction.getAmount() * -1);
        }
        try {
            playerRepository.changePlayerBalanceById(transaction.getPlayerId(), transaction.getAmount());
            transaction.setState(State.SUCCESS);
            return true;
        } catch (ImpossibleTransactionException e) {
            return false;
        }
    }


    public List<TransactionDto> findTransactionHistory(Long playerId) {
        return transactionRepository.getAllPlayerTransaction(playerId).stream().map(x -> mapper.transactionToTransactionDto(x)).toList();
    }
}
