package top.zjjjjkkk.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ConfigurationProperties(prefix="aliyun-oss")
@Configuration
public class OssConfig{
    private String endpoint;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String dir;
}

