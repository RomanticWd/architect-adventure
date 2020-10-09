package site.lgong.plugin.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import site.lgong.plugin.security.realm.SmartCustomRealm;
import site.lgong.plugin.security.realm.SmartJdbcRealm;

import java.util.LinkedHashSet;
import java.util.Set;

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
        String securityRealms = SecurityConfig.getRealms();
        if (StringUtils.isNotBlank(securityRealms)) {
            String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0) {
                //使Realm具有唯一性与顺序性
                Set<Realm> realms = new LinkedHashSet<>();
                for (String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        //添加基于jdbc的realm，需要配置相关sql查询语句
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        //添加基于定制化的realm，需要实现SmartSecurity接口
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager = (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addJdbcRealm(Set<Realm> realms) {
        //添加自己实现的基于JDBC的reaml
        SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
        realms.add(smartJdbcRealm);
    }

    private void addCustomRealm(Set<Realm> realms) {
        //读取smart.plugin.security.custom.class配置项，配置项指定了SmartSecurity接口的实现类
        SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
        //添加自己实现的reaml
        SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
        realms.add(smartCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager) {
        //读取smart.plugin.security.cache配置项
        if (SecurityConfig.isCacheable()) {
            CachingSecurityManager cachingSecurityManager = (CachingSecurityManager) webSecurityManager;
            //使用基于内存的CacheManager
            CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
