package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;
import site.lgong.framework.annotation.Aspect;
import site.lgong.framework.proxy.AspectProxy;
import site.lgong.framework.proxy.Proxy;
import site.lgong.framework.proxy.ProxyManager;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 方法拦截助手类
 * @createTime 2020/8/6 22:16
 */
@Slf4j
public final class AopHelper {

    //初始化AOP框架
    static {
        try {
            //获取代理类及其目标类集合之间的映射关系
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            //获取目标类和代理对象列表的映射关系
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            //遍历
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                //获取目标类
                Class<?> targetClass = targetEntry.getKey();
                //获取代理对象列表
                List<Proxy> proxyList = targetEntry.getValue();
                //获取代理对象
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                //将代理对象放入BeanMap中
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            log.error("aop 失败", e);
        }
    }

    /**
     * @return Set<Class < ?>>
     * @description 获取所有带有Aspect注解的类
     * @date: 2020/8/6
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        //获取Aspect注解中设置的注解类
        Class<? extends Annotation> annotation = aspect.value();
        //如果注解类不是Aspect，则调用getClassSetByAnnotation获取相关类
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * @return Map<Class < ?>, Set<Class<?>>>
     * @description 获取代理类及其目标类集合之间的映射关系，一个代理类对应一个或多个目标类
     * @date: 2020/8/6
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        //代理类及其目标类集合之间的映射关系
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        //获取所有扩展AspectProxy抽象类的代理类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            //如果带有Aspect注解的class
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * @return Map<Class < ?>, List<Proxy>>
     * @description 获取目标类和代理对象列表的映射关系
     * @date: 2020/8/6
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        //目标类和代理对象列表的映射关系
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            //获取所有代理类
            Class<?> proxyClass = proxyEntry.getKey();
            //获取所有目标类
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                //proxyClass继承了抽象类AspectProxy，而AspectProxy实现了Proxy接口, 所以可以通过proxyClass的instance方法实例化出代理对象
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    List<Proxy> proxyList = targetMap.get(targetClass);
                    proxyList.add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
