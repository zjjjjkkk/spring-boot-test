package top.zjk.boot.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zjk.sms.ccp")
public class CloopenConfig {
    private String serverIp;
    private String port;
    private String accountSid;
    private String accountToken;
    private String appId;
    private String templateId;
}