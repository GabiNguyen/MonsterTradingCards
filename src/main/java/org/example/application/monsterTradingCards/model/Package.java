package org.example.application.monsterTradingCards.model;

public class Package {
    private int tableId;
    private String id;
    private String card1;
    private String card2;
    private String card3;
    private String card4;
    private String card5;
    private boolean acquired;


    public Package(String card1, String card2, String card3, String card4, String card5) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
    }

    public Package(int tableId, String card1, String card2, String card3, String card4, String card5) {
        this.tableId = tableId;
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.card4 = card4;
        this.card5 = card5;
    }

    public Package(String id) { this.id = id; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCard1() { return card1; }
    public void setCard1(String card1) { this.card1 = card1; }

    public String getCard2() { return card2; }
    public void setCard2(String card2) { this.card2 = card2; }

    public String getCard3() { return card3; }
    public void setCard3(String card3) { this.card3 = card3; }

    public String getCard4() { return card4; }
    public void setCard4(String card1) { this.card4 = card4; }

    public String getCard5() { return card5; }
    public void setCard5(String card5) { this.card5 = card5; }

    public Boolean getAcquired() { return acquired; }
    public void setAcquired(Boolean acquired) { this.acquired = acquired; }
}
