/*
*/
package com.likaiyuan.Crypyto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;  
  
/** 
 * 工具类 
 *
 */  
public class SHACoder {
	
    public static void main(String[] args) throws Exception {  
        String testString1="likaiyuan";
        String testString2="sskkksjsslwklwdmskmlaslalkslaxlaxklsamxmkaxmsalxalxmlaxmslaxlal";
        System.out.println("likaiyuan哈希后为");
        System.out.println(SHACoder.encodeSHA256(testString1.getBytes()));
        System.out.println("sskkksjsslwklwdmskmlaslalkslaxlaxklsamxmkaxmsalxalxmlaxmslaxlal");
        System.out.println(SHACoder.encodeSHA(testString2.getBytes()));
//        System.out.println(SHACoder.encodeSHA384(testString.getBytes()));
//        System.out.println(SHACoder.encodeSHA512(testString.getBytes()));
     }  
    /** 
     * SHA-1消息摘要算法 
     */  
    public static String encodeSHA(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称  
        MessageDigest md = MessageDigest.getInstance("SHA");  
        // 执行摘要方法  
        byte[] digest = md.digest(data);  
        return new HexBinaryAdapter().marshal(digest);  
    }  
  
    /** 
     * SHA-256消息摘要算法 
     */  
    public static String encodeSHA256(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        // 执行摘要方法  
        byte[] digest = md.digest(data);  
        return new HexBinaryAdapter().marshal(digest);  
    }  
  
    /** 
     * SHA-384消息摘要算法 
     */  
    public static String encodeSHA384(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称  
        MessageDigest md = MessageDigest.getInstance("SHA-384");  
        // 执行摘要方法  
        byte[] digest = md.digest(data);  
        return new HexBinaryAdapter().marshal(digest);  
    }  
  
    /** 
     * SHA-512消息摘要算法 
     */  
    public static String encodeSHA512(byte[] data) throws Exception {
        // 初始化MessageDigest,SHA即SHA-1的简称  
        MessageDigest md = MessageDigest.getInstance("SHA-512");  
        // 执行摘要方法  
        byte[] digest = md.digest(data);  
        return new HexBinaryAdapter().marshal(digest);  
    }
    public static String sha256(String plainText) throws Exception {
        return SHACoder.encodeSHA256(plainText.getBytes(StandardCharsets.UTF_8));
    }
}  
  
/** 
 * 测试类 
 */  
/*class SHATest {
    public static void main(String[] args) throws Exception {  
       String testString="admin";  
       System.out.println(SHACoder.encodeSHA(testString.getBytes()));  
       System.out.println(SHACoder.encodeSHA256(testString.getBytes()));  
       System.out.println(SHACoder.encodeSHA384(testString.getBytes()));  
       System.out.println(SHACoder.encodeSHA512(testString.getBytes()));  
    }  
} */ 