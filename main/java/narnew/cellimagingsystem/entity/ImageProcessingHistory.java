package narnew.cellimagingsystem.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import narnew.cellimagingsystem.base.BaseEntity;

import java.time.LocalDateTime;
import java.time.LocalDate;
/**
 * 细胞图像处理历史记录表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "IMAGE_PROCESSING_HISTORY",autoResultMap = true)
public class ImageProcessingHistory extends BaseEntity {

   //关联用户id
  private String userId;

   //转换前图片id
  private String imageIdBefore;

   //转换后图片id
  private String imageIdAfter;

  //处理类型
  private Integer type;

  //备注
  private String note;


}
