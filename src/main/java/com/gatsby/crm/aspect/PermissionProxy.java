package com.gatsby.crm.aspect;

import com.gatsby.crm.annotation.RequiredPermission;
import com.gatsby.crm.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @PACKAGE_NAME: com.gatsby.crm.aspect
 * @NAME: PermissionProxy
 * @AUTHOR: Jonah
 * @DATE: 2023/6/10
 */
@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;

    @Around(value = "@annotation(com.gatsby.crm.annotation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        List<String> permissions = (List<String>) session.getAttribute("permissions");

        if (null == permissions || permissions.size() < 1) {
            throw new AuthException();
        }

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        RequiredPermission requiredPermission = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);

        if (!(permissions.contains(requiredPermission.code()))) {
            throw new AuthException();
        }

        result = pjp.proceed();
        return result;
    }
}
