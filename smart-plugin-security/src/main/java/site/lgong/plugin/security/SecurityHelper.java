package site.lgong.plugin.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description Security助手类
 * @createTime 2020/10/11 13:06
 */
@Slf4j
public final class SecurityHelper {

    /**
     * @description 登陆
     * @date: 2020/10/1
     */
    public static void login(String username, String password) {
        //获取当前登陆的用户对象
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            UsernamePasswordToken token = new UsernamePasswordToken();
            try {
                currentUser.login(token);
            } catch (AuthenticationException e) {
                log.error("登陆失败", e);
                throw new AuthenticationException(e);
            }
        }
    }

    /**
     * @description 注销
     * @date: 2020/10/1
     */
    public static void logout(String username, String password) {
        //获取当前登陆的用户对象
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
    }

}
