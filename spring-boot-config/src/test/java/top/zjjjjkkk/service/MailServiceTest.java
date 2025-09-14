package top.zjjjjkkk.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zjjjjkkk.model.Mail;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MailServiceTest {
    @Resource
    private MailService mailService;

    @Test
    void sendSimpleMail(){
        Mail mail = new Mail();
        mail.setTo("3348805775@qq.com");
        mail.setSubject("test");
        mail.setContent("内容");
        mailService.sendSimpleMail(mail);
    }

    @Test
    void sendHTMLMail(){
        Mail mail = new Mail();
        mail.setTo("3348805775@qq.com");
        mail.setSubject("test01");
        mail.setContent("<html><body><h1>测试</h1><h2>html</h2><strong>邮</strong><em>件</em></body></html>");
        mailService.sendHTMLMail(mail);
    }

}