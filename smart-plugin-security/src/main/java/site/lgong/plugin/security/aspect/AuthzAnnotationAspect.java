package site.lgong.plugin.security.aspect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import site.lgong.framework.annotation.Aspect;
import site.lgong.framework.annotation.Controller;
import site.lgong.framework.proxy.AspectProxy;
import site.lgong.plugin.security.annotation.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 授权注解切面
 * @createTime 2020/10/11 13:43
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy {

    private static final Class[] ANNOTATION_CLASS_ARRAY = {
            User.class
    };

    // 前置增强，访问Controller注解修饰的类时候会需要校验权限
    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

        //从目标类和目标方法中获取相应的注解
        Annotation annotation = getAnnotation(cls, method);
        if (annotation != null) {
            //判断授权注解的类型
            Class<? extends Annotation> annotationType = annotation.annotationType();
            //如果有user注解说明需要校验用户是否认证通过
            if (annotationType.equals(User.class)) {
                handleUser();
            }
        }

    }

    @SuppressWarnings("unchecked")
    private Annotation getAnnotation(Class<?> cls, Method method) {
        //遍历所有的授权注解
        for (Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY) {
            //判断目标方法上是否带有授权注解
            if (method.isAnnotationPresent(annotationClass)) {
                return method.getAnnotation(annotationClass);
            }
            //判断目标类上是否带有授权注解
            if (cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        //如果目标方法与目标类都没有带有授权注解，则返回空
        return null;
    }

    private void handleUser() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()) {
            throw new AuthorizationException("当前用户尚未登陆");
        }
    }
}
