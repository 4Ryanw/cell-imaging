package narnew.cellimagingsystem.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.mapper.ImageMapper;
import org.springframework.stereotype.Service;

/**
 * 图片服务
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Service
public class ImageService extends ServiceImpl<ImageMapper, CellImage> {
}
