package top.zjk.boot.exception.exception;

import top.zjk.boot.exception.enums.ErrorCode;

public class BusinessException extends RuntimeException {
    private int code;
    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.code = ErrorCode.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}