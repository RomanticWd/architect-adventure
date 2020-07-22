package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import site.lgong.framework.annotation.Action;
import site.lgong.framework.bean.Handler;
import site.lgong.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 控制器助手类
 * @createTime 2020/7/22 22:43
 */
@Slf4j
public final class ControllerHelper {

    /**
     * 存放请求与处理器之间的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //获取所有的controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            //遍历所有的controller
            for (Class<?> controllerClass : controllerClassSet) {
                //获取controller中的所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    //遍历所有的方法
                    for (Method method : methods) {
                        //方法是否带有action注解
                        if (method.isAnnotationPresent(Action.class)) {
                            //获取action注解
                            Action action = method.getAnnotation(Action.class);
                            //获取action参数中的url映射规则
                            String mapping = action.value();
                            //验证mapping中的url规则是否满足规则：”method:/url“
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @return Handler
     * @description 通过请求method和请求url获取handler，从而知道是哪个controller中的哪个方法
     * @date: 2020/7/22
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
