package top.zjk.boot.redis.entity; // 确保包路径正确（和文件实际位置一致）

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: zjk
 * @Date: 2025/9/24
 */
@SpringBootTest // 仅保留此注解，自动加载默认配置
@Slf4j
public class LoginTest {

    @Test
    public void testLoginFlow() {
        log.info("登录接口测试");
        log.info("1. 先调用发送短信接口: GET /api/sms/send?phone=13800138000"); // 补充完整路径（之前漏了/api前缀）
        log.info("2. 再调用登录接口: POST /api/login"); // 补充完整路径
        log.info(" 请求体示例:");
        log.info("  {");
        log.info("  \"phone\": \"13800138000\",");
        log.info("  \"code\": \"123456\"");
        log.info("  }");
    }
}