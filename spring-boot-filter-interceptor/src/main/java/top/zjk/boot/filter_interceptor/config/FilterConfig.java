package top.zjk.boot.filter_interceptor.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zjk.boot.filter_interceptor.filter.MyFilter;
import top.zjk.boot.filter_interceptor.filter.YourFilter;

/**
 * @author zjk
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> myFilterFilterRegistrationBean() {
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        // 设置拦截的URL
        registrationBean.addUrlPatterns("/*");
        // 设置过滤器的顺序
        registrationBean.setOrder(2);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<YourFilter> yourFilterRegistrationBean() {
        FilterRegistrationBean<YourFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new YourFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}