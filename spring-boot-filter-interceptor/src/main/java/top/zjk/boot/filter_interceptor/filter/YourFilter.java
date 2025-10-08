package top.zjk.boot.filter_interceptor.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: zjk
 * @Date: 2025/9/26
 */
@Slf4j
public class YourFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("YourFilter 初始化完成");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 请求处理前的逻辑
        log.info("YourFilter 开始处理请求");
        // 继续执行过滤器链
        filterChain.doFilter(servletRequest, servletResponse);
        // 请求处理后的逻辑
        log.info("YourFilter 完成请求处理");
    }

    @Override
    public void destroy() {
        log.info("YourFilter 已销毁");
    }
}
