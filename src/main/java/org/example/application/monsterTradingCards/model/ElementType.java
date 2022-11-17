package org.example.application.monsterTradingCards.model;

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
