package app.routes;

import app.DAOs.PoemDAO;
import app.config.HibernateConfig;
import app.controllers.PoemController;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PoemRoutes {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
    private final PoemDAO poemDAO = new PoemDAO(emf);
    private final PoemController poemController = new PoemController(poemDAO);

    public EndpointGroup getPoemRoutes() {
        return () -> {
            get("/", poemController::getAllPoems);
            get("/{id}", poemController::getPoemById);
            post("/", poemController::createPoem);
            put("/{id}", poemController::updatePoem);
            delete("/{id}", poemController::deletePoem);
        };
    }
}
