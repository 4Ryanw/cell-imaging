package narnew.cellimagingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Repository
public interface HistoryMapper extends BaseMapper<ImageProcessingHistory> {
}
