package ru.ylab.task1.in.impl;


import ru.ylab.task1.in.InputHandler;

import java.util.Scanner;

/**
 * The type Scanner input handler.
 */
public class ScannerInputHandler implements InputHandler {

    private final Scanner scanner;

    /**
     * Instantiates a new Scanner input handler.
     */
    public ScannerInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getUserInput() {

        return scanner.nextLine();
    }

    public void close() {//зачем?

        scanner.close();
    }

}
