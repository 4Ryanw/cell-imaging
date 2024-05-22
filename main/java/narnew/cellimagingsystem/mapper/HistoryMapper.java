package narnew.cellimagingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import narnew.cellimagingsystem.vo.ImageHistoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Repository
public interface HistoryMapper extends BaseMapper<ImageProcessingHistory> {
    List<ImageHistoryVo> listHistory(@Param("role") Boolean role,
                                     @Param("userId") String userId,
                                     @Param("type")Integer type);

}
