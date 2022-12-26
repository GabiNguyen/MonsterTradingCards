package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.repository.DeckRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;

public class DeckController {
    private final DeckRepository deckRepository;
    public DeckController(DeckRepository deckRepository) { this.deckRepository = deckRepository; }
    Card[] deck;

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.GET.method)) { return readDeck(request); }
        if (request.getMethod().equals(Method.PUT.method)) { return configure(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response readDeck(Request request) {
        String username = SessionController.user.getUsername();
        Card[] deck;

        //TODO: error handling if there is no deck yet (return null or sth)
        deck = deckRepository.findDeck(username);

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content;
        ArrayList<String> cardsContent = new ArrayList<>();

        for(int i = 0; i < deck.length; i++) {
            cardsContent.add("\r\n" + "Card {" +
                    "Id = '" + deck[i].getId() + '\'' +
                    ", Name = '" + deck[i].getName() + '\'' +
                    ", Damage = '" + deck[i].getDamage() + '\'' +
                    '}');
        }
        cardsContent.add("\r\n");
        content = String.valueOf(cardsContent);
        response.setContent(content);

        return response;
    }

    private Response configure(Request request) {
        User user = SessionController.user;

        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();

        try {
            deck = objectMapper.readValue(json, Card[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(request.getAuthorization().equals(LoginService.authorize(user))) {
            deck = deckRepository.configure(deck);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content;
        try {
            content = objectMapper.writeValueAsString(deck);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
}
