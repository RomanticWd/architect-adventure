package site.lgong.demo.service.impl;

import site.lgong.demo.service.Sequence;
import site.lgong.demo.thread.ClientThread;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 多线程共享static变量测试
 * @createTime 2020/8/12 21:54
 */
public class SequenceA implements Sequence {

    //线程之间共享的static变量无法保证对于不同线程而言是安全的，即不是线程安全的
    private static int number = 0;

    @Override
    public int getNumber() {
        number = number + 1;
        return number;
    }

    public static void main(String[] args) {
        Sequence sequence = new SequenceA();
        ClientThread thread1 = new ClientThread(sequence);
        ClientThread thread2 = new ClientThread(sequence);
        ClientThread thread3 = new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();

        //输出结果
        /*Thread-1 => 2
        Thread-1 => 3
        Thread-1 => 4
        Thread-2 => 5
        Thread-2 => 6
        Thread-2 => 7
        Thread-0 => 1
        Thread-0 => 8
        Thread-0 => 9*/

        //线程的启动顺序是随机的，所以打印顺序并不是标准升序，而static变量无法保证对于不同线程而言是安全的，所以不是每个线程都是从0开始打印。
    }
}
