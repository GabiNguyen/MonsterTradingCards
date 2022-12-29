package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInit.conn;

public class LoginService {
    private static String token;
    public static User login(User user) {
        // check credentials
        String checkCreds = "SELECT * FROM users WHERE username = ? and password = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkCreds)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // credentials are right
                    authorize(user);
                    return new User(rs.getString("username"), rs.getString("password"),
                                    rs.getInt("coins"), rs.getString("name"),
                                    rs.getString("bio"), rs.getString("image"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static User authorize(User user) {
        token = "Basic " + user.getUsername() + "-mtcgToken";
        String auhthorization = "INSERT INTO sessions (uid, token) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(auhthorization)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, token);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    public static User checkToken(String token) {
        String findToken = "SELECT * FROM sessions WHERE token = ?";
        try (PreparedStatement ps = conn.prepareStatement(findToken)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("uid"), rs.getString("token"));
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
