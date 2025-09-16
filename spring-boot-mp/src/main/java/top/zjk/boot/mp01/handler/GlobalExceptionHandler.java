package top.zjk.boot.mp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zjk.boot.mp.common.ApiResponse;
import top.zjk.boot.mp.exception.BusinessException;
import top.zjk.boot.mp.exception.ErrorCode;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /** 业务异常 */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusiness(BusinessException e) {
        return ApiResponse.error(400, e.getMessage());
    }

    /** 参数校验异常 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ApiResponse.error(ErrorCode.PARAM_ERROR.getCode(), msg);
    }

    /** 其他异常 */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleAll(Exception e) {
        log.error("系统异常", e);
        return ApiResponse.error(ErrorCode.SERVER_ERROR.getMsg());
    }
}