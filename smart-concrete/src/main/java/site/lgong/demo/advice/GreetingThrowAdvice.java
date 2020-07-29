package site.lgong.demo.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 抛出增强
 * @createTime 2020/7/29 22:14
 */
public class GreetingThrowAdvice implements ThrowsAdvice {

    /**
     * @description 利用抛出增强，可以将信息统一写入到日志或持久化到数据库中
     * @date: 2020/7/29
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        System.out.println("------------ Throw exception ----------");
        System.out.println("Target Class: " + target.getClass().getName());
        System.out.println("Method Name: " + method.getName());
        System.out.println("Exception Message: " + e.getMessage());
    }

}
