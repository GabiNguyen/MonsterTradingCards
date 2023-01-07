package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInit {
    private static String DB_URL = "jdbc:postgresql://localhost:5432/swe1db";
    private static String DB_USER = "swe1user";
    private static String DB_PW = "swe1pw";
    public static Connection conn;

    public static void init() throws SQLException {

        // Connection to database
        conn = DriverManager.getConnection( DB_URL, DB_USER, DB_PW );

        // Create user table
        Statement stmt1 = conn.createStatement();
        stmt1.execute(
                """
                    CREATE TABLE IF NOT EXISTS users (
                        username VARCHAR(255) PRIMARY KEY,
                        password VARCHAR(255) NOT NULL,
                        coins INT NOT NULL,
                        name VARCHAR(255),
                        bio VARCHAR(255),
                        image VARCHAR(255)
                    );
                    """
        );
        stmt1.close();

        // Create session table
        Statement stmt2 = conn.createStatement();
        stmt2.execute(
                """
                    CREATE TABLE IF NOT EXISTS sessions (
                        uid VARCHAR(255) PRIMARY KEY,
                        token VARCHAR(255) NOT NULL 
                    );
                    """
        );
        stmt2.close();

        // Create cards table
        Statement stmt3 = conn.createStatement();
        stmt3.execute(
                """
                    CREATE TABLE IF NOT EXISTS cards (
                        id VARCHAR(255) PRIMARY KEY,
                        userid VARCHAR(255),
                        FOREIGN KEY(userid) references users(username),
                        name VARCHAR(255) NOT NULL,
                        damage INT NOT NULL,
                        type VARCHAR(255) NOT NULL,
                        category VARCHAR(255) NOT NULL
                        
                    );
                    """
        );
        stmt3.close();

        // Create packages table
        Statement stmt4 = conn.createStatement();
        stmt4.execute(
                """
                    CREATE TABLE IF NOT EXISTS packages (
                        id serial PRIMARY KEY,
                        card1 VARCHAR(255) NOT NULL,
                        card2 VARCHAR(255) NOT NULL,
                        card3 VARCHAR(255) NOT NULL,
                        card4 VARCHAR(255) NOT NULL,
                        card5 VARCHAR(255) NOT NULL,
                        acquired BOOLEAN NOT NULL 
                    );
                    """
        );
        stmt4.close();


        // Create decks table
        Statement stmt5 = conn.createStatement();
        stmt5.execute(
                """
                    CREATE TABLE IF NOT EXISTS decks (
                        uid VARCHAR(255) PRIMARY KEY,
                        first VARCHAR(255) NOT NULL,
                        second VARCHAR(255) NOT NULL,
                        third VARCHAR(255) NOT NULL,
                        fourth VARCHAR(255) NOT NULL,
                        FOREIGN KEY(first) references cards(id),
                        FOREIGN KEY(second) references cards(id),
                        FOREIGN KEY(third) references cards(id),
                        FOREIGN KEY(fourth) references cards(id)
                    );
                    """
        );
        stmt5.close();

        // Create stats table
        Statement stmt6 = conn.createStatement();
        stmt6.execute(
                """
                    CREATE TABLE IF NOT EXISTS stats (
                        uid VARCHAR(255) PRIMARY KEY,
                        win INT NOT NULL,
                        loss INT NOT NULL,
                        draw INT NOT NULL,
                        games INT NOT NULL,
                        elo INT NOT NULL
                    );
                    """
        );
        stmt6.close();

        // Create tradings table
        Statement stmt7 = conn.createStatement();
        stmt7.execute(
                """
                    CREATE TABLE IF NOT EXISTS tradings (
                        id VARCHAR(255) PRIMARY KEY,
                        uid VARCHAR(255) NOT NULL,
                        FOREIGN KEY(uid) references users(username),
                        cardtotrade VARCHAR(255) NOT NULL,
                        type VARCHAR(255) NOT NULL,
                        mindamage INT NOT NULL
                    );
                    """
        );
        stmt7.close();

    }
}
