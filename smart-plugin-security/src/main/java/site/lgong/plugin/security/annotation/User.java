package site.lgong.plugin.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 判断当前用户是否已登陆（包括：已认证与已记住）
 * @createTime 2020/10/11 13:41
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {
}
