package narnew.cellimagingsystem.utils;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

    private static final String KEY_SHA = "SHA";

    public static String encrypt(String inputStr) {

        if (StringUtils.isEmpty(inputStr)) {
            throw new RuntimeException("加密失败字符串为空");
        }

        byte[] inputData = inputStr.getBytes();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            BigInteger sha = new BigInteger(messageDigest.digest());
            return sha.toString(32);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密失败：" + e.getMessage());
        }
    }
}
