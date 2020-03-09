package funProject;

import org.junit.jupiter.api.Test;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class HashPasswordTest {

    @Test
    void hasher() throws NoSuchAlgorithmException {
        HashPassword hashPassword = new HashPassword();
        String hash = hashPassword.hasher("Test");
        String expectedPassword = "[-15, -51, 102, 7, 112, 117, -74, 123, 109, -55, -119, -4, -51, 40, -65, 21, -85, -114, 74, -109, 70, 11, 119, 24, 64, -123, 109, 81, 95, 70, 75, -9, -14, 127, -7, -39, -113, 11, 109, -34, -75, -87, 65, 72, 86, 75, 32, -109, 4, -12, 78, -111, -104, 13, 66, -77, 21, -21, 101, -95, -72, -120, 123, 104]";
        System.out.println("Expected: " + expectedPassword + "\nActual: " + hash);
        assertEquals(expectedPassword, hash);
    }
}