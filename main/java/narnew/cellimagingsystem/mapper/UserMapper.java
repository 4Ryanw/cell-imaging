package narnew.cellimagingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import narnew.cellimagingsystem.entity.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfoDto> {

//    UserInfoDto getUserByUserName(String userName);
}
