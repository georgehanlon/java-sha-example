/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaexample;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 *
 * @author George
 * Source: https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 */
public class SHAExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String passwordToHash = "password";
        
        String s = encrypt(passwordToHash);
        
        String[] fromFile = s.split(" ");
        
        if (checkMatch(passwordToHash, fromFile[0], fromFile[1]) == true){
            System.out.println("we did it!");
        } else {
            System.out.println("oh no");
        }
    }
    
    public static String encrypt(String plainTextPassword) throws NoSuchAlgorithmException{
        String output = "";
        byte[] salt = getSalt();
        
        String salt_to_save = Base64.getEncoder().encodeToString(salt);
        
        String encryptedPass = get_SHA_256_SecurePassword(plainTextPassword, salt);
        
        output = encryptedPass + " " + salt_to_save;
        
        return output;
    }
    
    public static boolean checkMatch(String input, String encryptedPassword, String savedSalt){
        byte[] salt = Base64.getDecoder().decode(savedSalt);
        
        String result = get_SHA_256_SecurePassword(input, salt);
        
        if (result.contentEquals(encryptedPassword)){
            return true;
        } else {
            return false;
        }
    }
    
    public static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
