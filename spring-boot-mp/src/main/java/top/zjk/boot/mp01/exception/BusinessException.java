package top.zjk.boot.mp.exception;

import lombok.Getter;
import top.zjk.boot.mp.exception.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}