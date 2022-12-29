package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Package;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInit.conn;

public class PackageRepository {
    public Package save(Package _package) {

        // insert new package
        String insertPackage = "INSERT INTO packages(card1, card2, card3, card4, card5) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertPackage)) {
            ps.setString(1, _package.getCard1());
            ps.setString(2, _package.getCard2());
            ps.setString(3, _package.getCard3());
            ps.setString(4, _package.getCard4());
            ps.setString(5, _package.getCard5());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return _package;
    }

    public static Package read() {

        // Read package
        String getPackage = "SELECT * FROM packages";
        try (PreparedStatement ps = conn.prepareStatement(getPackage)) {
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return new Package(rs.getString("card1"), rs.getString("card2"),
                                       rs.getString("card3"), rs.getString("card4"),
                                       rs.getString("card5"));
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
