package ru.ylab.task1.model.transaction;


import java.util.Objects;

/**
 * The type Transaction.
 */
public class Transaction {
    private final Long id;
    private final TransactionType type;
    private double amount;
    private State state;
    private final Long playerId;

    /**
     * Instantiates a new Transaction.
     *
     * @param id       the id
     * @param type     the type
     * @param amount   the amount
     * @param playerId the player id
     */
    public Transaction(Long id, TransactionType type, double amount, Long playerId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.playerId = playerId;
        this.state = State.UNACTIVATED;
    }


    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public Long getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(id, that.id) && type == that.type && state == that.state && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, amount, state, playerId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", state=" + state +
                ", playerId=" + playerId +
                '}';
    }
}
