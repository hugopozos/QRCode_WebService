package com.qrcode.qrcode.controllers;

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

@RestController
public class QRCodeController {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @Autowired
    private QRCodeDao qrCodeDao;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/api/qrcode")
    public void generarQRCode(HttpServletResponse response,
                              @RequestBody String text,
                              @RequestParam(defaultValue = "350") int width,
                              @RequestParam(defaultValue = "350") int height) throws Exception{
        BufferedImage image = qrCodeService.generarQRCodeService(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
        /*logger.info("Generando código QR para el texto: {}", text);
        qrCodeDao.generarQRCode(response, text, width, height);
        logger.info("Código QR generado con éxito para el texto: {}", text);*/
    }
}
