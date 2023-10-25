package ru.ylab.task1.repository.impl;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.transaction.State;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Transaction repository.
 */
public class TransactionRepositoryImpl implements TransactionRepository {

    private Connection connection;

    public TransactionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public Transaction createTransaction(TransactionType type, double amount, Long playerId) throws DbException {
        String sql = "INSERT INTO my_schema.transactions (type, amount, state, player_id) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, type.name());
            statement.setDouble(2, amount);
            statement.setString(3, State.SUCCESS.toString());
            statement.setLong(4, playerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                return new Transaction(id, type, amount, playerId);
            } else {
                throw new DbException();
            }
        } catch (SQLException e) {
            throw new DbException();
        }
    }

    public List<Transaction> getAllPlayerTransaction(Long playerId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT id, type, amount, state FROM my_schema.transactions WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
                double amount = resultSet.getDouble("amount");
                State state = State.valueOf(resultSet.getString("state"));
                transactions.add(new Transaction(id, type, amount, playerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


}
