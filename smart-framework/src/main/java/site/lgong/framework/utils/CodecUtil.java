package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 编码与解码操作工具类
 * @createTime 2020/9/1 22:13
 */
@Slf4j
public final class CodecUtil {

    /**
     * @return String
     * @description 将URL编码
     * @date: 2020/9/1
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            log.error("url编码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * @return String
     * @description 将URL解码
     * @date: 2020/9/1
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            log.error("url解码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * MD5 加密
     */
    public static String md5(String source) {
        return DigestUtils.md5Hex(source);
    }
}
