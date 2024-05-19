package narnew.cellimagingsystem.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.config.LoginInterceptor;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.mapper.UserMapper;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.utils.SHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 主页service
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserInfoDto> {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    public UserInfoDto getUserByUserName(String userName){
        LambdaQueryWrapper<UserInfoDto> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInfoDto::getUserName, userName);
        return userMapper.selectOne(lqw);
    }

    /**
     * 根据邮箱查询用户信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    public UserInfoDto getUserByEmail(String email){
        LambdaQueryWrapper<UserInfoDto> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserInfoDto::getUserName, email);
        return getOne(lqw);
    }

    /**
     * 新增用户
     * @param account  用户信息
     */
    public void addAccount(UserInfoDto account) {
        userNameCheck(account);
        userEmailCheck(account);
        account.setPasswordHash(SHA.encrypt(account.getPasswordHash()));
        userMapper.insert(account);
    }

    public void userNameCheck(UserInfoDto account){
        UserInfoDto checkUserName = getUserByUserName(account.getUserName());
        if (checkUserName != null) {
            throw new CoreException(ErrorCodeEnum.SERVER_EXCEPTION, "账号名称已存在");
        }
    }

    public void userEmailCheck(UserInfoDto account){
        if (!StringUtils.isEmpty(account.getEmail())){
            UserInfoDto checkEmail = getUserByEmail(account.getEmail());
            if (checkEmail != null) {
                throw new CoreException(ErrorCodeEnum.SERVER_EXCEPTION, "该邮箱已被注册");
            }
        }
    }

    /**
     * 获取当前登录用户信息
     * @param request 请求
     * @return 用户信息
     */
    public  UserInfoDto getLoginUser(HttpServletRequest request){
        String token = LoginInterceptor.getToken(request);
        //token解析
        final JWT jwt = JWTUtil.parseToken(token);
        //获取payload
//        JSONObject payloads = jwt.getPayloads();
//        JSONUtil.toBean(payloads, UserInfoDto.class);
        String uid = jwt.getPayload("uid").toString();
        UserInfoDto userInfo = getById(uid);
        if (ObjectUtil.isNull(userInfo)) {
            throw new CoreException(ErrorCodeEnum.INVALID_TOKEN,"TOKEN异常");
        }
        return userInfo;
    }

    public void updateUser(UserInfoDto account) {
        LambdaUpdateWrapper<UserInfoDto> luw = new LambdaUpdateWrapper<>();
        UserInfoDto old = getById(account.getId());
        if (ObjectUtil.isNull(old)) {
            //用户不存在
            throw new CoreException(ErrorCodeEnum.NOT_EXISTS);
        }
        luw.eq(UserInfoDto::getId,account.getId());

        //修改用户名
        if (!StringUtils.isEmpty(account.getUserName())) {
           if (!account.getUserName().equals(old.getUserName())){
               userNameCheck(account);
               luw.set(UserInfoDto::getUserName,account.getUserName());
           }
        }
        if (!StringUtils.isEmpty(account.getEmail())) {
            if (!account.getEmail().equals(old.getEmail())) {
                userEmailCheck(account);
                luw.set(UserInfoDto::getEmail,account.getEmail());
            }
        }
        if (!StringUtils.isEmpty(account.getPasswordHash())) {
            String newPasswordHash = SHA.encrypt(account.getPasswordHash());
            luw.set(UserInfoDto::getPasswordHash,newPasswordHash);
        }
        update(luw);
    }
}
