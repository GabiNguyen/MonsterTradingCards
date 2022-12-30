package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.User;

import java.util.List;

public interface UserRepository {

//    List<User> findAll();
    User findAll(String username);
    void updateCoins(User user);
    User findByUsername(String username);
    User save(User user);

    User editData(User user, User sessionUser);
    //User delete(User user);
}