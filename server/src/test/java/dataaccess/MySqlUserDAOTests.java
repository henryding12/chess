package dataaccess;

import dataaccess.sql.MySqlUserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MySqlUserDAOTests {

    private MySqlUserDAO mySqlUserDAO;

    @BeforeEach
    void setUp() throws DataAccessException{
        mySqlUserDAO = new MySqlUserDAO();
        mySqlUserDAO.clear();
    }

    @Test
    void positiveClear() throws DataAccessException {
        mySqlUserDAO.createUser(new UserData("henryding", "1234", "henry@email.com"));
        mySqlUserDAO.clear();
        assertNull(mySqlUserDAO.getUser("henryding"));
    }

    @Test
    void positiveCreateUser() throws DataAccessException{
        UserData userData = new UserData("henryding", "1234", "henry@email.com");
        mySqlUserDAO.createUser(userData);
        // get
        var user = mySqlUserDAO.getUser("henryding");
        assertEquals("henryding", user.username());
    }

    @Test
    void negativeCreateUser() throws DataAccessException{
        UserData userData = new UserData("henryding",null,  "henry@email.com");
        assertThrows(DataAccessException.class, () -> mySqlUserDAO.createUser(userData));
    }

    @Test
    void negativeGetUser() throws DataAccessException {
        assertNull(mySqlUserDAO.getUser("henryding"));
    }


}
