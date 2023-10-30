package ru.ylab.task1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.task1.dto.RegistrationAndAuthorizationDto;
import ru.ylab.task1.dto.RegistrationAndAuthorizationResponse;
import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.transaction.Transaction;
import ru.ylab.task1.service.WalletService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    private WalletService walletService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        walletService = (WalletService) getServletContext().getAttribute("walletService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("mapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonRequest.append(line);
        }
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        if (id != null) {
            TransactionDto dto = objectMapper.readValue(jsonRequest.toString(), TransactionDto.class);
            if (dto != null) {
                dto.setPlayerId(id);
                boolean activated = false;
                try {
                    activated = walletService.activateTransaction(dto);
                } catch (DbException e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), activated);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
        Long id = (Long) httpSession.getAttribute("id");
        if (id != null) {
            List<TransactionDto> transactionDtoList = walletService.findTransactionHistory(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), transactionDtoList);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
