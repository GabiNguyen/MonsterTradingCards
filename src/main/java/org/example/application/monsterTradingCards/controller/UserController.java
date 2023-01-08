package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.StatsRepository;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;

public class UserController {

    static User user;
    private final UserRepository userRepository;
    private final StatsRepository statsRepository;

    public UserController(UserRepository userRepository, StatsRepository statsRepository) {
        this.userRepository = userRepository;
        this.statsRepository = statsRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return create(request); }
        if (request.getMethod().equals(Method.GET.method)) { return readAll(request); }
        if(request.getMethod().equals(Method.PUT.method)) {return editData(request);}

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
        // set starting stats if user is created
        if (user != null) {
            statsRepository.save(user);;
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            // send back created user
            content = user != null ? objectMapper.writeValueAsString(user) : "User already exists!";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

    private Response readAll(Request request) {
        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            if(sessionUser != null && request.getPath().endsWith("/" + sessionUser.getUsername())) {
                content = objectMapper.writeValueAsString(userRepository.findAll(sessionUser.getUsername()));
            } else {
                content = "Not authorized to do this action!";
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

    private Response editData(Request request) {

        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = request.getContent();
        User user;

        try {
            user = objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if(sessionUser != null && request.getPath().endsWith("/" + sessionUser.getUsername())) {
            user = userRepository.editData(user, sessionUser);
        } else {
            user = null;
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        String content = null;
        try {
            content = user != null ? objectMapper.writeValueAsString(user) : "Not allowed! Page of user does not match user token!";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContent(content);

        return response;
    }

}
