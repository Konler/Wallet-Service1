package ru.ylab.task1.repository;

import ru.ylab.task1.exception.DbException;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.exception.NotFoundException;
import ru.ylab.task1.model.Player;

/**
 * The interface Player repository.
 */
public interface PlayerRepository {

    /**
     * Register player.
     *
     * @param player the player
     */
    Long registerPlayer(Player player) throws DbException;

    /**
     * Change player balance by id.
     *
     * @param id     the id
     * @param amount the amount
     * @throws ImpossibleTransactionException the impossible transaction exception
     */
    void changePlayerBalanceById(Long id, double amount) throws ImpossibleTransactionException, DbException;

    /**
     * Login exists boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean loginExists(String login) throws DbException;

    /**
     * Password exists boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean passwordExists(Integer password) throws DbException;

    /**
     * Find player by id player.
     *
     * @param id the id
     * @return the player
     * @throws LoginExistsException the login exists exception
     */
    Player findPlayerById(Long id) throws LoginExistsException, NotFoundException, DbException;

    Player playerExist(String login, Integer password) throws NotFoundException, DbException;
}
