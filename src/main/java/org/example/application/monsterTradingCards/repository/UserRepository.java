package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByUsername(String username);

    User save(User user);

    User delete(User user);
}