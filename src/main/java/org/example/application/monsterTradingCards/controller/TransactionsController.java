package org.example.application.monsterTradingCards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Package;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.repository.PackageRepository;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.service.LoginService;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.example.server.http.StatusCode;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionsController {
    int packNum = 0;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    public TransactionsController(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }
    public Response handle(Request request) {
        if (request.getMethod().equals(Method.POST.method)) { return acquire(request); }
        Response response = new Response();
        response.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(request.getMethod() + ": Not allowed for " + request.getPath());

        return response;
    }

    private Response acquire(Request request) {

        // get all the packages that haven't been acquired yet
        ArrayList<Package> availablePackages = PackageRepository.findByAvailability();
        Package acquiredPackage = null;
        ArrayList<Card> stack = null;

        String authHeader = request.getAuthorization();
        User sessionUser = LoginService.checkToken(authHeader);

        // if authorization header matches token in session table and doesn't return null
        // and there are available packages to acquire
        if(sessionUser != null && availablePackages.size() != 0) {
            // acquire a package
            acquiredPackage = PackageRepository.updateAcquiredStatus(availablePackages, sessionUser);
            // stack can only be updated if user could acquire package
            if (acquiredPackage != null) {
                stack = CardRepository.update(acquiredPackage, sessionUser);
                // pay 5 coins (update coins column)
                userRepository.updateCoins(sessionUser);
            }
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.CREATED);
        response.setContentType(ContentType.APPLICATION_JSON);
        response.setAuthorization(Authorization.BASIC);
        String content = null;

        content = acquiredPackage != null ? stack.toString() : "User could not acquire package!";
        response.setContent(content);

        return response;
    }
}
