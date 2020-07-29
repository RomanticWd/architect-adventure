package site.lgong.demo;

import org.springframework.aop.framework.ProxyFactory;
import site.lgong.demo.advice.GreetingAroundAdvice;
import site.lgong.demo.advice.GreetingBeforeAndAfterAdvice;
import site.lgong.demo.advice.GreetingThrowAdvice;
import site.lgong.demo.service.Greeting;
import site.lgong.demo.service.impl.GreetingImpl;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 编程式增强的测试类
 * @createTime 2020/7/29 22:20
 */
public class AdviceTest {
    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new GreetingImpl());
        //测试spring aop的编程式前置增强，后置增强
        proxyFactory.addAdvice(new GreetingBeforeAndAfterAdvice());
        //测试spring aop的编程式环绕增强
        proxyFactory.addAdvice(new GreetingAroundAdvice());
        //测试spring aop的编程式抛出增强
        proxyFactory.addAdvice(new GreetingThrowAdvice());

        Greeting greeting = (Greeting) proxyFactory.getProxy();
        /*输出内容
        before
        before
        hello!java
        after
        after
        */
        greeting.sayHello("java");

        /*输出内容
        before
        before
        hello!java
        ------------ Throw exception ----------
        Target Class: site.lgong.demo.service.impl.GreetingImpl
        Method Name: sayHelloError
        Exception Message: Error
        */
        greeting.sayHelloError("java");
    }

}
