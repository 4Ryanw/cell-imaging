package narnew.cellimagingsystem.utils;

import cn.hutool.core.io.FileUtil;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
public class ImageUtil {

    /**
     * 获取文件类型
     * 暂时直接采取后缀名作为文件类型
     *
     * @param file 文件
     * @return 以文件后缀名作为文件类型
     */
    public static String getFileType(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        assert originalFilename != null;
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    /**
     * 服务器本地临时保存MultipartFile
     *
     * @param file 待保存的流媒体文件
     * @return 保存路径
     */
    public static String saveTemp(MultipartFile file, String suffixName) {
        //上传文件
        if (file == null || file.isEmpty()) {
            throw new CoreException(ErrorCodeEnum.FILE_NOT_EMPTY);
        }
        File dir = new File("./file/");
        File localFile = FileUtil.createTempFile("cell_", suffixName, dir,true);
        try {
            //把上传的文件保存至本地
            file.transferTo(localFile);
            return localFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
//            LOG.error("本地临时保存", e);
            throw new CoreException(ErrorCodeEnum.SYS_ERROR, "文件保存失败");
        }
    }
}
