package site.lgong.demo.service;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 定义一个序列号生成器，会有多个线程并发访问，保证每个线程得到的序列号都是自增的
 * @createTime 2020/8/12 21:49
 */
public interface Sequence {

    int getNumber();

}
