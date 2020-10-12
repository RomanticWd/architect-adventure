package site.lgong.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

import java.util.Arrays;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 判断当前用户是否拥有其中所有的角色（逗号分割，表示‘与’的关系）
 * @createTime 2020/10/11 13:24
 */
public class HasAllRolesTag extends RoleTag {

    private static final String ROLE_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String roleNames) {
        boolean hasAllRole = false;
        //父类中的方法，获取当前登陆的用户对象
        Subject subject = getSubject();
        if (subject != null) {
            if (subject.hasAllRoles(Arrays.asList(roleNames.split(ROLE_NAMES_DELIMITER)))) {
                hasAllRole = true;
            }
        }
        return hasAllRole;
    }
}
