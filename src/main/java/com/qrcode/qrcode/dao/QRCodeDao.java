package com.qrcode.qrcode.dao;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface QRCodeDao {

    void generarQRCode(HttpServletResponse response,
                       @RequestBody String text,
                       @RequestParam(defaultValue = "350") int width,
                       @RequestParam(defaultValue = "350") int height) throws Exception;
}
