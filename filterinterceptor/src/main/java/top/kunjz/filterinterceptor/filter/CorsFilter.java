package top.kunjz.filterinterceptor.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 跨域过滤器（全局处理跨域请求）
@Component
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 1. 允许的源（生产环境需指定具体域名，如"https://xxx.com"）
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // 2. 允许的请求方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // 3. 允许的请求头
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, token, userId");
        // 4. 允许携带凭证（如Cookie）
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        // 5. 预检请求缓存时间（秒）：减少OPTIONS请求次数
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // 6. 处理预检请求（OPTIONS方法）：直接返回200
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 7. 放行请求
        chain.doFilter(request, response);
    }
}