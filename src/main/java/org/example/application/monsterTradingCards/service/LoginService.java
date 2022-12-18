package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInit.conn;

public class LoginService {
    private String token;
    public static User login(User user) {
        // check credentials
        String checkCreds = "SELECT * FROM users WHERE username = ? and password = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkCreds)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // credentials are right
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String authorize(User user) {
        token = "Basic " + user.getUsername() + "-mtcgToken";
        return token;
    }
}
