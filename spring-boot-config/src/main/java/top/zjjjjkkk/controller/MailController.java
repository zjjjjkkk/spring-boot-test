package top.zjjjjkkk.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.zjjjjkkk.enums.ResultStatus;
import top.zjjjjkkk.model.ApiResponse;
import top.zjjjjkkk.model.Mail;
import top.zjjjjkkk.service.MailService;

/**
 * @author zjjjjkkk
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private MailService mailService;

    @PostMapping("/simple")
    public ResponseEntity<ApiResponse<ResultStatus>> sendSimpleMail(@Valid @RequestBody Mail mail) {
        ResultStatus rs = mailService.sendSimpleMail(mail);
        if (rs == ResultStatus.SUCCESS) {
            return ResponseEntity.ok(ApiResponse.success("发送成功", rs));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("发送失败"));
    }

    @PostMapping("/html")
    public ResponseEntity<ApiResponse<ResultStatus>> sendHTMLMail(@Valid @RequestBody Mail mail) {
        ResultStatus rs = mailService.sendHTMLMail(mail);
        if (rs == ResultStatus.SUCCESS) {
            return ResponseEntity.ok(ApiResponse.success("发送成功", rs));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("发送失败"));
    }
}