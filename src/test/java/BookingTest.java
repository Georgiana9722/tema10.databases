import org.junit.jupiter.api.*;
import java.sql.*;

public class BookingTest {
    private static BookingTest test;
    private static Connection conn;

    @BeforeAll
    static void setup() throws SQLException {
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "6ea21ee164f6ef7423e8a2f8f4e2f4a8");
        test = new BookingTest(conn);
    }

    @Test
    void testInsertAndPrint() throws SQLException {
        test.insertAccommodation(2, "Cabin", "Single", 3, "Mountain and lake view");
        test.insertRoomFair(2, 80.0, "Autumn");
        test.insertRelation(2, 2, 2);

        Assertions.assertDoesNotThrow(() -> test.printRoomPrices());
    }

    @AfterAll
    static void close() throws SQLException {
        conn.close();
    }
}