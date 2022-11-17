/*package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements UserRepository{
    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
        this.users.add(new User( "stefi.laber", "password"));
        this.users.add(new User("jana.haider", "stheasy"));
        this.users.add(new User("nico.lerchl", "whatever"));
        this.users.add(new User("janko.hu", "gacha"));
    }

    public User save(User user) {
        this.users.add(user);

        return user;
    }

    public List<User> findAll() {
        return this.users;
    }

    public User delete(User user) {
        this.users.remove(user);

        return user;
    }
}*/

package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMemoryRepository implements org.example.application.monsterTradingCards.repository.UserRepository {

    private final List<User> users;

    public UserMemoryRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public User findByUsername(String username) {
        for (User user: this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
        }

        return user;
    }

    @Override
    public User delete(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }

        return user;
    }
}