package com.likaiyuan.Crypyto;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

/**
 * @date 22/10/2021
 */

public class RSAUtils {
    /**
     * get RSAPrivateKey from exponent and modulus
     *
     * @param exponent at least 512 bits
     * @param modulus  at least 512 bits
     * @return an RSAPrivateKey object generated from the exponent and modulus
     */
    public static RSAPrivateKey generateRSAPrivateKey(BigInteger exponent, BigInteger modulus) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(rsaPrivateKeySpec);
        return rsaPrivateKey;
    }

    /**
     * get RSAPublicKey from exponent and modulus
     *
     * @param exponent at least 512 bits
     * @param modulus  at least 512 bits
     * @return an RSAPublicKey object generated from the exponent and modulus
     */
    public static RSAPublicKey generateRSAPublicKey(BigInteger exponent, BigInteger modulus) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(rsaPublicKeySpec);
        return rsaPublicKey;
    }

    /**
     * Generate a new RSA KeyPair
     *
     * @param keySize The length of modulus, usually 512 or 1024
     * @return generated RSA KeyPair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    private static BigInteger str2BigInteger(String s) {
        return new BigInteger(s.getBytes(StandardCharsets.UTF_8));
    }

    private static String bigInteger2str(BigInteger bigInteger) {
        return new String(bigInteger.toByteArray());
    }

    /**
     * encrypt process:
     * plainText -> byte array -> bigInteger -> mod power -> encrypted BigInteger
     *
     * @param rsaPublicKey public key, containing the exponent and modulus
     * @param plainText    the plain text you want to encrypt, recommending base64 encoded text
     * @return the encrypted BigInteger which can be encoded with base64 or other ways
     */
    public static BigInteger encrypt(RSAPublicKey rsaPublicKey, String plainText) {
        BigInteger plainTextBigInteger = str2BigInteger(plainText);
        BigInteger modulus = rsaPublicKey.getModulus();
        BigInteger exponent = rsaPublicKey.getPublicExponent();
        BigInteger encryptResult = plainTextBigInteger.modPow(exponent, modulus);
        return encryptResult;
    }

    /**
     * decrypt process:
     * cipherTextBigInteger -> mod power -> plainTextBigInteger -> byte array -> plainText
     *
     * @param rsaPrivateKey
     * @param cipherTextBigInteger
     * @return
     */
    public static String decrypt(RSAPrivateKey rsaPrivateKey, BigInteger cipherTextBigInteger) {
        BigInteger modulus = rsaPrivateKey.getModulus();
        BigInteger exponent = rsaPrivateKey.getPrivateExponent();
        BigInteger plainTextBigInteger = cipherTextBigInteger.modPow(exponent, modulus);
        String decryptResult = bigInteger2str(plainTextBigInteger);
        return decryptResult;
    }

    /**
     * encode a plain text (UTF-8) using base64
     *
     * @param plainText plain text you want to encode
     * @return cipher text of base64
     */
    public static String base64Encrypt(String plainText) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(encode);
    }

    /**
     * decode a cipher text with base64
     *
     * @param cipherText cipher you want to decode
     * @return plain text decoded with base64
     */
    public static String base64Decrypt(String cipherText) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(cipherText.getBytes(StandardCharsets.UTF_8));
        return new String(decode);
    }
}
