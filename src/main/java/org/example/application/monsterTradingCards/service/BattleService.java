package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.DeckRepository;
import org.example.application.monsterTradingCards.repository.StatsRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class BattleService {

    public static ArrayList<String> start(User player1, User player2) {
        int p1Score = 0;
        int p2Score = 0;
        String winner = "";
        String loser = "";
        ArrayList<String> log = new ArrayList<>();


        // get deck of players (convert Array of Cards to Array List in order to have a dynamic deck)
        // https://www.geeksforgeeks.org/array-to-arraylist-conversion-in-java/
        ArrayList<Card> p1Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player1.getUsername())));
        ArrayList<Card> p2Deck = new ArrayList<>(Arrays.asList(DeckRepository.findDeck(player2.getUsername())));


        // limit to 100 rounds
        for (int i = 1; i <= 100; i++) {
            log.add("\r\n--------------------------  ROUND: " + i + " --------------------------\r\n");

            if(p1Deck.size() == 0) {
                log.add("Player: " + player1.getUsername() + " has no cards left in the deck!\r\n");
                break;
            }
            if(p2Deck.size() == 0) {
                log.add("Player: " + player2.getUsername() + " has no cards left in the deck!\r\n");
                break;
            }

            // pick random index in Deck
            // https://www.geeksforgeeks.org/getting-random-elements-from-arraylist-in-java/
            int p1Pick = (int)(Math.random() * p1Deck.size());
            int p2Pick = (int)(Math.random() * p2Deck.size());

            // get card at picked index
            Card p1Card = p1Deck.get(p1Pick);
            Card p2Card = p2Deck.get(p2Pick);
            log.add("Card[" + p1Card.getName() + "] of player: " + player1.getUsername() + " goes against " + "Card[" + p2Card.getName() + "] of player: " + player2.getUsername() + "\r\n");

            // determine winner of round and increment score of round battle winner
            // + move card from loser deck to winner deck
            if ((roundBattle(p1Card, p2Card).equals(p1Card.getName())) || (roundBattle(p1Card, p2Card).equals(player1.getUsername()))) {
                log.add(p1Card.getDamage() + " vs " + p2Card.getDamage() + "\r\n");
                log.add(p1Card.getName() + " of player " + player1.getUsername() + " defeats " + p2Card.getName() + " of player " + player2.getUsername() + "\r\n");
                log.add("Player -" + player1.getUsername() + "- wins this round!\r\n");
                // add card from loser to winner deck
                p1Deck.add(p2Card);
                // remove card of player 2 from their deck
                p2Deck.remove(p2Pick);
                p1Score++;
            } else if ((roundBattle(p1Card, p2Card).equals(p2Card.getName())) || (roundBattle(p1Card, p2Card).equals(player2.getUsername()))) {
                log.add(p1Card.getDamage() + " vs " + p2Card.getDamage() + "\r\n");
                log.add(p2Card.getName() + " of player " + player2.getUsername() + " defeats " + p1Card.getName() + " of player " + player1.getUsername() + "\r\n");
                log.add("Player -" + player2.getUsername() + "- wins this round!\r\n");
                // add card from loser to winner deck
                p2Deck.add(p1Card);
                // remove card of player 1 from their deck
                p1Deck.remove(p1Pick);
                p2Score++;
            } else {
                log.add(p1Card.getDamage() + " vs " + p2Card.getDamage() + "\r\n");
                log.add("Card [" + p1Card.getName() + "] and Card [" + p2Card.getName() +"] are equally strong\r\n");
                log.add("Draw - nobody wins this round!\r\n");
            }

            // draw
            if (p1Score == p2Score) {
                winner = "";
            // check which score is bigger and update winner
            } else {
                winner = (p1Score > p2Score) ? player1.getUsername() : player2.getUsername();
                loser = (p1Score < p2Score) ? player1.getUsername() : player2.getUsername();

            }
        }

        log.add("\r\n------------------------ Battle Finished ------------------------\r\n");

        if (!winner.equals("")) {
            // update stats + elo calculation
            StatsRepository.update(winner, true);
            StatsRepository.update(loser, false);
            log.add("Congratulations! The winner of this game is -" + winner + "-\r\n");
            log.add("Nice try -" + loser + "-! Maybe you will get them next time :)\r\n");
            log.add("Score: " + winner + " won with [" + p1Score + "] points and " + loser + " lost with [" + p2Score + "] points\r\n");
        } else {
            // update only games column
            StatsRepository.updateDraw(player1.getUsername());
            StatsRepository.updateDraw(player2.getUsername());
            log.add("Draw!\r\n");
            log.add("Score: Both players got " + p1Score + " points\r\n");
        }

        System.out.println(log);
        return log;
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
                // check if same name
                if(card1.equals(card2)) {
                    // name of player
                    winner = (damage1 > damage2) ? p1Card.getCardHolder() : p2Card.getCardHolder();
                } else {
                    // name of card
                    winner = (damage1 > damage2) ? card1 : card2;
                }

            }
        }

        // Spell Fights (= round with only spell cards involved):
        else if (category1.equals("Spell") && category2.equals("Spell")) {
            // check if same name
            if(card1.equals(card2)) {
                // name of player
                winner = (damage1 > damage2) ? p1Card.getCardHolder() : p2Card.getCardHolder();
            } else {
                // name of card
                winner = effectiveness(p1Card, p2Card);
            }
        }

        // Mixed Fights (= round with a spell card vs a monster card):
        else if ((category1.equals("Spell") && category2.equals("Monster")) || (category2.equals("Spell") && category1.equals("Monster"))) {
            winner = effectiveness(p1Card, p2Card);
        }

        return winner;
    }

    // method to determine which element type is effective against another element type
    public static String effectiveness(Card p1Card, Card p2Card) {

        String winner;
        String type1 = p1Card.getElementType().elementType;
        String type2 = p2Card.getElementType().elementType;

        // effective (eg: water is effective against fire, so damage is doubled)
        // not effective (eg: fire is not effective against water, so damage is halved)
        if ((type1.equals("Water") && type2.equals("Fire")) || (type2.equals("Water") && type1.equals("Fire"))) {
            winner = calcDamage("Water", p1Card, p2Card);
        } else if ((type1.equals("Fire") && type2.equals("Normal")) || (type2.equals("Fire") && type1.equals("Normal"))) {
            winner = calcDamage("Fire", p1Card, p2Card);
        } else if ((type1.equals("Normal") && type2.equals("Water")) || (type2.equals("Normal") && type1.equals("Water"))) {
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
            if ((p2Card.getDamage() * 2) == (p1Card.getDamage() / 2)) {
                winner = "";
            } else {
                winner = (p2Card.getDamage() * 2) > (p1Card.getDamage() / 2) ? p2Card.getName() : p1Card.getName();
            }
        }

        return winner;
    }

}
