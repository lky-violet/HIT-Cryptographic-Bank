package com.likaiyuan.Crypyto;

public class AESTest {
    public static void main(String[] args) {
        String key = AESUtils.AESGenerateKey(32);
//        key = "^K#ojb^1Xfm\"4K6mgdU#3wp#INpS2U=i";
        String plainText = "Aloha, world!";
        String cipherText = AESUtils.AESEncrypt(key, plainText);
        String decryptText  = AESUtils.AESDecrypt(key, cipherText);
        System.out.println(key);
        System.out.println(plainText);
        System.out.println(cipherText);
        System.out.println(decryptText);
    }
}
