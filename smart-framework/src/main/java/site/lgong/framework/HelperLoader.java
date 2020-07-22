package site.lgong.framework;

import site.lgong.framework.helper.BeanHelper;
import site.lgong.framework.helper.ClassHelper;
import site.lgong.framework.helper.ControllerHelper;
import site.lgong.framework.helper.IocHelper;
import site.lgong.framework.utils.ClassUtil;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 统一加载所有的helper类
 * @createTime 2020/7/22 22:59
 */
public final class HelperLoader {

    /**
     * @description 加载相应的helper
     * @date: 2020/7/2
     */
    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName(), false);
        }
    }

}
