package org.example.application.monsterTradingCards.model;

public class Card {

    private String id;
    private String name;
    private int damage;
    private ElementType elementType;
    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {this.damage = damage;}

    public ElementType getElementType() {
        return elementType;
    }
    public void setElementType(ElementType elementType) {this.elementType = elementType;}
    public Category getCategory() { return category; }
    public void setCategory(Category category) {this.category = category;}

}


