/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shaexample;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
        byte[] salt = getSalt();
        System.out.println(salt);
        
        int c1 = (int)salt[0];
        String salt_to_save = Integer.toString(c1);
        for (int i = 1; i < salt.length; i++) {
            System.out.println(salt[i]);
            salt_to_save += ("," + Integer.toString((int)salt[i]));
        }
        System.out.println(salt_to_save);
        
        //byte[] salt = {-64,117,93,-83,86,79,-40,-4,82,82,118,99,63,-3,-6,24};
        
        String securePassword = get_SHA_256_SecurePassword(passwordToHash, salt);
        System.out.println(securePassword);
    }
    
    private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
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
    
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
