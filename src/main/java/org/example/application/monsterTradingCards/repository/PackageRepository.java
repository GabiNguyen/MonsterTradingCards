package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Package;
import org.example.application.monsterTradingCards.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.example.DatabaseInit.conn;

public class PackageRepository {

    public Package save(Package _package) {

        // insert new package
        String insertPackage = "INSERT INTO packages(card1, card2, card3, card4, card5, acquired) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(insertPackage)) {
            ps.setString(1, _package.getCard1());
            ps.setString(2, _package.getCard2());
            ps.setString(3, _package.getCard3());
            ps.setString(4, _package.getCard4());
            ps.setString(5, _package.getCard5());
            ps.setBoolean(6, false);
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
                if (rs.next()) {
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

    public static ArrayList<Package> findByAvailability() {
        ArrayList<Package> packages = new ArrayList<>();
        // check if the package is already acquired:
        String findCard = "SELECT * FROM packages WHERE acquired = ?";
        try(PreparedStatement ps = conn.prepareStatement(findCard)) {
            ps.setBoolean(1, false);
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    packages.add(new Package(rs.getInt("id"), rs.getString("card1"),
                                            rs.getString("card2"), rs.getString("card3"),
                                            rs.getString("card4"), rs.getString("card5")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return packages;
    }

    public static int getCoins(User user) {
        String query = "SELECT coins FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.execute();
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Package updateAcquiredStatus(ArrayList<Package> packages, User sessionUser) {

        if (getCoins(sessionUser) > 0) {
            String update = "UPDATE packages SET acquired = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(update)) {
                ps.setBoolean(1, true);
                // get first available package in packages array
                ps.setInt(2, packages.get(0).getTableId());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return packages.get(0);
        }
       return null;
    }

//    public static int packageCount() {
//        int count;
//        String query = "SELECT COUNT(*) AS recordCount FROM packages";
//        try(PreparedStatement ps = conn.prepareStatement(query)) {
//            try(ResultSet rs = ps.executeQuery()) {
//                if(rs.next()) {
//                     count = rs.getInt("recordCount");
//                } else {
//                    return 0;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        return count;
//    }
}
