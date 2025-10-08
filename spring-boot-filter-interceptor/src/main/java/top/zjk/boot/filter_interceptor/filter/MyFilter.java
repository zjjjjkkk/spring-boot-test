package top.zjk.boot.filter_interceptor.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: zjk
 * @Date: 2025/9/26
 */
@Slf4j
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 在请求接口之前执行的逻辑
        log.info("MyFilter 在请求接口之前执行的逻辑");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        // 在请求接口之后执行的逻辑
        log.info("MyFilter 在请求接口之后执行的逻辑");
    }

    @Override
    public void destroy() {
        log.info("MyFilter 销毁了");
    }
}