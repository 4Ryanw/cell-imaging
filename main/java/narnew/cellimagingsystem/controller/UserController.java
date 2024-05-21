package narnew.cellimagingsystem.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.base.BasePageQuery;
import narnew.cellimagingsystem.base.Response;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.service.UserService;
import narnew.cellimagingsystem.utils.SHA;
import narnew.cellimagingsystem.vo.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页面Controller
 *
 * @author narnew
 * @since 2024年05月12日
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${jwtKey}")
    private String JWT_KEY;


    /**
     * 用户登录验证
     * @return userInfo
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Response<UserInfoDto> login(
            HttpServletResponse res,
            @RequestBody LoginUserDto user
            ) {
        UserInfoDto account;
        //用户名登录
        if (!StringUtils.isEmpty(user.getUserName())){
            account = userService.getUserByUserName(user.getUserName());
        } else {
            //邮箱登录
            account = userService.getUserByEmail(user.getEmail());
        }

        if (ObjectUtil.isNull(account)) {
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS, "用户不存在");
        }

        String passwordTrue = account.getPasswordHash();
        if (!passwordTrue.equals(SHA.encrypt(user.getPassword()))) {
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS, "密码错误");
        } else {
            //签发token
            account.setPasswordHash(null);
            Map<String, Object> map = new HashMap<>();
            //过期时间 1小时
            map.put("uid",account.getId());
            map.put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 );
            String token = JWTUtil.createToken(map, JWT_KEY.getBytes());
            res.setHeader("Set-Cookie", "Authorization=" + token + "; Max-Age=86400; Path=/; ");
            //Bearer
            account.setPasswordHash("");
            return Response.with(account);
        }
    }

    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public Response<String> logout(HttpServletResponse res){
        // 撤销令牌
        Cookie cookie = new Cookie("Authorization", null);
        // 设置有效期为 0，使 Cookie 失效
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
        return Response.with("注销成功");
    }

    @ApiOperation("用户新增")
    @PostMapping()
    public Response<String> add(@RequestBody UserInfoDto account){
        account.setRole(false);
        userService.addAccount(account);
        return Response.with();
    }

    @ApiOperation("用户修改")
    @PutMapping()
    public Response<String> update(@RequestBody UserInfoDto account){
        userService.updateUser(account);
        return Response.with();
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping()
    public Response<UserInfoDto> getUserInfo(HttpServletRequest request){
        UserInfoDto loginUser = userService.getLoginUser(request);
        return Response.with(loginUser);
    }

    @ApiOperation("用户删除")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable String id,HttpServletRequest request){
        //鉴权
        UserInfoDto loginUser = userService.getLoginUser(request);
        if (!loginUser.getRole()) {
            throw new CoreException(ErrorCodeEnum.NOT_AUTH_OPTION);
        }
        userService.removeById(id);
        return Response.with();
    }

    @ApiOperation("用户列表")
    @PostMapping("/page")
    public Response<Page<UserInfoDto>> userList(@RequestBody BasePageQuery query) {
        Page<UserInfoDto> page = query.toPage();
        LambdaQueryWrapper<UserInfoDto> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfoDto::getRole,false);
        lambdaQueryWrapper.orderByDesc(UserInfoDto::getCreatedTime);
        return Response.with( userService.page(page, lambdaQueryWrapper));
    }
}
