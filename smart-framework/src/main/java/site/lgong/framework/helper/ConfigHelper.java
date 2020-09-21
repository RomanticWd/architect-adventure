package site.lgong.framework.helper;

import lombok.extern.slf4j.Slf4j;
import site.lgong.framework.common.ConfigConstant;
import site.lgong.framework.utils.PropsUtil;

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

    /**
     * @description 获取应用jsp路径
     * @date: 2020/9/1
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH);
    }

    /**
     * @description 获取应用静态资源路径
     * @date: 2020/9/1
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
    }

    /**
     * @param key
     * @description 根据属性名获取 String 类型的属性值
     * @date: 2020/9/21
     */
    public static String getString(String key) {
        return PropsUtil.getString(CONFIG_PROPS, key);
    }

    /**
     * @param key
     * @description 根据属性名获取 int 类型的属性值
     * @date: 2020/9/21
     */
    public static int getInt(String key) {
        return PropsUtil.getInt(CONFIG_PROPS, key);
    }

    /**
     * @param key
     * @description 根据属性名获取 boolean 类型的属性值
     * @date: 2020/9/21
     */
    public static boolean getBoolean(String key) {
        return PropsUtil.getBoolean(CONFIG_PROPS, key);
    }
}
