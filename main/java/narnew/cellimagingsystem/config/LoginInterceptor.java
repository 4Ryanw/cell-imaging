package narnew.cellimagingsystem.config;


import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Value("${jwtKey}")
    private String JWT_KEY;
    private final static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        调试接口时放开
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            throw new CoreException(ErrorCodeEnum.USER_NOT_LOGIN);
//            writeResponse(response, 401, "未登录");
//            return false;
        }
        //token校验
        if (!JWTUtil.verify(token,JWT_KEY.getBytes())) {
//            writeResponse(response, 401, "未登录");
            throw new CoreException(ErrorCodeEnum.INVALID_TOKEN);

        }
//        request.setAttribute("sTime", System.currentTimeMillis());
        return super.preHandle(request, response, handler);
    }

    private void writeResponse(HttpServletResponse response, int httpCode, String msg) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            Response<String> rp = Response.with(msg);
            response.getWriter().write(JSONUtil.toJsonStr(rp));
        } catch (Exception e) {
            log.error("writeResponse 异常:" + e);
        }
    }

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (Cookie cookie : cookies) {
            if ("Authorization".equals(cookie.getName())) {
                 token =  cookie.getValue();
            }
        }
     return token;
    }

}
