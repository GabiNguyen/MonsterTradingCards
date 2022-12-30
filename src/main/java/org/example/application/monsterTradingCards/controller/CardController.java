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

import java.util.ArrayList;
import java.util.List;

public class CardController {
    private final CardRepository cardRepository;
    public CardController(CardRepository cardRepository) { this.cardRepository = cardRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.GET.method)) { return readAll(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }
    private Response readAll(Request request) {

        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ArrayList<Card> allCards = cardRepository.findAll();
        ArrayList<Card> allCardsOfUser = null;

        if(sessionUser != null) {
            allCardsOfUser = cardRepository.showAll(allCards, sessionUser);
        }
        // TODO: error handling if there is no token

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        ArrayList<String> cardsContent = new ArrayList<>();
        String content;

        for(int i = 0; i < allCardsOfUser.size(); i++) {
            cardsContent.add("\r\n" + "Card {" +
                            "Id = '" + allCardsOfUser.get(i).getId() + '\'' +
                            ", Name = '" + allCardsOfUser.get(i).getName() + '\'' +
                            ", Damage = '" + allCardsOfUser.get(i).getDamage() + '\'' +
                            '}');
        }
        cardsContent.add("\r\n");
//        card address and where it's saved (e.g. "org.example.application.monsterTradingCards.model.Card@5e69d280")
//        content = allCardsOfUser.toString();
        content = String.valueOf(cardsContent);

        response.setContent(content);

        return response;
    }

}
