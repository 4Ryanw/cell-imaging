package narnew.cellimagingsystem.service;

import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice(basePackages = "narnew.cellimagingsystem")
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CoreException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    Response handleBadRequestException(CoreException ex, HandlerMethod handlerMethod) {
        LOG.error("CoreException:", ex);
        LOG.error("请求参数错误 controller出错，类名：{} 方法名：{}", handlerMethod.getMethod().getDeclaringClass().getName(),
                handlerMethod.getMethod().getName());

        return Response.with(ex.getCodeEnum(), null, ex.getMessage(), null);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Response handleGlobalException(RuntimeException ex, HandlerMethod handlerMethod) {
        LOG.error("RuntimeException:", ex);
        LOG.error("请求参数错误 controller出错，类名：{} 方法名：{}", handlerMethod.getMethod().getDeclaringClass().getName(),
                handlerMethod.getMethod().getName());

        return Response.with(ErrorCodeEnum.SYS_ERROR, ex.getMessage());
    }

}
