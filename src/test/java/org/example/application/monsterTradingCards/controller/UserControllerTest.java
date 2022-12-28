package org.example.application.monsterTradingCards.controller;

import org.example.application.monsterTradingCards.repository.UserRepository;
import org.example.application.monsterTradingCards.model.User;
import org.example.server.dto.Request;
import org.example.server.dto.Response;
import org.example.server.http.StatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    org.example.application.monsterTradingCards.controller.UserController userController;

    @Mock
    UserRepository userRepository;

    @Test
    void testGetHouses() {
        //Arrange
        List<User> users = new ArrayList<>();
        users.add(new User("stefi.laber", "password"));
        users.add(new User("jana.haider", "idk"));
        users.add(new User("nico.lerchl", "otis"));
        users.add(new User("janko.hu", "catss"));

//        when(userRepository.findAll()).thenReturn(users);

        //houseRepository = new HouseMemoryRepository();
        userController = new org.example.application.monsterTradingCards.controller.UserController(userRepository);

        Request request = new Request();
        request.setMethod("GET");
        request.setPath("/users");

        //Act
        Response response = userController.handle(request);

        //Assert
        assertEquals(StatusCode.OK.code, response.getStatus());
        assertEquals(users.toString(), response.getContent());
        //verify(userRepository.findAll(), times(1)); für Spy

        ;
    }
}