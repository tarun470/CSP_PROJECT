package com.example.water;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Update these values according to your Oracle setup
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
    private static final String USER = "system"; 
    private static final String PASSWORD = "tarun"; 

    public static Connection getConnection() {
        try {
            // Load Oracle JDBC driver (optional in modern Java, but safe)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to Oracle Database successfully!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Oracle JDBC Driver not found. Add ojdbc8.jar to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }
}
