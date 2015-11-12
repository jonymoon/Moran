package com.geekband.jonymoon.moran.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created by jony on 2015/10/24 0024.
 */
public class StringUtil {
    private static Pattern email =Pattern.compile("\\w+([-+.]\\w+)*@\\w+" +
            "([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static Pattern password =Pattern.compile(".{6,}");
    private static Pattern nickname=Pattern.compile("[\\u4e00-\\u9fa5A-Za-z0-9]{2,20}");

//    判断是否为空
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\n' && c != '\r') {
                return false;
            }
        }
        return true;
    }
//检查昵称是否有效
    public static boolean isNicknameValid(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return nickname.matcher(input).matches();
    }


//判断邮箱是否有效
    public static boolean isEmail(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return email.matcher(input).matches();
    }

//判断密码是否有效
    public static boolean isPasswardValid(String passward) {
        if (passward == null || passward.trim().length() == 0) {
            return false;
        }
        return password.matcher(passward).matches();
    }

//    半角转换为全角
    public static String toDBC(String input) {
       char[] c=input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i]=(char)32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String encryptPhoneNumber(String phone) {
        if (phone == null) return "******";
        if (phone.length() < 11) return phone;
        return phone.substring(0, 3) + "******" + phone.substring(0, 8);
    }

    public static String convertUserDeliveryAddress(String address) {
        String[] info = address.split("#");
        StringBuffer text = new StringBuffer();
        text
                .append(info[1])
                .append(info[2])
                .append(info[3])
                .append(info[4])
                .append("\n")
                .append(info[0])
                .append(" ")
                .append(info[6])
        ;
        return text.toString();
    }

//    MD5加密
    public static String getMD5(String password) {

        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] results = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int b:results) {
                String hexStr = Integer.toHexString(b & 0xff);
                stringBuilder.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
}