package com.clj.demo.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @description: 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
 * @author: yewubin
 * @date: 2021/3/23 15:28
 * @version: v1.0
 */
public class AesEncryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AesEncryptUtil.class);
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * RSA公钥加密AES密钥明文
     *
     * @param aesKey    AES密钥明文
     * @param publicKey RSA公钥
     * @return 密文
     */
    public static String encrypt(String aesKey, String publicKey) {
        String outStr = null;
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            outStr = Base64.encodeBase64String(cipher.doFinal(aesKey.getBytes("UTF-8")));
        } catch (Exception e) {
            LOGGER.error("RSA公钥加密AES密钥异常：", e);
        }
        return outStr;
    }

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            // 加密
            byte[] encrypted = cipher.doFinal(plaintext);
            //通过Base64转码返回
            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            LOGGER.error("加密方法异常:", e);
            return null;
        }
    }

    /**
     * RSA私钥解密 AES密钥密文
     *
     * @param aesKey     AES密钥密文
     * @param privateKey
     * @return AES密钥明文
     */
   /* public static String decode(String aesKey, String privateKey) {
        String outStr = null;
        try {
            //Base64解码
            byte[] decoded = Base64.decodeBase64(privateKey);
            // 取得私钥
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] array = Base64.decodeBase64(aesKey.getBytes("UTF-8"));
            int length = array.length;
            //当长度过长的时候，需要分解后解密128个字节
            int MAX_ENCRYPT_BLOCK = 128;
            //标识
            byte[] resultBytes = {};
            byte[] cache = {};
            int offSet = 0;
            while (length - offSet > 0) {
                if (length - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(array, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(array, offSet, length - offSet);
                    offSet = length;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }
            outStr = Base64.encodeBase64String(resultBytes);
        } catch (Exception e) {
            LOGGER.error("RSA私钥解密AES密钥异常：", e);
        }
        return outStr;
    }*/

    public static String decode(String aesKey, String priv) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] decodedKey = Base64.decodeBase64(priv.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] dataBytes = Base64.decodeBase64(aesKey);
            int inputLen = dataBytes.length;
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            // 解密后的内容
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("RSA私钥解密AES密钥异常：", e);
        }
        return null;
    }
    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     */
    public static String desEncrypt(String data, String key, String iv) {
        try {
            byte[] encrypted = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception e) {
            LOGGER.error("解密方法异常:", e);
            return null;
        }
    }


}
