package com.example.water;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VillagerDAO {

    public List<String> getAllPhoneNumbers() {
        List<String> phoneNumbers = new ArrayList<>();
        String sql = "SELECT phone_number FROM villagers";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                phoneNumbers.add(rs.getString("phone_number"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching phone numbers!");
            e.printStackTrace();
        }

        return phoneNumbers;
    }
}
