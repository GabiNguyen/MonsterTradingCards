package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Trading;
import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInit.conn;

public class TradingsRepository {

    // method to get all trading deals
    public Trading findAll() {
        String query = "SELECT * FROM tradings";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Trading(rs.getString("id"), rs.getString("cardtotrade"),
                                       rs.getString("type"), rs.getDouble("mindamage"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Trading findById(String id) {
        // check if the user already exists:
        String query = "SELECT * FROM tradings WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // id already exists
                    return new Trading(rs.getString("id"), rs.getString("cardtotrade"),
                                       rs.getString("type"), rs.getDouble("mindamage"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String findTrader(String id) {
        String query = "SELECT uid FROM tradings WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("uid");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public Trading save(Trading trading, User user) {
        Trading foundTradeId = findById(trading.getId());
        // trading deal not found in database (-> insert trading)
        if(foundTradeId == null) {
            // insert new trading deal
            String query = "INSERT INTO tradings (id, uid, cardtotrade, type, mindamage) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, trading.getId());
                ps.setString(2, user.getUsername());
                ps.setString(3, trading.getCardToTrade());
                ps.setString(4, trading.getType());
                ps.setDouble(5, trading.getMinimumDamage());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return trading;
        }
        return null;
    }

    public static Trading delete(Trading trading, String uid) {
        Trading foundTradeId = findById(trading.getId());
        // trading deal found in database (-> delete trading)
        if (foundTradeId != null) {
            String query = "DELETE FROM tradings WHERE id = ? AND uid = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, trading.getId());
                ps.setString(2, uid);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return trading;
        }
        return null;
    }

}
