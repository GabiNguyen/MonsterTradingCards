package org.example.application.monsterTradingCards.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Card {

    private String id;
    private String name;
    private Double damage;

    private String cardHolder;

    // initialize element type and category -> otherwise null pointer exception
    private ElementType elementType = ElementType.NORMAL;
    private Category category = Category.SPELL;

    public Card(String id, String name, Double damage, String cardHolder, String elementType, String category) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.cardHolder = cardHolder;
        this.elementType = ElementType.valueOf(elementType.toUpperCase());
        this.category = Category.valueOf(category.toUpperCase());
    }

    public Card(String id) {
        this.id = id;
    }

    public Card() {}

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) { this.damage = damage; }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public ElementType getElementType() { return elementType; }
    public void setElementType(ElementType elementType) { this.elementType = elementType; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

//    @Override
//    public String toString() {
//        return "Card{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", damage='" + damage + '\'' +
//                "}";
//    }
}


