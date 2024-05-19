package narnew.cellimagingsystem.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.entity.ImageProcessingHistory;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
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

//    private final static String TYPE_SPLIT = "细胞分割";
//    private final static String TYPE_CHECK = "细胞检测";
    public void addRecord(String[] imgIds,String userId,int type) {
        String beforeImageId = imgIds[0];
        String afterImageId = imgIds[1];
        //添加历史记录
        ImageProcessingHistory imageProcessingHistory = new ImageProcessingHistory();
        imageProcessingHistory.setImageIdBefore(beforeImageId);
        imageProcessingHistory.setImageIdAfter(afterImageId);
        imageProcessingHistory.setUserId(userId);
        imageProcessingHistory.setType(type);
        save(imageProcessingHistory);
    }

    public void updateNote(String historyId, String note) {
        ImageProcessingHistory history = getById(historyId);
        if (ObjectUtil.isNull(history)){
            throw new CoreException(ErrorCodeEnum.INVALID_PARAMETER);
        }
        LambdaUpdateWrapper<ImageProcessingHistory> luw = new LambdaUpdateWrapper<>();
//        LambdaUpdateWrapper<ImageProcessingHistory> luw = new LambdaUpdateWrapper();
        luw.set(ImageProcessingHistory::getNote,note)
                .eq(ImageProcessingHistory::getId,historyId);
        update(luw);
    }
}
