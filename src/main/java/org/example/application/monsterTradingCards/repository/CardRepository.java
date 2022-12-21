package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Category;
import org.example.application.monsterTradingCards.model.ElementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.DatabaseInit.conn;

public class CardRepository {
    private final List<Card> cards;

    public CardRepository() { this.cards = new ArrayList<>(); }

    public List<Card> findAll() { return this.cards; }

    public static Card findById(String id) {
        // check if the card already exists:
        String findCard = "SELECT * FROM cards WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCard)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // card already exists
                    // https://stackoverflow.com/questions/65197006/saving-and-reading-the-enum-value-to-the-database-with-jdbc
                    return new Card(rs.getString("id"), rs.getString("name"), rs.getDouble("damage"));
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
    public static Card save(Card[] cards) {
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
//                ps.setString(4, card[4].getElementType().name());
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
            for (Card card : cards) { return card;}
            }
        return null;
    }

}
