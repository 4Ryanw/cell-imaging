package narnew.cellimagingsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import narnew.cellimagingsystem.mapper.HistoryMapper;
import narnew.cellimagingsystem.mapper.ImageMapper;
import org.springframework.stereotype.Service;

/**
 * 历史服务
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Service
public class HistoryService extends ServiceImpl<HistoryMapper, ImageProcessingHistory> {
}
