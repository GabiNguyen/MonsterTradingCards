package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.BattleRepository;

import org.example.application.monsterTradingCards.service.BattleService;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class BattleController {
    private final BattleRepository battleRepository;
    ArrayList<User> players = new ArrayList<>();

    public BattleController(BattleRepository battleRepository) { this.battleRepository = battleRepository; }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return battle(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response battle(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        String authHeader = request.getAuthorization();
        players.add(LoginService.checkToken(authHeader));

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;

        try {
            // check if there are two players
            if ( players.size() % 2 == 0) {
//                content = objectMapper.writeValueAsString(battleRepository.read(players.get(0), players.get(1)));
                content = objectMapper.writeValueAsString(BattleService.start(players.get(0), players.get(1)));
            } else {
                content = "Waiting for a competitor!";
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;

    }

}
