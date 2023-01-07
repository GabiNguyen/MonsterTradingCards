package org.example.application.monsterTradingCards.controller;

import org.example.application.monsterTradingCards.model.User;

import org.example.application.monsterTradingCards.service.BattleService;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.ArrayList;

public class BattleController {
    ArrayList<User> players = new ArrayList<>();

    public BattleController() {}
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return battle(request); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response battle(Request request) {

        String authHeader = request.getAuthorization();
        players.add(LoginService.checkToken(authHeader));

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content;

        if (players.size() % 2 == 0) {
            content = String.valueOf(BattleService.start(players.get(oddIndex()), players.get(evenIndex())));
        } else {
            content = "\nWaiting for a competitor!\n";
        }

        response.setContent(content);

        return response;
    }

    int evenIndex() {
        int index;
        // e.g. players.size = 4 -> array(index) [0, 1, 2, 3] -> get the fourth(last) element -> index 3
        index = (players.size() - 1);
        return index;
    }

    int oddIndex() {
        int index;
        // e.g. players.size = 4 -> array(index) [0, 1, 2, 3] -> get the third(2nd to last) element-> index 2
        index = (players.size() - 2);
        return index;
    }
}

