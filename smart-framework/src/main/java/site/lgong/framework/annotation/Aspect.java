package site.lgong.framework.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 切面注解
 * @createTime 2020/8/3 22:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解类，用来定义Controller这些注解
     */
    Class<? extends Annotation> value();
}
