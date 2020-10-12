package site.lgong.plugin.security;

import java.util.Set;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 提供SmartSecurity接口给开发者实现，完成认证和授权的操作，用户不实现接口的话还可在config.properties文件中提供配置项进行实现
 * @createTime 2020/9/21 22:47
 */
public interface SmartSecurity {

    /**
     * @return String
     * @description 根据用户名获取密码
     * @date: 2020/9/21
     */
    String getPassword(String username);

    /**
     * @return String
     * @description 根据用户名获取角色名集合
     * @date: 2020/9/21
     */
    Set<String> getRoleNameSet(String username);

    /**
     * @return String
     * @description 根据用户名获取权限名集合
     * @date: 2020/9/21
     */
    Set<String> getPermissionNameSet(String username);

}
