package top.kunjz.filterinterceptor.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TimeStatInterceptor implements HandlerInterceptor {
    private static final String START_TIME_KEY = "startTime";

    // 控制器执行前：记录请求开始时间
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_KEY, startTime);
        return true; // 放行请求
    }

    // 视图渲染后：计算并打印耗时
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long startTime = (long) request.getAttribute(START_TIME_KEY);
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        log.info("接口耗时 - 方法: {}, 耗时: {}ms", handler.getClass().getSimpleName(), costTime);
    }
}