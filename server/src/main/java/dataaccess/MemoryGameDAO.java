package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> game = new HashMap<>();

    @Override
    public void clear() {
        game.clear();
    }
}
