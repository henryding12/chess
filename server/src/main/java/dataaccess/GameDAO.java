package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {
    void clear();
    int addGame(String gameName, ChessGame game);
    List listGames();
    GameData getGame(int gameID);
    void updateGame(int gameID, String username, String color);

}
