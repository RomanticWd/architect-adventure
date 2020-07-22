package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import site.lgong.framework.annotation.Inject;
import site.lgong.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 依赖注入助手类，从beanHelper中获取bean Map，并进行实例化，由于bean Map是提前创建好的，所有对象都是默认单例的
 * @createTime 2020/7/22 22:23
 */
@Slf4j
public final class IocHelper {

    static {
        //获取所有的bean类与bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            //遍历所有的Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从beanMap中获取bean类
                Class<?> beanClass = beanEntry.getKey();
                //从beanMap中bean实例
                Object beanInstance = beanEntry.getValue();
                //获取bean类的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历bean的成员变量
                    for (Field beanField : beanFields) {
                        //如果带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //获取成员变量的类
                            Class<?> beanFieldClass = beanField.getType();
                            //获取该成员变量对应的实例
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值，即给beanInstance对象的beanField字段初始化对象
                                //通过for循环，将所有bean中Inject注解标注的对象全部实例化，而不需要开发者自己通过new实例化
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
