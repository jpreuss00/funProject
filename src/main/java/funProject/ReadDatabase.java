package funProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadDatabase {

    private final Connection connection;

    public ReadDatabase(Connection connection){
        this.connection = connection;
    }

    public String userdatareader(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("SELECT pwd FROM userdata WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();

        result.next();

        return result.getString(1);
    }
}