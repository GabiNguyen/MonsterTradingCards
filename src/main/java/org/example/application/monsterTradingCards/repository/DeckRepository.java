package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.controller.SessionController;
import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Deck;
import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.DatabaseInit.conn;
import static org.example.application.monsterTradingCards.repository.CardRepository.findById;

public class DeckRepository {
    private final List<Card> deck;

    public DeckRepository() { this.deck = new ArrayList<>(); }

    // method to get id of cards in the deck table
    public static Card[] deck(String uid) {
        Card[] deck = new Card[4];
        // cards in deck start in 2nd column
        int cardNum = 2;
        String findCardInDeck = "SELECT * FROM decks WHERE uid = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCardInDeck)) {
            ps.setString(1, uid);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    for (int i = 0; i < deck.length; i++) {
                        deck[i] = new Card(rs.getString(cardNum));
                        cardNum++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return deck;
    }

    // method to get all information about a card and put it in an array
    public static Card[] findDeck(String uid) {
        Card[] deckOfCardsId = deck(uid);
        Card[] deck = new Card[4];
        String id = null;
        int i = 0;

        // check if deck is empty
        if(deckOfCardsId[0] == null) { return null; }

        for (Card card: deckOfCardsId) {
            id = card.getId();
            String showDeck = "SELECT * FROM cards WHERE id = ? AND userid = ?";
            try(PreparedStatement ps = conn.prepareStatement(showDeck)) {
                ps.setString(1, id);
                ps.setString(2, uid);
                try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        deck[i] = new Card(rs.getString("id"), rs.getString("name"),
                                           rs.getDouble("damage"), rs.getString("userid"),
                                           rs.getString("type"), rs.getString("category"));
                        i++;
                    }
                    else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return deck;
    }

    public static Card[] configure(Card[] cards, User sessionUser) {
        Card foundCard;
        Card[] configuredDeck = new Card[4];
        int i = 0;
        if(cards.length == configuredDeck.length) {
            for (Card card : cards) {
                foundCard = findById(card.getId());
                // gets in if id of found card in database and card id of passed content matches and if cards of deck are in packages of user
                if (foundCard.getId().equals(card.getId()) && foundCard.getCardHolder().equals(sessionUser.getUsername())) {
                    configuredDeck[i] = foundCard;
                } else {
                    return null;
                }
                i++;
            }
            save(configuredDeck);
            return configuredDeck;
        }
        return null;
    }

    public static Card[] save(Card[] cards) {

        // save picked cards in deck
        String insertCard = "INSERT INTO decks(uid, first, second, third, fourth) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(insertCard)) {
                ps.setString(1, cards[0].getCardHolder());
                ps.setString(2, cards[0].getId());
                ps.setString(3, cards[1].getId());
                ps.setString(4, cards[2].getId());
                ps.setString(5, cards[3].getId());
                ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return cards;
    }
}
