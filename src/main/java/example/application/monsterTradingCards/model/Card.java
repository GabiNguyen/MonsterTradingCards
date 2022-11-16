package example.application.monsterTradingCards.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

public class Card {
    public enum ElementType {
        WATER("Water"),
        FIRE("Fire"),
        NORMAL("Normal")
        ;

        ElementType(String elementType) {
            this.elementType = elementType;
        }

        public String elementType;
    }

    public static class Session {
    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    public static class User {

        private String username;
        private String password;
        private int coins;
        private ArrayList<Card> stack;
        private ArrayList<Card> deck;
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
}
