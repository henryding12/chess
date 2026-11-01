package dataaccess;

import chess.ChessGame;
import dataaccess.sql.MySqlGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MySqlGameDAOTests {
    private MySqlGameDAO mySqlGameDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        mySqlGameDAO = new MySqlGameDAO();
        mySqlGameDAO.clear();
    }

    @Test
    void positiveAddGame() throws DataAccessException {
        int gameId = mySqlGameDAO.addGame("112", new ChessGame());
        assertEquals("112", mySqlGameDAO.getGame(gameId).gameName());
    }

    @Test
    void negativeAddGame() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> mySqlGameDAO.addGame(null, new ChessGame()));
    }

    @Test
    void positiveUpdateGame() throws DataAccessException {
        int gameId = mySqlGameDAO.addGame("112", new ChessGame());
        mySqlGameDAO.updateGame(gameId, "henryding", "WHITE");
        assertEquals("henryding", mySqlGameDAO.getGame(gameId).whiteUsername());
    }

    @Test
    void negativeUpdateGame() throws DataAccessException {
        int gameId = mySqlGameDAO.addGame("112", new ChessGame());
        mySqlGameDAO.updateGame(1234, "henryding", "WHITE");
        assertNull(mySqlGameDAO.getGame(gameId).whiteUsername());
    }
}