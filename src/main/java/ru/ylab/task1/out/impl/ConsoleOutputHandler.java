package ru.ylab.task1.out.impl;

import ru.ylab.task1.out.OutputHandler;

/**
 * The type Console output handler.
 */
public class ConsoleOutputHandler implements OutputHandler {
    private static final String FINISH_MESSAGE = "Программа завершена";


    public void displayMessage(String message) {

        System.out.println(message);
    }

    public void close() {

        displayMessage(FINISH_MESSAGE);
    }

}
