package narnew.cellimagingsystem.aspect;

import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建数据时设置RequestBody的项目信息
 *
 * @author wdh
 * @since 2023/2/10
 */
@Aspect
@Component
public class AuthCheckAspect {

    @Autowired
    UserService userService;
    @Pointcut("@annotation(narnew.cellimagingsystem.annotation.AuthCheck)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        //获取方法的参数名和参数值

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        //鉴权
        UserInfoDto loginUser = userService.getLoginUser(request);
        if (!loginUser.getRole()){
            throw new CoreException(ErrorCodeEnum.NOT_AUTH_OPTION);
        }

    }
}
