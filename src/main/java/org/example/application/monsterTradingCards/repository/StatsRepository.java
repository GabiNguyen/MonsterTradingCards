package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Stat;
import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static Stat read(String username) {
        String query = "SELECT * FROM stats WHERE uid = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
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

    public static void update(String username, boolean winner) {
        Stat stat = read(username);
        String update = "UPDATE stats SET win = ?, loss = ?, games = ?, elo = ? WHERE uid = ?";
        try (PreparedStatement ps = conn.prepareStatement(update)) {
            if (winner) {
                ps.setInt(1, stat.getWin() + 1);
                ps.setInt(2, stat.getLoss());
                ps.setInt(4, stat.getElo() + 3);
            }
            else {
                ps.setInt(1, stat.getWin());
                ps.setInt(2, stat.getLoss() + 1);
                ps.setInt(4, stat.getElo() - 5);
            }
            ps.setInt(3, stat.getGames() + 1);

            ps.setString(5, stat.getUsername());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void updateDraw(String player) {
        Stat stat = read(player);
        String update = "UPDATE stats SET games = ? WHERE uid = ?";
        try (PreparedStatement ps = conn.prepareStatement(update)) {
            assert stat != null;
            ps.setInt(1, stat.getGames() + 1);
            ps.setString(2, player);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
