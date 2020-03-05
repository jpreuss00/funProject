package funProject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.*;

public class HashPassword {

    public byte[] hasher(String passwordToHash) throws NoSuchAlgorithmException {

        System.out.println("Password before hash: " + passwordToHash);

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        byte[] passwordAfterHash = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        System.out.println("Password after hash: " + passwordAfterHash);

        return passwordAfterHash;
    }
}
