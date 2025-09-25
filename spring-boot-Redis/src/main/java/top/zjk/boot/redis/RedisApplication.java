package top.zjk.boot.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import top.zjk.boot.redis.config.CloopenConfig;


/**
 * @author zjk
 */
@SpringBootApplication
@EnableConfigurationProperties(CloopenConfig.class)
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}