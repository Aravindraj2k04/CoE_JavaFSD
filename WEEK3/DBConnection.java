import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fee_report_system";
    private static final String USER = "root";
    private static final String PASSWORD = "password";  // Change it to your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }
}
