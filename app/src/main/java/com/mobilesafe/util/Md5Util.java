package com.mobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /**
     * 字符串md5加密
     * @param encodeString 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5Encode(String encodeString){
        // 加盐
        try {
            encodeString = encodeString + "md5Encoding";
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] encodedStr = digest.digest(encodeString.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : encodedStr) {
                int i = b & 0xff;
                // 转换成16进制
                String str = Integer.toHexString(i);
                if(str.length() < 2){
                    str = "0" + str;
                }

                stringBuffer.append(str);
            }

            return  stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
