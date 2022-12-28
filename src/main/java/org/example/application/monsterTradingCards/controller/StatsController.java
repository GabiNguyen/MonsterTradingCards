package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.StatsRepository;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;

public class StatsController {

    private final StatsRepository statsRepository;

    public StatsController(StatsRepository statsRepository) { this.statsRepository = statsRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.GET.method)) { return stats(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response stats(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        String username = SessionController.user.getUsername();
        User user = SessionController.user;

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            // gets only in if path ends with logged-in user and if authorization header equals token of logged-in user
            if (request.getPath().endsWith("/" + username) && request.getAuthorization().equals(LoginService.authorize(user))) {
                content = objectMapper.writeValueAsString(statsRepository.showStats(username));
            } else {
                content = "Not authorized to do this action!";
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
}
