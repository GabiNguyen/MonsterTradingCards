package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.DatabaseInit.conn;

public class ScoreRepository {
    public ArrayList<Score> read() {
        ArrayList<Score> scores= new ArrayList<>();
        // order by highest to lowest elo value
        String query = "SELECT uid, elo FROM stats ORDER BY elo DESC";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    scores.add(new Score(rs.getString("uid"), rs.getInt("elo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return scores;
    }
}
