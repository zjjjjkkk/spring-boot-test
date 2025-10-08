package top.kunjz.filterinterceptor.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;

@Slf4j
@Component
public class BusinessLogInterceptor implements HandlerInterceptor, ResponseBodyAdvice<Object> {
    private String methodName; // 记录当前执行的控制器方法名
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 控制器执行前：记录方法名
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof org.springframework.web.method.HandlerMethod handlerMethod) {
            methodName = handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName();
            log.info("业务日志 - 执行方法: {}", methodName);
        }
        return true;
    }

    // 响应返回前：记录返回结果
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // 对所有响应生效
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null) {
            log.info("业务日志 - 方法: {}, 返回结果: {}", methodName, objectMapper.writeValueAsString(body));
        }
        return body;
    }
}