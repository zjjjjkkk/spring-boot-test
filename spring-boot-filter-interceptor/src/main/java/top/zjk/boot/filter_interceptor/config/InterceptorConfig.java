package top.zjk.boot.filter_interceptor.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.zjk.boot.filter_interceptor.interceptor.MyInterceptor;

@Configuration
@Slf4j
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/test");
    }
}