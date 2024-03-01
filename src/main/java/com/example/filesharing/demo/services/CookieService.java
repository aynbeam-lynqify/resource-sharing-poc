package com.example.filesharing.demo.services;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.filesharing.demo.vo.EncryptMessage;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    private static final String key = "YourSecretKey"; // Replace with your own secret key

    // The IV size is typically the same as the block size (16 bytes for AES-128 or 32 bytes for AES-256).
    private static final int IV_SIZE = 16;
    
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    public Cookie[] getCokkies(){
        Cookie[] cookies = request.getCookies();

        return cookies;
    }

    public boolean cookiesContain(Long code, String uuid){

        if(Objects.nonNull(request.getCookies())){
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(String.valueOf(code)) && cookie.getValue().equals(uuid)){
                    return true;
                }
            }    
        }
        
        return false;
    }

    /*
        We create a cookie with the name “username” and value “Jovan”.
        The cookie is set to expire in 7 days.
        It’s marked as secure (only sent over HTTPS).
        It’s marked as HttpOnly (not accessible via JavaScript).
        The cookie is accessible globally (“/”).
     */
    public void setCookie(String key, String value) {
        
        Cookie cookie = new Cookie(key, encrypt(value).getEncrypted());
        cookie.setMaxAge(7 * 24 * 60 * 60);
        // cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
    
        response.addCookie(cookie);
    }

    /*
     * Encrypting and Decrypting Cookies: We can use AES encryption to secure the cookie data. 
     * However, keep in mind that encryption and decryption can be resource-intensive. 
     * To improve performance, consider using a singleton instance of the initialized cipher.
     */
    public static EncryptMessage encrypt(String message) {
        try {
            /*
                Initialization Vector (IV)
                An IV is a random or pseudorandom value used in cryptographic algorithms to provide the initial state for encryption or decryption.
                It ensures that repeated usage of the same key does not allow an attacker to infer relationships between segments of the encrypted message.
                For AES (Advanced Encryption Standard), the IV size is typically the same as the block size (16 bytes for AES-128 or 32 bytes for AES-256).
             */
            byte[] iv = new byte[IV_SIZE];

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

            return EncryptMessage.builder()
                .encrypted(Base64.getEncoder().encodeToString(encryptedBytes))
                .ivBase64(Base64.getEncoder().encodeToString(iv))
                .build();
        } catch (Exception e) {
            return null;
        }
    }

    public String decryptMessage(String encryptedMessage, byte[] iv) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
    
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
    
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);
    
            return decryptedMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateRandomString(int desiredLength) {
        return new Random().ints(desiredLength, 97, 123)
                .mapToObj(i -> (char) i)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

}
