package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class CardController {
    private final CardRepository cardRepository;
    public CardController(CardRepository cardRepository) { this.cardRepository = cardRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.GET.method)) { return readAll(); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }
    private Response readAll() {

        User user = SessionController.user;
        //Card[] allCards = null;

        //STH WRONG
        ArrayList<Card> allCards = null;
        allCards.add(cardRepository.findAll());
        ArrayList<Card> allCardsOfUser = null;

        if(LoginService.authorize(user) != null) {
//            for (int i = 0; i < allCards.size(); i++) {
//                deck = cardRepository.showAll(allCards.get(i));
//            }
            allCardsOfUser = cardRepository.showAll(allCards);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        content = allCardsOfUser.toString();
        response.setContent(content);

        return response;
    }

}
