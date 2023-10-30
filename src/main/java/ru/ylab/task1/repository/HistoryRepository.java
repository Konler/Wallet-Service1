package ru.ylab.task1.repository;


import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.model.HistoryItem;

import java.util.List;

/**
 * The interface History repository.
 */
public interface HistoryRepository {
    /**
     * Gets player history.
     *
     * @param playerId the player id
     * @return the player history
     */
    List<HistoryItem> getPlayerHistory(Long playerId) throws DbException;

    /**
     * Add history item.
     *
     * @param historyItem the history item
     */
    void addHistoryItem(HistoryItem historyItem) throws DbException;
}
