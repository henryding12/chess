package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.basic.BasicDesktopIconUI;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    static final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    static final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    static final AdminService adminService = new AdminService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
    static final UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
    static final GameService gameService = new GameService(memoryAuthDAO, memoryGameDAO);

    private String token;
    @BeforeEach
    void clear() throws BadRequestException, AlreadyTakenException {
        adminService.clear();
        UserData user = new UserData("henryding", "1234", "henryding@email.com");
        AuthData authData = userService.register(user);
        token = authData.authToken();

    }

    @Test
    void createGamePositive() throws UnauthorizedException, BadRequestException {
        int gameID = gameService.createGame(token, "testGame");
        GameData game = memoryGameDAO.getGame(gameID);
        assertEquals("testGame", game.gameName());
    }

    @Test
    void createGameNegative() throws UnauthorizedException, BadRequestException {
        // no name filled
        assertThrows(BadRequestException.class, () -> gameService.createGame(token, null));
    }

    @Test
    void listGamesPositive() throws UnauthorizedException, BadRequestException {
        gameService.createGame(token, "Game 1");
        gameService.createGame(token, "Game 2");
        var games = gameService.listGames(token);
        assertEquals(2, games.size());
    }

    @Test
    void listGamesNegative() throws UnauthorizedException, BadRequestException {
        gameService.createGame(token, "Game 1");
        gameService.createGame(token, "Game 2");
        String faultyToken = "badToken";
        // bad token
        assertThrows(UnauthorizedException.class, () -> gameService.listGames(faultyToken));
    }

    @Test
    void joinGamePositive() throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        gameService.createGame(token, "Game 1");
        gameService.joinGame(token, "BLACK", 1);
        assertEquals(memoryGameDAO.getGame(1).blackUsername(), memoryAuthDAO.getAuth(token).username());
    }

    @Test
    void joinGameNegative() throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        gameService.createGame(token, "Game 1");
        gameService.joinGame(token, "BLACK", 1);
        assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(token, "BLACK", 1));
    }


}
