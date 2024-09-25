package app;

import app.controllers.PoemController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.router.contextPath = "/api/poems";
        }).start(7000);


        app.get("/", PoemController::getAllPoems);
        app.get("/{id}", PoemController::getPoemById);
        app.post("/", PoemController::createPoem);

    }
}