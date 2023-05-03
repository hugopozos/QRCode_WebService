package com.qrcode.qrcode.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.qrcode.qrcode.utils.QRCodeService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Repository
@Transactional
public class QRCodeDaoImp implements QRCodeDao{

    @Autowired
    private QRCodeService qrCodeService;

    @Override
    @Transactional
    public void generarQRCode(HttpServletResponse response,
                              @RequestBody String text,
                              @RequestParam(defaultValue = "350") int width,
                              @RequestParam(defaultValue = "350") int height) throws Exception {
       // String qrText = text.substring(5); // Elimina los primeros 5 caracteres ("text=")
        BufferedImage image = qrCodeService.generarQRCodeService(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public String encriptarQRCode(String correoEncriptado) throws Exception {
        return qrCodeService.encriptar(correoEncriptado);
    }

    @Override
    public String desencriptarQRCode(String correoEncriptado) throws Exception {
        return qrCodeService.desencriptar(correoEncriptado);
    }


}
