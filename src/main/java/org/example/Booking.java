package org.example;

import java.sql.*;

public class Booking {
    private final Connection conn;

    public Booking(Connection conn) {
        this.conn = conn;
    }

    public void insertAccommodation(int id, String type, String bedType, int maxGuests, String description) throws SQLException {
       String sql = "INSERT INTO accommodation (id, type, bed_type, max_guests, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, type);
            stmt.setString(3, bedType);
            stmt.setInt(4, maxGuests);
            stmt.setString(5, description);
            stmt.executeUpdate();
        }
    }

    public void insertRoomFair(int id, double value, String season) throws SQLException {
        String sql = "INSERT INTO room_fair (id, value, season) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setDouble(2, value);
            stmt.setString(3, season);
            stmt.executeUpdate();
        }
    }

    public void insertRelation(int id, int accommodationId, int roomFairId) throws SQLException {
        String sql = "INSERT INTO accommodation_room_fair_relation (id, accommodation_id, room_fair_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, accommodationId);
            stmt.setInt(3, roomFairId);
            stmt.executeUpdate();
        }
    }

    public void printRoomPrices() throws SQLException {
        String sql = """
            SELECT a.type, a.bed_type, a.max_guests, a.description, rf.value, rf.season
            FROM accommodation a
            JOIN accommodation_room_fair_relation rel ON a.id = rel.accommodation_id
            JOIN room_fair rf ON rf.id = rel.room_fair_id
        """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("Type: %s, Bed: %s, Guests: %d, Description: %s, Price: %.2f, Season: %s%n",
                        rs.getString("type"),
                        rs.getString("bed_type"),
                        rs.getInt("max_guests"),
                        rs.getString("description"),
                        rs.getDouble("value"),
                        rs.getString("season"));
            }
        }
    }
}
