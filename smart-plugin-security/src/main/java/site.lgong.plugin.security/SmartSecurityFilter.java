package site.lgong.plugin.security;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 安全过滤器
 * @createTime 2020/9/21 22:12
 */
public class SmartSecurityFilter extends ShiroFilter {
    @Override
    public void init() throws Exception {
        super.init();
        WebSecurityManager webSecurityManager = super.getSecurityManager();
        //设置Realm，可同时支持多个Realm，并按照先后顺序用逗号分隔
        setRealms(webSecurityManager);
        //设置Cache，用于减少数据库的查询次数，降低I/O访问
        setCache(webSecurityManager);

    }

    private void setRealms(WebSecurityManager webSecurityManager) {
        //读取smart.plugin.security.realms配置项

    }

    private void setCache(WebSecurityManager webSecurityManager) {
        //读取smart.plugin.security.cache配置项


    }
}
