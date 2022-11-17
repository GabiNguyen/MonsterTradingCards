package org.example.application.monsterTradingCards.controller;

import org.example.application.monsterTradingCards.repository.UserMemoryRepository;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.model.User;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

import java.util.List;

public class SessionController {

    public Response handle(Request request) {

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }
    private UserMemoryRepository userMemoryRepository;

    private Response getUsers(Request request) {
        List<User> users = this.userMemoryRepository.findAll();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(users.toString());

        return response;
    }

    public UserRepository getUserRepository() {
        return userMemoryRepository;
    }

    /*private User login(User user) {
        for(int i = 0; i < .size(); i++) {
            if ()
        }
    }*/
}
