package org.example.application.monsterTradingCards.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class User {

    private String username;
    private String password;
    private int coins;

//    private ArrayList<Card> stack;
//    private ArrayList<Card> deck;
//    private ArrayList<Card> package;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
    }

    public User() {
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getCoins() { return coins; }

    public void setCoins(int coins) { this.coins = coins; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';

    }
}
