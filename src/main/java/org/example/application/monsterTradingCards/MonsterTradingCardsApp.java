package org.example.application.monsterTradingCards;

import org.example.application.monsterTradingCards.controller.*;
import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.repository.UserMemoryRepository;
import org.example.server.Application;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;

public class MonsterTradingCardsApp implements Application {

    private UserController userController;
    private BattleController battleController;
    private SessionController sessionController;
    private CardController cardController;
    private PackageController packageController;
    private ScoreController scoreController;
    private StatsController statsController;
    private TradingController tradingController;

    public MonsterTradingCardsApp() {
        UserRepository userRepository = new UserMemoryRepository();
        this.userController = new UserController(userRepository);
        this.sessionController = new SessionController(userRepository);
    }

    @Override
    public Response handle(Request request) {
        if (request.getPath().startsWith("/users")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/sessions")) {
            return sessionController.handle(request);
        }

        if (request.getPath().startsWith("/cards")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/battles")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/packages")) {
            return packageController.handle(request);
        }

        if (request.getPath().startsWith("/score")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/stats")) {
            return userController.handle(request);
        }

        if (request.getPath().startsWith("/tradings")) {
            return userController.handle(request);
        }

        Response response = new Response();
        response.setStatusCode(StatusCode.NOT_FOUND);
        response.setContentType(ContentType.TEXT_PLAIN);
        response.setContent(StatusCode.NOT_FOUND.message);

        return response;
    }
}