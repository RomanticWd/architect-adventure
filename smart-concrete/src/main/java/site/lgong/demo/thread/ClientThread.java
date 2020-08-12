package site.lgong.demo.thread;

import site.lgong.demo.service.Sequence;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 线程类
 * @createTime 2020/8/12 21:51
 */
public class ClientThread extends Thread {

    private Sequence sequence;

    public ClientThread(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        //连续三次输出线程名与其对应的序列号
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + " => " + sequence.getNumber());
        }
    }
}
