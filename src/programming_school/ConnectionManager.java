package programming_school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String PROGRAMMING_SCHOOL = "programming_school";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "coderslab"; // unsafe :(
    private static final String DB_PORT = "3309";

    private ConnectionManager() {
    } // just static methods

    /**
     * one place to connect with method
     */
    public static Connection getConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:" + DB_PORT + "/" + PROGRAMMING_SCHOOL + "?useSSL=false&characterEncoding=utf8",
                DB_USERNAME,
                DB_PASSWORD)) {

            return connection;
        }
    }
}