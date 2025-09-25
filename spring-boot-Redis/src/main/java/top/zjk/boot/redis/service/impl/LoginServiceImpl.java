package top.zjk.boot.redis.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zjk.boot.redis.cache.RedisCache;
import top.zjk.boot.redis.cache.RedisKeys;
import top.zjk.boot.redis.entity.LoginRequest;
import top.zjk.boot.redis.entity.LoginResponse;
import top.zjk.boot.redis.enums.ErrorCode;
import top.zjk.boot.redis.exception.ServerException;
import top.zjk.boot.redis.service.LoginService;
import top.zjk.boot.redis.utils.CommonUtils;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final RedisCache redisCache;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String phone = loginRequest.getPhone();
        String inputCode = loginRequest.getCode();

        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PHONE_ERROR);
        }

        if (inputCode == null || inputCode.trim().isEmpty()) {
            throw new ServerException("验证码不能为空");
        }

        String redisKey = RedisKeys.getSmsKey(phone);
        String redisCode = redisCache.get(redisKey).toString();

        if (redisCode == null) {
            throw new ServerException("验证码已过期或不存在");
        }

        if (!inputCode.equals(redisCode)) {
            throw new ServerException("验证码错误");
        }

        redisCache.delete(redisKey);
        String token = generateToken(phone);
        log.info("用户 {} 登录成功", phone);
        return new LoginResponse(token, phone);
    }

    private String generateToken(String phone) {
        return UUID.randomUUID().toString().replace("-", "") + phone.hashCode();
    }
}