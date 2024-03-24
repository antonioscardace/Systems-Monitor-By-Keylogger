package systems.monitor.server.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the {@link AesCryptService} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

@SpringBootTest
class AesCryptServiceTest {
    
    @Test
    void testEncryptionDecryption_Successful() {
        String plaintext = "test";
        String cipherText = AesCryptService.encrypt(plaintext);
        assertEquals(plaintext, AesCryptService.decrypt(cipherText));
    }

    @Test
    void testEncryptionDecryption_Unsuccessful() {
        String plaintext = "test";
        String cipherText = AesCryptService.encrypt(plaintext);
        assertNotEquals("unexpected text", AesCryptService.decrypt(cipherText));
    }
}