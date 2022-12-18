package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.repository.UserMemoryRepository;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

import java.util.List;

public class SessionController {

    private final UserRepository userRepository;

    public SessionController(UserRepository userRepository) { this.userRepository = userRepository; }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return login(request); }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private Response login(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        User user;
        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        user = LoginService.login(user);

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

    //    private UserMemoryRepository userMemoryRepository;

//    private Response getUsers(Request request) {
//        List<User> users = this.userRepository.findAll();
//
//        Response response = new Response();
//        response.setStatusCode(StatusCode.OK);
//        response.setContentType(ContentType.TEXT_PLAIN);
//        response.setContent(users.toString());
//
//        return response;
//    }

//    public UserRepository getUserRepository() {
//        return userRepository;
//    }

}
