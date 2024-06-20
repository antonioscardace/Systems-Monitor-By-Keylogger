package systems.monitor.manager.crypt;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Unit tests for the {@link AesCrypt} class.
// The tests examine both successful and unsuccessful scenarios.
// @author Antonio Scardace
// @version 1.0

class AesCryptTest {

    private final Faker faker = new Faker();
    
    @Test
    void testEncryptionDecryption_Successful() {
        String plaintext = faker.lorem().word();
        String cipherText = AesCrypt.encrypt(plaintext);
        assertEquals(plaintext, AesCrypt.decrypt(cipherText));
    }

    @Test
    void testEncryptionDecryption_Unsuccessful() {
        String randomPlaintext = faker.lorem().sentence(2);
        String plaintext = faker.lorem().word();
        String cipherText = AesCrypt.encrypt(plaintext);
        assertNotEquals(randomPlaintext, AesCrypt.decrypt(cipherText));
    }
}