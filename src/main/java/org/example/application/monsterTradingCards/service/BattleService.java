package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.DeckRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleService {
    public static String start(User player1, User player2) {
        int p1Score = 0;
        int p2Score = 0;

        // get deck of players (convert Array of Cards to Array List in order to have a dynamic deck)
        // https://www.geeksforgeeks.org/array-to-arraylist-conversion-in-java/
        ArrayList<Card> p1Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player1.getUsername())));
        ArrayList<Card> p2Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player2.getUsername())));

        // limit to 100 rounds
        for (int i = 0; i < 100; i++) {

            if(p1Deck.size() == 0) { break; }
            if(p2Deck.size() == 0) { break; }

            // pick random Card in Deck
            // https://www.geeksforgeeks.org/getting-random-elements-from-arraylist-in-java/
            int p1Pick = (int)(Math.random() * p1Deck.size());
            int p2Pick = (int)(Math.random() * p2Deck.size());

            Card p1Card = p1Deck.get(p1Pick);
            Card p2Card = p2Deck.get(p2Pick);


        }

        return null;
    }

    public static String roundBattle(Card p1Card, Card p2Card) {

        String card1 = p1Card.getName();
        String card2 = p2Card.getName();

        String category1 = p1Card.getCategory().category;
        String category2 = p2Card.getCategory().category;

        String type1 = p1Card.getElementType().elementType;
        String type2 = p2Card.getElementType().elementType;

        // Goblins are too afraid of Dragons to attack
        if ((card1.contains("Goblin") && card2.equals("Dragon")) || (card2.contains("Goblin") && card1.equals("Dragon"))) {

        }

        //  Wizzard can control Orks so they are not able to damage them
       else if ((card1.contains("Wizzard") && card2.equals("Ork")) || (card2.contains("Wizzard") && card1.equals("Ork"))) {

        }

        // The armor of Knights is so heavy that WaterSpells make them drown them instantly
        else if ((card1.equals("Knight") && card2.equals("WaterSpell")) || (card2.equals("Knight") && card1.equals("WaterSpell"))) {

        }

        // The Kraken is immune against spells
        else if ((card1.equals("Kraken") && card2.contains("Spell")) || (card2.equals("Kraken") && card1.contains("Spell"))) {

        }

        // The FireElves know Dragons since they were little and can evade their attacks
        else if ((card1.equals("FireElf") && card2.equals("Dragon")) || (card2.equals("FireElf") && card1.equals("Dragon"))) {

        }

        // Monster Fights (= round with only monster cards involved):
        else if (category1.equals("Monster") && category2.equals("Monster")) {

        }

        // Spell Fights (= round with only spell cards involved):
        else if (category1.equals("Spell") && category2.equals("Spell")) {
            effectiveness(type1, type2);
        }

        // Mixed Fights (= round with a spell card vs a monster card):
        else if ((category1.equals("Spell") && category2.equals("Monster")) || (category2.equals("Spell") && category1.equals("Monster"))) {
            effectiveness(type1, type2);
        }

        return "kein bock mehr";
    }

    public static String effectiveness(String type1, String type2) {
        // effective (eg: water is effective against fire, so damage is doubled)
        if (type1.equals("Water") && type2.equals("Fire")) {

        }
        // not effective (eg: fire is not effective against water, so damage is halved)
        else if (type2.equals("Water") && type1.equals("Fire")) {

        }

        else if (type1.equals("Fire") && type2.equals("Normal")) {

        }

        else if (type2.equals("Fire") && type1.equals("Normal")) {

        }

        else if (type1.equals("Normal") && type2.equals("Water")) {

        }

        else if (type2.equals("Normal") && type1.equals("Water")) {

        }

        // no effect (eg: normal monster vs normal spell, no change of damage, direct
        else {

        }

        return "kein bock mehr";
    }

}
