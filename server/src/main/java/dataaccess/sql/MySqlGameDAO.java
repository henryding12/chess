package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySqlGameDAO implements GameDAO {
    public MySqlGameDAO () throws DataAccessException {
        SqlUtils.configureDatabase(createStatements);
    }

    @Override
    public void clear() throws DataAccessException{
        var statement = "TRUNCATE gamedata";
        SqlUtils.executeUpdate(statement);
    }

    @Override
    public int addGame(String gameName, ChessGame game) throws DataAccessException{
        var statement = "INSERT INTO gamedata(gameName, game) VALUES (?, ?)";
        return SqlUtils.executeUpdate(statement, gameName, new Gson().toJson(game));
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        var games = new ArrayList<GameData>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM gamedata";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        games.add(readGame(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: unable to list games", e);
        }
        return games;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM gamedata where gameID = ?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: unable to read auth", e);
        }
        return null;
    }

    @Override
    public void updateGame(int gameID, String username, String color) throws DataAccessException{
        String statement;
        if (Objects.equals(color, "WHITE")) {
            statement = "UPDATE gamedata SET whiteUsername = ? WHERE gameID = ?";
        } else {
            statement = "UPDATE gamedata SET blackUsername = ? WHERE gameID = ?";
        }
        SqlUtils.executeUpdate(statement, username, gameID);
    }

    private String[] createStatements = {
        """
        CREATE TABLE IF NOT EXISTS gamedata(
            `gameID` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` varchar(256),
            `blackUsername` varchar(256),
            `gameName` varchar(256) NOT NULL,
            `game` text NOT NULL,
            PRIMARY KEY (`gameID`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
    };



    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }
}
