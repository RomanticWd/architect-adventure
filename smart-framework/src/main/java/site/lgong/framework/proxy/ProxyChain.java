package site.lgong.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 代理链，将多个代理通过一条链子穿起来，一个个的去执行，执行顺序取决于添加到链上的先后顺序
 * @createTime 2020/8/3 22:35
 */
public class ProxyChain {

    /**
     * 目标类
     */
    private final Class<?> targetClass;
    /**
     * 目标对象
     */
    private final Object targetObject;
    /**
     * 目标方法
     */
    private final Method targetMethod;
    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;
    /**
     * 方法参数
     */
    private final Object[] methodParams;

    /**
     * 代理列表
     */
    private List<Proxy> proxyList = new ArrayList<>();
    /**
     * 代理索引
     */
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,
                      MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        //如果计数器proxyIndex未达到proxyList的上限，则从proxyList中取出相应的proxy对象，并调用doProxy方法
        if (proxyIndex < proxyList.size()) {
            //Proxy接口的实现类中会提供相应的横切逻辑，并调用doProxyChain方法
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            //最后调用methodProxy的invokeSuper，执行目标对象的业务逻辑
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }

}
