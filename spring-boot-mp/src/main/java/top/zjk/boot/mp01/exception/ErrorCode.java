package top.zjk.boot.mp01.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    PARAM_ERROR(400, "参数参数"),
    UNAUTHORIZED(401,"登录失效，请重新登录"),
    NOT_FOUND(404,"查无此人！！！"),
    NOT_PERMISSION(403,"权限不足"),
    METHOD_ERROR(405,"方法不允许"),
    SERVER_ERROR(500,"服务器异常");
    private final int code;
    private final String msg;

}
