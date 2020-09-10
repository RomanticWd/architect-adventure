package site.lgong.plugin.security;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description Smart Security插件
 * @createTime 2020/9/10 23:00
 */
public class SmartSecurityPlugin implements ServletContainerInitializer {

    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //设置初始化参数
    }
}
