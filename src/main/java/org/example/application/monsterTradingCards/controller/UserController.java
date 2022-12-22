package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class UserController {

    static User user;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) { this.userRepository = userRepository; }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return create(request); }
        if (request.getMethod().equals(Method.GET.method)) { return readAll(); }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHOD_NOT_ALLOWED.message);

        return response;
    }

    private Response create(Request request) {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();

        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        user = userRepository.save(user);

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
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

    private Response readAll() {
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = objectMapper.writeValueAsString(userRepository.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

}
