package ru.ylab.task1.dto;

public class RegistrationAndAuthorizationResponse {

    private boolean isSuccessful;
    private Long playerId;

    public RegistrationAndAuthorizationResponse(boolean isSuccessful, Long playerId) {
        this.isSuccessful = isSuccessful;
        this.playerId = playerId;
    }



}
