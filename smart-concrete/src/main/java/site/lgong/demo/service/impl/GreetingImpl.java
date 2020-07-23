package site.lgong.demo.service.impl;

import site.lgong.demo.service.Greeting;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 问候接口的实现类
 * @createTime 2020/7/23 22:43
 */
public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String name) {
        System.out.println("hello!" + name);
    }
}
