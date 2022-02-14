package com.example.mytext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Utils {
    //md5 加密算法
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            // 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
            byte[] result = digest.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            // result数组，digest.digest ( ); -> text.getBytes();
            // for 循环数组byte[] result;
            for (byte b : result) {
                // 0xff 16进制
                int number = b & 0xff;
                // 转换为字符串
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //发送异常return空字符串
            return "";
        }
    }
}
