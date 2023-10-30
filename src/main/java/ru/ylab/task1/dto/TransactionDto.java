package ru.ylab.task1.dto;


public class TransactionDto {

    private String type;
    private double amount;
    private String state;
    private Long playerId;

    public TransactionDto() {
    }


    // Геттеры и сеттеры для полей


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
