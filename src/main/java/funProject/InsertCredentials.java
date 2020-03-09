package funProject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class InsertCredentials {

    private final Connection connection;
    private final HashPassword hashPassword;

    public InsertCredentials(Connection connection, HashPassword hashPassword) {
        this.connection = connection;
        this.hashPassword = hashPassword;
    }

    public void insertInDB(String username, String password) throws NoSuchAlgorithmException, SQLException {

        String hashedPassword = hashPassword.hasher(password);

        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("INSERT INTO userdata(username, pwd)" + "VALUES (?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.executeUpdate();
    }
}