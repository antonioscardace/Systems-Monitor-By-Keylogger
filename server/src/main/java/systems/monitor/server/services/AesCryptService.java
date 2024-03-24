package systems.monitor.server.services;

import lombok.extern.java.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Service;

// This class is a service component responsible for cryptography.
// It uses AES in ECB mode with a 128-bit key for encryption using PKCS5 padding.
// The ciphertexts are URL-safe thanks to Base64-encoding.
// The key must be 32 url-safe Base64-encoded bytes.
// @author Antonio Scardace
// @version 1.0

@Log
@Service
public class AesCryptService {
 
    private static final String KEY = System.getenv("AES_KEY");

    private AesCryptService() {

    }

    // Encrypts the given plaintext using AES encryption algorithm in ECB mode with PKCS5Padding.
    // Returns the Base64-encoded ciphertext if encryption is successful, or null if an error occurs.

    public static String encrypt(String plaintext) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(ciphertext);
        }
        catch (Exception e) {
            log.warning(e.getMessage());
            return null;
        }
    }

    // Decrypts the given ciphertext using AES decryption algorithm in ECB mode with PKCS5Padding.
    // Returns the plaintext if decryption is successful, or null if an error occurs.

    public static String decrypt(String ciphertext) {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decryptedBytes);
        }
        catch (Exception e) {
            log.warning(e.getMessage());
            return null;
        }
    }
}