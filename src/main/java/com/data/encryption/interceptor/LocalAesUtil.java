package com.data.encryption.interceptor;

import cn.hutool.crypto.symmetric.SymmetricCrypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author chenchicheng
 * @date 2024/3/21
 */
public class LocalAesUtil {

    public static final String DEFAULT_IV = "1UZ1R_2UJXxCH#I#";
    public static final String DEFAULT_KEY = "1UZ1R_2UJXxCH#I#";

    static SymmetricCrypto util = new SymmetricCrypto("AES/CBC/NoPadding", new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES"));

    public static String decrypt(String val) {
        util.setIv(DEFAULT_IV.getBytes());
        String res = null;
        try {
            res = util.decryptStr(val).trim();
        } catch (Exception e) {
            res = null;
        }
        return res;
    }


    public static String encrypt(String source) {
        if (null != source && !source.isEmpty()) {
            try {
                return encryptByAes(source);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    private static String encryptByAes(String source) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(DEFAULT_IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] srcByteArray = source.getBytes(StandardCharsets.UTF_8);

        // 计算需要补\0的位数
        int n = 16 - (source.getBytes(StandardCharsets.UTF_8).length % 16);

        // 如果原始数据就是对齐的，则不再补齐
        if (n == 16) {
            n = 0;
        }

        byte[] byteFillZero = new byte[srcByteArray.length + n];

        System.arraycopy(srcByteArray, 0, byteFillZero, 0, srcByteArray.length);

        for (int i = srcByteArray.length; i < byteFillZero.length; i++) {
            byteFillZero[i] = (byte) '\0';
        }

        byte[] outText = cipher.doFinal(byteFillZero);

        return Base64.getEncoder().encodeToString(outText);
    }
}
