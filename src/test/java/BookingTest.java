
import java.sql.*;

public class BookingTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/";
        String user = "root";
        String password = "6ea21ee164f6ef7423e8a2f8f4e2f4a8";

        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            String insertAccommodation = "INSERT INTO accommodation (id, type, bed_type, max_guests, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psAcc= conn.prepareStatement(insertAccommodation);
            psAcc.setInt(1,1);
            psAcc.setString(2,"Hotel");
            psAcc.setString(3,"King");
            psAcc.setInt(4,2);
            psAcc.setString(5,"room with balcony");
            psAcc.executeUpdate();

            String insertRoomFair = "INSERT INTO fair_room (id, value, season) VALUES (?, ?, ?)";
            PreparedStatement psFair= conn.prepareStatement(insertRoomFair);
            psFair.setInt(1,1);
            psFair.setDouble(2, 120.00);
            psFair.setString(3, "summer");
            psFair.executeUpdate();

            String insertRelation = "INSERT INTO accommodation_room_fair_relation (id, accomodation_id, room_fair_id) VALUES (?, ?, ?)";
            PreparedStatement psRelation = conn.prepareStatement(insertRelation);
            psRelation.setInt(1, 1);
            psRelation.setInt(2, 1);
            psRelation.setInt(3, 1);
            psRelation.executeUpdate();

            System.out.println("Inserted succesfuly");

            String query = """ 
                    SELECT  acc.id AS accommodation_id, acc.type, acc.bed_type, acc.max_guests, rf.value AS price, rf.season
                    FROM accommodation acc
                     JOIN accommodation_room_fair_relation arfr ON  a.id=arfr.accommodation_id
                     JOIN room_fair rf ON rf.id = arfr.room_fair_id
                     """;

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.printf("Room ID: %d, Type: %s, Bed: %s, Guests: %d, Price: %.2f, Season: %s%n",
                        resultSet.getInt("accomodation_id"),
                        resultSet.getString("Type"),
                        resultSet.getString("bed_type"),
                        resultSet.getInt("max_guests"),
                        resultSet.getDouble("price"),
                        resultSet.getString("season")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
