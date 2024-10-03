package app.controllers;

import app.DTOs.Message;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionController {
    private final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    public void apiExceptionHandler(ApiException e, Context ctx) {
        log.error("{} {}", ctx.res().getStatus(), e.getStatusCode());
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage()));
    }
}
