package top.zjk.boot.redis.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 校验中国大陆手机号（修复正则表达式，确保正确匹配11位手机号）
     */
    public static boolean checkPhone(String phone) {
        // 第一步：先判空和长度（11位）
        if (phone == null || phone.trim().isEmpty() || phone.length() != 11) {
            return false;
        }
        // 第二步：正则表达式（修复空格问题，正确匹配13-9开头的11位数字）
        String regex = "^1[3-9]\\d{9}$"; // 原代码可能因"1[3 - 9]"中间多空格导致正则失效
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    // 生成4位验证码方法（保持不变）
    public static int generateCode() {
        return ThreadLocalRandom.current().nextInt(1000, 9999);
    }
}