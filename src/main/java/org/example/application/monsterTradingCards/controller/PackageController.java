package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class PackageController {

    private final CardRepository cardRepository;
    public PackageController(CardRepository cardRepository) { this.cardRepository = cardRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return create(request); }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private Response create(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
//        System.out.println(json);
        Card[] pack;

        try {
            pack = objectMapper.readValue(json, Card[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        pack = new Card[]{CardRepository.save(pack)};

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.AUTHORIZATION);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(pack);
            System.out.println(content);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
}
