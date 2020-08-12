package site.lgong.demo.service.impl;

import site.lgong.demo.service.Sequence;
import site.lgong.demo.thread.ClientThread;
import site.lgong.demo.thread.MyThreadLocal;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 使用自定义的MyThreadLocal测试多线程访问
 * @createTime 2020/8/12 21:54
 */
public class SequenceC implements Sequence {

    //通过MyThreadLocal封装一个Integer类型的numberContainer静态变量，初始值是0
    private static MyThreadLocal<Integer> numberContainer = new MyThreadLocal() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    @Override
    public int getNumber() {
        numberContainer.set(numberContainer.get() + 1);
        return numberContainer.get();
    }

    public static void main(String[] args) {
        Sequence sequence = new SequenceC();
        ClientThread thread1 = new ClientThread(sequence);
        ClientThread thread2 = new ClientThread(sequence);
        ClientThread thread3 = new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();

        //输出结果
        /*Thread-0 => 1
        Thread-0 => 2
        Thread-0 => 3
        Thread-2 => 1
        Thread-1 => 1
        Thread-2 => 2
        Thread-2 => 3
        Thread-1 => 2
        Thread-1 => 3*/
    }
}
