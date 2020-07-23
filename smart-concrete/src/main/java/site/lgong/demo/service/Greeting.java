package site.lgong.demo.service;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 问候接口，现在希望在调用sayHello之前与之后可以进行一些操作
 * @createTime 2020/7/23 22:42
 */
public interface Greeting {

    void sayHello(String name);

}
