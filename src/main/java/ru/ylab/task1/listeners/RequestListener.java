package ru.ylab.task1.listeners;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.impl.HistoryRepositoryImpl;
import ru.ylab.task1.repository.impl.PlayerRepositoryImpl;
import ru.ylab.task1.service.DatabaseConnectionService;
import ru.ylab.task1.service.PlayerService;
import ru.ylab.task1.service.impl.PlayerServiceImpl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebListener
public class RequestListener implements ServletRequestListener {

//    private PlayerService playerService;
//
//
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        Object service = sre.getServletContext().getAttribute("playerService");
//        if (service != null) {
//            playerService = (PlayerService) service;
//        }
//        if (playerService != null) {
//            ServletRequest request = sre.getServletRequest();
//            if (request instanceof HttpServletRequest) {
//                HttpServletRequest httpRequest = (HttpServletRequest) request;
//                HttpSession session = httpRequest.getSession();
//                Long id = (Long) session.getAttribute("id");
//                try {
//                    String requestURI = httpRequest.getRequestURI();
//                    playerService.addActionToHistory(id, requestURI);
//                } catch (DbException e) {
//                }
//            }
//        }
//    }

}
