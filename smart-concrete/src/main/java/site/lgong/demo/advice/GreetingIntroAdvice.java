package site.lgong.demo.advice;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import site.lgong.demo.service.Apology;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 引入增强
 * @createTime 2020/7/29 22:33
 */
public class GreetingIntroAdvice extends DelegatingIntroductionInterceptor implements Apology {
    @Override
    public void saySorry(String name) {
        System.out.println("Sorry! " + name);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return super.invoke(mi);
    }
}
