package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Package;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.repository.PackageRepository;

public class PackageController {
    private final CardRepository cardRepository;
    private final PackageRepository packageRepository;
    public PackageController(CardRepository cardRepository, PackageRepository packageRepository) {
        this.cardRepository = cardRepository;
        this.packageRepository = packageRepository;
    }
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
        Card[] cards;
        Package pack;
        String authorization = request.getAuthorization();

        try {
            cards = (objectMapper.readValue(json, Card[].class));
            pack = new Package(cards[0].getId(), cards[1].getId(), cards[2].getId(), cards[3].getId(), cards[4].getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // TODO: error handling if user isn't logged-in (user == null)
        // only admin can create packages (save it to the database)
        if(authorization.equals("Basic admin-mtcgToken")) {
            cards = cardRepository.save(cards);
            packageRepository.save(pack);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content = null;

        try {
            content = objectMapper.writeValueAsString(cards);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }
}
