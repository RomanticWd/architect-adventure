package site.lgong.demo.proxy;

import site.lgong.demo.service.Greeting;
import site.lgong.demo.service.impl.GreetingImpl;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 静态代理
 * @createTime 2020/7/23 22:44
 */
public class GreetingProxy implements Greeting {

    private GreetingImpl greetingImpl;

    public GreetingProxy(GreetingImpl greetingImpl) {
        this.greetingImpl = greetingImpl;
    }

    @Override
    public void sayHello(String name) {
        before();
        greetingImpl.sayHello(name);
        after();
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
