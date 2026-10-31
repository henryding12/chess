package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlAuthDAO implements AuthDAO {

    public MySqlAuthDAO() throws DataAccessException {
        SqlUtils.configureDatabase(createStatements);
    }

    @Override
    public void clear() throws DataAccessException{
        var statement = "TRUNCATE authdata";
        SqlUtils.executeUpdate(statement);
    }

    @Override
    public void createAuth(AuthData authData) throws DataAccessException{
        var statement = "INSERT INTO authdata(authToken, username) VALUES (?, ?)";
        SqlUtils.executeUpdate(statement, authData.authToken(), authData.username());
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM authdata where authToken = ?";
            try(PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, token);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error: unable to read auth", e);
        }
        return null;
    }


    @Override
    public void deleteAuth(String token) throws DataAccessException{
        var statement = "DELETE FROM authdata WHERE authToken = ?";
        SqlUtils.executeUpdate(statement, token);
    }

    private String[] createStatements = {
        """
        CREATE TABLE IF NOT EXISTS authdata(
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`authToken`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
    };

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        return new AuthData(authToken, username);
    }
}
