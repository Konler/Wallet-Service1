package ru.ylab.task1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.task1.dto.HistoryItemDto;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private PlayerService playerService;
    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        playerService = (PlayerService) getServletContext().getAttribute("playerService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("mapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonRequest.append(line);
        }
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute("id");
        if (id != null) {
            try {
                List<HistoryItemDto> historyItemDtoList = playerService.getAllHistory(id);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getWriter(), historyItemDtoList);
            } catch (DbException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            
        }

    }
}
