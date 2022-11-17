/*package org.example.application.monsterTradingCards.model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int coins;
    private ArrayList<Card> stack;
    private ArrayList<Card> deck;
    //private ArrayList<Card> package;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
    }

    public String getUsername() { return username; }
    public void setUsername() { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword() { this.password = password; }

    public int getCoins() { return coins; }
    public void setCoins() { this.coins = coins; }
}*/

package org.example.application.monsterTradingCards.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class User {

    private String username;
    private String password;
    private int coins;
    private ArrayList<org.example.application.monsterTradingCards.model.Card> stack;
    private ArrayList<org.example.application.monsterTradingCards.model.Card> deck;
    //private ArrayList<Card> package;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.coins = 20;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
