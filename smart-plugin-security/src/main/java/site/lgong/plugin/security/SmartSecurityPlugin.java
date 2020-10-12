package site.lgong.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
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
        servletContext.setInitParameter("shiroConfigLocation", "classpath:smart-security.ini");
        //注册Listener
        servletContext.addListener(EnvironmentLoaderListener.class);
        //注册Filter
        FilterRegistration.Dynamic smartSecurityFilter = servletContext.
                addFilter("SmartSecurityFilter", SmartSecurityFilter.class);
        smartSecurityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
