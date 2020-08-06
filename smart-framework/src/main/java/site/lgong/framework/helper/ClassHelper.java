package site.lgong.framework.helper;

import site.lgong.framework.annotation.Controller;
import site.lgong.framework.annotation.Service;
import site.lgong.framework.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 类操作助手
 * @createTime 2020/7/22 0:04
 */
public final class ClassHelper {

    /**
     * 定义的类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        //properties文件中定义的基础包名
        String basePackage = ConfigHelper.getAppBasePackage();
        //加载基础包名下的所有类
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下的所有类
     * @date: 2020/7/22
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下的所有Service类
     * @date: 2020/7/2
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下的所有Controller类
     * @date: 2020/7/2
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(Controller.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下的所有Bean类(包括Service 、 Controller)
     * @date: 2020/7/2
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下某父类（或接口）的所有子类（或实现类）
     * @date: 2020/8/6
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取应用包下带有某注解的所有类
     * @date: 2020/8/6
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

}
