package top.kunjz.filterinterceptor.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter 初始化...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 转换为HttpServletRequest获取更多信息
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        String ip = httpRequest.getRemoteAddr();

        // 拼接请求参数
        StringBuilder params = new StringBuilder();
        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            params.append(name).append("=").append(httpRequest.getParameter(name)).append("&");
        }
        String paramStr = !params.isEmpty() ? params.substring(0, params.length() - 1) : "";

        log.info("请求开始 - URL: {}, 方法: {}, IP: {}, 参数: {}", url, method, ip, paramStr);

        // 记录请求耗时
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response); // 放行请求
        long endTime = System.currentTimeMillis();

        log.info("请求结束 - URL: {}, 耗时: {}ms", url, endTime - startTime);
    }

    @Override
    public void destroy() {
        log.info("LogFilter 销毁...");
    }
}