package narnew.cellimagingsystem.controller;

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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

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

    /**
     * 用户登录验证
     * @return userInfo
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Response<UserInfoDto> login(
            HttpServletRequest request,
            HttpServletResponse response, @RequestBody LoginUserDto user
            ) {
        UserInfoDto account = userService.getUserByUserName(user.getUserName());
        if (StringUtils.isEmpty(account)) {
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS, "用户不存在");
        }
        String passwordTrue = account.getPasswordHash();
        if (StringUtils.isEmpty(account)) {
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS, "用户不存在");
        } else if (!passwordTrue.equals(SHA.encrypt(user.getPassword()))) {
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS, "密码错误");
        } else {
            String token = UUID.randomUUID().toString();
            request.getSession().setAttribute("token", token);
            request.setAttribute("token", token);
            account.setPasswordHash("");
            return Response.with(account);
        }
    }


    @ApiOperation("用户新增")
    @PostMapping()
    public Response add(@RequestBody UserInfoDto account){
        userService.addAccount(account);
        account.setRole(false);
        return Response.with();
    }

    @ApiOperation("用户删除")
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id){
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
//        if (query.getUsePage() != null && query.getUsePage()) {
//            records = page(page, lambdaQueryWrapper).getRecords();
//        } else
        ;
//        records = userService.list (lambdaQueryWrapper);
//            page.setTotal(records.size());
//        page.setRecords(records);
        return Response.with( userService.page(page, lambdaQueryWrapper));
    }
}
