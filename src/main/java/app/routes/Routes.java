package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private PoemRoutes poemRoutes = new PoemRoutes();

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/poems", poemRoutes.getPoemRoutes());
        };
    }
}

