package org.example.application.monsterTradingCards.model;

public class Deck {

    private String uid;
    private String cardId;
    private String first;
    private String second;
    private String third;
    private String fourth;

    public Deck(String first, String second, String third, String fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Deck(String cardId) {
        this.cardId = cardId;
    }

    public Deck() {}
    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }
    public String getCardId() { return cardId; }

    public void setCardId(String cardId) { this.cardId = cardId; }
    public String getFirst() { return first; }

    public void setFirst(String first) { this.first = first; }
    public String getSecond() { return second; }

    public void setSecond(String second) { this.second = second; }
    public String getThird() { return third; }

    public void setThird(String third) { this.third = third; }
    public String getFourth() { return fourth; }

    public void setFourth(String fourth) { this.fourth = fourth; }

}
