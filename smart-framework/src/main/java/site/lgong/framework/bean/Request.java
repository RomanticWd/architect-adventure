package site.lgong.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 封装请求信息
 * @createTime 2020/7/22 22:39
 */
@Data
@AllArgsConstructor
public class Request {
    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径
     */
    private String requestPath;

}
