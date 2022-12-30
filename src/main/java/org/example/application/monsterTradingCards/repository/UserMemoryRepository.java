package org.example.application.monsterTradingCards.repository;

import org.example.application.monsterTradingCards.model.Package;
import org.example.application.monsterTradingCards.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.DatabaseInit.conn;

public class UserMemoryRepository implements UserRepository {

    public User findByUsername(String username) {
        // check if the user already exists:
        String findUser = "SELECT * FROM users WHERE username = ?";
        try(PreparedStatement ps = conn.prepareStatement(findUser)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // username already exists
                    return new User(rs.getString("username"), rs.getString("password"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public User save(User user) {
        User foundUser = findByUsername(user.getUsername());
        // user not found in database (-> insert user)
        if(foundUser == null) {
            // user gets 20 coins when registered
            user.setCoins(20);
            // insert new user
            String insertUser = "INSERT INTO users (username, password, coins) VALUES (?, ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setInt(3, user.getCoins());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return user;
        }
        return null;
    }

    // method to get user data
    public User findAll(String username) {
        String getUserData = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(getUserData)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
    public void updateCoins(User user) {
        int coins = PackageRepository.getCoins(user);
        if(coins > 0) {
            String updateCoins = "UPDATE users SET coins = ? WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateCoins)) {
                ps.setInt(1, coins - 5);
                ps.setString(2, user.getUsername());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    // maybe change parameters later
    public User editData(User user, User sessionUser) {
        String update = "UPDATE users SET name = ?, bio = ?, image = ? WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(update)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getBio());
            ps.setString(3, user.getImage());
            ps.setString(4, sessionUser.getUsername());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

//    public User authorize(User user) {
//        User foundUser = findByUsername(user.getUsername());
//        if (foundUser != null) {
//            // check credentials (if password corresponds to username)
//            String checkPW = "SELECT * FROM users WHERE password = ?";
//            try (PreparedStatement ps3 = conn.prepareStatement(checkPW)) {
//                ps3.setString(1, user.getPassword());
//                try (ResultSet rs = ps3.executeQuery()) {
//                    if (rs.next()) {
//                        // password matched
//                        return user;
//                    } else {
//                        return null;
//                    }
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }

//    public User save(User user) {
//
//        String findUser = "SELECT * FROM users WHERE username = ?";
//
//        try(PreparedStatement ps = conn.prepareStatement(findUser)) {
//            ps.setString(1, user.getUsername());
//            try(ResultSet rs = ps.executeQuery()) {
//                if(rs.next()) {
//                    // username already exists
//                    return null;
//                }
//                else {
//                    // insert new user
//                    String InsertUser = "INSERT INTO users (username, password) VALUES (?, ?)";
//
//                    try(PreparedStatement ps2 = conn.prepareStatement(InsertUser)) {
//                        ps2.setString(1, user.getUsername());
//                        System.out.println(user.getUsername());
//                        ps2.setString(2, user.getPassword());
//                        System.out.println(user.getPassword());
//                        ps2.execute();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                        throw new RuntimeException(e);
//                    }
//                    return user;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    private final List<User> users;

    public UserMemoryRepository() { this.users = new ArrayList<>(); }

//    @Override
//    public List<User> findAll() { return this.users; }

//    @Override
//    public User delete(User user) {
//        if (this.users.contains(user)) {
//            this.users.remove(user);
//        }
//
//        return user;
//    }
}
