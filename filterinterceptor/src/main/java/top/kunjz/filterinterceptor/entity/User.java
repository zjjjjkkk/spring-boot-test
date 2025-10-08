package top.kunjz.filterinterceptor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password; // 实际项目需加密存储
    private String email;
    private String phone;
    private String role; // 角色（ADMIN/USER）
}