package top.zjk.boot.redis.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.zjk.boot.redis.enums.ErrorCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException{
    private int code;
    private String msg;

    public ServerException(String msg) {
        this.code = ErrorCode.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public ServerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    // 原代码在此处定义 @ExceptionHandler，属于错位（应在 GlobalExceptionHandler 中），已删除
}