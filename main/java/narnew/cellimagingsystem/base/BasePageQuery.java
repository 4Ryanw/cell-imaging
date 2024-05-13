package narnew.cellimagingsystem.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 分页查询条件基础类
 *
 * @author narnew
 */
@Data
public class BasePageQuery  {
    @ApiModelProperty("当前页")
    private Integer pageNum;
    @ApiModelProperty("每页大小")
    private Integer pageSize;
    @ApiModelProperty("使用分页")
    private Boolean usePage = true;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public <T> Page<T> toPage() {
        int pageNum = getPageNum()==null?1:getPageNum();
        int pageSize = getPageSize()==null?10:getPageSize();
        return Page.of(pageNum, pageSize);
    }
}
