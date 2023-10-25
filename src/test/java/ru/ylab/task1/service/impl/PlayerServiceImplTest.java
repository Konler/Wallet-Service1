package ru.ylab.task1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayerServiceImplTest {

    private PlayerServiceImpl playerService;
    private PlayerRepository playerRepository;
    private HistoryRepository historyRepository;


    @BeforeEach
    public void setUp() {
        playerRepository = Mockito.mock(PlayerRepository.class);
        historyRepository = Mockito.mock(HistoryRepository.class);
        playerService = new PlayerServiceImpl(playerRepository, historyRepository);
    }

    @Test
    public void testRegisterPlayerWhenValidLoginAndPasswordThenPlayerRegistered() throws Exception {
        String login = "testLogin";
        String password = "testPassword";

        when(playerRepository.loginExists(login)).thenReturn(false);

        Long playerId = playerService.registerPlayer(login, password);

        assertNotNull(playerId);
        verify(playerRepository, times(1)).registerPlayer(any(Player.class));
    }

    @Test
    public void testRegisterPlayerWhenExistingLoginThenLoginExistsException() throws Exception {
        String login = "testLogin";
        String password = "testPassword";

        when(playerRepository.loginExists(login)).thenReturn(true);

        assertThrows(LoginExistsException.class, () -> playerService.registerPlayer(login, password));
    }


    @Test
    public void testAddActionToHistoryWhenValidPlayerIdAndActionThenActionAddedToHistory() throws Exception {
        Long playerId = 1L;
        String action = "testAction";

        playerService.addActionToHistory(playerId, action);

        verify(historyRepository, times(1)).addHistoryItem(any(HistoryItem.class));
    }
}
