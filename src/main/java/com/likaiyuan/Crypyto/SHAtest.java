package com.likaiyuan.Crypyto;

public class SHAtest {
    public static void main(String[] args) {
        String name="likaiyuan";
        String result=SHA256.sha256(name);
        System.out.println(result);
        String name2="likaiyuan";
        String result2=SHA256.sha256(name2);
        System.out.println(result2);
        System.out.println(result.equals(result2));
    }
}
