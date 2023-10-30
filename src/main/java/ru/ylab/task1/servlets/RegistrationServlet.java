package ru.ylab.task1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ylab.task1.dto.RegistrationAndAuthorizationDto;
import ru.ylab.task1.dto.RegistrationAndAuthorizationResponse;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private PlayerService playerService;
    private ObjectMapper objectMapper;


    @Override
    public void init() throws ServletException {
        playerService = (PlayerService) getServletContext().getAttribute("playerService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("mapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonRequest.append(line);
        }

        RegistrationAndAuthorizationDto registrationRequest = objectMapper.readValue(jsonRequest.toString(), RegistrationAndAuthorizationDto.class);

        if (registrationRequest != null) {
            boolean registrationSuccessful = false;
            Long id = -1l;
            try {
                id = playerService.registerPlayer(registrationRequest.getUsername(), registrationRequest.getPassword());
                registrationSuccessful = true;
                HttpSession session = req.getSession(true);
                session.setAttribute("id", id);
            } catch (LoginExistsException e) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            } catch (DbException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            RegistrationAndAuthorizationResponse registrationAndAuthorizationResponse = new RegistrationAndAuthorizationResponse(registrationSuccessful, id);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), registrationAndAuthorizationResponse);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
