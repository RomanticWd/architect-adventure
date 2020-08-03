package site.lgong.framework.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 切面代理
 * @createTime 2020/8/3 22:58
 */
@Slf4j
public abstract class AspectProxy implements Proxy {

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            //如果有拦截，会调用before，after方法，如果子类对这两个方法进行了重写，就可以在目标方法执行前后添加其他需要执行的代码了
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                //调用doProxyChain方法中会判断计数器proxyIndex是否达到proxyList的上限，没有则在内部再次调用doProxy方法，再次到这个方法中来，最终拿出所有代理对象
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("代理失败", e);
            error(cls, method, params, e);
        } finally {
            end();
        }
        return result;
    }

    //下面的抽象方法可在AspectProxy的子类中有选择性的进行实现
    public void begin() {
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) throws Throwable {
    }

    public void end() {
    }
}
