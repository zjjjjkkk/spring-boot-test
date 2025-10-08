package top.kunjz.filterinterceptor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 登录响应结果
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long userId;
    private String username;
    private String role;
    private String token; // JWT令牌
}