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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdminServiceTest {

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
    void clearAll() throws BadRequestException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        assertEquals(user, memoryUserDAO.getUser(user.username()));
        adminService.clear();
        assertNull(memoryUserDAO.getUser(user.username()));
        assertNull(memoryAuthDAO.getAuth(authData.authToken()));
    }
}
