package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 类型转换工具类
 * @createTime 2020/7/21 22:29
 */
@Slf4j
public final class CastUtil {

    private CastUtil() {
        log.error("工具类无法实例化");
    }

    /**
     * @return String
     * @description 转为string类型
     * @date: 2020/7/21
     */
    public static String castString(Object obj) {
        return castString(obj, StringUtils.EMPTY);
    }

    /**
     * @return String
     * @description 转为string类型(指定默认值)
     * @date: 2020/7/21
     */
    private static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * @return double
     * @description 转为double类型
     * @date: 2020/7/21
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * @return double
     * @description 转为double类型(指定默认值)
     * @date: 2020/7/21
     */
    private static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * @return long
     * @description 转为long类型
     * @date: 2020/7/21
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * @return long
     * @description 转为long类型(指定默认值)
     * @date: 2020/7/21
     */
    private static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * @return int
     * @description 转为int类型
     * @date: 2020/7/21
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * @return int
     * @description 转为int类型(指定默认值)
     * @date: 2020/7/21
     */
    private static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtils.isNotBlank(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * @return boolean
     * @description 转为boolean类型
     * @date: 2020/7/21
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * @return boolean
     * @description 转为boolean类型(指定默认值)
     * @date: 2020/7/21
     */
    private static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}