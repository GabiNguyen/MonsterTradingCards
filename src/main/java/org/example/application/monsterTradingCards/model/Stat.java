package org.example.application.monsterTradingCards.model;

public class Stat {
    private String username;
    private int win;
    private int loss;
    private int games;
    private int elo;

    public Stat(String username, int win, int loss, int games, int elo) {
        this.username = username;
        this.win = win;
        this.loss = loss;
        this.games = games;
        this.elo = elo;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getWin() { return win; }
    public void setWin(int win) { this.win = win; }

    public int getLoss() { return loss; }
    public void setLoss(int loss) { this.win = loss; }

    public int getGames() { return games; }
    public void setGames(int games) { this.games = games; }

    public int getElo() { return elo; }
    public void setElo(int elo) { this.win = elo; }

}
