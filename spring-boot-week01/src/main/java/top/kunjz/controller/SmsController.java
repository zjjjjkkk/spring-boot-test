package top.kunjz.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.kunjz.service.SmsService;

@RestController
public class SmsController {
    @Resource
    private SmsService smsService;

    @GetMapping("/sms")
    public void sendSms( String phone){   // 加 @RequestParam
        smsService.sendSms(phone);
    }
}
