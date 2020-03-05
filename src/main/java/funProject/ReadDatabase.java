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

    public byte[] userdatareader(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("SELECT pwd FROM userdata WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();

        String dbpwd = "";

        if(result.next()){
            dbpwd = result.getString(1);
        }

        byte[] dbpwdInByte = dbpwd.getBytes();

        System.out.println("Password from the database: " + dbpwdInByte);

        return dbpwdInByte;
    }
}
