package top.zjk.boot.mp01.config;


import org.springframework.web.bind.annotation.*;
import top.zjk.boot.mp01.common.R;

@RestControllerAdvice
public class GlobalEx {
    @ExceptionHandler(Exception.class)
    public R<Void> handle(Exception e) {
        return R.fail(e.getMessage());
    }
}