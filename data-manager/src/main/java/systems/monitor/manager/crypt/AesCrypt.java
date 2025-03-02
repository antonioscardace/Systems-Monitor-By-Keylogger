package systems.monitor.manager.crypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Optional;

// This class is responsible for cryptography.
// It implements methods for encryption and decryption with AES-128 in ECB mode, using PKCS7 padding.
// The ciphertexts are URL-safe thanks to Base64-encoding.
// The key must be 32 URL-safe Base64-encoded bytes.
// @author Antonio Scardace
// @version 1.0

@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AesCrypt {
 
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String KEY = Optional.ofNullable(System.getenv("AES_KEY")).orElse("wbtPC2g/3FUPxw6TjB6IYw==");

    // Encrypts the given plaintext.
    // Returns the Base64-encoded ciphertext if encryption is successful, or null if an error occurs.

    public static String encrypt(String plaintext) {
        byte[] ciphertext = AesCrypt.process(Cipher.ENCRYPT_MODE, plaintext.getBytes());
        return Base64.getEncoder().encodeToString(ciphertext);
    }

    // Decrypts the given ciphertext.
    // Returns the plaintext if decryption is successful, or an empty string if an error occurs.

    public static String decrypt(String ciphertext) {
        byte[] decryptedBytes = AesCrypt.process(Cipher.DECRYPT_MODE, Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    // Processes (encrypts or decrypts) the input byte array.
    // The "mode" parameter is used to set the modality (encryption or decryption).

    private static byte[] process(int mode, byte[] input) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(AesCrypt.KEY);
            SecretKey secretKey = new SecretKeySpec(keyBytes, AesCrypt.ALGORITHM);
            Cipher cipher = Cipher.getInstance(AesCrypt.TRANSFORMATION);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        }
        catch (Exception e) {
            log.warning("Failed to process the data: " + e.getMessage());
            return new byte[0];
        }
    }
}