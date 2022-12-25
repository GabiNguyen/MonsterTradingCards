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
    int packNum = 0;
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

        User user = SessionController.user;
        Card[] stack = PackageController.allCards.get(packNum);
        System.out.println("STACK: " + stack);

        // only logged-in users can acquire packages
        // TODO: change if to match Authorization Header and Token
        if(LoginService.authorize(user) != null) {
            if(user.getCoins() != 0) {
                stack = cardRepository.update(stack);
                // package costs 5 coins
                user.setCoins(user.getCoins() - 5);
                packNum += 1;
//                System.out.println(packNum);
            }
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        // TODO: convert array to string
        String content = null;
        content = Arrays.toString(stack);
        response.setContent(content);

        return response;
    }
}
