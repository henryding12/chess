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

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    private final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    private final AdminService adminService = new AdminService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
    private final UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);
    private final GameService gameService = new GameService(memoryAuthDAO, memoryGameDAO);

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
    void createGameNegative2() throws UnauthorizedException, BadRequestException {
        String badToken = "badtoken";
        assertThrows(UnauthorizedException.class, () -> gameService.createGame(badToken, "Greatness"));
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
        assertThrows(UnauthorizedException.class, () -> gameService.listGames(faultyToken));
    }

    @Test
    void joinGamePositive() throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        int gameID = gameService.createGame(token, "Game 1");
        gameService.joinGame(token, "BLACK", gameID);
        assertEquals(memoryGameDAO.getGame(gameID).blackUsername(), memoryAuthDAO.getAuth(token).username());
    }

    @Test
    void joinGameNegative() throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        int gameID = gameService.createGame(token, "Game 1");
        gameService.joinGame(token, "BLACK", gameID);
        assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(token, "BLACK", gameID));
    }

    @Test
    void joinGameNegative2() throws UnauthorizedException, BadRequestException {
        String badToken = "badtoken";
        assertThrows(UnauthorizedException.class, () -> gameService.joinGame(badToken, "WHITE", 0));
    }
}
