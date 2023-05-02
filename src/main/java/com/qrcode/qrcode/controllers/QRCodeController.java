package com.qrcode.qrcode.controllers;

import com.qrcode.qrcode.utils.EncryptionService;
import jakarta.servlet.ServletOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qrcode.qrcode.dao.QRCodeDao;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.qrcode.qrcode.utils.QRCodeService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

@RestController
public class QRCodeController {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    private QRCodeDao qrCodeDao;

    @Autowired
    private QRCodeService qrCodeService;

    /* Función para encriptar un texto con MD5
    private String encryptWithMD5(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] digest = md.digest();
        return Hex.encodeHexString(digest);
    } */

    @PostMapping("/api/qrcode")
    public void generarQRCode(HttpServletResponse response,
                              @RequestBody String text,
                              @RequestParam(defaultValue = "350") int width,
                              @RequestParam(defaultValue = "350") int height) throws Exception{

        String correoEncriptado = EncryptionService.encrypt(text);

        logger.info("Generando código QR para el texto: {}", text);
        qrCodeDao.generarQRCode(response, correoEncriptado, width, height);
        logger.info("Código QR generado con éxito para el texto: {}", correoEncriptado);
        String correoDesencriptado = EncryptionService.decrypt(correoEncriptado);
        logger.info("Codigo desencriptado: {}", correoDesencriptado);
    }
}
