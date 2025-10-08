package top.kunjz.filterinterceptor.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.kunjz.filterinterceptor.result.Result;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

// 全局参数校验拦截器（处理POST JSON参数）
@Component
public class ParamValidateInterceptor implements HandlerInterceptor {
    // 初始化JSR-303校验器（单例）
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 仅处理POST请求且Content-Type为application/json
        if ("POST".equalsIgnoreCase(request.getMethod())
                && "application/json".equals(request.getContentType())) {

            // 确保是控制器方法
            if (handler instanceof HandlerMethod handlerMethod) {
                // 读取请求体（使用包装类缓存流，避免控制器无法再次读取）
                CachedBodyRequest cachedRequest = new CachedBodyRequest(request);
                String requestBody = cachedRequest.getBody();

                if (StringUtils.hasText(requestBody)) {
                    // 获取方法的@RequestBody参数类型
                    Type[] genericParameterTypes = handlerMethod.getMethod().getGenericParameterTypes();
                    Class<?> paramType = null;
                    for (Type type : genericParameterTypes) {
                        if (type instanceof ParameterizedType parameterizedType &&
                                parameterizedType.getRawType().equals(jakarta.servlet.http.HttpServletRequest.class)) {
                            continue;
                        }
                        paramType = (Class<?>) type;
                        break;
                    }

                    if (paramType != null) {
                        // 转换为对应的DTO类型
                        Object dto = objectMapper.readValue(requestBody, paramType);

                        // 执行参数校验
                        Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(dto);
                        if (!violations.isEmpty()) {
                            // 拼接错误信息
                            String errorMsg = violations.stream()
                                    .map(ConstraintViolation::getMessage)
                                    .collect(Collectors.joining("; "));

                            // 返回校验失败响应
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(objectMapper.writeValueAsString(Result.error(400, errorMsg)));
                            return false;
                        }
                    }
                }
            }
        }

        // 校验通过：放行
        return true;
    }

    // 缓存请求体的包装类（解决流只能读取一次的问题）
    static class CachedBodyRequest extends HttpServletRequestWrapper {
        private final String body;

        public CachedBodyRequest(HttpServletRequest request) throws IOException {
            super(request);
            // 读取流并缓存
            try (InputStream is = request.getInputStream()) {
                byte[] bytes = is.readAllBytes();
                this.body = new String(bytes);
            }
        }

        // 获取缓存的请求体
        public String getBody() {
            return body;
        }
    }
}