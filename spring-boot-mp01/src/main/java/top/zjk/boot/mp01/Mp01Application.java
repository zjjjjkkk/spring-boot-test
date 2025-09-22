package top.zjk.boot.mp01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zjk
 */
@SpringBootApplication
@MapperScan("top.zjk.boot.mp01.mapper")
public class Mp01Application {
    public static void main(String[] args) {
        SpringApplication.run(Mp01Application.class, args);
    }
}