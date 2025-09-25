package top.zjk.boot.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "未找到该资源"),
    UNAUTHORIZED(401, "登陆失败，请重新登录"),
    NOT_PERMISSION(403, "权限不足"),
    METHOD_ERROR(405, "方法不允许"),
    SERVER_ERROR(500, "服务器异常，请稍后再试"),
    CODE_SEND_FAIL(3001, "验证码发送失败"),
    SMS_CODE_ERROR(3002, "短信验证码错误"),
    PHONE_ERROR(3003, "手机号错误"); // 原代码为 SMS_CODE_EXPIRED，与含义不符，修正枚举项名
    private final int code;
    private final String msg;
}