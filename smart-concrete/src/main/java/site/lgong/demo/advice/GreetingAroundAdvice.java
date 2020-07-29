package site.lgong.demo.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description spring中利用编程式实现环绕增强
 * @createTime 2020/7/29 22:08
 */
public class GreetingAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        before();
        Object result = invocation.proceed();
        after();
        return result;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
