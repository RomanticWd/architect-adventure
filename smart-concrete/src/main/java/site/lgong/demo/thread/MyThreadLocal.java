package site.lgong.demo.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 自己实现的一个ThreadLocal
 * @createTime 2020/8/12 22:08
 */
public class MyThreadLocal<T> {

    private Map<Thread, T> container = new ConcurrentHashMap<>();

    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if (value == null && !container.containsKey(thread)) {
            value = initialValue();
            container.put(thread, value);
        }
        return value;
    }

    public void remove() {
        container.remove(Thread.currentThread());
    }

    /**
     * @return T
     * @description 初始化 修饰符是protected为了提醒成绪垣这个方法需要实现，给线程局部变量设置一个初始值
     * @date: 2020/8/12
     */
    protected T initialValue() {
        return null;
    }
}
