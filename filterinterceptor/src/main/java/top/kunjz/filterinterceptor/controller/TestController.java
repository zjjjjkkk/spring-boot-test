package top.kunjz.filterinterceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.kunjz.filterinterceptor.result.Result;

@RestController
@RequestMapping("/api")
public class TestController {
    // 1. 测试日志过滤器/耗时拦截器/业务日志拦截器
    @GetMapping("/test/filter")
    public Result<String> testFilter(@RequestParam String name) {
        return Result.ok("Hello, " + name + "! 过滤器与拦截器测试成功");
    }

    // 2. 测试限流过滤器（支付接口）
    @GetMapping("/pay/{id}")
    public Result<String> pay(@PathVariable long id) {
        return Result.ok("支付成功，订单号：" + id + "，金额：99.00元");
    }

    // 3. 测试跨域过滤器（简单GET接口）
    @GetMapping("/test/cors")
    public Result<String> testCors() {
        return Result.ok("跨域请求测试通过，当前时间：" + System.currentTimeMillis());
    }
}