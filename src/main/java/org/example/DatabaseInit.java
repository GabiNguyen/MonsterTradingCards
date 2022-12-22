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
                        password VARCHAR(255) NOT NULL
                    );
                    """
        );
        stmt1.close();

        // Create cards table
        Statement stmt2 = conn.createStatement();
        stmt2.execute(
                """
                    CREATE TABLE IF NOT EXISTS cards (
                        id VARCHAR(255) PRIMARY KEY,
                        userid VARCHAR(255),
                        FOREIGN KEY(userid) references users(username),
                        name VARCHAR(255) NOT NULL,
                        damage INT NOT NULL
                    );
                    """
        );
        stmt2.close();

//        for later  maybe
//        elementType CHAR(255) NOT NULL,
//        category CHAR(255) NOT NULL
    }
}
