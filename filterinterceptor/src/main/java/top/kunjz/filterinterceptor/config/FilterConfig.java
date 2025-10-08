package top.kunjz.filterinterceptor.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.kunjz.filterinterceptor.filter.CorsFilter;
import top.kunjz.filterinterceptor.filter.LogFilter;
import top.kunjz.filterinterceptor.filter.RateLimitFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class FilterConfig {
    private final LogFilter logFilter;
    private final RateLimitFilter rateLimitFilter;
    private final CorsFilter corsFilter;

    // 1. 跨域过滤器（order=0，最高优先级）
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(corsFilter);
        registration.setUrlPatterns(List.of("/*")); // 拦截所有请求
        registration.setOrder(0); // 跨域处理需最先执行
        return registration;
    }

    // 2. 日志过滤器（order=2）
    @Bean
    public FilterRegistrationBean<LogFilter> logFilterRegistration() {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registration.setUrlPatterns(urlPatterns);
        registration.setOrder(2);
        registration.addInitParameter("exclusions", "*.js,*.css,*.png");
        return registration;
    }

    // 3. 限流过滤器（order=3）
    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration() {
        FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(rateLimitFilter);
        registration.addUrlPatterns("/api/pay/*");
        registration.setOrder(3);
        return registration;
    }
}