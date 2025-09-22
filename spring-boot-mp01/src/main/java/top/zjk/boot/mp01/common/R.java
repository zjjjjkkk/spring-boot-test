package top.zjk.boot.mp01.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zjk
 */
@Data @AllArgsConstructor
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok(T data) { return new R<>(200, "success", data); }
    public static <T> R<T> ok() { return ok(null); }
    public static <T> R<T> fail(String msg) { return new R<>(400, msg, null); }
}