package com.example.DrawLots.util;

/* author: koishikiss */

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    private BCryptUtil() {}

    //加密密码
    public static String encrypt(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static String encrypt(String plainText, String salt) { return BCrypt.hashpw(plainText, salt); }

    //解密密码
    @Deprecated
    public static String decrypt(String cipherText) {
        throw new UnsupportedOperationException("BCrypt does not support decryption.");
    }

    //验证密码和密文对应性
    public static boolean validate(String plainText, String cipherText) {
        return BCrypt.checkpw(plainText, cipherText);
    }
}
