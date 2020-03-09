package funProject;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

public class Login {

    private final HashPassword hashPassword;
    private final ReadDatabase readDatabase;

    public Login(HashPassword hashPassword, ReadDatabase readDatabase){
        this.hashPassword = hashPassword;
        this.readDatabase = readDatabase;
    }

    public boolean validateCredentials(String password, String username) throws NoSuchAlgorithmException, SQLException {
        return hashPassword.hasher(password).equals(readDatabase.userdatareader(username));
    }
}