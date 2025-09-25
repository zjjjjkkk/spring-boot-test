package top.zjk.boot.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
// 去掉 @Configuration  !!! 只保留下面这一行
@ConfigurationProperties(prefix = "zjk.sms.ccp")
public class CloopenConfig {
    private String serverIp;
    private String port;
    private String accountSid;
    private String accountToken;
    private String appId;
    private String templateId;
}