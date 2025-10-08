package top.kunjz.filterinterceptor.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    // 从配置文件读取JWT秘钥和过期时间
    @Value("${jwt.secret:defaultSecret1234567890}")
    private String secret;

    @Value("${jwt.expires-in:3600}")
    private long expiresIn;

    // 获取签名密钥
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 生成JWT令牌（含用户名和角色）
    public String generateToken(String username, String userRole) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiresIn * 1000);

        return Jwts.builder()
                .setSubject(username) // 用户名（主题）
                .claim("userRole", userRole) // 自定义声明：用户角色
                .setIssuedAt(now) // 签发时间
                .setExpiration(expireDate) // 过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 签名算法
                .compact();
    }

    // 验证令牌有效性
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // 解析令牌，无效则抛出异常
            return true;
        } catch (Exception e) {
            log.error("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    // 从令牌中获取用户名
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    // 从令牌中获取用户角色
    public String getUserRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.get("userRole", String.class) : null;
    }

    // 从令牌中获取所有声明
    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析Token失败: {}", e.getMessage());
            return null;
        }
    }

    // 判断令牌是否过期
    public boolean isTokenExpired(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}