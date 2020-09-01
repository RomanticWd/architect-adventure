package site.lgong.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import site.lgong.framework.bean.Handler;
import site.lgong.framework.bean.Param;
import site.lgong.framework.helper.BeanHelper;
import site.lgong.framework.helper.ConfigHelper;
import site.lgong.framework.helper.ControllerHelper;
import site.lgong.framework.utils.CodecUtil;
import site.lgong.framework.utils.ReflectionUtil;
import site.lgong.framework.utils.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 请求转发器
 * @createTime 2020/9/1 21:54
 */
//等价于在web.xml中配置servlet，urlPatterns等价于<url-pattern>标签，loadOnStartup 指定 Servlet 的加载顺序，等价于 <load-on-startup>标签
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 1. 初始化相关的Helper类
        HelperLoader.init();
        // 2. 获取ServletContext 用于注册servlet
        ServletContext servletContext = servletConfig.getServletContext();
        // 3. 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 3. 注册处理静态资源的默认servlet
        ServletRegistration defalutServlet = servletContext.getServletRegistration("default");
        jspServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法和请求路径
        String requestMethod = requset.getMethod().toLowerCase();
        String requestPath = requset.getPathInfo();
        //获取action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            //获取controller及其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = requset.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = requset.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(requset.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = StringUtils.splitByWholeSeparator(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtils.splitByWholeSeparator(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            //调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //根据返回值类型View返回jsp页面或者Data返回json数据。。。
        }
    }
}
