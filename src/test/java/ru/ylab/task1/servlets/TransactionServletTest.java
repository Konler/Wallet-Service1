package ru.ylab.task1.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.task1.dto.TransactionDto;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.service.WalletService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransactionServletTest {

    private TransactionServlet transactionServlet;
    private WalletService walletService;
    private ObjectMapper objectMapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        walletService = mock(WalletService.class);
        objectMapper = new ObjectMapper();
        transactionServlet = new TransactionServlet();
        transactionServlet.setWalletService(walletService);
        transactionServlet.setObjectMapper(objectMapper);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPostWhenTransactionActivatedThenReturnTrue() throws Exception {
        TransactionDto dto = new TransactionDto();
        dto.setType("DEBIT");
        dto.setAmount(100.0);
        dto.setState("SUCCESS");
        dto.setPlayerId(1L);

        when(session.getAttribute("id")).thenReturn(1L);
        when(walletService.activateTransaction(dto)).thenReturn(true);

        BufferedReader reader = new BufferedReader(new StringReader(objectMapper.writeValueAsString(dto)));
        when(request.getReader()).thenReturn(reader);

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        transactionServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        assertEquals("true", writer.toString());
    }

    @Test
    public void testDoPostWhenDbExceptionThenReturnInternalServerError() throws Exception {
        TransactionDto dto = new TransactionDto();
        dto.setType("DEBIT");
        dto.setAmount(100.0);
        dto.setState("SUCCESS");
        dto.setPlayerId(1L);

        when(session.getAttribute("id")).thenReturn(1L);
        when(walletService.activateTransaction(dto)).thenThrow(DbException.class);

        BufferedReader reader = new BufferedReader(new StringReader(objectMapper.writeValueAsString(dto)));
        when(request.getReader()).thenReturn(reader);

        transactionServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testDoPostWhenInvalidJsonThenReturnBadRequest() throws Exception {
        String invalidJson = "{invalid json}";

        BufferedReader reader = new BufferedReader(new StringReader(invalidJson));
        when(request.getReader()).thenReturn(reader);

        transactionServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testDoGetWhenTransactionHistoryExistsThenReturnTransactionList() throws Exception {
        TransactionDto dto = new TransactionDto();
        dto.setType("DEBIT");
        dto.setAmount(100.0);
        dto.setState("SUCCESS");
        dto.setPlayerId(1L);
        List<TransactionDto> transactionDtoList = Arrays.asList(dto);

        when(session.getAttribute("id")).thenReturn(1L);
        when(walletService.findTransactionHistory(1L)).thenReturn(transactionDtoList);

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        transactionServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(objectMapper.writeValueAsString(transactionDtoList), writer.toString());
    }
}