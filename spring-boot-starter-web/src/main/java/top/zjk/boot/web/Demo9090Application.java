package top.zjk.boot.web.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zjk
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration.class
})
public class Demo9090Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo9090Application.class, args);
    }
}