package org.example.application.monsterTradingCards.model;

public enum Category {
    MONSTER("Monster"),
    SPELL("Spell")
    ;

    Category(String category) {
        this.category = category;
    }

    public String category;
}
