package ru.ylab.task1.service.impl;

import ru.ylab.task1.dto.HistoryItemDto;
import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.exception.NotFoundException;
import ru.ylab.task1.mapper.HistoryMapper;
import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.service.PlayerService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The type Player service.
 */
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final HistoryRepository historyRepository;
    private final Random random;

    private HistoryMapper historyMapper;

    /**
     * Instantiates a new Player service.
     *
     * @param playerRepository  the player repository
     * @param historyRepository the history repository
     */
    public PlayerServiceImpl(PlayerRepository playerRepository, HistoryRepository historyRepository) {
        this.playerRepository = playerRepository;
        this.historyRepository = historyRepository;
        random = new Random();
        historyMapper = HistoryMapper.INSTANCE;
    }

    public Long registerPlayer(String login, String password) throws LoginExistsException, DbException {
        if (playerRepository.loginExists(login)) {
            throw new LoginExistsException();
        }
        Player player = new Player(login, Arrays.hashCode(password.toCharArray()));
        Long id = playerRepository.registerPlayer(player);
        return id;
    }

    @Override
    public Player authorizationPlayer(String login, String password) throws LoginExistsException, DbException, NotFoundException {
        if (playerRepository.loginExists(login)) {
            return playerRepository.playerExist(login, Arrays.hashCode(password.toCharArray()));
        }
        throw new LoginExistsException();
    }

    /**
     * Метод возвращает лист всех действий пользователя
     *
     * @param playerId the player id
     * @return
     */
    public List<HistoryItemDto> getAllHistory(Long playerId) throws DbException {
        return historyRepository.getPlayerHistory(playerId).stream().map(x -> historyMapper.historyItemToDto(x)).toList();
    }

    /**
     * Добавление объекта "история" в репозиторий истории(в мапу) с рандомным id самого объекта HistoryItem,id игрока,действие игрока
     * и время операции
     *
     * @param playerId the player id
     * @param action   the action
     */
    public void addActionToHistory(Long playerId, String action) throws DbException {
        if (playerId != null) {
            historyRepository.addHistoryItem(new HistoryItem(random.nextLong(), playerId, action, LocalDateTime.now()));
        }
    }


}
