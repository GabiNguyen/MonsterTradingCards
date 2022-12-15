package org.example;

import org.example.application.monsterTradingCards.MonsterTradingCardsApp;
import org.example.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MonsterTradingCardsApp());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//jackson greift auf die getter zu