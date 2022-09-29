package com.likaiyuan.Crypyto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * @author paerxiusi from HIT
 * @date 24/10/2021
 */
public class AESUtils {
    // relevant algorithm string
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
    /**
     * generate a random AES key
     * @param size byte number of the key, a multiple of 32, e.g. size % 16 == 0
     * @return a string of the key
     */
    public static String AESGenerateKey(int size) {
        // If size is not a multiple of 16, the default value is 32.
        if(size % 16 != 0){
            size = 32;
        }
        byte[] bytes = new byte[size];
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)(new Random().nextInt(96)+32);
        }
        return new String(bytes);
    }

    /**
     * AES encrypt using key to encrypt plainText
     * @param AESKey AES key, represented by a string
     * @param plainText plain text
     * @return cipher text
     */
    public static String AESEncrypt(String AESKey, String plainText){
        KeyGenerator kgen = null;
        try {
            kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(AESKey.getBytes(), "AES"));
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes("utf-8"));
            return base64Encode(cipherBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES decrypt using key to decrypt cipherText
     * @param AESKey AES key, represented by a string
     * @param cipherText cipher text
     * @return plain text
     */
    public static String AESDecrypt(String AESKey, String cipherText){
        try {
            return aesDecryptByBytes(base64Decode(cipherText), AESKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String base64Encode(byte[] bytes){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    private static byte[] base64Decode(String base64Code) throws Exception{
        Base64.Decoder decoder = Base64.getMimeDecoder();
        return decoder.decode(base64Code);
    }


    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}
