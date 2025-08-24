
package com.example.water;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;

public class EventDAO {

    /**
     * Insert a new water release event into the database
     */
    public void insertRelease(LocalDateTime releaseTime, int duration, String gate, String notes) {
        String sql = "INSERT INTO water_releases (release_time, duration, gate, notes) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(releaseTime)); // Convert LocalDateTime to SQL Timestamp
            stmt.setInt(2, duration);
            stmt.setString(3, gate);
            stmt.setString(4, notes);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Water release event inserted successfully!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to insert water release event!");
            e.printStackTrace();
        }
    }
}
