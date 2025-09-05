package top.kunjz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@SpringBootApplication
public class Application{
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
    @GetMapping("/hello")
        public String hello() {
        return "hello world!!!";
    }

    @GetMapping("/strings")
    public List<String> strings() {
        return List.of("hello world!!!");
    }
}