package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 反射工具类，用于将ClassHelper中加载的类实例化为对象
 * @createTime 2020/7/22 21:55
 */
@Slf4j
public final class ReflectionUtil {

    private ReflectionUtil() {
        log.error("工具类无法实例化");
    }

    /**
     * @return Object
     * @description 创造实例化对象
     * @date: 2020/7/22
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("实例化对象失败", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * @return Object
     * @description 创建实例（根据类名）
     * @date: 2020/9/21
     */
    public static Object newInstance(String className) {
        Class<?> cls = ClassUtil.loadClass(className);
        return newInstance(cls);
    }

    /**
     * @return Object
     * @description 调用对象的指定方法
     * @date: 2020/7/22
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            //调用对象的某个方法
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("调用方法失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * @return Object
     * @description 设定成员变量的值
     * @date: 2020/7/22
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            //设定对象的值
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("设定对象值失败", e);
            throw new RuntimeException(e);
        }
    }
}
