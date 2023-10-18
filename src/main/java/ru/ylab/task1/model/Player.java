package ru.ylab.task1.model;

public class Player {
    private Long id;
    private final String login;
    private double balance;
    int password;

    public Player(Long id, String login, double balance, int password) {
        this.id = id;
        this.login = login;
        this.balance = balance;
        this.password = password;
    }

    public Player(String login, int password) {
        this.login = login;
        this.password = password;
        this.balance = 0;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
