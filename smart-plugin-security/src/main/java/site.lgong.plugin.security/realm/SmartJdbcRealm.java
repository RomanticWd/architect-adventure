package site.lgong.plugin.security.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import site.lgong.framework.helper.DatabaseHelper;
import site.lgong.plugin.security.SecurityConfig;
import site.lgong.plugin.security.password.Md5CredentialsMatcher;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 基于Smart的JDBC Realm(需要提供相关 smart.plugin.security.jdbc.* 配置项)
 * @createTime 2020/10/9 22:35
 */
public class SmartJdbcRealm extends JdbcRealm {
    public SmartJdbcRealm() {
        super.setDataSource(DatabaseHelper.getDataSource());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthQuery());
        super.setUserRolesQuery(SecurityConfig.getJdbcRolesQuery());
        super.setPermissionsQuery(SecurityConfig.getJdbcPermissionQuery());
        super.setPermissionsLookupEnabled(true);
        super.setCredentialsMatcher(new Md5CredentialsMatcher());
    }
}
