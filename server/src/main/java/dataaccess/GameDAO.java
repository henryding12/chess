package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public interface GameDAO {
    void clear() throws DataAccessException;
    int addGame(String gameName, ChessGame game) throws DataAccessException;
    List listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(int gameID, String username, String color) throws DataAccessException;
}
