package top.kunjz.filterinterceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义权限注解：用于标注接口所需角色
@Target({ElementType.METHOD}) // 仅作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface RequireRole {
    String[] value(); // 所需角色数组（支持多个）
}