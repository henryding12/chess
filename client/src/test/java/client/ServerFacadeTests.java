package client;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static AuthData player1Auth;
    private static final AuthData faulty = new AuthData("player1", "faultyToken");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("started testing on http server " + port);
        facade = new ServerFacade(port);
    }

    @BeforeEach
    void setup() throws Exception {
        facade.clear();
        player1Auth = facade.register(new UserData("player1", "password", "p1@email.com"));
    }

    @AfterAll
    static void stopServer()  {
        server.stop();
    }

    @Test
    void register() throws Exception {
        assertTrue(player1Auth.authToken().length() > 10);
    }

    @Test
    void login() throws Exception {
        var authData = facade.login(new UserData("player1", "password", null));
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void logout() throws Exception {
        var authData = facade.login(new UserData("player1", "password", null));
        String message = facade.logout(authData);
        assertEquals("{}", message);
    }


    @Test
    void createGame() throws Exception {
        Map gameInfo = facade.createGame("game1", player1Auth);
        var expected = Map.of("gameID", 1.0);
        assertEquals(expected, gameInfo);
    }

    // fix listGames output potentially
    @Test
    void listGames() throws Exception {
        Map gameInfo = facade.createGame("game1", player1Auth);
        ListGamesResult listOfGames = facade.listGames(player1Auth);
        var expectedList = new ArrayList<GameData>();
        var game = new GameData(1,null, null, "game1", new ChessGame());
        expectedList.add(game);
        assertEquals(expectedList, listOfGames.games());
    }

    @Test
    void joinGame() throws Exception {
        Map gameInfo = facade.createGame("game1", player1Auth);
        int gameID = ((Double) gameInfo.get("gameID")).intValue();
        String result = facade.joinGame(gameID, "WHITE", player1Auth);
        assertEquals("{}", result);
    }

    @Test
    void registerAgain() {
        assertThrows(ResponseException.class,
                () -> facade.register(new UserData("player1", "password", "p1@email.com")));
    }

    @Test
    void wrongPassword() {
        assertThrows(ResponseException.class,
                () -> facade.login(new UserData("player1", "wrong", null)));
    }

    @Test
    void logoutBadAuth() {
        assertThrows(ResponseException.class, () -> facade.logout(faulty));
    }

    @Test
    void createGameFaultyAuth() {
        assertThrows(ResponseException.class, () -> facade.createGame("game1", faulty));
    }

    @Test
    void listGamesFaultyAuth() {
        assertThrows(ResponseException.class, () -> facade.listGames(faulty));
    }

    @Test
    void joinGameWrongColor() throws Exception {
        Map gameInfo = facade.createGame("game1", player1Auth);
        int gameID = ((Double) gameInfo.get("gameID")).intValue();
        facade.joinGame(gameID, "WHITE", player1Auth);
        assertThrows(ResponseException.class, () -> facade.joinGame(gameID, "WHITE", player1Auth));
    }

}
