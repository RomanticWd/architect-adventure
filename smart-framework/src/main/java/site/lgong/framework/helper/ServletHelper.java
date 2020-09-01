package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description Servlet助手类
 * @createTime 2020/9/1 22:33
 */
@Slf4j
public final class ServletHelper {

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @description 初始化
     * @date: 2020/9/1
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * @description 销毁
     * @date: 2020/9/1
     */
    public static void destroy() {
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * @description 获取Request对象
     * @date: 2020/9/1
     */
    private static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * @description 获取Response对象
     * @date: 2020/9/1
     */
    private static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * @description 获取Session对象
     * @date: 2020/9/1
     */
    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * @description 获取ServletContext对象
     * @date: 2020/9/1
     */
    private static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }
}
