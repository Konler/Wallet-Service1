package ru.ylab.task1.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type History item.
 */
public class HistoryItem {

    private Long id;
    private Long playerId;

    private String action;

    private LocalDateTime time;

    /**
     * Instantiates a new History item.
     *
     * @param id       the id
     * @param playerId the player id
     * @param action   the action
     * @param time     the time
     */
    public HistoryItem(Long id, Long playerId, String action, LocalDateTime time) {
        this.id = id;
        this.playerId = playerId;
        this.action = action;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Action = '" + action + '\'' +
                ", time = " + time.format(DateTimeFormatter.ofPattern("d MMM uuuu hh:mm"));
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public Long getPlayerId() {
        return playerId;
    }

    /**
     * Sets player id.
     *
     * @param playerId the player id
     */
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
