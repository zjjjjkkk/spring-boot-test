package top.kunjz.filterinterceptor.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.kunjz.filterinterceptor.annotation.RequireRole;
import top.kunjz.filterinterceptor.util.JwtUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// 类名统一为 RoleAuthInterceptor（与文档要求一致）
@Slf4j
@Component
@AllArgsConstructor
public class RoleAuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 仅拦截控制器方法（非控制器请求直接放行）
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // 2. 检查方法是否需要权限（是否有@RequireRole注解）
        RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);
        if (requireRole == null) {
            return true; // 无注解：无需权限，直接放行
        }

        // 3. 从请求头获取Token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"请先登录（Token为空）\"}");
            return false;
        }

        // 4. 验证Token有效性（包含过期校验）
        if (!jwtUtil.validateToken(token) || jwtUtil.isTokenExpired(token)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"Token无效或已过期\"}");
            return false;
        }

        // 5. 权限校验（用户角色是否匹配）
        String userRole = jwtUtil.getUserRoleFromToken(token);
        List<String> requiredRoles = Arrays.asList(requireRole.value());
        if (!requiredRoles.contains(userRole)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":403,\"msg\":\"权限不足（所需角色：" + Arrays.toString(requireRole.value()) + "，当前角色：" + userRole + "）\"}");
            return false;
        }

        // 6. 权限通过：放行
        log.info("JWT认证通过 - 用户名: {}, 角色: {}", jwtUtil.getUsernameFromToken(token), userRole);
        return true;
    }
}