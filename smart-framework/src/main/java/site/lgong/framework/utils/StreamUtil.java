package site.lgong.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 流操作工具类
 * @createTime 2020/9/1 22:18
 */
@Slf4j
public final class StreamUtil {

    /**
     * @return String
     * @description 从输入流中获取字符串
     * @date: 2020/9/1
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("读取流失败", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
