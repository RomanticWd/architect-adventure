package site.lgong.demo.service;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 道歉接口
 * @createTime 2020/7/29 22:27
 */
public interface Apology {

    /**
     * @description 不希望让GreetingImpl直接实现这个接口，而是想在程序执行的时候动态的实现它，因为直接实现还需要改写GreetingImpl这个类
     * @date: 2020/7/29
     */
    void saySorry(String name);

}
