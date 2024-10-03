package app.controllers;

import app.DAOs.PoemDAO;
import app.DTOs.PoemDTO;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityNotFoundException;

import java.util.Set;


public class PoemController {
    private static PoemDAO poemDAO;

    public PoemController(PoemDAO poemDAO) {
        this.poemDAO = poemDAO;
    }

    public void getAllPoems(Context ctx) {
        try {
            Set<PoemDTO> poems = poemDAO.getAll();
            ctx.json(poems, PoemDTO.class);

        } catch (Exception e) {
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getPoemById(Context ctx) {
        try {
            String id = ctx.pathParam("id");
            ctx.json(poemDAO.getById(Long.parseLong(id)), PoemDTO.class);
            ctx.status(200);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    public void createPoem(Context ctx) {
        try {
            String jSonString = ctx.body();
            System.out.println(jSonString);
            PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);
            poemDAO.create(poemDTO);
            ctx.status(201);

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updatePoem(Context ctx) {
        try {
            PoemDTO poemDTO = ctx.bodyAsClass(PoemDTO.class);

            PoemDTO updatedPoemDTO = poemDAO.update(poemDTO);
            ctx.res().setStatus(200);
            ctx.json(updatedPoemDTO);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());
        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }

    public void deletePoem(Context ctx) {
        try {
            String id = ctx.pathParam("id");

            poemDAO.delete(Long.parseLong(id));
            ctx.res().setStatus(204);

        } catch (EntityNotFoundException e) {
            throw new ApiException(404, e.getMessage());

        } catch (Exception e) {
            throw new ApiException(400, e.getMessage());
        }
    }
}
