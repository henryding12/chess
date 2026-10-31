package dataaccess.sql;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.GameData;

import java.util.List;

public class MySqlGameDAO implements GameDAO {
    @Override
    public void clear() {

    }

    @Override
    public int addGame(String gameName, ChessGame game) {
        return 0;
    }

    @Override
    public List listGames() {
        return List.of();
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public void updateGame(int gameID, String username, String color) {

    }
}
