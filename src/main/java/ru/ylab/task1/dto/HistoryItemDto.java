package ru.ylab.task1.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryItemDto {

    private Long id;
    private Long playerId;

    private String action;

    private String time;

    /**
     * Instantiates a new History item.
     *
     * @param id       the id
     * @param playerId the player id
     * @param action   the action
     * @param time     the time
     */
    public HistoryItemDto(Long id, Long playerId, String action, String time) {
        this.id = id;
        this.playerId = playerId;
        this.action = action;
        this.time = time;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

}
