package site.lgong.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import site.lgong.framework.utils.CodecUtil;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description MD5密码匹配器
 * @createTime 2020/10/9 22:47
 */
public class Md5CredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取从表单提交过来的密码、明文，尚未通过MD5加密
        String submitted = String.valueOf(((UsernamePasswordToken) token).getPassword());
        // 获取数据库中存储的密码，已通过MD5加密
        String encrypted = String.valueOf(info.getCredentials());
        return CodecUtil.md5(submitted).equals(encrypted);
    }
}
