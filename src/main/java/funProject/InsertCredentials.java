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

    public String insertInDB(String username, String password) throws NoSuchAlgorithmException, SQLException {

        String hashedPassword = hashPassword.hasher(password);

        PreparedStatement preparedStatement = null;

        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM userdata WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();
        result.next();
        System.out.println("1: " + result.getString(1));
        System.out.println("2: " + result.getString(1).equals(0));
        if (Integer.parseInt(result.getString(1)) == 0){
            preparedStatement = connection.prepareStatement("INSERT INTO userdata(username, pwd)" + "VALUES (?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
            return "User has been registered successfully";
        } else {
            return "User is already registered";
        }
    }
}