package ru.ylab.task1.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.task1.DatabasePreparing;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.repository.HistoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class HistoryRepositoryImplTest {

    private static HistoryRepository historyRepository;

    @Container
    private static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:12.9-alpine");


    @BeforeAll
    public static void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword());
        historyRepository = new HistoryRepositoryImpl(connection);
        DatabasePreparing.createTables(connection);
    }

    @Test
    public void testGetPlayerHistoryWhenPlayerIdExistsThenReturnHistoryItems() throws DbException {
        Long playerId = 1L;
        HistoryItem historyItem1 = new HistoryItem(1L, playerId, "action1", LocalDateTime.now());
        HistoryItem historyItem2 = new HistoryItem(2L, playerId, "action2", LocalDateTime.now());
        historyRepository.addHistoryItem(historyItem1);
        historyRepository.addHistoryItem(historyItem2);
        List<HistoryItem> result = historyRepository.getPlayerHistory(playerId);
        assertEquals(2, result.size());
    }

    @Test
    public void testAddHistoryItemWhenCalledThenAddToHistoryMap() throws DbException {
        HistoryItem historyItem = new HistoryItem(1L, 1L, "action", LocalDateTime.now());
        historyRepository.addHistoryItem(historyItem);
    }
}
