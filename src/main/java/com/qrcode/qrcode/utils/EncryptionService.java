package com.qrcode.qrcode.utils;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class EncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "my_secret_key";

    public static String encrypt(String valueToEncrypt) throws Exception {
        SecretKeySpec key = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(valueToEncrypt.getBytes("utf-8"));
        String encryptedValue = new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedByteValue), "utf-8");
        return encryptedValue;
    }

    public static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec key = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByteValue = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedValue.getBytes("utf-8")));
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
    }

    private static SecretKeySpec generateSecretKey() throws Exception {
        byte[] keyAsBytes;
        MessageDigest sha = null;
        keyAsBytes = KEY.getBytes("UTF-8");
        sha = MessageDigest.getInstance("MD5");
        keyAsBytes = sha.digest(keyAsBytes);
        keyAsBytes = java.util.Arrays.copyOf(keyAsBytes, 16);
        SecretKeySpec key = new SecretKeySpec(keyAsBytes, ALGORITHM);
        return key;
    }

}