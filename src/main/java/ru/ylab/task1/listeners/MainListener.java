package ru.ylab.task1.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.TransactionRepository;
import ru.ylab.task1.repository.impl.HistoryRepositoryImpl;
import ru.ylab.task1.repository.impl.PlayerRepositoryImpl;
import ru.ylab.task1.repository.impl.TransactionRepositoryImpl;
import ru.ylab.task1.service.DatabaseConnectionService;
import ru.ylab.task1.service.PlayerService;
import ru.ylab.task1.service.impl.PlayerServiceImpl;
import ru.ylab.task1.service.impl.WalletServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileNotFoundException;
import java.sql.Connection;

@WebListener
public class MainListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Connection connection = null;
        try {
            connection = DatabaseConnectionService.getInstance(context);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
        PlayerRepository playerRepository = new PlayerRepositoryImpl(connection);
        TransactionRepository transactionRepository = new TransactionRepositoryImpl(connection);
        HistoryRepository historyRepository = new HistoryRepositoryImpl(connection);
        WalletServiceImpl wallerService = new WalletServiceImpl(transactionRepository, playerRepository);
        PlayerService playerService = new PlayerServiceImpl(playerRepository, historyRepository);
        context.setAttribute("mapper", new ObjectMapper());
        context.setAttribute("walletService", wallerService);
        context.setAttribute("playerService", playerService);
    }
    
}
