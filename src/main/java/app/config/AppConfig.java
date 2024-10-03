package app.config;

import app.controllers.ExceptionController;
import app.exceptions.ApiException;
import app.routes.Routes;
import app.util.ApiProps;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig {

    private static final Routes routes = new Routes();
    private static final ExceptionController exceptionController = new ExceptionController();

    private static void configuration(JavalinConfig config) {
        //Server
        config.router.contextPath = ApiProps.API_CONTEXT; // Base path for all routes.

        //Plugin
        config.bundledPlugins.enableRouteOverview("/routes");
        config.bundledPlugins.enableDevLogging();

        // Routes
        config.router.apiBuilder(routes.getApiRoutes());
    }

    //Exceptions
    private static void exceptionContext(Javalin app) {
        app.exception(ApiException.class, exceptionController::apiExceptionHandler);
    }

    public static void startServer() {
        Javalin app = Javalin.create(AppConfig::configuration);
        exceptionContext(app);
        app.start(ApiProps.PORT);
    }
}
