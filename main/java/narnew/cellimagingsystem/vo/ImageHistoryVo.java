package narnew.cellimagingsystem.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * 图像历史视图类
 * @author narnew
 * @since 2024年05月12日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageHistoryVo {
    @JsonFormat(pattern ="yyyy-MM-dd")
    private Date date;
    private String fileId_before;
    private String fileId_after;

}
