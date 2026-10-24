package server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import service.AdminService;

public class Server {

    private final Javalin javalin;
    private final AdminService adminService;

    public Server() {
        this(new AdminService(new MemoryUserDAO(), new MemoryGameDAO(), new MemoryAuthDAO()));
    }

    public Server(AdminService adminService) {
        this.adminService = adminService;
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", this::clear);

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

    };

}

