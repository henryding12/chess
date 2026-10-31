package server;

import com.google.gson.Gson;
import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import io.javalin.*;
import io.javalin.http.Context;
import model.AuthData;
import model.UserData;
import service.AdminService;
import service.GameService;
import service.UserService;

import java.util.List;
import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final AdminService adminService;
    private final UserService userService;
    private final GameService gameService;

    public Server() {
        this(new MemoryUserDAO(), new MemoryGameDAO(), new MemoryAuthDAO());
    }

    public Server(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
        adminService = new AdminService(userDAO, gameDAO, authDAO);
        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(authDAO, gameDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        javalin.delete("/db", this::clear);
        javalin.post("/user", this::addUser);
        javalin.post("/session", this::login);
        javalin.delete("/session", this::logout);
        javalin.post("/game", this::createGame);
        javalin.get("/game", this::listGames);
        javalin.put("/game", this::joinGame);
        javalin.exception(BadRequestException.class, this::badRequestHandler);
        javalin.exception(AlreadyTakenException.class, this::alreadyTakenHandler);
        javalin.exception(UnauthorizedException.class, this::unauthorizedHandler);
        javalin.exception(DataAccessException.class, this::dataAccessHandler);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void clear(Context ctx) {
        adminService.clear();
        ctx.status(200);
        ctx.result("{}");
    }

    private void addUser(Context ctx) throws BadRequestException, AlreadyTakenException, DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData authData = userService.register(user);
        ctx.status(200);
        ctx.result(new Gson().toJson(authData));
    }

    private void login(Context ctx) throws BadRequestException, UnauthorizedException, DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData authData = userService.login(user);
        ctx.status(200);
        ctx.result(new Gson().toJson(authData));
    }

    private void logout(Context ctx) throws UnauthorizedException, DataAccessException {
        String token = ctx.header("authorization");
        userService.logout(token);
        ctx.status(200);
        ctx.result("{}");
    }

    private void createGame(Context ctx) throws BadRequestException, UnauthorizedException {
        String token = ctx.header("authorization");
        Map body = new Gson().fromJson(ctx.body(), Map.class);
        String gameName = (String) body.get("gameName");
        int createGameResult = gameService.createGame(token, gameName);
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of("gameID", createGameResult)));
    }

    private void listGames(Context ctx) throws UnauthorizedException {
        String token = ctx.header("authorization");
        List listGameResult = gameService.listGames(token);
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of("games", listGameResult)));
    }

    private void joinGame(Context ctx) throws UnauthorizedException, BadRequestException, AlreadyTakenException {
        String token = ctx.header("authorization");
        Map gameInfo = new Gson().fromJson(ctx.body(), Map.class);
        String playerColor = (String) gameInfo.get("playerColor");
        Double gameIDasDouble = (Double) gameInfo.get("gameID");
        if (gameIDasDouble != null) {
            int gameID = gameIDasDouble.intValue();
            gameService.joinGame(token, playerColor, gameID);
        } else {
            gameService.joinGame(token, playerColor, 0);
        }
        ctx.status(200);
        ctx.result("{}");
    }

    private void badRequestHandler(BadRequestException ex, Context ctx) {
        ctx.status(400);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void alreadyTakenHandler(AlreadyTakenException ex, Context ctx) {
        ctx.status(403);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void unauthorizedHandler(UnauthorizedException ex, Context ctx) {
        ctx.status(401);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void dataAccessHandler(DataAccessException ex, Context ctx) {
        ctx.status(500);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }
}

