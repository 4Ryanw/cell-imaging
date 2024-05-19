package narnew.cellimagingsystem.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import narnew.cellimagingsystem.base.BaseEntity;

/**
 * 细胞图像
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "CELL_IMAGES",autoResultMap = true)
public class CellImage extends BaseEntity {

   //关联用户id
  private String userId;

   //文件路径
  private String filePath;


}
