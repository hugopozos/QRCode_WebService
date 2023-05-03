package com.qrcode.qrcode.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;

@Service
public class QRCodeService {

    private static final String ALGORITHM = "AES";

    private static final String KEY = "my_secret_key";

    public BufferedImage generarQRCodeService(String text, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String encriptar(String valueToEncrypt) throws Exception {
        SecretKeySpec key = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(valueToEncrypt.getBytes("UTF-8"));
        String encryptedValue = new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedByteValue), "UTF-8");
        return encryptedValue;
    }

    public String desencriptar(String encryptedValue) throws Exception {
        SecretKeySpec key = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByteValue = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedValue.getBytes("utf-8")));
        String decryptedValue = new String(decryptedByteValue,"UTF-8");
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