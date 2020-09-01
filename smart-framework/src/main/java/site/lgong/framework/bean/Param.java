package site.lgong.framework.bean;

import site.lgong.framework.utils.CastUtil;

import java.util.Map;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 请求参数对象
 * @createTime 2020/9/1 22:24
 */

public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * @return long
     * @description 根据参数名获取long型参数值
     * @date: 2020/9/1
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * @return Map<String, Object>
     * @description 获取所有字段信息
     * @date: 2020/9/1
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }
}
