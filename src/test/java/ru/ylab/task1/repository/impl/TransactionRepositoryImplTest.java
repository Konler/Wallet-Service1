package ru.ylab.task1.repository.impl;

import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.task1.DatabasePreparing;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class TransactionRepositoryImplTest {

    private static TransactionRepositoryImpl transactionRepository;
    private static Connection connection;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12.9-alpine");

    @BeforeAll
    public static void setConnection() throws SQLException, LiquibaseException {
        connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        transactionRepository = new TransactionRepositoryImpl(connection);
        DatabasePreparing.createTables(connection);
    }

    @Test
    public void testCreateTransactionWhenNewTransactionThenAddedToMap() throws DbException {
        TransactionType type = TransactionType.CREDIT;
        double amount = 100.0;
        Long playerId = 1L;
        Transaction newTransaction1 = new Transaction(null, playerId, type, amount);
        Transaction transaction = transactionRepository.createTransaction(newTransaction1);
        assertEquals(transaction.getAmount(), amount);
    }

    @Test
    public void testGetAllPlayerTransactionWhenTransactionsExistThenReturnCorrectTransactions() throws DbException {
        Long playerId = 2L;
        Transaction newTransaction1 = new Transaction(null, playerId, TransactionType.CREDIT, 100.0);
        Transaction newTransaction2 = new Transaction(null, playerId, TransactionType.DEBIT, 50.0);
        Transaction transaction1 = transactionRepository.createTransaction(newTransaction1);
        Transaction transaction2 = transactionRepository.createTransaction(newTransaction2);
        List<Transaction> transactions = transactionRepository.getAllPlayerTransaction(playerId);
        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(transaction1));
        assertTrue(transactions.contains(transaction2));
    }

    @Test
    public void testGetAllPlayerTransactionWhenNoTransactionsThenReturnEmptyList() {
        Long playerId = 1L;
        List<Transaction> transactions = transactionRepository.getAllPlayerTransaction(playerId);
        assertTrue(transactions.isEmpty());
    }
}
