package app.controllers;

import app.DAOs.PoemDAO;
import app.DTOs.PoemDTO;
import app.config.HibernateConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class PoemController {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
    private static PoemDAO poemDAO = new PoemDAO(emf);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void addRoutes(Javalin app) {
        app.get("/", ctx -> getAllPoems(ctx));
        app.get("/{id}", ctx -> getPoemById(ctx));
        app.post("/", ctx -> createPoem(ctx));
    }

    private static void getAllPoems(Context ctx) {
        Set<PoemDTO> poems = poemDAO.getAll();
        ctx.json(poems, PoemDTO.class);
    }

    private static void getPoemById(Context ctx) {
        String id = ctx.pathParam("id");
        ctx.status(200);
        ctx.json(poemDAO.getById(Long.parseLong(id)), PoemDTO.class);
    }

    private static void createPoem(Context ctx) throws JsonProcessingException {
        String jSonString = ctx.body();
        PoemDTO poemDTO = objectMapper.readValue(jSonString, PoemDTO.class);
        ctx.status(201);
        poemDAO.create(poemDTO);
    }

    private static void updatePoem(Context ctx) {

    }

    private static void deletePoem(Context ctx) {

    }
}
