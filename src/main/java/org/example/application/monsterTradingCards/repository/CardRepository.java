package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.controller.SessionController;
import org.example.application.monsterTradingCards.model.Card;

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
                                          rs.getDouble("damage"), rs.getString("userid")));
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
                    // https://stackoverflow.com/questions/65197006/saving-and-reading-the-enum-value-to-the-database-with-jdbc
                    return new Card(rs.getString("id"), rs.getString("name"), rs.getDouble("damage"), rs.getString("userid"));
//                    for later maybe
//                    ElementType.valueOf(rs.getString("elementtype")), Category.valueOf(rs.getString("category")
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
    public static Card[] save(Card[] cards) {
        Card foundCard = null;
        for (Card card : cards) {
            foundCard = findById(card.getId());
//            System.out.println(card.getId());
        }
        // card not found in database
        if(foundCard == null) {
            // create new card
            String insertCard = "INSERT INTO cards (id, name, damage) VALUES (?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(insertCard)) {
//                ps.setString(1, card[0].getId());
//                ps.setString(2, card[0].getName());
//                ps.setDouble(3, card[0].getDamage());
//                ps.setString(4, card[4].getElementType().name());s
//                ps.setString(5, card[5].getCategory().name());
                for (Card card : cards) {
                    ps.setString(1, card.getId());
                    ps.setString(2, card.getName());
                    ps.setDouble(3, card.getDamage());
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

    public static Card[] update(Card[] cards) {
        String cardHolder = null;
        String username = SessionController.user.getUsername();
        for (Card card : cards) {
            cardHolder = findCardHolder(card.getCardHolder());
        }

        // if no user owns the card
        if(cardHolder == null) {
            // update foreign key (ownership of card)
            String update = "UPDATE cards SET userid = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                for (Card card : cards) {
                    ps.setString(1, username);
                    ps.setString(2, card.getId());
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

    public static ArrayList<Card> showAll(ArrayList<Card> cards) {
        String cardHolder;
        ArrayList<Card> allCardsOfUser = new ArrayList<>();
        String username = SessionController.user.getUsername();
        for (Card card : cards) {
            cardHolder = findCardHolder(card.getCardHolder());
            // if FK-key(=username int database) matches logged-in user
            if (cardHolder.equals(username)) {
                // show all acquired cards of a user
                allCardsOfUser.add(card);
            }
        }
        return allCardsOfUser;
    }

}
