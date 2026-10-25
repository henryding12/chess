package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    
    static final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    static final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    static final AdminService adminService = new AdminService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
    static final UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);

    @BeforeEach
    void clear() {
        adminService.clear();
    }

    @Test
    void positive() throws BadRequestException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        assertEquals(user, memoryUserDAO.getUser(user.username())); // user stored properly
        assertEquals(user.username(), authData.username()); // right username returned
        assertNotNull(authData.authToken()); // token generated
    }

    @Test
    void negative() throws BadRequestException, AlreadyTakenException{
        var user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        var dupUser = new UserData("henryding", "1233", "hello@email.com");
        assertThrows(AlreadyTakenException.class, () -> userService.register(dupUser));
    }
}
