package top.zjk.boot.redis.service;

import top.zjk.boot.redis.entity.LoginRequest;
import top.zjk.boot.redis.entity.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}