package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;
import site.lgong.framework.common.ConfigConstant;

import java.util.Properties;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 属性文件助手类
 * @createTime 2020/7/20 23:42
 */
@Slf4j
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * @description 获取jdbc驱动
     * @date: 2020/7/21
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * @description 获取jdbc URL
     * @date: 2020/7/21
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * @description 获取jdbc用户名
     * @date: 2020/7/21
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * @description 获取jdbc密码
     * @date: 2020/7/21
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * @description 获取应用基础包名
     * @date: 2020/7/21
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }
}
