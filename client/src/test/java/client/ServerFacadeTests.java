package client;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        String serverUrl = "http://localhost:8080";
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);

        facade = new ServerFacade(serverUrl);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void register() throws Exception {
        var authData = facade.register(new UserData("player1", "password", "p1@email.com"));
        assertTrue(authData.authToken().length() > 10);
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
    public void sampleTest() {
        assertTrue(true);
    }

}
