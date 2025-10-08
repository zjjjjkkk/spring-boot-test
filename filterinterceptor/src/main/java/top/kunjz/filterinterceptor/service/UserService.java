package top.kunjz.filterinterceptor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kunjz.filterinterceptor.dto.LoginRequest;
import top.kunjz.filterinterceptor.entity.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    // 模拟数据库（静态集合存储用户）
    private static final Set<User> MOCK_USERS = new HashSet<>();

    // 初始化测试用户（ADMIN和USER角色）
    static {
        MOCK_USERS.add(new User(1L, "admin", "123456", "admin@test.com", "13800000001", "ADMIN"));
        MOCK_USERS.add(new User(2L, "user", "123456", "user@test.com", "13800000002", "USER"));
    }

    // 用户登录认证，接收LoginRequest参数
    public User authenticate(LoginRequest request) {
        log.info("用户登录认证 - 用户名: {}", request.getUsername());
        return MOCK_USERS.stream()
                .filter(u -> request.getUsername().equals(u.getUsername())      // ✅ 非 null 在前
                        && request.getPassword().equals(u.getPassword()))   // ✅ 非 null 在前
                .findFirst()
                .orElse(null);
    }
    // 根据用户名查询用户
    public User getUserByUsername(String username) {
        return MOCK_USERS.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}