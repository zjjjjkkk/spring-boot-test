package top.zjk.boot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjk
 */
@RestController
public class TestController {
    @GetMapping()
    public String index() {
        return "hello world";
    }
}