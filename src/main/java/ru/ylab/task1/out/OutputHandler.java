package ru.ylab.task1.out;

/**
 * The interface Output handler.
 */
public interface OutputHandler {
    /**
     * Display message.
     *
     * @param message the message
     */
    void displayMessage(String message);

    /**
     * Close.
     */
    void close();

}
