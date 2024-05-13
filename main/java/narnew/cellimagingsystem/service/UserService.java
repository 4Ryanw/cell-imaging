package narnew.cellimagingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.mapper.UserMapper;
import narnew.cellimagingsystem.entity.UserInfoDto;
import narnew.cellimagingsystem.utils.SHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 新增用户
     * @param account  用户信息
     */
    public void addAccount(UserInfoDto account) {
        UserInfoDto exist = getUserByUserName(account.getUserName());
        if (exist != null) {
            throw new CoreException(ErrorCodeEnum.SERVER_EXCEPTION, "账号名称已存在");
        }
        account.setPasswordHash(SHA.encrypt(account.getPasswordHash()));
        userMapper.insert(account);
    }

}
