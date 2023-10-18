package ru.ylab.task1.repository.impl;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.repository.HistoryRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type History repository.
 */
public class HistoryRepositoryImpl implements HistoryRepository {

    private final Connection connection;

    public HistoryRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<HistoryItem> getPlayerHistory(Long playerId) throws DbException {
        List<HistoryItem> historyItems = new ArrayList<>();
        String sql = "SELECT id, player_id, action, time FROM my_schema.history WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String action = resultSet.getString("action");
                LocalDateTime time = resultSet.getObject("time", LocalDateTime.class);
                historyItems.add(new HistoryItem(id, playerId, action, time));
            }
        } catch (SQLException e) {
            throw new DbException();
        }
        return historyItems;
    }

    @Override
    public void addHistoryItem(HistoryItem historyItem) throws DbException {
        String sql = "INSERT INTO my_schema.history (player_id, action, time) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, historyItem.getPlayerId());
            statement.setString(2, historyItem.getAction());
            statement.setObject(3, historyItem.getTime());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                historyItem.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DbException();
        }
    }


}
