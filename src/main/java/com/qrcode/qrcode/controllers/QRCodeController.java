package com.qrcode.qrcode.controllers;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qrcode.qrcode.dao.QRCodeDao;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class QRCodeController {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    private QRCodeDao qrCodeDao;

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
                              @RequestParam(defaultValue = "350") int height) throws Exception {

        String correoEncriptado = qrCodeDao.encriptarQRCode(text);
        logger.info("Generando código QR para el texto: {}", correoEncriptado);
        qrCodeDao.generarQRCode(response, correoEncriptado, width, height);

    }

    @PostMapping("/api/leer_qrcode")
    @ResponseBody
    public Map<String, String> leerQRCode(@RequestBody Map<String, String> requestMap) throws Exception {
        String qrData = requestMap.get("qrData");
        String qrDataDesencriptado = "";
        if (qrData != null) {
            qrDataDesencriptado = qrCodeDao.desencriptarQRCode(qrData);
            logger.info("Correo Desencriptado: {}", qrDataDesencriptado);
        }
        // Devuelve el valor de qrDataDesencriptado en formato JSON
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("qrDataDesencriptado", qrDataDesencriptado);
        return responseMap;
    }










}
