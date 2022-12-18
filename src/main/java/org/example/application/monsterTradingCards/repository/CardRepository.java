package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.User;

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
        // check if the user already exists:
        String findCard = "SELECT * FROM cards WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCard)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // username already exists
                    return new Card();
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static Card save(Card card) {
        Card foundCard = findById(card.getId());
        // card not found in database
        if(foundCard == null) {
            // create new card
            String insertCard = "INSERT INTO cards (id, name, damage, elementType, category) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(insertCard)) {
                ps.setString(1, card.getId());
                ps.setString(2, card.getName());
                ps.setInt(3, card.getDamage());
                ps.setString(4, card.getElementType().name());
                ps.setString(5, card.getCategory().name());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return card;
        }
        return null;
    }
}
