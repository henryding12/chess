package dataaccess;

import dataaccess.sql.MySqlAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MySqlAuthDAOTests {
    private MySqlAuthDAO mySqlAuthDAO;

    @BeforeEach
    void setUp() throws DataAccessException{
        mySqlAuthDAO = new MySqlAuthDAO();
        mySqlAuthDAO.clear();
    }

    @Test
    void positiveCreateAuth() throws DataAccessException {
        var auth = new AuthData("henryding", "1b23c23433f3eff1");
        mySqlAuthDAO.createAuth(auth);
        assertEquals("1b23c23433f3eff1", mySqlAuthDAO.getAuth("1b23c23433f3eff1").authToken());
    }

    @Test
    void negativeCreateAuth() throws DataAccessException {
        var auth = new AuthData(null, "1b23c23433f3eff1");
        assertThrows(DataAccessException.class, () -> mySqlAuthDAO.createAuth(auth));
    }

    @Test
    void negativeGetAuth() throws DataAccessException {
        assertNull(mySqlAuthDAO.getAuth("1111"));
    }


}
