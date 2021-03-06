package funProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQL {

    private String host;
    private String user;
    private String password;
    private String database;

    public PostgreSQL(String host, String user, String password, String database) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":5432/" + database + "?user=" + user + "&password=" + password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}