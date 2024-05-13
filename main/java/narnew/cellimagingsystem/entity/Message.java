package narnew.cellimagingsystem.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 消息回复表
 */
@Data
@TableName(value = "MESSAGE",autoResultMap = true)
public class Message  {
    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonProperty("date")
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
   //用户名
   @JsonProperty("username")
  private String userName;

//   //日期
//  private LocalDateTime date;

   //内容
  private String content;

   //回复对象
   @JsonProperty("to_username")
  private String toUsername;

   //上级id
  private Long parentId;

    @TableField(exist = false)
    private List<Message> sons;

}
