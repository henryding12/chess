package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

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

    @Override
    public GameData getGame(int gameID) {
        return gameMap.get(gameID);
    }

    @Override
    public void updateGame(int gameID, String username, String color) {
        GameData gameData = gameMap.get(gameID);

        if (Objects.equals(color, "WHITE")) {
            GameData newGameInfo = new GameData(
                    gameData.gameID(),
                    username,
                    gameData.blackUsername(),
                    gameData.gameName(),
                    gameData.game()
            );
            gameMap.put(gameID, newGameInfo);
        } else {
            GameData newGameInfo = new GameData(
                    gameData.gameID(),
                    gameData.whiteUsername(),
                    username,
                    gameData.gameName(),
                    gameData.game()
            );
            gameMap.put(gameID, newGameInfo);
        }

        ;

    }
}
