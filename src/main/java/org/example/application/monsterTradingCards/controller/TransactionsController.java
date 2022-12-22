package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.Arrays;

public class TransactionsController {
    private final CardRepository cardRepository;
    public TransactionsController(CardRepository cardRepository) { this.cardRepository = cardRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return acquire(request); }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private Response acquire(Request request) {

//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = request.getContent();
        Card[] stack = PackageController.pack;
        User user = SessionController.user;

//        try {
//            stack = (objectMapper.readValue(json, Card[].class));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

        // only logged-in users can acquire packages
        if(LoginService.authorize(user) != null) { stack = cardRepository.update(stack); }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content = null;
        content = Arrays.toString(stack);

//        try {
//            content = objectMapper.writeValueAsString(stack);
//            System.out.println(content);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        response.setContent(content);

        return response;
    }
}
