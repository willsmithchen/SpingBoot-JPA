package com.clj.demo.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiaobo
 * @Classname ZzSecurityHelper
 * @Description 加密用的Key 可以用26个字母和数字组成 使用AES-128-CBC加密模式，key需要为16位。
 * @Date 2020-02-28
 */
public class ZzSecurityHelper {

    private ZzSecurityHelper() {
    }

    /**
     * 偏移量
     */
    private static final String IV = "NIfb&95GUY86Gfgh";

    /**
     * @param data 明文
     *             key  密钥，长度16
     *             IV   偏移量，长度16
     * @return 密文
     * @Description AES算法加密明文
     */
    public static String encryptAES(String data, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = data.getBytes();
        int plaintextLength = dataBytes.length;

        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        SecretKeySpec keyspec = new SecretKeySpec(secret.getBytes(), "AES");
        // CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);

        // BASE64做转码。
        return ZzSecurityHelper.encode(encrypted).trim();
    }

    /**
     * @param data 密文
     *             key  密钥，长度16
     *             IV   偏移量，长度16
     * @return 明文
     * @Description AES算法解密密文
     */
    public static String decryptAES(String data, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //先用base64解密
        byte[] encrypted1 = ZzSecurityHelper.decode(data);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keyspec = new SecretKeySpec(secret.getBytes(), "AES");
        IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString.trim();
    }

    /**
     * 编码
     *
     * @param byteArray
     * @return
     */
    public static String encode(byte[] byteArray) {
        return new String(new Base64().encode(byteArray));
    }

    /**
     * 解码
     *
     * @param base64EncodedString
     * @return
     */
    public static byte[] decode(String base64EncodedString) {
        return new Base64().decode(base64EncodedString);
    }

}
