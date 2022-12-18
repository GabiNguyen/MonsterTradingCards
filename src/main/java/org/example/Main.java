package org.example;

import org.example.application.monsterTradingCards.MonsterTradingCardsApp;
import org.example.server.Server;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MonsterTradingCardsApp());
        try {
            DatabaseInit.init();
            server.start();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

//jackson greift auf die getter zu