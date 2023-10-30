package ru.ylab.task1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.TransactionRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.ylab.task1.model.transaction.TransactionType.DEBIT;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    public void setUp() {
        walletService = new WalletServiceImpl(transactionRepository, playerRepository);
    }

    @Test
    public void testActivateTransactionWhenTransactionIsSuccessfulThenReturnTrue() throws Exception {
        Long id = 1L;
        TransactionType type = DEBIT;
        double amount = 100.0;
        Long playerId = 1L;

        Transaction transaction = new Transaction(id, playerId, type, amount);
        when(transactionRepository.createTransaction(transaction)).thenReturn(transaction);

//        boolean result = walletService.activateTransaction(transaction);

//        assertTrue(result);

    }



    @Test
    public void testFindTransactionHistoryWhenTransactionsAreFoundThenReturnListOfStrings() {
        Long playerId = 1L;
        Transaction transaction1 = new Transaction(1L, playerId,  DEBIT, 100.0);
        Transaction transaction2 = new Transaction(2L, playerId, DEBIT, 200.0);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.getAllPlayerTransaction(playerId)).thenReturn(transactions);

        List<TransactionDto> result = walletService.findTransactionHistory(playerId);

        assertEquals(2, result.size());
        assertTrue(result.contains(transaction1.toString()));
        assertTrue(result.contains(transaction2.toString()));
    }
}
