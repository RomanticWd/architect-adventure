package site.lgong.plugin.security;

import site.lgong.framework.helper.ConfigHelper;
import site.lgong.framework.utils.ReflectionUtil;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 从配置文件中获取相关属性
 * @createTime 2020/9/21 22:15
 */
public final class SecurityConfig {

    public static String getRealms() {
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static SmartSecurity getSmartSecurity() {
        String className = ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
        return (SmartSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRolesQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionQuery() {
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSIONS_QUERY);
    }

    public static boolean isCacheable() {
        return ConfigHelper.getBoolean(SecurityConstant.CACHE);
    }

}
