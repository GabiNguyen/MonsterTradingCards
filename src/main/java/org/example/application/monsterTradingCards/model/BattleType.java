package org.example.application.monsterTradingCards.model;

public enum BattleType {
    MONSTER("Monster"),
    SPELL("Spell"),
    MIXED("Mixed")
    ;

    BattleType(String battleType) {
        this.battleType = battleType;
    }

    public String battleType;
}
