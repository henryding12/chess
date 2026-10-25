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
import org.eclipse.jetty.server.Authentication;
import service.AdminService;
import service.UserService;

import java.util.Map;

public class Server {

    private final Javalin javalin;
    private final AdminService adminService;
    private final UserService userService;

    public Server() {
        this(new MemoryUserDAO(), new MemoryGameDAO(), new MemoryAuthDAO());
    }

    public Server(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {
        adminService = new AdminService(userDAO, gameDAO, authDAO);
        userService = new UserService(userDAO, authDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", this::clear);
        javalin.post("/user", this::addUser);
        javalin.post("/session", this::login);
        javalin.delete("/session", this::logout);
        javalin.exception(BadRequestException.class, this::badRequestHandler);
        javalin.exception(AlreadyTakenException.class, this::alreadyTakenHandler);
        javalin.exception(UnauthorizedException.class, this::unauthorizedHandler);

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

    private void addUser(Context ctx) throws BadRequestException, AlreadyTakenException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData authData = userService.register(user);
        ctx.status(200);
        ctx.result(new Gson().toJson(authData));

    }

    private void login(Context ctx) throws BadRequestException, UnauthorizedException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData authData = userService.login(user);
        ctx.status(200);
        ctx.result(new Gson().toJson(authData));
    }
    private void logout(Context ctx) throws UnauthorizedException {
        String token = ctx.header("authorization");
        userService.logout(token);
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
    private void unauthorizedHandler(UnauthorizedException ex, Context ctx){
        ctx.status(401);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

}

