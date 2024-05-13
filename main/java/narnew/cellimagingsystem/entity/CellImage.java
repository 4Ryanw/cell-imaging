package narnew.cellimagingsystem.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import narnew.cellimagingsystem.base.BaseEntity;

/**
 * 细胞图像
 */
@Data
@TableName(value = "CELL_IMAGES",autoResultMap = true)
public class CellImage extends BaseEntity {

   //关联用户id
  private String userId;

   //base64转码内容
  private String content;


}
