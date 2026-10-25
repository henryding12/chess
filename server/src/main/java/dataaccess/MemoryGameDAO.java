package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> gameMap = new HashMap<>();
    private int id = 1;

    @Override
    public void clear() {
        gameMap.clear();
    }

    @Override
    public int addGame(String gameName, ChessGame game) {
        GameData gameData = new GameData(id, null, null, gameName, game);
        gameMap.put(id, gameData);
        return id++;
    }

    @Override
    public List<GameData> listGames() {
        return new ArrayList<>(gameMap.values());
    }
}
