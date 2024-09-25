package app;

import app.controllers.PoemController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.router.contextPath = "/api/poems"; // all base pat for all routes
            config.bundledPlugins.enableRouteOverview("/routes"); // enables route overview at /routes
        });

        PoemController.addRoutes(app);

        app.start(7000);
    }
}