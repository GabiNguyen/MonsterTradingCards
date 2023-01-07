package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Trading;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.repository.TradingsRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.application.monsterTradingCards.service.TradeService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class TradingController {

    private final TradingsRepository tradingsRepository;

    public TradingController(TradingsRepository tradingsRepository) { this.tradingsRepository = tradingsRepository; }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.GET.method)) { return check(request); }
        if (request.getMethod().equals(Method.POST.method) && request.getPath().endsWith("tradings")) {
            return create(request);
        } else if (request.getMethod().equals(Method.POST.method)){
            return accept(request);
        }
        if (request.getMethod().equals(Method.DELETE.method)) { return delete(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response check(Request request) {
        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content;

        try {
            if(sessionUser != null) {
                content = objectMapper.writeValueAsString(tradingsRepository.findAll());
            } else {
                content = "Not authorized to do this action!";
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
    private Response create(Request request) {
        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        Trading trading;

        try {
            trading = objectMapper.readValue(json, Trading.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (sessionUser != null) {
            trading = tradingsRepository.save(trading, sessionUser);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content;

        try {
            if (sessionUser != null) {
                content = objectMapper.writeValueAsString(trading);
            } else {
                content = "Not authorized to do this action!";
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
    private Response delete(Request request) {
        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();
        Trading trading = tradingsRepository.findAll();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content;

        try {
            if(sessionUser != null && request.getPath().endsWith("/" + trading.getId())) {
                content = objectMapper.writeValueAsString(tradingsRepository.delete(trading, sessionUser.getUsername()));
            } else {
                content = "Not authorized to do this action!";
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

    // accept trading deal
    private Response accept(Request request) {
        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        Card cardToTrade;
        // https://dumbitdude.com/how-to-get-the-last-word-from-a-url-in-java/
        // get id of trade deal from last bit of request path
        String tradeId = request.getPath().substring(request.getPath().lastIndexOf("/") + 1);
        Trading trading = TradingsRepository.findById(tradeId);
        String validTrade;

        try {
            cardToTrade = objectMapper.readValue(json, Card.class);
            // get instance of card (all information)
            cardToTrade = CardRepository.findById(cardToTrade.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content;

        try {
            if(sessionUser != null) {
                content = objectMapper.writeValueAsString(cardToTrade);
                validTrade = TradeService.checkValidTrade(trading, sessionUser, cardToTrade);
                content = (content + "\r\n" + validTrade);
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
