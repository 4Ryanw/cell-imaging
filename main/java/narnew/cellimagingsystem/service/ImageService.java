package narnew.cellimagingsystem.service;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import narnew.cellimagingsystem.CoreException;
import narnew.cellimagingsystem.entity.CellImage;
import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.mapper.ImageMapper;
import narnew.cellimagingsystem.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片服务
 *
 * @author narnew
 * @since 2024年05月12日
 */
@Service
public class ImageService extends ServiceImpl<ImageMapper, CellImage> {

    @Value("${pythonScript2}")
    private String PY_PATH_SPLIT;
    @Value("${pythonScript}")
    private String PY_PATH_CHECK;





    private File usePython(String fileName,String path,int type) {

        String property = System.getProperty("user.dir");
        String newFilePath = property+"/file/"+fileName+"_after";
        // 设置Python脚本及参数
        List<String> command = new ArrayList<>();
        command.add("python3");
        switch (type){
            case 1:command.add(PY_PATH_SPLIT);break;
            case 2:command.add(PY_PATH_CHECK);break;
            default:
                throw new CoreException(ErrorCodeEnum.UNSUPPORT);
        }
        command.add(path);
        command.add(newFilePath);
        try {
            // 执行Python脚本
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // 处理Python脚本的输出流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                log.debug(line);
            }
            // 等待子进程执行完毕
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

            // 继续执行后续代码
            System.out.println("Continue executing Java code...");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //读取新的图片
        return new File(newFilePath+".jpg");
    }

    /**
     * 预览文件
     * @param id 文件id
     * @param response 响应
     */
    public void preview(String id, HttpServletResponse response) {
        //查询文件
        CellImage byId = getById(id);
        //写入响应流
        try {
            InputStream is = Files.newInputStream(new File(byId.getFilePath()).toPath());
            IoUtil.copy(is, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取失败", e);
        }
    }


    /**
     * 文件处理
     * @param multipartFile 文件
     * @param userId 用户id
     */
    public String[] deal(MultipartFile multipartFile, String userId,int type) {
        //保存处理前的图片
        CellImage beforeImage = new CellImage();
        File beforeFile = new File(ImageUtil.saveTemp(multipartFile, ImageUtil.getFileType(multipartFile)));

        beforeImage.setUserId(userId);
        beforeImage.setFilePath(beforeFile.getPath());
        save(beforeImage);
        //读取python代码生成图片
        File resFile = usePython(beforeFile.getName().split("\\.")[0],beforeFile.getPath(),type);
        //生成后的图片保存入库
        CellImage afterImage = new CellImage();
        afterImage.setUserId(userId);
        afterImage.setFilePath(resFile.getPath());
        save(afterImage);
        return new String[]{beforeImage.getId(),afterImage.getId()};

    }
}
