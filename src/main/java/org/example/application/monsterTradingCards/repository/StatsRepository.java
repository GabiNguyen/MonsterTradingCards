package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Stat;
import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInit.conn;
public class StatsRepository {

    public void save(User user) {

        // insert new stats
        String query = "INSERT INTO stats (uid, win, loss, games, elo) VALUES (?, ?, ?, ?, ?)";
        // insert starting values of stats
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setInt(2, 0);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 100);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Stat read(User user) {
        String query = "SELECT * FROM stats WHERE uid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Stat(rs.getString("uid"), rs.getInt("win"),
                            rs.getInt("loss"), rs.getInt("games"),
                            rs.getInt("elo"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
