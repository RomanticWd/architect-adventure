package site.lgong.framework.proxy;

import lombok.extern.slf4j.Slf4j;
import site.lgong.framework.annotation.Transaction;
import site.lgong.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 事务代理
 * @createTime 2020/8/24 22:55
 */
@Slf4j
public class TransactionProxy implements Proxy {

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        //如果增加了事务注解，且当前线程的事务控制相关逻辑没有在执行中， 注意这里是同一线程中。
        /* TODO 同一个线程中会存在多个事务吗，如果存在下面的if判断就会有问题，会导致其他加了transaction注解的方法却没有有事务的执行sql*/
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.debug("开启事务");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                log.debug("提交事务");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.debug("回滚事务");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
