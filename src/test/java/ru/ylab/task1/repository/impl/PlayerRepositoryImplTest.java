package ru.ylab.task1.repository.impl;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.ylab.task1.DatabasePreparing;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.exception.NotFoundException;
import ru.ylab.task1.model.Player;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class PlayerRepositoryImplTest {

    private static PlayerRepositoryImpl playerRepository;
    private static Connection connection;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12.9-alpine");

    @BeforeAll
    public static void setConnection() throws SQLException, LiquibaseException {
        connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        playerRepository = new PlayerRepositoryImpl(connection);
        DatabasePreparing.createTables(connection);
    }

    @Test
    public void testFindPlayerByIdWhenPlayerExistsThenReturnPlayer() throws Exception {
        Player player = new Player("test2", 1234);
        Long playerId = playerRepository.registerPlayer(player);
        Player result = playerRepository.findPlayerById(playerId);
        assertEquals(player.getLogin(), result.getLogin(), "The returned player should be the same as the one in the player map");
    }

    @Test
    public void testFindPlayerByIdWhenPlayerDoesNotExistThenThrowException() {
        Long playerId = 200L;
        assertThrows(NotFoundException.class, () -> playerRepository.findPlayerById(playerId));
    }

    @Test
    public void testRegisterPlayer() throws DbException {
        Player player = new Player("test66", 1234);
        insertPlayer(player);
        assertTrue(playerRepository.loginExists("test66"), "The method should return true when the login exists in the player map");
    }

    @Test
    public void testLoginExistsWhenLoginExistsThenReturnTrue() throws DbException {
        String login = "test34";
        Player player = new Player(login, 1234);
        insertPlayer(player);
        boolean result = playerRepository.loginExists(login);
        assertTrue(result, "The method should return true when the login exists in the player map");
    }

    @Test
    public void testLoginExistsWhenLoginDoesNotExistThenReturnFalse() throws DbException {
        String login = "test101";
        boolean result = playerRepository.loginExists(login);
        assertFalse(result, "The method should return false when the login does not exist in the player map");
    }

    @Test
    public void testChangePlayerBalanceByIdWhenTransactionIsPossibleThenChangeBalance() throws ImpossibleTransactionException, LoginExistsException, NotFoundException, DbException {
        Long playerId = 1L;
        double initialBalance = 100;
        double amount = 50;
        Player player = new Player( "test1", 1234);
        player.setBalance(initialBalance);
        insertPlayer(player);
        playerRepository.changePlayerBalanceById(playerId, amount);
        Player updatedPlayer = playerRepository.findPlayerById(playerId);
        assertEquals(initialBalance + amount, updatedPlayer.getBalance(), "The player's balance should be changed by the given amount");
    }

    @Test
    public void testChangePlayerBalanceByIdWhenTransactionIsImpossibleThenThrowException() {
        Long playerId = 1L;
        double initialBalance = 100;
        double amount = -200;
        Player player = new Player("test", 1234);
        player.setBalance(initialBalance);
        insertPlayer(player);
        assertThrows(ImpossibleTransactionException.class, () -> playerRepository.changePlayerBalanceById(playerId, amount));
    }

    private void insertPlayer(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO my_schema.players (login, password, balance) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, player.getLogin());
            preparedStatement.setInt(2, player.getPassword());
            preparedStatement.setDouble(3, player.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}