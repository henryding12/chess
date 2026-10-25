package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    private final AdminService adminService = new AdminService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
    private final UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);

    @BeforeEach
    void clear() {
        adminService.clear();
    }

    @Test
    void registerPositive() throws BadRequestException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        assertEquals(user, memoryUserDAO.getUser(user.username())); // user stored properly
        assertEquals(user.username(), authData.username()); // right username returned
        assertNotNull(authData.authToken()); // token generated
    }

    @Test
    void registerNegative() throws BadRequestException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        userService.register(user);
        var dupUser = new UserData("henryding", "1233", "hello@email.com");
        assertThrows(AlreadyTakenException.class, () -> userService.register(dupUser));
    }

    @Test
    void loginPositive() throws BadRequestException, UnauthorizedException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        userService.register(user);
        AuthData authData = userService.login(user);
        assertNotNull(authData);
    }

    @Test
    void loginNegative() throws BadRequestException, UnauthorizedException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        userService.register(user);
        UserData loginUser = new UserData("henryding", "badpassword", "henryding@email.com");
        assertThrows(UnauthorizedException.class, () -> userService.login(loginUser));
    }

    @Test
    void logoutPositive() throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        String token = authData.authToken();
        userService.logout(token);
        assertNull(memoryAuthDAO.getAuth(token));
    }

    @Test
    void logoutNegative() throws BadRequestException, UnauthorizedException, AlreadyTakenException {
        var user = new UserData("henryding", "1234", "henryding@email.com");
        userService.register(user);
        String badToken = "badtoken";
        assertThrows(UnauthorizedException.class, () -> userService.logout(badToken));
    }
}
