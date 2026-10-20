package dao.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    private static final String URL ="jdbc:mysql://localhost:3306/campeonato?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String USER = System.getenv("DB_USER");
    private static String PASS = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}