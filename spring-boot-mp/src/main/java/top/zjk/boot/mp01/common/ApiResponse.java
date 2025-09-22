package top.zjk.boot.mp01.common;

import lombok.Getter;

/**
 * @author zjk
 */
@Getter
public class ApiResponse<T> {

    private final int code;
    private final String msg;
    private final T data;

    /* ---------- 私有构造器 ---------- */
    private ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /* ---------- 成功响应 ---------- */
    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(200, msg, data);
    }

    public static <T> ApiResponse<T> success(String msg) {
        return success(msg, null);
    }

    /* ---------- 失败响应 ---------- */
    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(400, msg, null);
    }

    /* 可按需扩展更多错误码重载 */
    public static <T> ApiResponse<T> error(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }
}