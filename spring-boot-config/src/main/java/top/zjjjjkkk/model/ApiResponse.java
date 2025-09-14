package top.zjjjjkkk.model;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String msg, T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.success = true;
        resp.message = msg;
        resp.data = data;
        return resp;
    }

    public static <T> ApiResponse<T> error(String msg) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.success = false;
        resp.message = msg;
        return resp;
    }
}