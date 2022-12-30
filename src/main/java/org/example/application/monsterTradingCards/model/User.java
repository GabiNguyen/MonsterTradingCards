package org.example.application.monsterTradingCards.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class User {

    private String username;
    private String password;
    private int coins;
    private String name;
    private String bio;
    private String image;

//    private ArrayList<Card> stack;
//    private ArrayList<Card> deck;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int coins, String name, String bio, String image) {
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public User() {}

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getCoins() { return coins; }

    public void setCoins(int coins) { this.coins = coins; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }
    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", coins='" + getCoins() + '\'' +
//                '}';
//
//    }
}
