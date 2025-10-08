package top.zjk.boot.filter_interceptor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zjk
 * @Date: 2025/9/26
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String get() {
        log.info("访问测试接口 /api/test");
        return "测试接口调用成功";
    }

    @GetMapping("/pay/{id}")
    public String pay(@PathVariable int id) {
        log.info("进入支付环节, id: {}", id);
        return "订单号为 " + id + "，支付成功";
    }

    @GetMapping("/order")
    public String order(@RequestParam String username) {
        log.info("进入用户 {} 的订单生成环节", username);
        return "用户：" + username + " 的订单生成成功";
    }
}