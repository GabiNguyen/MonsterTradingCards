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

public class PackageController {
//    static Card[] pack;
    static ArrayList<Card[]> allCards = new ArrayList<Card[]>();
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
        Card[] pack;
        User user = SessionController.user;

        try {
            pack = (objectMapper.readValue(json, Card[].class));
            allCards.add(pack);
//            System.out.println(allCards);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // TODO: error handling if user isn't logged-in (user == null)
        // only admin can create packages (save it to the database)
        if(LoginService.authorize(user).equals("Basic admin-mtcgToken")) { pack = cardRepository.save(pack); }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content = null;

        try {
            content = objectMapper.writeValueAsString(pack);
//            System.out.println(content);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
}
