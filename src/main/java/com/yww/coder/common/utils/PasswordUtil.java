package com.yww.coder.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.crypto.digest.MD5;

/**
 * @author GYQ
 * @date 2024/1/8 15:48
 *       密码工具类
 */

public class PasswordUtil {
    /**
     * 这个正则要求密码至少包含三类（数字、小写字母、大写字母、特殊符号），并且长度在 6 到 15 位之间。
     */
    private static final String PASSWORD_REGEX = "^(?=(.*[a-z]))(?=(.*[A-Z]))(?=(.*\\d))(?=(.*[!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./])).{6,15}$|^(?=(.*[a-z]))(?=(.*[A-Z]))(?=(.*\\d)).{6,15}$|^(?=(.*[a-z]))(?=(.*[!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./])).{6,15}$|^(?=(.*[A-Z]))(?=(.*[!@#$%^&*()_+\\-={}|\\[\\]:\";'<>?,./])).{6,15}$";
    /**
     * 加密的盐
     */
    private final static String salt = "MHGNO8SpQyBm9w3i";

    /**
     * 校验密码是否符合规范
     */
    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 加密
     */
    public static String encrypt(String password) {

        int index = 0;
        int count = 2;
        MD5 md5 = new MD5(salt.getBytes(StandardCharsets.UTF_8), index, count);
        return md5.digestHex(password);
    }

    /**
     * 密码校验
     *
     * @param password 密码
     * @param encrypt  加密后的密码
     * @return
     */
    public static boolean match(String password, String encrypt) {
        int index = 0;
        int count = 2;
        MD5 md5 = new MD5(salt.getBytes(StandardCharsets.UTF_8), index, count);
        return md5.digestHex(password).equals(encrypt);
    }

    public static void main(String[] args) {
        String pwd = "GYQgyq11";
        System.out.println(PasswordUtil.checkPassword(pwd));
        String encrypt = PasswordUtil.encrypt(pwd);
        System.out.println(encrypt);
        String ency = "0a35ecd33eb153cee36ae7d62c0378c5";
        System.out.println(PasswordUtil.match(pwd, ency));

    }
}
