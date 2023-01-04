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
        String winner = "";

        // get deck of players (convert Array of Cards to Array List in order to have a dynamic deck)
        // https://www.geeksforgeeks.org/array-to-arraylist-conversion-in-java/
        ArrayList<Card> p1Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player1.getUsername())));
        ArrayList<Card> p2Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player2.getUsername())));

        // limit to 100 rounds
        for (int i = 0; i < 100; i++) {

            if(p1Deck.size() == 0) { break; }
            if(p2Deck.size() == 0) { break; }

            // pick random index in Deck
            // https://www.geeksforgeeks.org/getting-random-elements-from-arraylist-in-java/
            int p1Pick = (int)(Math.random() * p1Deck.size());
            int p2Pick = (int)(Math.random() * p2Deck.size());

            // get card at picked index
            Card p1Card = p1Deck.get(p1Pick);
            Card p2Card = p2Deck.get(p2Pick);

            // determine winner of round and increment score of round battle winner
            // + move card from loser deck to winner deck
            if (roundBattle(p1Card, p2Card).equals(p1Card.getName())) {
                p1Deck.remove(p1Pick);
                p1Deck.add(p2Card);
                p1Score++;
            } else if (roundBattle(p1Card, p2Card).equals(p1Card.getName())) {
                p2Deck.remove(p2Pick);
                p2Deck.add(p1Card);
                p2Score++;
            }

            // draw
            if (p1Score == p1Score) {
                winner = "";
            // check which score is bigger and update winner
            } else {
                winner = (p1Score > p2Score) ? player1.getUsername() : player2.getUsername();
            }
        }

        return winner;
    }

    public static String roundBattle(Card p1Card, Card p2Card) {

        String card1 = p1Card.getName();
        String card2 = p2Card.getName();
        String winner = "";

        String category1 = p1Card.getCategory().category;
        String category2 = p2Card.getCategory().category;

        double damage1 = p1Card.getDamage();
        double damage2 = p2Card.getDamage();

        // Goblins are too afraid of Dragons to attack
        if ((card1.contains("Goblin") && card2.equals("Dragon")) || (card2.contains("Goblin") && card1.equals("Dragon"))) {
            winner = (card1.equals("Dragon")) ? card1 : card2;
        }

        //  Wizzard can control Orks so they are not able to damage them
        else if ((card1.contains("Wizzard") && card2.equals("Ork")) || (card2.contains("Wizzard") && card1.equals("Ork"))) {
            winner = (card1.contains("Wizzard")) ? card1 : card2;
        }

        // The armor of Knights is so heavy that WaterSpells make them drown them instantly
        else if ((card1.equals("Knight") && card2.equals("WaterSpell")) || (card2.equals("Knight") && card1.equals("WaterSpell"))) {
            winner = (card1.equals("WaterSpell")) ? card1 : card2;
        }

        // The Kraken is immune against spells
        else if ((card1.equals("Kraken") && card2.contains("Spell")) || (card2.equals("Kraken") && card1.contains("Spell"))) {
            winner = (card1.equals("Kraken")) ? card1 : card2;
        }

        // The FireElves know Dragons since they were little and can evade their attacks
        else if ((card1.equals("FireElf") && card2.equals("Dragon")) || (card2.equals("FireElf") && card1.equals("Dragon"))) {
            winner = (card1.equals("FireElf")) ? card1 : card2;
        }

        // Monster Fights (= round with only monster cards involved):
        // The element type does not affect pure monster fights
        else if (category1.equals("Monster") && category2.equals("Monster")) {
            if (damage1 == damage2) {
                winner = "";
            } else {
                winner = (damage1 > damage2) ? card1 : card2;
            }
        }

        // Spell Fights (= round with only spell cards involved):
        else if (category1.equals("Spell") && category2.equals("Spell")) {
            winner = effectiveness(p1Card, p2Card);
        }

        // Mixed Fights (= round with a spell card vs a monster card):
        else if ((category1.equals("Spell") && category2.equals("Monster")) || (category2.equals("Spell") && category1.equals("Monster"))) {
            winner = effectiveness(p1Card, p2Card);
        }

        return winner;
    }

    public static String effectiveness(Card p1Card, Card p2Card) {

        String winner;
        String type1 = p1Card.getElementType().elementType;
        String type2 = p2Card.getElementType().elementType;

        // effective (eg: water is effective against fire, so damage is doubled)
        // not effective (eg: fire is not effective against water, so damage is halved)
        if (type1.equals("Water") && type2.equals("Fire") || type2.equals("Water") && type1.equals("Fire")) {
            winner = calcDamage("Water", p1Card, p2Card);
        } else if (type1.equals("Fire") && type2.equals("Normal") || type2.equals("Fire") && type1.equals("Normal")) {
            winner = calcDamage("Fire", p1Card, p2Card);
        } else if (type1.equals("Normal") && type2.equals("Water") || type2.equals("Normal") && type1.equals("Water")) {
            winner = calcDamage("Normal", p1Card, p2Card);
        }
        // no effect (eg: normal monster vs normal spell, no change of damage, direct
        else {
            if (p1Card.getDamage() == p2Card.getDamage()) {
                winner = "";
            } else {
                winner = (p1Card.getDamage() > p2Card.getDamage()) ? p1Card.getName() : p2Card.getName();
            }
        }

        return winner;
    }

    public static String calcDamage(String type, Card p1Card, Card p2Card) {

        String winner;

        // "type" is the effective element type
        // means card of player1 has the effective element type else card of player2 is the effective one
        if (p1Card.getElementType().elementType.equals(type)) {
            if ((p1Card.getDamage() * 2) == (p2Card.getDamage() / 2)) {
                winner = "";
            } else {
                winner = (p1Card.getDamage() * 2) > (p2Card.getDamage() / 2) ? p1Card.getName() : p2Card.getName();
            }
        } else {
            if ((p1Card.getDamage() * 2) == (p2Card.getDamage() / 2)) {
                winner = "";
            } else {
                winner = (p2Card.getDamage() * 2) > (p1Card.getDamage() / 2) ? p2Card.getName() : p1Card.getName();
            }
        }

        return winner;
    }

}
