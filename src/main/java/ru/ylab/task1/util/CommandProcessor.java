package ru.ylab.task1.util;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.in.InputHandler;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.out.OutputHandler;
import ru.ylab.task1.service.PlayerService;
import ru.ylab.task1.service.impl.WalletServiceImpl;

import java.util.List;

/**
 * The type CommandProcessor.
 */
public class CommandProcessor {

    private final InputHandler inputHandler;

    private final OutputHandler outputHandler;

    private final WalletServiceImpl wallerService;

    private final PlayerService playerService;

    /**
     * Instantiates a new CommandProcessor.
     *
     * @param inputHandler  the input handler
     * @param outputHandler the output handler
     * @param wallerService the waller service
     * @param playerService the player service
     */
    public CommandProcessor(InputHandler inputHandler, OutputHandler outputHandler, WalletServiceImpl wallerService, PlayerService playerService) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.wallerService = wallerService;
        this.playerService = playerService;
    }

    /**
     * Process change balance command.
     *
     * @param transactionType the transaction type
     * @param playerId        the player id
     */
    public void processChangeBalanceCommand(TransactionType transactionType, Long playerId) {
        outputHandler.displayMessage("Введите сумму транзакции:");
        String amountCredit = inputHandler.getUserInput();
        boolean done = false;
        try {
            double amount = Double.parseDouble(amountCredit);
            done = wallerService.activateTransaction(transactionType, amount, playerId);
        } catch (NumberFormatException ex) {
            outputHandler.displayMessage("Ошибка ввода данных");
        } catch (DbException e) {
            outputHandler.displayMessage("Возникла проблема в БД");
        }
        if (!done) {
            outputHandler.displayMessage("Транзакция не выполнена");
        } else {
            outputHandler.displayMessage("Транзакция успешна");
        }
    }

    /**
     * Функция получения значения поля
     *
     * @return возвращает айди нового игрока
     */
    public Long registerNewPlayer() {
        outputHandler.displayMessage("Для выполнения транзакций необходима регистрация. Введите логин:");
        String name = inputHandler.getUserInput();
        outputHandler.displayMessage("Введите пароль");
        String password = inputHandler.getUserInput();
        try {
            return playerService.registerPlayer(name, password);
        } catch (LoginExistsException e) {
            outputHandler.displayMessage("Пользователь с таким логином существует");
            return -1L;
        } catch (DbException e) {
            outputHandler.displayMessage("Возникла проблема в БД");
            return -1L;
        }
    }

    public Player authorizationPlayer() {
        outputHandler.displayMessage("Введите логин для авторизации:");
        String name = inputHandler.getUserInput();
        outputHandler.displayMessage("Введите пароль");
        String password = inputHandler.getUserInput();
        try {
            return playerService.authorizationPlayer(name, password);
        } catch (Exception e) {
            outputHandler.displayMessage("Пользователь с таким логином не существует");
            return null;
        }
    }

    /**
     * Read command string.
     * Запрашивает у пользователя какую операцию он хочет совершить
     *
     * @return the string
     */
    public String readCommand() {
        outputHandler.displayMessage("""
                Выберите тип транзакции:\s
                1-кредит(пополнение)
                2-дебит(списание)
                3-просмотр истории транзакций
                4-просмотреть аудит
                0-для выхода из меню""");
        return inputHandler.getUserInput();
    }


    /**
     * Display all transactions.
     *
     * @param playerId the player id
     */
    public void displayAllTransactions(Long playerId) {
        List<String> history = wallerService.findTransactionHistory(playerId);
        StringBuilder sb = new StringBuilder();
        for (String transaction : history) {
            sb.append(transaction).append("\n");
        }
        outputHandler.displayMessage(sb.toString());
    }

    /**
     * Display all history.
     *
     * @param playerId the player id
     */
    public void displayAllHistory(Long playerId) {
        List<String> history = null;
        try {
            history = playerService.getAllHistory(playerId);
            StringBuilder sb = new StringBuilder();
            for (String transaction : history) {
                sb.append(transaction).append("\n");
            }
            outputHandler.displayMessage(sb.toString());
        } catch (DbException e) {
            outputHandler.displayMessage("Ошибка в БД");
        }

    }
}
