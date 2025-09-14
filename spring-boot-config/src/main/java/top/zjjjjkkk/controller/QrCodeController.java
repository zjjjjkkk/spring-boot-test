package top.zjjjjkkk.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import org.springframework.beans.factory.annotation.Value;   // ✅ Spring 的注解
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
public class QrCodeController {

    @Value("${custom.qrcode.content}")
    private String content;

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQrCode() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        QrCodeUtil.generate(content, 200, 200, "png", bos);
        byte[] bytes = bos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}