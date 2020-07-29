package site.lgong.demo;

import org.springframework.aop.framework.ProxyFactory;
import site.lgong.demo.advice.GreetingAroundAdvice;
import site.lgong.demo.advice.GreetingIntroAdvice;
import site.lgong.demo.service.Apology;
import site.lgong.demo.service.impl.GreetingImpl;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 引用增强的测试
 * @createTime 2020/7/29 22:39
 */
public class IntroAdviceTest {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();

        GreetingImpl greetingImpl = new GreetingImpl();

        proxyFactory.setTarget(greetingImpl);

        //测试spring aop的编程式引入增强
        proxyFactory.addAdvice(new GreetingIntroAdvice());
        proxyFactory.addAdvice(new GreetingAroundAdvice());

        //将目标类强制向上转型为apology接口（这就是引入增强给我们带来的特性，也就是“接口动态实现”）saySorry方法可以直接被greetingImpl对象调用
        Apology greeting = (Apology) proxyFactory.getProxy();

        /*输出内容
        hello! java
        Sorry! java
        */
        /*TODO 疑问点：当我debug模式启动的时候 打印内容如下
        before
        after
        hello!java
        Sorry! java
        */
        greetingImpl.sayHello("java");
        greeting.saySorry("java");
    }

}
