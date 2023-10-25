package ru.ylab.task1.repository.impl;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.exception.NotFoundException;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.repository.PlayerRepository;

import java.sql.*;
import java.util.Map;

/**
 * The type Player repository.
 */
public class PlayerRepositoryImpl implements PlayerRepository {

    private Connection connection;

    public PlayerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Player findPlayerById(Long id) throws NotFoundException, DbException {
        String sql = "SELECT id, login, balance, password FROM my_schema.players WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                double balance = resultSet.getDouble("balance");
                int password = resultSet.getInt("password");
                return new Player(id, login, balance, password);
            } else {
                throw new NotFoundException();
            }
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    @Override
    public Long registerPlayer(Player player) throws DbException {
        String sql = "INSERT INTO my_schema.players (login, balance, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, player.getLogin());
            statement.setDouble(2, player.getBalance());
            statement.setInt(3, player.getPassword());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                player.setId(generatedKeys.getLong(1));
            }
            return player.getId();
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    @Override
    public Player playerExist(String login, Integer password) throws NotFoundException, DbException {
        String sql = "SELECT id, balance FROM my_schema.players WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setInt(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                double balance = resultSet.getDouble("balance");
                return new Player(id, login, balance, password);
            }
        } catch (SQLException e) {
            throw new DbException();
        }
        throw new NotFoundException();
    }

    @Override
    public boolean loginExists(String login) throws DbException {
        String sql = "SELECT id FROM my_schema.players WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    @Override
    public boolean passwordExists(Integer password) throws DbException {
        String sql = "SELECT id FROM my_schema.players WHERE password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    @Override
    public void changePlayerBalanceById(Long id, double amount) throws ImpossibleTransactionException, DbException {
        if (isNegativeBalance(id, amount)) {
            throw new ImpossibleTransactionException("Transaction is impossible due to negative balance");
        }

        String sql = "UPDATE my_schema.players SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setLong(2, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) {
                throw new ImpossibleTransactionException("Transaction is impossible");
            }
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    private boolean isNegativeBalance(Long id, double amount) throws DbException {
        String query = "SELECT balance FROM my_schema.players WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double balance = resultSet.getDouble("balance");
                    return (balance + amount) < 0;
                }
            }
        } catch (SQLException e) {
            throw new DbException();
        }
        return false;
    }

}
