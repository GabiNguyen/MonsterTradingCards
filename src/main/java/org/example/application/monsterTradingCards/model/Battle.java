package org.example.application.monsterTradingCards.model;

import java.util.ArrayList;

public class Battle {
    int round;
    User player1;
    User player2;
    BattleType battleType;
    ArrayList<Deck> deck1;
    ArrayList<Deck> deck2;
    String log;

    public int getRound() { return round; }
    public void setRound(int round) { this.round = round; }

    public User getPlayer1() { return player1; }
    public void setPlayer1(User player1) { this.player1 = player1; }

    public User getPlayer2() { return player2; }
    public void setPlayer2(User player2) { this.player2 = player2; }

    public BattleType getBattleType() { return battleType; }
    public void setBattleType(BattleType battleType) { this.battleType = battleType; }

    public ArrayList<Deck> getDeck1() { return deck1; }
    public void setDeck1(ArrayList<Deck> deck1) { this.deck1 = deck1; }

    public ArrayList<Deck> getDeck2() { return deck2; }
    public void setDeck2(ArrayList<Deck> deck2) { this.deck2 = deck2; }

    public String getLog() { return log; }
    public void setLog(String log) { this.log = log; }
}
