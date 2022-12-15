package org.example.application.monsterTradingCards.model;

import java.lang.annotation.ElementType;

public class Card {

    private String name;
    private int damage;

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {this.damage = damage;}

}


