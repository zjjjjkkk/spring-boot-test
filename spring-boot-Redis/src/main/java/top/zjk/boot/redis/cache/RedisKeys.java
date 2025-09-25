package top.zjk.boot.redis.cache;

public class RedisKeys {
    public static String getSmsKey(String phone) {
        return "sms:captcha:" + phone;
    }
}