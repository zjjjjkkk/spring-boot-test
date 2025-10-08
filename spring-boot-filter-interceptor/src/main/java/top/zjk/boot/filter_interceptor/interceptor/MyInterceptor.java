package top.zjk.boot.filter_interceptor.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("MyInterceptor preHandle: {}", requestURI);
        LocalDateTime beginTime = LocalDateTime.now();
        request.setAttribute("beginTime", beginTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LocalDateTime beginTime = (LocalDateTime) request.getAttribute("beginTime");
        log.info("begin Time: {}", beginTime);

        // 计算耗时
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(beginTime, endTime);
        long millis = duration.toMillis();
        log.info("接口耗时: {} 毫秒", millis);

        log.info("拦截器执行结束: {}, {}", request.getRequestURI(), endTime);
    }
}