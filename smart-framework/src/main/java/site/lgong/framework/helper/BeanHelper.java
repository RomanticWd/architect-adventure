package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;
import site.lgong.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description Bean助手类，用于获取框架管理的所有bean类,根据getBean方法传入一个bean类，就能获取到bean实例
 * @createTime 2020/7/22 22:08
 */
@Slf4j
public final class BeanHelper {

    //存放所有的bean类与bean实例映射关系
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        //获取应用包下的所有类
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        //将所有的类创建实例化对象，并存放到map中
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * @return Map<Class < ?>, Object>
     * @description 获取所有的bean映射
     * @date: 2020/7/22
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * @return Map<Class < ?>, Object>
     * @description 根据class获取bean实例
     * @date: 2020/7/22
     */
    @SuppressWarnings("unchecked") //注解用于抑制编译器产生警告信息
    public static <T> T getBean(Class<?> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("无法获取" + cls + "的bean对象");
        }
        return (T) BEAN_MAP.get(cls);
    }

}
