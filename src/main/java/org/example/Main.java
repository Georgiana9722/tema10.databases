package org.example;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "6ea21ee164f6ef7423e8a2f8f4e2f4a8";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Booking test = new Booking(conn);


            test.insertAccommodation(1, "House", "Double", 3, "Sea View");
            test.insertRoomFair(1, 150.0, "Autumn");
            test.insertRelation(1, 1, 1);

            test.printRoomPrices();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

