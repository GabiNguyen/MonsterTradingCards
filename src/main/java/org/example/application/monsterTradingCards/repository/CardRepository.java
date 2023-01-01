package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.controller.SessionController;
import org.example.application.monsterTradingCards.model.*;
import org.example.application.monsterTradingCards.model.Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.DatabaseInit.conn;

public class CardRepository {
    private final List<Card> cards;

    public CardRepository() { this.cards = new ArrayList<>(); }

//    public List<Card> findAll() { return this.cards; }

    public static ArrayList<Card> findAll() {
        ArrayList<Card> allCards = new ArrayList<>();
        String card = "SELECT * FROM cards";
        try(PreparedStatement ps = conn.prepareStatement(card)) {
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    allCards.add(new Card(rs.getString("id"), rs.getString("name"),
                                          rs.getDouble("damage"), rs.getString("userid"),
                                          rs.getString("type"), rs.getString("category")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return allCards;
    }

    public static Card findById(String id) {
        // check if the card already exists:
        String findCard = "SELECT * FROM cards WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCard)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // card already exists
                    return new Card(rs.getString("id"), rs.getString("name"),
                                    rs.getDouble("damage"), rs.getString("userid"),
                                    rs.getString("type"), rs.getString("category"));

                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String findCardHolder(String username) {
        // check if the card already belongs to a user:
        String findCardHolder = "SELECT id FROM cards WHERE userid = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCardHolder)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // card owner already exists
                    return username;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
//    public static Card[] save(Card[] cards) {
//        Card foundCard = null;
//        for (Card card : cards) {
//            foundCard = findById(card.getId());
//        }
//        // card not found in database
//        if(foundCard == null) {
//            // create new card
//            String insertCard = "INSERT INTO cards (id, name, damage,) VALUES (?, ?, ?)";
//            try(PreparedStatement ps = conn.prepareStatement(insertCard)) {
//                for (Card card : cards) {
//                    ps.setString(1, card.getId());
//                    ps.setString(2, card.getName());
//                    ps.setDouble(3, card.getDamage());
//                    ps.execute();
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//            return cards;
//        }
//        return null;
//    }

    public static Card[] save(Card[] cards) {
        Card foundCard = null;
        for (Card card : cards) {
            foundCard = findById(card.getId());
        }
        // card not found in database
        if(foundCard == null) {
            // create new card
            String insertCard = "INSERT INTO cards (id, name, damage, type, category) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(insertCard)) {
                for (Card card : cards) {
                    ps.setString(1, card.getId());
                    ps.setString(2, card.getName());
                    ps.setDouble(3, card.getDamage());
                    if(card.getName().contains("Water")) {
                        ps.setString(4, ElementType.WATER.elementType);
                    } else if (card.getName().contains("Fire")) {
                        ps.setString(4, ElementType.FIRE.elementType);
                    } else {
                        ps.setString(4, ElementType.NORMAL.elementType);
                    }
                    if(card.getName().contains("Spell")) {
                        ps.setString(5, Category.SPELL.category);
                    } else {
                        ps.setString(5, Category.MONSTER.category);
                    }
                    ps.execute();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return cards;
        }
        return null;
    }

    public static ArrayList<Card> update(Package pack, User sessionUser) {
        ArrayList<String> cardIds = new ArrayList<>();
        cardIds.add(pack.getCard1());
        cardIds.add(pack.getCard2());
        cardIds.add(pack.getCard3());
        cardIds.add(pack.getCard4());
        cardIds.add(pack.getCard5());

        // Array List for storing all information about a card
        ArrayList<Card> cards = new ArrayList<>();
        for (String cardId : cardIds) {
            cards.add(findById(cardId));
        }

        // update foreign key (ownership of card)
        String update = "UPDATE cards SET userid = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(update)) {
            for (String cardId : cardIds) {
                ps.setString(1, sessionUser.getUsername());
                ps.setString(2, cardId);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cards;
    }

    public static ArrayList<Card> showAll(ArrayList<Card> cards, User sessionUser) {
        String cardHolder;
        ArrayList<Card> allCardsOfUser = new ArrayList<>();
        for (Card card : cards) {
            cardHolder = findCardHolder(card.getCardHolder());
            // if FK-key(=username int database) matches logged-in user
            if (cardHolder != null && cardHolder.equals(sessionUser.getUsername())) {
                // show all acquired cards of a user
                allCardsOfUser.add(card);
            }
        }
        return allCardsOfUser;
    }

}
