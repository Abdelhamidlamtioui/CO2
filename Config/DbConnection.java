package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection instance = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/green_pulse";
    private static final String USER = "GreenPulse";
    private static final String PASSWORD = "";
    private Connection connection;

    private DbConnection() {
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error initializing database connection: " + e.getMessage());
        }
    }

    public static synchronized DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(1)) {
                System.out.println("Re-establishing database connection.");
                closeConnection();
                initializeConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection status: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
