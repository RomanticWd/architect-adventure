package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 读取properties属性文件工具类
 * @createTime 2020/7/21 22:29
 */
@Slf4j
public final class PropsUtil {

    private PropsUtil() {
        log.error("工具类无法实例化");
    }

    /**
     * @return Properties
     * @description 加载属性文件
     * @date: 2020/7/21
     */
    public static Properties loadProps(String fileName) {
        Properties prop = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            prop = new Properties();
            prop.load(is);
        } catch (IOException e) {
            log.error("properties文件加载失败", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("关闭输入流失败", e);
                }
            }
        }
        return prop;
    }

    /**
     * @return String
     * @description 获取字符型属性（默认为空字符串）
     * @date: 2020/7/21
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, StringUtils.EMPTY);
    }

    /**
     * @return String
     * @description 获取字符型属性（指定默认值）
     * @date: 2020/7/21
     */
    private static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * @return int
     * @description 获取数值型属性（默认为0）
     * @date: 2020/7/21
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * @return int
     * @description 获取数值型属性（指定默认值）
     * @date: 2020/7/21
     */
    private static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * @return boolean
     * @description 获取布尔型属性（默认为false）
     * @date: 2020/7/21
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * @return boolean
     * @description 获取布尔型属性（可指定默认值）
     * @date: 2020/7/21
     */
    private static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
