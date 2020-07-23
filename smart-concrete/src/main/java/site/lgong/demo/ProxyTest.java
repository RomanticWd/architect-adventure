package site.lgong.demo;

import site.lgong.demo.proxy.CGLibDynamicProxy;
import site.lgong.demo.proxy.GreetingProxy;
import site.lgong.demo.proxy.JDKDynamicProxy;
import site.lgong.demo.service.Greeting;
import site.lgong.demo.service.impl.GreetingImpl;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 测试代理方法
 * @createTime 2020/7/20 22:00
 */
public class ProxyTest {

    public static void main(String[] args) {

        //测试静态代理
        /*输出内容
        before
        hello!java
        after
        */
        Greeting greetingProxy = new GreetingProxy(new GreetingImpl());
        greetingProxy.sayHello("java");

        //测试JDK动态代理
        /*输出内容
        before
        hello!java
        after
        */
        Greeting greetingJDKProxy = new JDKDynamicProxy(new GreetingImpl()).getProxy();
        greetingJDKProxy.sayHello("java");

        //测试CGLib动态代理
        /*输出内容
        before
        hello!java
        after
        */
        Greeting greetingCGLibProxy = CGLibDynamicProxy.getInstance().getProxy(GreetingImpl.class);
        greetingCGLibProxy.sayHello("java");
    }

}
