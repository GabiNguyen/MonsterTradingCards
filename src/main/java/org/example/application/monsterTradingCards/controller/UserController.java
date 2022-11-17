/*package org.example.application.monsterTradingCards.controller;

import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {

        if (request.getMethod().equals("GET")) {
            return getUsers(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private User createUser(String username, String password) {
        User user = new User(username, password);
        return user;
    }

    private Response getUsers(Request request) {
        List<User> users = this.userRepository.findAll();

        Response response = new Response();
        response.setStatusCode(StatusCode.OK);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(users.toString());

        return response;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}*/

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

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) {
            return create(request);
        }

        if (request.getMethod().equals(Method.GET.method)) {
            return readAll();
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.METHODE_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.METHODE_NOT_ALLOWED.message);

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


    private Response create(Request request) {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = request.getContent();
        User user;
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
}
