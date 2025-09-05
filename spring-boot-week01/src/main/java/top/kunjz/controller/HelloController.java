package top.kunjz.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${my.feature.helloSwitch}")
    private boolean isHelloEnabled;

    @Value("${my.feature.closeMsg}")
    private String closeMessage;

    @GetMapping("/hellos")
    public String hellos() {
        if(isHelloEnabled) {
            return "这是俺第一个spring项目";
        }else {
            return closeMessage;
        }
    }
}