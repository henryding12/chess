package server;

import com.google.gson.Gson;
import dataaccess.*;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
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
        javalin.exception(BadRequestException.class, this::badRequestHandler);
        javalin.exception(AlreadyTakenException.class, this::alreadyTakenHandler);

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

    private void badRequestHandler(BadRequestException ex, Context ctx) {
        ctx.status(400);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }
    private void alreadyTakenHandler(AlreadyTakenException ex, Context ctx) {
        ctx.status(403);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

}

