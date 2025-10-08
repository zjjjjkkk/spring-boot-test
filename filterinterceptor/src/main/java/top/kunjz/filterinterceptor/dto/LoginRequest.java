package top.kunjz.filterinterceptor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 登录请求参数（JSR-303校验）
@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}