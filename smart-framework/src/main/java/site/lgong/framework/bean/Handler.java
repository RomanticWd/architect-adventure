package site.lgong.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 封装action信息
 * @createTime 2020/7/22 22:41
 */
@Getter
@AllArgsConstructor
public class Handler {

    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * action方法
     */
    private Method actionMethod;

}
