package ru.ylab.task1.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.impl.HistoryRepositoryImpl;
import ru.ylab.task1.repository.impl.PlayerRepositoryImpl;
import ru.ylab.task1.service.DatabaseConnectionService;
import ru.ylab.task1.service.PlayerService;
import ru.ylab.task1.service.impl.PlayerServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class HistoryAspect {
    private final PlayerService playerService;

    {
        Connection connection = DatabaseConnectionService.getExistingConnection();
        PlayerRepository playerRepository = new PlayerRepositoryImpl(connection);
        HistoryRepository historyRepository = new HistoryRepositoryImpl(connection);
        playerService = new PlayerServiceImpl(playerRepository, historyRepository);
    }

    @Pointcut("execution(* ru.ylab.task1.servlets..*.*(..))")
    public void servletMethods() {}

    @After("servletMethods()")
    public void logServletMethodExecution(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        if (methodName != "init") {
            HttpSession session = ((HttpServletRequest) joinPoint.getArgs()[0]).getSession(true);
            Long id = (Long) session.getAttribute("id");
            if (id != null) {
                try {
                    playerService.addActionToHistory(id, methodName);
                } catch (DbException e) {
                }
            }
        }
    }

}
