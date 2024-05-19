package narnew.cellimagingsystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import narnew.cellimagingsystem.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户信息表
 */
@Data
@TableName(value = "USER_INFO",autoResultMap = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto extends BaseEntity {

  //用户名
  private String userName;

  //密码
  private String passwordHash;

  //邮箱
  private String email;

  //角色 1=管理员 0=普通用户
  private Boolean role;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserInfoDto)) {
      return false;
    }
    UserInfoDto user = (UserInfoDto) o;
    return Objects.equals(userName, user.userName) && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), userName, email);
  }

}
